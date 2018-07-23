package io.java.springboot.parser;

import io.java.springboot.config.ClientConfig;
import net.sf.flatpack.DataSet;
import net.sf.flatpack.DefaultParserFactory;
import net.sf.flatpack.Parser;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileReader;

public class FlatPackParser implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        System.out.println(new ClassPathResource("fixed-length.pzmap.xml").getFile() + "{}" +  exchange.getIn().getHeader("CamelFileAbsolutePath").toString());
        String fileName = exchange.getIn().getHeader("CamelFileNameOnly").toString();
        ClientConfig config = ClientConfig.filterByStartsWith(ClientConfig.getDefaultConfig(), fileName) ;

        //call( new ClassPathResource("fixed-length.pzmap.xml").getFile(), exchange.getIn().getHeader("CamelFileAbsolutePath").toString());
        /*

        System.out.println(exchange.getIn().getHeaders()); //CamelFileAbsolutePath
        String myString = exchange.getIn().getBody(String.class);
        //System.out.println(" data " + myString);
        ClientConfig config = ClientConfig.filterByStartsWith(ClientConfig.getDefaultConfig(), fileName) ;
        //System.out.println(config);
        exchange.getIn().setBody(myString);
        */
    }

    public static void call(final File mapping, final String data) throws Exception {
        final Parser pzparser = DefaultParserFactory.getInstance().newFixedLengthParser(new FileReader(mapping), new FileReader(data));
        pzparser.setIgnoreExtraColumns(true);

        final DataSet ds = pzparser.parse();

        final String[] colNames = ds.getColumns();

        while (ds.next()) {
            for (final String colName : colNames) {
                System.out.println("COLUMN NAME: " + colName + " VALUE: " + ds.getString(colName));
            }

            System.out.println("===========================================================================");
        }

    }
}
