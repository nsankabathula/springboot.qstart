package io.java.springboot.parser;

import io.java.springboot.camel.ICamelProcessor;
import io.java.springboot.config.ClientConfig;
import io.java.springboot.config.ConfigConstants;
import org.apache.camel.Exchange;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * Created by Naveen on 7/22/18.
 */
public interface IParser extends ICamelProcessor {

    public List<Map<String, Object>> parse( final String filePath, final ClientConfig clientConfig)throws Exception ;

    public default void process(Exchange exchange) throws Exception {

        //ClientConfig clientConfig = (ClientConfig) exchange.getIn().getHeader(ConfigConstants.CAMEL_HEADERS.FILE_CONFIG);
        ClientConfig clientConfig = (ClientConfig) exchange.getIn().getBody(ClientConfig.class);
        //File fileMapping = new File(clientConfig.getConfigFileName());
        exchange.getIn().setBody(parse(exchange.getIn().getHeader(ConfigConstants.CAMEL_HEADERS.FILE_ABSLOUTE_PATH).toString(), clientConfig));
    }

}
