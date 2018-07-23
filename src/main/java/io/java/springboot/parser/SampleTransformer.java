package io.java.springboot.parser;

import io.java.springboot.config.ClientConfig;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * Created by Naveen on 7/22/18.
 */
public class SampleTransformer implements Processor {


    @Override
    public void process(Exchange exchange) throws Exception {
        String fileName = exchange.getIn().getHeader("CamelFileNameOnly").toString();
        //System.out.println(exchange.getIn().getHeader("CamelFileNameOnly"));
        String myString = exchange.getIn().getBody(String.class);
        System.out.println(myString);
        ClientConfig config = ClientConfig.filterByStartsWith(ClientConfig.getDefaultConfig(), fileName) ;
        System.out.println(config);
        exchange.getIn().setBody(myString);
    }
}
