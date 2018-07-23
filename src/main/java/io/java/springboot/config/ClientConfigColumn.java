package io.java.springboot.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Naveen on 7/22/18.
 */
@XmlRootElement
public class ClientConfigColumn {
    String columnName;
    Long beginIndex;
    Long endIndex;

    @XmlAttribute
    public Long getBeginIndex() {
        return beginIndex;
    }

    @XmlAttribute
    public String getColumnName() {
        return columnName;
    }


    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @XmlAttribute
    public Long getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Long endIndex) {
        this.endIndex = endIndex;
    }

    public void setBeginIndex(Long beginIndex) {
        this.beginIndex = beginIndex;
    }

    public boolean expectedFromSource(){
        return beginIndex!= null && endIndex != null;
    }

    @Override
    public String toString() {
        return "ClientConfigColumn{" +
                "beginIndex=" + beginIndex +
                ", columnName='" + columnName + '\'' +
                ", endIndex=" + endIndex +
                '}';
    }
}
