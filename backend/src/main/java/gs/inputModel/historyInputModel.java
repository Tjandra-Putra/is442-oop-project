package gs.inputModel;

import java.io.Serializable;

public class historyInputModel implements Serializable {

    private String ticker;
    private String date;
    private double adjClosePrice;

    public String getTicker() {
        return this.ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getDate() {
        return this.date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getAdjClosePrice() {
        return this.adjClosePrice;
    }

    public void setAdjClosePrice(double adjClosePrice) {
        this.adjClosePrice = adjClosePrice;
    }
    
}
