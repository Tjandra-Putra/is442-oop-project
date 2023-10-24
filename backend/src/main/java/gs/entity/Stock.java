package gs.entity;

import java.util.List;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "stock")
    private List<PortfolioStock> PortfolioStock;

    @OneToOne(mappedBy = "stock") // Refers to the 'stock' property in StockInfo class
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
