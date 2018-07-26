package io.java.springboot.camel;

import io.java.springboot.config.ConfigConstants;
import io.java.springboot.config.ConfigService;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.commons.csv.writer.CSVConfig;
import org.springframework.stereotype.Component;

@Component
public class CamelScheduler extends RouteBuilder{

    @Override
    public void configure() throws Exception {

        final String name = getClass().getName();
        from("timer://trigger?repeatCount=1")
                //.noAutoStartup()
                .routeId(String.format( "%s.configBuiilder", name))
                .tracing()
                .bean(ConfigService.class, "writeAllConfigs")
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
                //.log("FixedLengthFileParser: ${body}")
                .to("direct:CsvMarshal")
                .end();

        from ("direct:DelimitedFileParser")
                .routeId(String.format( "%s.delimitedFileParser", name))
                //.processRef("delimitedFileParser")
                .to("log:DelimitedFileParser")
                .to ("direct:CsvMarshal")
                .end();

        CsvDataFormat csv = new CsvDataFormat();
        CSVConfig config = new CSVConfig();
        config.setDelimiter(',');
        System.out.println(csv.getStrategy());
        csv.setConfig(config);

        from ("direct:CsvMarshal")
                .routeId(name + ".csvMarshal")
                .delay(10)
                .marshal(csv)
                //.convertBodyTo(String.class)
                .to(String.format("file://data_target?fileName=${header.%s}", ConfigConstants.CAMEL_HEADERS.FILENAME_ONLY) )
                //<to uri="file:/home/user/?fileName=abc.csv"/>
                .log(String.format("csvMarshal: ${header.%s}",  ConfigConstants.CAMEL_HEADERS.FILENAME_ONLY ))
                .end();


    }
}
