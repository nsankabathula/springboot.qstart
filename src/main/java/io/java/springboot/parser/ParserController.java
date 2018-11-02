package io.java.springboot.parser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@RestController
@RequestMapping("/parser")
public class ParserController {

    @Autowired
    PDFParser pdfParser;

    @Autowired
    HtmlParser htmParser;


    @PutMapping("pdf2txt")
    public ResponseEntity<?> pdfToText(@RequestParam("file") MultipartFile file){
        System.out.println(file.getOriginalFilename());
        try
        {
            String text =  pdfParser.toText(file);
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("text/plain"))
                    .body(text);
        }
        catch (Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
        }


    }

    @PutMapping("htm2pdf")
    public ResponseEntity<?> htmlToPdf(@RequestBody String url){
        try {
            File file =  htmParser.toPdf(url);
            InputStreamResource resource = new InputStreamResource(
                    new FileInputStream(file)

            );

            return ResponseEntity.ok()

                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/pdf"))

                    .body(resource);
        }
        catch(Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
        }

    }




    @PutMapping("htm2txt")
    public ResponseEntity<?> htmlToText(@RequestBody String url ){

        try {
            System.out.println(url);
            File file =  htmParser.toPdf(url);
            String text =  pdfParser.toText(file);
            System.out.println(file.getAbsolutePath());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("text/plain"))
                    .body(text);

        }
        catch(Exception ex) {
            System.out.println(ex);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex);
        }

    }
}
