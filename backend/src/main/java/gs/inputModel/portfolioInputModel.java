package gs.inputModel;

import java.io.Serializable;

public class PortfolioInputModel implements Serializable{
    private long portfolioId;
    private String portfolioName;
    private double capitalAmt;
    private String description;
    private long userId;
    private double portfolioValue;

    public long getPortfolioId() {
        return portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public double getCapitalAmt() {
        return capitalAmt;
    }

    public String getDescription() {
        return description;
    }

    public long getUserId() {
        return userId;
    }

    public void setPortfolioId(long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public void setCapitalAmt(double capitalAmt) {
        this.capitalAmt = capitalAmt;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public double getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(double portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

}
