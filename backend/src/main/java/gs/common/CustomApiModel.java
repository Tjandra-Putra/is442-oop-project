package gs.common;

import java.util.List;
import java.util.Map;
import java.io.Serializable;

public class CustomApiModel<D extends Serializable> extends ApiModel<D> {
    private Map<String, List<Double>> data;

    public CustomApiModel() {
    }

    public static <D extends Serializable> CustomApiModel<D> ok(Map<String, List<Double>> data){
        CustomApiModel<D> apiModel = new CustomApiModel<>();
        apiModel.setStatus("OK");
        apiModel.setMessage("Success");
        apiModel.setData(data);
        return apiModel;
    }

    public CustomApiModel<D> setData(Map<String, List<Double>> data) {
        this.data = data;
        return this;
    }
}