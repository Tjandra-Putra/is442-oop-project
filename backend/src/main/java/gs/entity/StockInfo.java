package gs.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "stockInfo")
public class StockInfo {
     @Id
    @OneToOne
    @JoinColumn(name = "ticker")
    private Stock stock;


    @Column(
        name = "currency",
        nullable = false
        )
    private String currency;

    @Column(
        name = "sector",
        nullable = false
        )
    private String sector;


    @Column(
        name = "industry",
        nullable = false
        )
    private String industry;

    @Column(
        name = "country",
        nullable = false
        )
    private String country;
    
    public StockInfo() {};

    // public String getTicker() {
    //     return this.ticker;
    // }

    // public void setTicker(String ticker) {
    //     this.ticker = ticker;
    // }

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
    
}




