package org.example;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Olá.\nEstamos iniciando o procedimento do teste 1, e posteriormente, iremos iniciar o teste 2, transformando os dados provindos do teste 1.");
        var scrapping = new WebScraper();
        scrapping.init();
        scrapping.awaitCompletion();

        System.out.println("Ambos arquivos baixados. Iremos iniciar o procedimento do teste 2.");
        String filePath = "downloads/Anexo 1.pdf";
        var extractor = new PDFExtractor(filePath);
        extractor.init();
    }

}