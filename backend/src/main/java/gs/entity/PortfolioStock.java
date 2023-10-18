package gs.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "portfolio_stock")
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
        updatable = false,
        nullable = false
    )    
    private Date buyDate;

    @Column(
        name = "price",
        updatable = false,
        nullable = false
    )    
    private double price;
    





}



