package io.java.springboot.config;


import org.apache.camel.Exchange;
import org.apache.camel.dataformat.csv.CsvDataFormat;
import org.apache.camel.model.DataFormatDefinition;
import org.apache.camel.spi.DataFormat;
import org.apache.commons.csv.CSVStrategy;
import org.apache.commons.csv.writer.CSVConfig;
import org.apache.commons.csv.writer.CSVField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
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
        List<ClientConfig> fixedConfigs = ClientConfig.getDefaultConfig();//.parallelStream().filter(t-> t.isFixed()).collect(Collectors.toList());
        //System.out.println(fixedConfigs);
        for (ClientConfig config : fixedConfigs){
            configPaths.add(config.writeConfigToFile(xmlConverter));
        }

        //List<ClientConfig> delimitedConfigs = ClientConfig.getDefaultConfig().parallelStream().filter(t-> !t.isFixed()).collect(Collectors.toList());

        return configPaths;
    }

    public DataFormatDefinition getCSVDataFormat() throws  Exception{
        DataFormatDefinition dataFormatDefinition = new DataFormatDefinition();
        DataFormat dataFormat = new DataFormat() {
            @Override
            public void marshal(Exchange exchange, Object o, OutputStream outputStream) throws Exception {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                PrintWriter writer = new PrintWriter(outputStreamWriter);
                System.out.println(o);

                List<Map<String, Object>> fileData = ( List<Map<String, Object>>) o;
                fileData.forEach(stringObjectMap -> {
                    final StringBuffer  stringBuffer = new StringBuffer();
                    stringObjectMap.forEach((s, obj) -> {
                        stringBuffer.append(obj);
                        stringBuffer.append(',');
                    });
                    try {
                        //final StringBuffer  newstringBuffer  = stringBuffer.deleteCharAt(stringBuffer.length());
                        stringBuffer.delete(stringBuffer.length()-1, stringBuffer.length());
                        writer.println(stringBuffer.toString());
                    }
                    catch (Exception ex){

                    }


                });
                outputStreamWriter.flush();
                outputStreamWriter.close();
                outputStream.close();
            }

            @Override
            public Object unmarshal(Exchange exchange, InputStream inputStream) throws Exception {
                return null;
            }
        };
        dataFormatDefinition.setDataFormat(dataFormat);
        return dataFormatDefinition;
    }

/* Test
    public List<?> getFixedLengthClientConfigColumnsForXML() throws Exception{
        ArrayList<List<ClientConfigColumn> > configPaths = new ArrayList<>();
        System.out.println("writeAllConfigs");
        List<ClientConfig> fixedConfigs = ClientConfig.getDefaultConfig().parallelStream().filter(t-> t.isFixed()).collect(Collectors.toList());
        System.out.println(fixedConfigs);
        for (ClientConfig config : fixedConfigs){
            //configPaths.add(config.getFixedLengthClientConfigColumnsForXML());

            //ObjectMapper objectMapper = new XmlMapper();
            //System.out.println(objectMapper.writeValueAsString(new ClientConfigColumns(config.getFixedLengthClientConfigColumnsForXML())));
            JAXBContext jaxbContext = JAXBContext.newInstance(ClientConfigColumns.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.setProperty("com.sun.xml.internal.bind.xmlHeaders",
                    "\n<!DOCTYPE PZMAP SYSTEM  \"flatpack.dtd\">");//<!DOCTYPE PZMAP SYSTEM	"flatpack.dtd" >

            //Marshal the employees list in console
            jaxbMarshaller.marshal(new ClientConfigColumns(config.getFixedLengthClientConfigColumnsForXML()), System.out);

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

    public CsvDataFormat getCSVDataFormat(String fileNameStartsWith) throws Exception{

        ClientConfig clientConfig = ClientConfig.filterByStartsWith(getConfig(), fileNameStartsWith);

        CsvDataFormat csvFileConfig = new CsvDataFormat();
        CSVConfig csvConfig = new CSVConfig();
        csvConfig.setDelimiter(clientConfig.getDelimiter());
        CSVStrategy strategy =csvFileConfig.getStrategy();
        strategy.setEscape('"');

        List<CSVField> csvFields = new ArrayList<>();
        clientConfig.getClientConfigColumns().forEach(clientConfigColumn -> {
            CSVField field = new CSVField();
            field.setName(clientConfigColumn.getColumnName());

        });
        csvConfig.setFields(csvFields);
        csvFileConfig.setConfig(csvConfig);

        return csvFileConfig;

    }



}

