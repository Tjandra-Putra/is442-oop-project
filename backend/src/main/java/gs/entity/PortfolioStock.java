package gs.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "portfolio_stock")
@IdClass(PortfolioStockId.class)
public class PortfolioStock {
    
    @Id
    @ManyToOne
    @JoinColumn(
        name = "ticker"
    )
    private Stock stock;

    @Id
    @ManyToOne
    @JoinColumn(name = "portfolio_id")
    private Portfolio portfolio;

    @Column(
        name = "quantity",
        updatable = true,
        nullable = false
    )    
    private int quantity;

    @Column(
        name = "buy_date",
        updatable = true,
        nullable = false
    )    
    private Date buyDate;

    @Column(
        name = "price",
        updatable = true,
        nullable = false
    )    
    private double price;
    
    public Stock getStock() {
        return stock;
    }

    public Portfolio getPortfolio() {
        return portfolio;
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

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
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



