package io.java.springboot.parser;

import io.java.springboot.config.ClientConfig;
import io.java.springboot.config.ClientConfigColumn;
import io.java.springboot.config.ConfigConstants;
import net.sf.flatpack.DataSet;
import net.sf.flatpack.DefaultParserFactory;
import net.sf.flatpack.Parser;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import org.apache.camel.component.flatpack.FlatpackDataFormat;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class FixedLengthParser implements Processor, IParser{

    @Override
    public void process(Exchange exchange) throws Exception {
        //System.out.println("in FixedLengthParser " );
        //System.out.println(exchange.getIn().getBody()) ;
        //System.out.println(exchange.getIn().getHeaders()) ;

        ClientConfig clientConfig = (ClientConfig)exchange.getIn().getBody();
        File fileMapping = new File(clientConfig.getConfigFileName());
        exchange.getIn().setBody(parse( exchange.getIn().getHeader(ConfigConstants.CAMEL_HEADERS.FILE_ABSLOUTE_PATH).toString(), clientConfig));


        //FlatpackDataFormat fp = new FlatpackDataFormat();
        //fp.setDefinition();
        /*
        System.out.println(new ClassPathResource("fixed-length.pzmap.xml").getFile() + "{}" +  exchange.getIn().getHeader("CamelFileAbsolutePath").toString());
        String fileName = exchange.getIn().getHeader("CamelFileNameOnly").toString();
        ClientConfig config = ClientConfig.filterByStartsWith(ClientConfig.getDefaultConfig(), fileName) ;

        System.out.println(fileMapping.getPath());

        call( fileMapping, exchange.getIn().getHeader("CamelFileAbsolutePath").toString());
        */
        /*

        System.out.println(exchange.getIn().getHeaders()); //CamelFileAbsolutePath
        String myString = exchange.getIn().getBody(String.class);
        //System.out.println(" data " + myString);
        ClientConfig config = ClientConfig.filterByStartsWith(ClientConfig.getDefaultConfig(), fileName) ;
        //System.out.println(config);
        exchange.getIn().setBody(myString);
        */
    }

    public List<Map<String, Object>> parse(final String filePath, ClientConfig clientConfig) throws Exception {
        List<Map<String, Object>> fileData = new ArrayList<>();
        final Parser pzparser = DefaultParserFactory.getInstance().newFixedLengthParser(new FileReader(clientConfig.getConfigFileName()), new FileReader(filePath));

        pzparser.setIgnoreExtraColumns(true);

        final DataSet ds = pzparser.parse();

        final String[] colNames = ds.getColumns();

        Map<String, ClientConfigColumn> clientConfigColumnMap = clientConfig.getClientConfigColumnsMap();

        while (ds.next()) {
            Map<String, Object> row = new HashMap();
            clientConfigColumnMap.forEach((columnName,clientConfigColumn)->{
                if(clientConfigColumn.expectedFromSource())
                    row.put(columnName, ds.getString(columnName));
                else if(clientConfigColumn.getUseDefault()){
                    row.put(columnName, clientConfigColumn.getValue());
                }
                else if(clientConfigColumn.getMetaFlag()){
                    row.put(columnName, "meta");
                }
                else
                    row.put(columnName, null);

            });
            fileData.add(row);
        }
        return fileData;
    }
}
