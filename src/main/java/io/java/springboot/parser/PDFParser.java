package io.java.springboot.parser;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;

@Component
public class PDFParser {

    public String toText(MultipartFile file) throws Exception{
        //File file = new File("C:/PdfBox_Examples/new.pdf");
        return toText(file.getInputStream());
    }

    public String toText(File file) throws Exception{
        //File file = new File("C:/PdfBox_Examples/new.pdf");
        PDDocument document = PDDocument.load(file);
        //Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();

        //Retrieving text from PDF document
        String text = pdfStripper.getText(document);
        //System.out.println(text);

        //Closing the document
        document.close();
        return text;

    }

    public String toText(InputStream stream) throws Exception{
        //File file = new File("C:/PdfBox_Examples/new.pdf");
        PDDocument document = PDDocument.load(stream);
        //Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();

        //Retrieving text from PDF document
        String text = pdfStripper.getText(document);
        //System.out.println(text);

        //Closing the document
        document.close();
        return text;

    }

    public String toText() throws Exception{
        //File file = new File("C:/PdfBox_Examples/new.pdf");
        File file = new File("C:\\Users\\nsankabathula\\Downloads\\agreement_data\\test.pdf");
        PDDocument document = PDDocument.load(file);
        //Instantiate PDFTextStripper class
        PDFTextStripper pdfStripper = new PDFTextStripper();

        //Retrieving text from PDF document
        String text = pdfStripper.getText(document);
        //System.out.println(text);

        //Closing the document
        document.close();
        return text;

    }
}
