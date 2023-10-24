package gs.inputModel;


import java.io.Serializable;

public class StockInfoInputModel implements Serializable{
    private String ticker;
    private String currency;
    private String sector;
    private String industry;
    private String country;
    private double today_price;

    public String getTicker() {
        return this.ticker;
    }

    public String getCurrency() {
        return this.currency;
    }

    public String getSector() {
        return this.sector;
    }

    public String getIndustry() {
        return this.industry;
    }

    public String getCountry() {
        return this.country;
    }

    public double getTodayPrice(){
        return this.today_price;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTodayPrice(double today_price) {
        this.today_price = today_price;
    }


}
