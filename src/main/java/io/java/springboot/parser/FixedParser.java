package io.java.springboot.parser;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class FixedParser  extends RouteBuilder {

    public static final String ROUTE_NAME = "FIXED_LENGTH_PARSER";

    @Override
    public void configure() throws Exception {

        from("file://data?noop=true")
                //.to("flatpack:fixed:fixed-length.pzmap.xml")
                //.process(new SampleTransformer())
                //.to("bean:mailCamel?method=config(${body})")
                .to("log:fileLog");

        //FlatpackDataFormat fp = new FlatpackDataFormat();
        //fp.setDataFormat(
          System.out.println(new ClassPathResource("fixed-length.pzmap.xml").toString());
        //);


    }


}
