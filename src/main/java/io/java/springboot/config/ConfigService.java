package io.java.springboot.config;

import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class ConfigService  {

    public List<ClientConfig> getConfig()throws Exception{
        return ClientConfig.getDefaultConfig();
    }

    public Map<String, ClientConfig> getConfigMap()throws Exception{
        return ClientConfig.getMap(getConfig());
    }

    public ClientConfig getConfigByFileName(String fileNameStartsWith) throws Exception{
        return ClientConfig.filterByStartsWith(getConfig(), fileNameStartsWith);
    }

    @XmlRootElement(name = "columns")
    private class ClientConfigColumns
    {
        @XmlElement(name = "column")
        private List<ClientConfigColumn> columns = null;

        public List<ClientConfigColumn> getColumns() {
            return columns;
        }

        public void setColumns(List<ClientConfigColumn> columns) {
            this.columns = columns;
        }
    }
    public String writeConfigToFile(String fileNameStartsWith) throws Exception{
        ClientConfig config = ClientConfig.filterByStartsWith(getConfig(), fileNameStartsWith);
        String fileName = config.fileName + ".xml";
        File file = new File(fileName);
        JAXBContext jaxbContext = JAXBContext.newInstance(ClientConfigColumns.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        ClientConfigColumns columns  = new ClientConfigColumns();
        columns.setColumns(config.getClientConfigColumns());

        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        jaxbMarshaller.marshal(columns, file);


        return file.getPath();
    }
}
