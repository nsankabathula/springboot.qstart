package io.java.springboot.hello;


import io.java.springboot.mail.MailCamel;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class TimerRoute extends RouteBuilder {

    public static final String ROUTE_NAME = "TIMER_ROUTE";

    @Override
    public void configure() throws Exception {

        from("file://data?noop=true")
                .process(new SampleTransformer())
                //.to("bean:mailCamel?method=hello(${body})")
                .to("log:fileLog");
/*
from("file://inbox?move=.done").to("bean:handleOrder");
        from("timer:initial//start?period=10000")
                //.bean(MailCamel.class, "hello(text)")
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
