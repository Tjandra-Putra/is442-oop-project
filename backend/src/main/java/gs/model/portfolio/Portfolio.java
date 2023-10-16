package gs.model.portfolio;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "portfolio")
public class Portfolio {
    @Id
    @Column(name = "portfolioId")
    @GeneratedValue
    
    private int portfolioId;
    private String portfolioName;
    private String portfolioDescription;
    private double portfolioCapitalAmt;

    public Portfolio() {

    };

    public Portfolio(int portfolioId, String portfolioName, String portfolioDescripton, double portfolioCapitalAmt) {
        this.portfolioId = portfolioId;
        this.portfolioName = portfolioName;
        this.portfolioDescription = portfolioDescripton;
        this.portfolioCapitalAmt = portfolioCapitalAmt;
    }

    public int getPortfolioId() {
        return portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public String getPortfolioDescription() {
        return portfolioDescription;
    }

    public double getPortfolioCapitalAmt() {
        return portfolioCapitalAmt;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public void setPortfolioDescription(String portfolioDescription) {
        this.portfolioDescription = portfolioDescription;
    }

    public void setPortfolioCapitalAmt(double portfolioCapitalAmt) {
        this.portfolioCapitalAmt = portfolioCapitalAmt;
    }
}
