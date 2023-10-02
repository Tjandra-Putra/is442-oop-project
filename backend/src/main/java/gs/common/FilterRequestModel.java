package gs.common;

import java.util.List;

public class FilterRequestModel {
    private String fieldName;
    private String value;

    private List<MultiSelectModel> values;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }

    public List<MultiSelectModel> getValues() {
        return values;
    }

    public void setValues(List<MultiSelectModel> values) {
        this.values = values;
    }

}
