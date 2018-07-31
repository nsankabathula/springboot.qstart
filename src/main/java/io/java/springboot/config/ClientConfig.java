package io.java.springboot.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
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

    List<ClientConfigColumn> clientConfigColumns;

    public ClientConfig() {

    }

    public static final List<ClientConfig> getDefaultConfig() throws  Exception{
        ObjectMapper m = new ObjectMapper();
        List<ClientConfig> configs = m.readValue(new File(TEMP_CLIENT_CONFIGS_FILE_NAME), new TypeReference<List<ClientConfig>>() {});

        return configs;
    }


    public String writeConfigToFile(IConvertor xmlConverter, String configFileName ) throws IOException{
        configFileName = (configFileName != null || configFileName.length()>0)? configFileName : getConfigFileName();
        xmlConverter.convertFromObjectToXML(new ClientConfigColumns(getFixedLengthClientConfigColumnsForXML()), configFileName);

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

    public List<ClientConfigColumn> getFixedLengthColumns() {
        List<ClientConfigColumn> result =  clientConfigColumns.stream().filter(clientConfigColumn -> clientConfigColumn.expectedFromSource()).collect(Collectors.toList());
        result.sort(Comparator.comparing(ClientConfigColumn::getBeginIndex));

        return result;
    }

    public Map<String , ClientConfigColumn> getClientConfigColumnsMap(){
        Map<String, ClientConfigColumn> result =
                getClientConfigColumns().stream().collect(Collectors.toMap(ClientConfigColumn::getColumnName,
                        Function.identity()));
        return result;
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

    public List<ClientConfigColumn> getFixedLengthClientConfigColumnsForXML(){
        if(isFixed())
            return ClientConfig.getFixedLengthClientConfigColumnsForXML(getClientConfigColumns());
        else if(isDelimited())
            return ClientConfig.getDelimitedClientConfigColumnsForXML(getClientConfigColumns());
        else
            throw new UnsupportedOperationException("Not supported yet");
    }

    public static final List<ClientConfigColumn> getDelimitedClientConfigColumnsForXML(List<ClientConfigColumn> clientConfigColumns) {
        ArrayList<ClientConfigColumn> result =  (ArrayList<ClientConfigColumn>)clientConfigColumns.stream().filter(clientConfigColumn -> clientConfigColumn.expectedFromSource()).collect(Collectors.toList());
        result.sort(Comparator.comparing(ClientConfigColumn::getBeginIndex));
        ArrayList<ClientConfigColumn> sourceColumns = new ArrayList<>();
        for (int  curr_idx = 0, prev_idx = curr_idx - 1  ; curr_idx < result.size(); curr_idx ++, prev_idx++ ) {
            Long runDummyLoop = 0L;

            ClientConfigColumn currentConfig = result.get(curr_idx);
            if(prev_idx == -1){
                runDummyLoop = (currentConfig.getBeginIndex()>1L)?  currentConfig.getBeginIndex(): 1L;
            }
            else{
                //ClientConfigColumn prevConfig = result.get(prev_idx);
                runDummyLoop = currentConfig.getBeginIndex() - sourceColumns.size();
            }



            for(int i =0; i < runDummyLoop ; i++ ){
                ClientConfigColumn dummy = new ClientConfigColumn();
                dummy.setColumnName("dummy_" + curr_idx + "_" + i);
                dummy.setColIndex(sourceColumns.size());
                sourceColumns.add(dummy);
            }
            currentConfig.setColIndex(sourceColumns.size());
            sourceColumns.add(currentConfig);

        }

        return sourceColumns;
    }

    public static final List<ClientConfigColumn> getFixedLengthClientConfigColumnsForXML(List<ClientConfigColumn> clientConfigColumns){
        ArrayList<ClientConfigColumn> result =  (ArrayList<ClientConfigColumn>)clientConfigColumns.stream().filter(clientConfigColumn -> clientConfigColumn.expectedFromSource()).collect(Collectors.toList());
        result.sort(Comparator.comparing(ClientConfigColumn::getBeginIndex));
        ArrayList<ClientConfigColumn> sourceColumns = new ArrayList<>();
        for (int  curr_idx = 0, prev_idx = curr_idx - 1  ; curr_idx < result.size(); curr_idx ++, prev_idx++ ){
            Long prevEndIndex = 0L;
            ClientConfigColumn currentConfig = result.get(curr_idx);
            ClientConfigColumn source = new ClientConfigColumn();
            if(prev_idx == -1){
                prevEndIndex = (currentConfig.getBeginIndex()>0L)? 0L: currentConfig.getBeginIndex();
            }
            else
            {
                prevEndIndex = result.get(prev_idx).getEndIndex();
            }

            if(currentConfig.getBeginIndex() > prevEndIndex){
                source.setColumnName("dummy_" + curr_idx);
                source.setBeginIndex(prevEndIndex);
                source.setEndIndex(currentConfig.getBeginIndex());
                sourceColumns.add(source);
            }


            sourceColumns.add(currentConfig);
        }

        //System.out.println("getFixedLengthClientConfigColumnsForXML %s".format(sourceColumns.toString()));
        return sourceColumns;

    }


    public  static final Map<String, ClientConfig> getMap(List<ClientConfig> clientConfigs){
        Map<String, ClientConfig> result =
                clientConfigs.stream().collect(Collectors.toMap(ClientConfig::getFileName,
                        Function.identity()));
        return result;
    }

    public static final ClientConfig filterByStartsWith(List<ClientConfig> clientConfigs, String startsWith){
        List<ClientConfig>  configs = clientConfigs.stream().filter(config -> startsWith.startsWith(config.getFileName()))
                .collect(Collectors.toList());
        //ClientConfig.getFixedLengthClientConfigColumnsForXML(configs.get(0).getClientConfigColumns());
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
