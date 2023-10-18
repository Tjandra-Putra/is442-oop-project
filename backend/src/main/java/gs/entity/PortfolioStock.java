package gs.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
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

    // Other properties specific to PortfolioStock

    // Constructors, getters, and setters
}



