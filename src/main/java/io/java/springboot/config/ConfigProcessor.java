package io.java.springboot.config;

import io.java.springboot.camel.ICamelProcessor;
import org.apache.camel.Exchange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConfigProcessor implements ICamelProcessor {


    @Autowired
    ConfigService configService;

    @Override
    public void process(Exchange exchange) throws Exception {
        //configService = (ConfigService) exchange.getContext().getRegistry().lookupByName("configService");
        final String  camelAbsoluteFilePath = exchange.getIn().getHeader(ICamelProcessor.HEADER_FILE_ABSLOUTE_PATH).toString();
        ClientConfig config = configService.getConfigByFileName(  exchange.getIn().getHeader(ICamelProcessor.HEADRER_FILENAME_ONLY).toString()) ;

        //System.out.println(exchange.getIn().getBody()) ;
        //exchange.getContext().

        //exchange.getIn().setHeader("config", config);
        exchange.getIn().setHeader(ICamelProcessor.HEADER_IS_FIXED_LENGTH_FILE, config.isFixed());
        exchange.getIn().setHeader(ICamelProcessor.HEADER_IS_DELIMITED_LENGTH_FILE, config.isDelimited());
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
