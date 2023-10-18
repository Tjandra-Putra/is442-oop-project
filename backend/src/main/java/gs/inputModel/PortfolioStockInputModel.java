package gs.inputModel;

import java.io.Serializable;
import java.util.Date;

public class PortfolioStockInputModel implements Serializable{
    private String ticker;
    private long portfolioId;
    private int quantity;
    private Date buyDate;
    private double price;

    public String getTicker() {
        return ticker;
    }

    public long getPortfolioId() {
        return portfolioId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public double getPrice() {
        return price;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public void setPortfolioId(long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setBuyDate(Date buyDate) {
        this.buyDate = buyDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
