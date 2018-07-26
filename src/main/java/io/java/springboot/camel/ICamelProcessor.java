package io.java.springboot.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public interface ICamelProcessor  extends Processor{


    String HEADER_FILE_ABSLOUTE_PATH = "CamelFileAbsolutePath";
    String HEADRER_FILENAME_ONLY = "CamelFileNameOnly";
    String HEADER_IS_FIXED_LENGTH_FILE = "FILE_IS_FIXED_LENGTH";
    String HEADER_IS_DELIMITED_LENGTH_FILE = "FILE_IS_DELIMITED_LENGTH";



    @Override
    default  void process(Exchange exchange) throws Exception {

    }



}
