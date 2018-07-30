package io.java.springboot.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
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
        return ClientConfig.filterByStartsWith(ClientConfig.getDefaultConfig(), fileNameStartsWith);
    }

    public List<?> writeAllConfigs() throws Exception{
        ArrayList<String > configPaths = new ArrayList<>();
        System.out.println("writeAllConfigs");
        List<ClientConfig> fixedConfigs = ClientConfig.getDefaultConfig().parallelStream().filter(t-> t.isFixed()).collect(Collectors.toList());
        System.out.println(fixedConfigs);
        for (ClientConfig config : fixedConfigs){
            configPaths.add(config.writeConfigToFile(xmlConverter));
        }
        return configPaths;
    }

/* Test
    public List<?> getClientConfigColumnsForXML() throws Exception{
        ArrayList<List<ClientConfigColumn> > configPaths = new ArrayList<>();
        System.out.println("writeAllConfigs");
        List<ClientConfig> fixedConfigs = ClientConfig.getDefaultConfig().parallelStream().filter(t-> t.isFixed()).collect(Collectors.toList());
        System.out.println(fixedConfigs);
        for (ClientConfig config : fixedConfigs){
            //configPaths.add(config.getClientConfigColumnsForXML());

            //ObjectMapper objectMapper = new XmlMapper();
            //System.out.println(objectMapper.writeValueAsString(new ClientConfigColumns(config.getClientConfigColumnsForXML())));
            JAXBContext jaxbContext = JAXBContext.newInstance(ClientConfigColumns.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders",
                    "\n<!DOCTYPE PZMAP SYSTEM  \"flatpack.dtd\">");//<!DOCTYPE PZMAP SYSTEM	"flatpack.dtd" >

            //Marshal the employees list in console
            jaxbMarshaller.marshal(new ClientConfigColumns(config.getClientConfigColumnsForXML()), System.out);

            break;
            //configPaths.add(config.writeConfigToFile(xmlConverter));

        }
        return configPaths;
    }
*/


    public String writeConfigToFile(String fileNameStartsWith) throws Exception{
        ClientConfig config = ClientConfig.filterByStartsWith(getConfig(), fileNameStartsWith);


        //String fileName = new ClassPathResource(config.getConfigFileName()).getPath();

        //xmlConverter.convertFromObjectToXML(new ClientConfigColumns(config.getClientConfigColumns()), fileName);
        String fileName = config.writeConfigToFile(xmlConverter);

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

