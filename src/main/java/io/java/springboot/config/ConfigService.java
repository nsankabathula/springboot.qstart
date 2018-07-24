package io.java.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ConfigService  {

    @Autowired
    public XMLConverter xmlConverter;

    public List<ClientConfig> getConfig()throws Exception{
        return ClientConfig.getDefaultConfig();
    }

    public Map<String, ClientConfig> getConfigMap()throws Exception{
        return ClientConfig.getMap(getConfig());
    }

    public ClientConfig getConfigByFileName(String fileNameStartsWith) throws Exception{
        return ClientConfig.filterByStartsWith(getConfig(), fileNameStartsWith);
    }


    public String writeConfigToFile(String fileNameStartsWith) throws Exception{
        ClientConfig config = ClientConfig.filterByStartsWith(getConfig(), fileNameStartsWith);

        String fileName =  new ClassPathResource(config.getXMLConfigPath()).getPath();

        xmlConverter.convertFromObjectToXML(new ClientConfigColumns(config.getClientConfigColumns()), fileName);

        //File file = new File(fileName);
        //JAXBContext jaxbContext = JAXBContext.newInstance(ClientConfigColumns.class);
        //Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
         //ClientConfigColumns configColumns = new ClientConfigColumns(config.getClientConfigColumns());
        //ArrayList<ClientConfigColumn> columns  = (ArrayList<ClientConfigColumn>)config.getClientConfigColumns();
        // output pretty printed
        //jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        //jaxbMarshaller.marshal(configColumns, file);


        return fileName;
    }



}

