package io.java.springboot.config;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name = "PZMAP")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClientConfigColumns {

    @XmlElement(name = "COLUMN")
    List<ClientConfigColumn> clientConfigColumns;

    public ClientConfigColumns() {
    }

    public ClientConfigColumns(List<ClientConfigColumn> clientConfigColumns) {
        this.clientConfigColumns = clientConfigColumns;
    }

    public List<ClientConfigColumn> getClientConfigColumns() {
        return clientConfigColumns;
    }

    public void setClientConfigColumns(List<ClientConfigColumn> clientConfigColumns) {
        this.clientConfigColumns = clientConfigColumns;
    }
}
