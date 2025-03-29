package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PDFExtractor {
    private List<List<String>> tableData = new ArrayList<>();
        private ObjectExtractor oe;
        private SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();

        public PDFExtractor(String path) throws IOException {
            this.oe = new ObjectExtractor(PDDocument.load(new File(path)));
        }

        void init() throws IOException {
            PageIterator pageIterator = oe.extract();
            while (pageIterator.hasNext()) {
                Page page = pageIterator.next();
                int pageNumber = page.getPageNumber();

                if (pageNumber >= 2 && pageNumber <= 4) {
                    System.out.println("\nProcessing page " + pageNumber);

                    List<Table> tables = sea.extract(page);

                    for (Table table : tables) {
                        extractTableRow(table);
                    }
                }
            }
            oe.close();
            saveToCSV("extracted_data.csv");
            zipFile("extracted_data.csv", "Teste_Jamil.zip");
        }


    private void extractTableRow(Table table) {
        for (List<RectangularTextContainer> row : table.getRows()) {
            List<String> rowData = new ArrayList<>();
            for (int i = 0; i < row.size(); i++) {
                String text = row.get(i).getText()
                        .replace("\r", " ")
                        .replace("\n", " ")
                        .trim();
                rowData.add(text);
            }
            tableData.add(rowData);
            System.out.println(rowData);
            System.out.println("----------------------------------------");
        }
    }

    private void saveToCSV(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (List<String> row : tableData) {
                writer.write(String.join(";", row));
                writer.newLine();
            }
            System.out.println("Data saved to CSV: " + fileName);
        }
    }

    private void zipFile(String fileName, String zipFileName) throws IOException {
        try (FileInputStream fis = new FileInputStream(fileName);
             FileOutputStream fos = new FileOutputStream(zipFileName);
             ZipOutputStream zipOut = new ZipOutputStream(fos)) {

            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) >= 0) {
                zipOut.write(buffer, 0, length);
            }
            zipOut.closeEntry();
            System.out.println("CSV file zipped: " + zipFileName);
        }
    }
}