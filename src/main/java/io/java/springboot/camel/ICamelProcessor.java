package io.java.springboot.camel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public interface ICamelProcessor  extends Processor{


    @Override
    default  void process(Exchange exchange) throws Exception {

    }



}
