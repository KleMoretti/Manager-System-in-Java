package console;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultSetData implements Serializable {
    private List<String[]> data;
    private String[] columnNames;

    public ResultSetData(String[] columnNames, List<String[]> data) {
        this.columnNames = columnNames;
        this.data = data;
    }

    public String[] getColumnNames() {
        return columnNames;
    }

    public List<String[]> getData() {
        return data;
    }
}