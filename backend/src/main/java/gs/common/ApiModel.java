package gs.common;

import java.io.*;
import java.util.*;

public class ApiModel<D extends Serializable> implements Serializable {
    private String status;
    private String message;
    private D data;

    public ApiModel() {
    }

    public static <D extends Serializable> ApiModel<ArrayList<D>> ok(List<D> dataList){
        ApiModel<ArrayList<D>> apiModel = new ApiModel<>();
        apiModel.setStatus("OK");
        apiModel.setMessage("Success");
        
        if (!Objects.isNull(dataList) && !dataList.isEmpty()) {
            apiModel.setData((ArrayList) dataList);
        } else {
            apiModel.setData(new ArrayList(0));
        }
        return apiModel;
    }

    public String getStatus() {
        return status;
    }

    public ApiModel<D> setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public ApiModel<D> setMessage(String message) {
        this.message = message;
        return this;
    }

    public D getData() {
        return data;
    }

    public ApiModel<D> setData(D data) {
        this.data = data;
        return this;
    }
}
