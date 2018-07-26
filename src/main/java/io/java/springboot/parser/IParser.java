package io.java.springboot.parser;

import io.java.springboot.camel.ICamelProcessor;
import io.java.springboot.config.ClientConfig;

import java.util.List;
import java.util.Map;

/**
 * Created by Naveen on 7/22/18.
 */
public interface IParser {

    public List<Map<String, Object>> parse( final String filePath, final ClientConfig clientConfig)throws Exception ;

}
