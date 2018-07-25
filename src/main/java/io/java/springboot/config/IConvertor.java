package io.java.springboot.config;

import java.io.IOException;

public interface IConvertor {

    public void convertFromObjectToXML(Object object, String filepath) throws IOException;
    public Object convertFromXMLToObject(String xmlfile) throws IOException;
}
