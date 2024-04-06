package com.example.docgeneratorspring.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.example.docgeneratorspring.entity.Car;
import com.example.docgeneratorspring.entity.Client;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;

@Component
public class WordTextReplacer {

    public void generateDoc(Client client, String docNumber) {

        String filePath = "src/main/resources/static/doc.docx";
        Map<String, String> replacements = new HashMap<>();
        replacements.put("б/н",docNumber);
        replacements.put("qaab", generateCurrentDate());
        replacements.put("qaac", client.getClSurname());
        replacements.put("qaad", client.getClName());
        replacements.put("qaae", client.getClLastname());
        replacements.put("qaaf", client.getClPassport());
        replacements.put("qaag", client.getClInfo());
        replacements.put("qaan", client.getClSurname() + " " + client.getClName().toCharArray()[0] + "." + " " + client.getClLastname().toCharArray()[0] + ".");

        try {
            XWPFDocument doc = new XWPFDocument(new FileInputStream(filePath));
            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                replaceTextInParagraph(paragraph, replacements);
            }

            for (XWPFTable table : doc.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            replaceTextInParagraph(paragraph, replacements);
                        }
                    }
                }
            }

            try (FileOutputStream out = new FileOutputStream("src/main/resources/static/modifiedDoc.docx")) {
                doc.write(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void generateDocAct(Client client, Car car, String docNumber, String docDate, String priceInDay, String finalPrice, String allRentDays, String dayRentStartTime, String dayRentStartDays, String dayRentEndTime, String dayRentEndDays, String address, String fuel) throws ParseException {
        String filePath = "src/main/resources/static/docAct.docx";
        Map<String, String> replacements = new HashMap<>();
        replacements.put("qaao",client.getClDateReg());
        replacements.put("qaap",client.getClAddress());
        replacements.put("qaaq",client.getClCarUd());
        String[] dt = docDate.split("-");
        String tmpDate = dt[2] + "-" +dt[1]  + "-" +  dt[0];
        replacements.put("qaar",tmpDate);
        replacements.put("qaas",docNumber);
        replacements.put("qaah",car.getCarModel());
        replacements.put("qaaj",car.getCarGovNumber());
        replacements.put("qaai", String.valueOf(car.getCarYear()));
        replacements.put("qaat", car.getCarColor());


        replacements.put("qaaw", priceInDay);
        replacements.put("qaax", finalPrice);


        replacements.put("qaal", car.getCarTex());
        replacements.put("qaay", car.getCarTex());


        replacements.put("qabb", allRentDays);
        replacements.put("qabc", dayRentStartTime);
        replacements.put("qabd", dayRentStartDays);
        replacements.put("qabe", dayRentEndTime);
        replacements.put("qabf", dayRentEndDays);

        replacements.put("qabg", address);
        replacements.put("qabh", fuel);

        replacements.put("qabi", car.getCarInfo());

        replacements.put("qaaz", car.getCarStNum());
        replacements.put("qaba", car.getCarStValidDate());
        replacements.put("qaab", generateCurrentDate());
        replacements.put("qaac", client.getClSurname());
        replacements.put("qaad", client.getClName());
        replacements.put("qaae", client.getClLastname());
        replacements.put("qaaf", client.getClPassport());
        replacements.put("qaag", client.getClInfo());
        replacements.put("qaan", client.getClSurname() + " " + client.getClName().toCharArray()[0] + "." + " " + client.getClLastname().toCharArray()[0] + ".");

        try {
            XWPFDocument doc = new XWPFDocument(new FileInputStream(filePath));
            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                replaceTextInParagraph(paragraph, replacements);
            }

            for (XWPFTable table : doc.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            replaceTextInParagraph(paragraph, replacements);
                        }
                    }
                }
            }

            try (FileOutputStream out = new FileOutputStream("src/main/resources/static/modifiedDocAct.docx")) {
                doc.write(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void replaceTextInParagraph(XWPFParagraph paragraph, Map<String, String> replacements) {
        for (int i = 0; i < paragraph.getRuns().size(); i++) {
            String text = paragraph.getRuns().get(i).getText(0);
            if (text != null) {
                for (Map.Entry<String, String> entry : replacements.entrySet()) {
                    String searchText = entry.getKey();
                    String replaceText = entry.getValue();
                    if (text.contains(searchText)) {
                        text = text.replace(searchText, replaceText);
                        paragraph.getRuns().get(i).setText(text, 0);
                    }
                }
            }
        }
    }
    private String generateCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String[] monthNames = new String[]{"января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        int month = calendar.get(Calendar.MONTH);
        String monthName = monthNames[month];
        int year = calendar.get(Calendar.YEAR);
        String res = String.valueOf(day + " " + monthName + " " + year + " г.");
        return res;
    }
}
