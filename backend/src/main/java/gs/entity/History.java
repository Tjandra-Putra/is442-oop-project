package gs.entity;

import java.util.Date;
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
    private String ticker;

    @Id
    private Date date;
    private double adjClosePrice;
    
    @ManyToOne
    @JoinColumn(name = "ticker", insertable = false, updatable = false)
    private Stock stock;

    public History(String ticker, Date date, double adjClosePrice) {
        this.ticker = ticker;
        this.date = date;
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

    public double getAdjClosePrice() {
        return adjClosePrice;
    }

    public void setAdjClosePrice(double adjClosePrice){
        this.adjClosePrice = adjClosePrice;
    }

}