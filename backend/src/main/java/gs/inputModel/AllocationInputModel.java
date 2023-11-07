package gs.inputModel;

import java.io.Serializable;

public class AllocationInputModel implements Serializable{
    private String allocationName;
    private double percentage;

    public String getAllocationName() {
        return allocationName;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setAllocationName(String allocationName) {
        this.allocationName = allocationName;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
