package io.java.springboot.parser;

import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import org.jsoup.Jsoup;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.springframework.stereotype.Component;


import java.io.*;
import java.net.URL;
import java.util.UUID;

@Component
public class HtmlParser {

    public File toPdf(String url) throws Exception{
        final String  pdfFileName = "src/output/" + UUID.randomUUID().toString() + ".pdf";
        File htmlFile = toHtml(url);
        Document document = new Document();
        PdfWriter   writer = PdfWriter.getInstance(document,
                new FileOutputStream(pdfFileName));

        document.open();
        HTMLWorker htmlWorker = new HTMLWorker(document);
        htmlWorker.parse(new FileReader(htmlFile));
        /*
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(htmlFile));
                */
        document.close();
        writer.close();

        return  new File(pdfFileName);
    }



    public File toHtml(String url) throws Exception {
        final String htmlFileName = "src/output/" + UUID.randomUUID().toString() + ".htm";
        /*
        URL oracle = new URL(url);
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

        PrintWriter writer = new PrintWriter(htmlFileName, "UTF-8");

        String inputLine;

        while ((inputLine = in.readLine()) != null) {
            writer.println(inputLine);

        }
        in.close();
        writer.close();
        */
        //Jsoup.connect("http://jsoup.org").get();

        org.jsoup.nodes.Document cleanDoc  = new Cleaner(Whitelist.basic()).clean(Jsoup.connect(url).get());

        PrintWriter cleanWriter = new PrintWriter(htmlFileName, "UTF-8");
        cleanWriter.println(cleanDoc.html());
        cleanWriter.close();

        return new File(htmlFileName);
    }

}
