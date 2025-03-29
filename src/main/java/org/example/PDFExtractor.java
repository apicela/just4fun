package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFExtractor {
        private static List<List<String>> columnsData = new ArrayList<>();
        private ObjectExtractor oe;
        private SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();

        public PDFExtractor(String path) throws IOException {
            this.oe = new ObjectExtractor(PDDocument.load(new File(path)));
        }

        void init() throws IOException {
            PageIterator pageIterator = oe.extract();
            // Process each page
            while (pageIterator.hasNext()) {
                Page page = pageIterator.next();
                int pageNumber = page.getPageNumber();

                // Only process pages 2-4 (assuming tables are there)
                if (pageNumber >= 2 && pageNumber <= 4) {
                    System.out.println("\nProcessing page " + pageNumber);

                    // Extract entire page or specify area if needed
                    List<Table> tables = sea.extract(page);

                    for (Table table : tables) {
                        extractTableRow(table);
                    }
                }
            }
            oe.close();
        }

        void printColumn(int column){
            var list = columnsData.get(column);
            for(String s : list){
                System.out.println(s);
            }
        }

    private static void extractTableRow(Table table) {
        for (List<RectangularTextContainer> row : table.getRows()) {
            for (int i = 0; i < row.size(); i++) {
                String text = row.get(i).getText()
                        .replace("\r", " ")
                        .replace("\n", " ")
                        .trim();
                System.out.println(i+ ": " + text);
                // Se a coluna ainda não foi criada, cria uma nova lista para ela
                if (columnsData.size() <= i) {
                    columnsData.add(new ArrayList<>());
                }

                // Adiciona o texto à coluna correspondente
                columnsData.get(i).add(text);
            }
            System.out.println("----------------------------------------");
        }
    }
}