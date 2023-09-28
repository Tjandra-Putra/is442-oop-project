package gs.common;

import java.io.Serializable;
import java.util.Map;

public class InputModel implements Serializable {
    private String codeDesc;
    private String codeValue;
    private Map data;

    public InputModel() {
    }
    
    public InputModel(String codeDesc, String codeValue) {
        this.codeDesc = codeDesc;
        this.codeValue = codeValue;
    }

    public Map getData() {
        return data;
    }

    public void setData(Map data) {
        this.data = data;
    }

    public String getCodeDesc() {
        return codeDesc;
    }
    
    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }
    
}
