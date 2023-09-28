package gs.common;

import java.io.Serializable;

public class MultiSelectModel implements Serializable {
    private String codeValue;
    private String codeDesc;

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue;
    }

    public String getCodeDesc() {
        return codeDesc;
    }

    public void setCodeDesc(String codeDesc) {
        this.codeDesc = codeDesc;
    }
}
