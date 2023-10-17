package gs.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "stockInfo")
public class StockInfo {
    @Id
    @Column(
        name = "ticker",
        nullable = false
        )
    private String ticker;

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
    
    @OneToOne
    @JoinColumn(name = "ticker", referencedColumnName = "ticker")
    private Stock stock;

    public StockInfo(String ticker, String currency, String sector, String industry, String country) {
        this.ticker = ticker;
        this.currency = currency;
        this.sector = sector;
        this.industry = industry;
        this.country = country;
    }

    public StockInfo() {};

    public String getTicker() {
        return this.ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

}




