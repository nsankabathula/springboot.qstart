package io.java.springboot.hello;

/**
 * Created by Naveen on 7/22/18.
 */
public class ClientConfigColumn {
    String columnName;
    int beginIndex;
    int endIndex;

    public int getBeginIndex() {
        return beginIndex;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public void setBeginIndex(int beginIndex) {
        this.beginIndex = beginIndex;
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
