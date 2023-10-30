package gs.inputModel;

import java.io.Serializable;

public class HistoryInputModel implements Serializable {

    private String ticker;
    private String date;
    private double openPrice;
    private double highPrice;
    private double lowPrice;
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

    public double getOpenPrice() {
        return openPrice;
    }

    public double getHighPrice() {
        return highPrice;
    }

    public double getLowPrice() {
        return lowPrice;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public void setHighPrice(double highPrice) {
        this.highPrice = highPrice;
    }

    public void setLowPrice(double lowPrice) {
        this.lowPrice = lowPrice;
    }

  

    
}
