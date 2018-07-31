package io.java.springboot.parser;

import org.apache.camel.Exchange;
import org.apache.camel.model.DataFormatDefinition;
import org.apache.camel.spi.DataFormat;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class CSVDataFormatter extends DataFormatDefinition {

    public CSVDataFormatter() {

        super(new DataFormat() {
            @Override
            public void marshal(Exchange exchange, Object o, OutputStream outputStream) throws Exception {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                PrintWriter writer = new PrintWriter(outputStreamWriter);
                System.out.println(o);

                List<Map<String, Object>> fileData = ( List<Map<String, Object>>) o;
                fileData.forEach(stringObjectMap -> {
                    final StringBuffer  stringBuffer = new StringBuffer();
                    stringObjectMap.forEach((s, obj) -> {
                        stringBuffer.append(obj);
                        stringBuffer.append(',');
                    });
                    try {
                        //final StringBuffer  newstringBuffer  = stringBuffer.deleteCharAt(stringBuffer.length());
                        stringBuffer.delete(stringBuffer.length()-1, stringBuffer.length());
                        writer.println(stringBuffer.toString());
                    }
                    catch (Exception ex){

                    }


                });
                outputStreamWriter.flush();
                outputStreamWriter.close();
                outputStream.close();
            }

            @Override
            public Object unmarshal(Exchange exchange, InputStream inputStream) throws Exception {
                return null;
            }
        });
    }
}
