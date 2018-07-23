package io.java.springboot.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Naveen on 7/22/18.
 */



public class ClientConfig {


    static final String DELIMETER_FILE_FORMAT = "DELIMETER";
    static final String FIXED_FILE_FORMAT = "FIXED";
    static final String TEMP_CLIENT_CONFIGS_FILE_NAME = "clientConfig.json";

    String clientId;
    String fileName;
    String fileFormat;
    String delimeter;

    public ClientConfig() {

    }

    public static final List<ClientConfig> getDefaultConfig() throws  Exception{
        ObjectMapper m = new ObjectMapper();
        List<ClientConfig> configs = m.readValue(new File(TEMP_CLIENT_CONFIGS_FILE_NAME), new TypeReference<List<ClientConfig>>() {});
        return configs;
    }



    public boolean isFixed(){
        return fileFormat == ClientConfig.FIXED_FILE_FORMAT;
    }
    public boolean isDelimeted(){
        return fileFormat == ClientConfig.DELIMETER_FILE_FORMAT;
    }


    public List<ClientConfigColumn> getClientConfigColumns() {
        return clientConfigColumns;
    }

    public void setClientConfigColumns(List<ClientConfigColumn> clientConfigColumns) {
        this.clientConfigColumns = clientConfigColumns;
    }



    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getDelimeter() {
        return delimeter;
    }

    public void setDelimeter(String delimeter) {
        this.delimeter = delimeter;
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
                ", delimeter='" + delimeter + '\'' +
                '}';
    }
}
