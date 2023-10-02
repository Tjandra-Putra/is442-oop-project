package gs.common;

import java.util.List;

public class RequestModel {
    private List<FilterRequestModel> filters;

    public List<FilterRequestModel> getFilters() {
        return filters;
    }

    public void setFilters(List<FilterRequestModel> filters) {
        this.filters = filters;
    }
}
