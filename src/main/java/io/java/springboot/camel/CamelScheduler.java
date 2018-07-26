package io.java.springboot.camel;

import io.java.springboot.config.ConfigProcessor;
import io.java.springboot.parser.FlatPackParser;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.processor.interceptor.Tracer;
import org.springframework.stereotype.Component;

@Component
public class CamelScheduler extends RouteBuilder{

    @Override
    public void configure() throws Exception {



        from("file://data?noop=true")
                .processRef("configProcessor")
                //.to("log:afterProcess ${in.header}")
                .choice()
                    .when(header("isFixed").isEqualTo(true))
                        .process(new FlatPackParser())
                        //.to("direct:FlatPackParser" )
                    .otherwise()
                    //.to("bean:mailCamel?method=config(${body})")
                        .to("log:file ${header}");



    }
}
