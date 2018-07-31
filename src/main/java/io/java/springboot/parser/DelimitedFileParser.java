package io.java.springboot.parser;

import io.java.springboot.camel.ICamelProcessor;
import io.java.springboot.config.ClientConfig;
import io.java.springboot.config.ClientConfigColumn;
import io.java.springboot.config.ConfigConstants;
import net.sf.flatpack.DataSet;
import net.sf.flatpack.DefaultParserFactory;
import net.sf.flatpack.Parser;
import net.sf.flatpack.brparse.BuffReaderDelimParser;
import net.sf.flatpack.brparse.BuffReaderParseFactory;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVStrategy;
import org.apache.commons.csv.writer.CSVConfig;
import org.apache.commons.csv.writer.CSVField;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class DelimitedFileParser implements IParser {

/*
    @Override
    public void process(Exchange exchange) throws Exception {

        ClientConfig clientConfig = (ClientConfig) exchange.getIn().getHeader(ConfigConstants.CAMEL_HEADERS.FILE_CONFIG);

        CsvDataFormat csvFileConfig = new CsvDataFormat();
        CSVConfig config = new CSVConfig();
        config.setDelimiter(clientConfig.getDelimiter().charAt(0));
        CSVStrategy strategy =csvFileConfig.getStrategy();
        strategy.setEscape('"');

        List<CSVField> csvFields = new ArrayList<>();
        clientConfig.getClientConfigColumns().forEach(clientConfigColumn -> {
            CSVField field = new CSVField();
            field.setName(clientConfigColumn.getColumnName());

        });
        config.setFields(csvFields);
        csvFileConfig.setConfig(config);

        exchange.getIn().setHeader(ConfigConstants.CAMEL_HEADERS.CSV_FILE_CONFIG, csvFileConfig);
    }
*/
    @Override
    public List<Map<String, Object>> parse(String filePath, ClientConfig clientConfig) throws Exception {


        List<Map<String, Object>> fileData = new ArrayList<>();
        final BuffReaderDelimParser pzparser = (BuffReaderDelimParser)  BuffReaderParseFactory.getInstance().newDelimitedParser(new FileReader(clientConfig.getConfigFileName()), new FileReader(filePath), clientConfig.getDelimiter().charAt(0),'"',false);

        pzparser.setIgnoreExtraColumns(true);
        pzparser.setFlagEmptyRows(true);


        final DataSet ds = pzparser.parse();

        //final String[] colNames = ds.getColumns();

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
