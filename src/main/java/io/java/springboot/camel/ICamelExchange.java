package io.java.springboot.camel;

import org.apache.camel.Exchange;

public interface ICamelExchange extends Exchange {
    String FILE_ABSOLUTE_PATH = "CamelFileAbsolutePath";
    //String FIXED_LENGTH_PARSER = "USE_FIXED_LENGTH_PARSER";

    public default String getFileAbsolutePath(){
        return getIn().getHeader(this.FILE_ABSOLUTE_PATH).toString();
    }

    public default String getFileName(){
        return getIn().getHeader(this.FILE_NAME).toString();
    }




}
