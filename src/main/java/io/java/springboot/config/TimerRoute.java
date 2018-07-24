package io.java.springboot.config;


import io.java.springboot.parser.FlatPackParser;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.SpringRouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TimerRoute extends RouteBuilder {

    public static final String ROUTE_NAME = "TIMER_ROUTE";

    @Override
    public void configure() throws Exception {

        from("file://data?noop=true")
                .process(new FlatPackParser())
                //.to("bean:mailCamel?method=config(${body})")
                .to("log:fileLog");
/*
from("file://inbox?move=.done").to("bean:handleOrder");
        from("timer:initial//start?period=10000")
                //.bean(MailCamel.class, "config(text)")
                .process(new Processor() {
                    public void process(Exchange exchange)

                            throws Exception {
                        Message in = exchange.getIn();
                        in.setBody(in.getBody(String.class) + "(polling)");
                    }
                })
                .routeId(ROUTE_NAME)
                .to("log:executed");
                from("file://inputdir/").process(new Processor() { public void process(Exchange exchange) throws Exception { Object body = exchange.getIn().getBody(); // do some business logic with the input body } });
                */



    }

}
