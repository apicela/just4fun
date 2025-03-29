package org.example;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.zip.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.util.concurrent.*;


public class WebScraper {
    private static final String URL = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";
    private static final String OUTPUT_DIR = "downloads";
    private static final String ZIP_FILE = "anexos.zip";

    public static void main(String[] args) {
        try {
            Files.createDirectories(Paths.get(OUTPUT_DIR));

            // Criar um ExecutorService para baixar arquivos em paralelo
            ExecutorService executor = Executors.newFixedThreadPool(2);

            // Conectar ao site e obter os links dos arquivos
            Document doc = Jsoup.connect(URL)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36")
                    .get();

            Elements links = doc.select("a[href]");

            for (Element link : links) {
                String fileUrl = link.absUrl("href");
                if (!fileUrl.contains("pdf")) continue;

                if (fileUrl.toLowerCase().contains("anexo_i") || fileUrl.toLowerCase().contains("anexo_ii")) {
                    executor.submit(() -> downloadFile(fileUrl)); // Executa em paralelo
                }
            }

            executor.shutdown();
            executor.awaitTermination(10, TimeUnit.MINUTES);

            zipFiles();
            System.out.println("Download e compactação concluídos!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void downloadFile(String fileUrl) {
        System.out.println("DOWNLOADING... " + fileUrl);
        try {
            URL url = new URL(fileUrl);
            String fileName = fileUrl.toLowerCase().contains("anexo_ii") ? "Anexo 2.pdf" : "Anexo 1.pdf";
            Path outputPath = Paths.get(OUTPUT_DIR, fileName);

            try (InputStream in = url.openStream()) {
                Files.copy(in, outputPath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Baixado: " + fileName);
            }
        } catch (IOException e) {
            System.err.println("Erro ao baixar: " + fileUrl);
            e.printStackTrace();
        }
    }

    private static void zipFiles() throws IOException {
        try (FileOutputStream fos = new FileOutputStream(ZIP_FILE);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            Files.list(Paths.get(OUTPUT_DIR)).forEach(filePath -> {
                try {
                    zos.putNextEntry(new ZipEntry(filePath.getFileName().toString()));
                    Files.copy(filePath, zos);
                    zos.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
