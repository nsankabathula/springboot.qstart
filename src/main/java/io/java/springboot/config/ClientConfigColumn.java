package io.java.springboot.config;

import javax.xml.bind.annotation.*;

/**
 * Created by Naveen on 7/22/18.
 */

public class ClientConfigColumn {
    String columnName;
    Long beginIndex;
    Long endIndex;


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
        return beginIndex!= null && endIndex != null;
    }

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
                '}';
    }
}
