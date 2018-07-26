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

        final String  camelAbsoluteFilePath = exchange.getIn().getHeader(ConfigConstants.CAMEL_HEADERS.FILE_ABSLOUTE_PATH).toString();
        ClientConfig config = configService.getConfigByFileName(  exchange.getIn().getHeader(ConfigConstants.CAMEL_HEADERS.FILENAME_ONLY).toString()) ;


        //exchange.getIn().setHeader("config", config);
        exchange.getIn().setHeader(ConfigConstants.CAMEL_HEADERS.IS_FIXED_LENGTH_FILE, config.isFixed());
        exchange.getIn().setHeader(ConfigConstants.CAMEL_HEADERS.IS_DELIMITED_LENGTH_FILE, config.isDelimited());
        exchange.getIn().setBody(config, ClientConfig.class);
        //exchange.getIn().setBody(config.isFixed());

    }
}
