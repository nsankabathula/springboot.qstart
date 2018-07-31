package io.java.springboot.camel;

import io.java.springboot.config.ConfigConstants;
import io.java.springboot.config.ConfigService;
import io.java.springboot.parser.CSVDataFormatter;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.DataFormatDefinition;
import org.apache.camel.spi.DataFormat;
import org.apache.commons.csv.CSVStrategy;
import org.apache.commons.csv.writer.CSVConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.io.OutputStream;

@Component
public class CamelScheduler extends RouteBuilder{

    @Autowired
    ConfigService configService;

    @Override
    public void configure() throws Exception {

        final String name = getClass().getName();
        from("timer://trigger?repeatCount=1")
                //.noAutoStartup()
                .routeId(String.format( "%s.configBuiilder", name))
                .tracing()
                .bean(ConfigService.class, "writeAllConfigs")
                .to("log:file ${header}")
                .end();


        from("file://data?noop=true")
                .routeId(String.format( "%s.fileWatcher", name))
                .delayer(1000)
                .processRef("configProcessor")
                //.to("log:" + name + "?level=DEBUG")
                .choice()
                    .when(header(ConfigConstants.CAMEL_HEADERS.IS_FIXED_LENGTH_FILE).isEqualTo(true))
                        //.process(new FixedLengthParser())
                        .to("direct:FixedLengthFileParser" )
                    .when(header(ConfigConstants.CAMEL_HEADERS.IS_DELIMITED_LENGTH_FILE).isEqualTo(true))
                        .to("direct:DelimitedFileParser" )

                    .otherwise()
                    //.to("bean:mailCamel?method=config(${body})")
                        .to("log:file ${header}")
                .endChoice()
                .end();


        from ("direct:FixedLengthFileParser")
                .routeId(String.format( "%s.fixedLengthParser", name))
               .processRef("fixedLengthParser")
                .log("fixedLengthParser: ${body}")
                .to("direct:CsvMarshal")
                .end();

        from ("direct:DelimitedFileParser")
                .routeId(String.format( "%s.delimitedFileParser", name))
                .processRef("delimitedFileParser")
                //.log(String.format("delimitedFileParser: ${body}"))
                .to ("direct:CsvMarshal")
                .end();

        CsvDataFormat csvMarshal = new CsvDataFormat();
        CSVConfig config = new CSVConfig();
        config.setDelimiter(',');
        csvMarshal.setAutogenColumns(true);
        CSVStrategy csvStrategy = csvMarshal.getStrategy();
                System.out.println(csvStrategy.getEscape());
        System.out.println(csvStrategy.getEncapsulator());

        csvMarshal.setConfig(config);


        from ("direct:CsvMarshal")
                .routeId(String.format( "%s.csvMarshal", name))
                .delay(10)
                .log(String.format("csvMarshal: ${body}"))
                //.to("dataformat:csv:marshal?delimiter=${header.FIELD_DELIMITER}")
                .marshal(new CSVDataFormatter())


                //.marshal(new CsvDataFormat())
                //.convertBodyTo(String.class)
                .to(String.format("file://data_target?fileName=${header.%s}", ConfigConstants.CAMEL_HEADERS.FILENAME_ONLY) )
                //<to uri="file:/home/user/?fileName=abc.csv"/>
                .log(String.format("csvMarshal: ${header.%s}",  ConfigConstants.CAMEL_HEADERS.FILENAME_ONLY ))
                .end();


    }
}
