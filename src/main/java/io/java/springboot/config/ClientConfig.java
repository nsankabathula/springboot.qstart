package io.java.springboot.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Naveen on 7/22/18.
 */



public class ClientConfig {


    static final String DELIMITER_FILE_FORMAT = "DELIMIED";
    static final String FIXED_FILE_FORMAT = "FIXED";
    static final String TEMP_CLIENT_CONFIGS_FILE_NAME = "clientConfig.json";
    static final String FOLDER_PATH = "configs/";

    String clientId;
    String fileName;
    String fileFormat;
    String delimiter;

    public ClientConfig() {

    }

    public static final List<ClientConfig> getDefaultConfig() throws  Exception{
        ObjectMapper m = new ObjectMapper();
        List<ClientConfig> configs = m.readValue(new File(TEMP_CLIENT_CONFIGS_FILE_NAME), new TypeReference<List<ClientConfig>>() {});
        return configs;
    }


    public String writeConfigToFile(IConvertor xmlConverter, String configFileName ) throws IOException{
        configFileName = (configFileName != null || configFileName.length()>0)? configFileName : getConfigFileName();
        xmlConverter.convertFromObjectToXML(new ClientConfigColumns(getClientConfigColumns()), configFileName);

        System.out.println(configFileName);
        return configFileName;
    }

    public String writeConfigToFile(IConvertor xmlConverter ) throws IOException{

        String configFileName = writeConfigToFile(xmlConverter, getConfigFileName());

        return configFileName;
    }

    public boolean isFixed(){
        return ClientConfig.FIXED_FILE_FORMAT.equals(fileFormat);
    }
    public boolean isDelimited(){
        return ClientConfig.DELIMITER_FILE_FORMAT.equals(fileFormat);
    }


    public List<ClientConfigColumn> getClientConfigColumns() {
        return clientConfigColumns;
    }

    public void setClientConfigColumns(List<ClientConfigColumn> clientConfigColumns) {
        this.clientConfigColumns = clientConfigColumns;
    }

    public String getConfigFileName(){
        return FOLDER_PATH + clientId + ".xml";
    }



    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    List<ClientConfigColumn> clientConfigColumns;


    public  static final Map<String, ClientConfig> getMap(List<ClientConfig> clientConfigs){
        Map<String, ClientConfig> result =
                clientConfigs.stream().collect(Collectors.toMap(ClientConfig::getFileName,
                        Function.identity()));
        return result;
    }

    public static final ClientConfig filterByStartsWith(List<ClientConfig> clientConfigs, String startsWith){
        List<ClientConfig>  configs = clientConfigs.stream().filter(config -> startsWith.startsWith(config.getFileName()))
                .collect(Collectors.toList());
        return configs.get(0) ;
    }

    @Override
    public String toString() {
        return "ClientConfig{" +
                "clientConfigColumns=" + clientConfigColumns +
                ", clientId='" + clientId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileFormat='" + fileFormat + '\'' +
                ", delimiter='" + delimiter + '\'' +
                '}';
    }
}
