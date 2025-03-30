package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import technology.tabula.*;
import technology.tabula.extractors.SpreadsheetExtractionAlgorithm;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class PDFExtractor {
    PDDocument document;
    private String OD, AMB;
    private List<List<String>> tableData = new ArrayList<>();
    private ObjectExtractor oe;
    private SpreadsheetExtractionAlgorithm sea = new SpreadsheetExtractionAlgorithm();
    boolean hasHeader = false;
    public PDFExtractor(String path) throws IOException {
        this.document = PDDocument.load(new File(path));
        this.oe = new ObjectExtractor(document);
    }

    void init() throws IOException {
        findAbbreviations();
        extractPDFData();

    }


    void extractPDFData() throws IOException {
        for (int pageNumber = 1; pageNumber <= document.getNumberOfPages(); pageNumber++) {
                Page page = oe.extract(pageNumber);
                List<Table> tables = sea.extract(page);

                for (Table table : tables) {
                    extractTableRow(table);
                }
            }

        oe.close();
        saveToCSV("extracted_data.csv");
        zipFile("extracted_data.csv", "Teste_Jamil.zip");
    }

     void findAbbreviations() throws IOException {
        PDFTextStripper stripper = new PDFTextStripper();

        stripper.setStartPage(4);
        stripper.setEndPage(4);
        String text = stripper.getText(document);
        String[] lines = text.split("\n");
        for(int i = lines.length -1; i> 0; i--){
            if(lines[i].startsWith("OD: ")) {
                this.OD = lines[i].replace("OD: ","").trim();
            }
             else if(lines[i].startsWith("AMB: ")) {
                AMB = lines[i].replace("AMB: ","").trim();
            }
            if(OD != null && AMB != null) break;
        }
    }


    private void extractTableRow(Table table) {
        for (List<RectangularTextContainer> row : table.getRows()) {
            List<String> rowData = new ArrayList<>();
            for (int i = 0; i < row.size(); i++) {
                String text = row.get(i).getText()
                        .replace("\r", " ")
                        .replace("\n", " ")
                        .trim();

                if(hasHeader && text.equals("PROCEDIMENTO")) break;
                else if(text.equals("PROCEDIMENTO")) hasHeader = true;

                    if (i == 3 && !text.isEmpty()) {
                    text = OD;
                }
                else if (i == 4 && !text.isEmpty()){
                    text = AMB.toString();
                }
                rowData.add(text);
            }
            if(rowData.size() > 1) tableData.add(rowData);
        }
    }

    private void saveToCSV(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (List<String> row : tableData) {
                if(row.isEmpty()) continue;
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