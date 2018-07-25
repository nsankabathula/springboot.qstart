package io.java.springboot.config;

import io.java.springboot.camel.ICamelExchange;
import io.java.springboot.camel.ICamelProcessor;
import io.java.springboot.config.ClientConfig;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class ConfigProcessor implements ICamelProcessor {

    @Override
    public void process(Exchange exchange) throws Exception {
        final String  camelAbsoluteFilePath = exchange.getIn().getHeader("CamelFileAbsolutePath").toString();
        ClientConfig config = ClientConfig.filterByStartsWith(ClientConfig.getDefaultConfig(),  exchange.getIn().getHeader("CamelFileNameOnly").toString()) ;

        //System.out.println(exchange.getIn().getBody()) ;
        //exchange.getContext().

        //exchange.getIn().setHeader("config", config);
        exchange.getIn().setHeader("isFixed", config.isFixed());
        exchange.getIn().setBody(config, ClientConfig.class);

        //System.out.println(config.isFixed());

        //exchange.getIn().setHeader("camelAbsoluteFilePath", camelAbsoluteFilePath);

        /*

        System.out.println(exchange.getIn().getHeaders()); //CamelFileAbsolutePath
        String myString = exchange.getIn().getBody(String.class);
        //System.out.println(" data " + myString);
        ClientConfig config = ClientConfig.filterByStartsWith(ClientConfig.getDefaultConfig(), fileName) ;
        //System.out.println(config);
        exchange.getIn().setBody(myString);
        */

    }
}
