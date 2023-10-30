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
@Table(name = "history")
@IdClass(HistoryCompositeKey.class)

public class History{
    @Id
    @Column(
        name = "ticker",
        nullable = false
    )
    private String ticker;

    @Id
    @Column(
        name = "date",
        nullable = false,
        updatable = false
    )
    private Date date;
    
    @ManyToOne
    @JoinColumn(name = "ticker", insertable = false, updatable = false)
    private Stock stock;

    @Column(
        name = "open_price"
    )
    private double openPrice;

    @Column(
        name = "high_price"
    )
    private double highPrice;

    @Column(
        name = "low_price"
    )
    private double lowPrice;

    @Column(
        name = "adj_close_price"
    )
    private double adjClosePrice;


    public History() {};

    public History(String ticker, Date date, double openPrice, double highPrice, double lowPrice, double adjClosePrice) {
        this.ticker = ticker;
        this.date = date;
        this.openPrice = openPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.adjClosePrice = adjClosePrice;
    }   
    
    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
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

    public double getAdjClosePrice() {
        return adjClosePrice;
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

    public void setAdjClosePrice(double adjClosePrice) {
        this.adjClosePrice = adjClosePrice;
    }

}
