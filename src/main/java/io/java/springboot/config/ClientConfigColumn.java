package io.java.springboot.config;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by Naveen on 7/22/18.
 */


@XmlAccessorType(XmlAccessType.FIELD)

public class ClientConfigColumn {

    @XmlAttribute(name = "name")
    String columnName;

    @XmlTransient
    Long beginIndex;

    @XmlTransient
    Long endIndex;

    @XmlTransient
    String dataType;

    @XmlTransient
    Boolean useDefault;

    @XmlTransient
    Object value;

    @XmlTransient
    Boolean metaFlag;

    @XmlTransient
    int colIndex;

    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }





    public Boolean getMetaFlag() {
        return metaFlag;
    }

    public void setMetaFlag(Boolean metaFlag) {
        this.metaFlag = metaFlag;
    }

    public Boolean getUseDefault() {
        return useDefault;
    }

    public void setUseDefault(Boolean useDefault) {
        this.useDefault = useDefault;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public Long getBeginIndex() {
        return beginIndex;
    }


    public String getColumnName() {
        return columnName;
    }


    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }


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
        return beginIndex!= null ;//&& endIndex != null;
    }

    @XmlAttribute(name = "length")
    public Long getColumnLength(){
        if (beginIndex == null || endIndex  == null){
            return 0L;
        }
        else
            return endIndex - beginIndex;
    }

    @Override
    public String toString() {
        return "ClientConfigColumn{" +
                "beginIndex=" + beginIndex +
                ", columnName='" + columnName + '\'' +
                ", endIndex=" + endIndex +
                ", dataType=" + dataType +
                '}';
    }
}
