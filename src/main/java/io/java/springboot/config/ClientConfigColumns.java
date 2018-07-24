package io.java.springboot.config;

import java.util.List;

public class ClientConfigColumns {

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
