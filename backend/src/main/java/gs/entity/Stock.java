package gs.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "stock")

public class Stock {
    
    @Id
    @Column(
        name = "ticker",
        nullable = false
        )
    private String ticker;

    @Column(
        name = "stockName",
        nullable = false
        )
    private String stockName;

    @OneToOne(mappedBy = "stock")
    private StockInfo stockInfo;

    public Stock(String ticker, String stockName) {
        this.ticker = ticker;
        this.stockName = stockName;
    }

    public Stock() {};

    public String getTicker() {
        return this.ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getStockName() {
        return this.stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}
