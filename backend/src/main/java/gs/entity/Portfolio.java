package gs.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "portfolio")
public class Portfolio {

    @Id
    @SequenceGenerator(
        name = "portfolio_sequence",
        sequenceName = "portfolio_sequence",
        allocationSize = 1
    )
    @GeneratedValue(
        strategy = GenerationType.SEQUENCE,
        generator = "portfolio_sequence"
    )
    @Column(
        name = "portfolio_id",
        updatable = false
    )
    private long portfolioId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(
        name = "portfolio_name",
        updatable = true,
        nullable = false
    )
    private String portfolioName;

    @Column(
        name = "description",
        updatable = true,
        nullable = false
    )
    private String description;

    @Column(
        name = "capital_amount",
        updatable = true,
        nullable = false
    )    
    private double capitalAmt;

    @OneToMany(mappedBy = "portfolio")
    private List<PortfolioStock> PortfolioStock;

    public Portfolio() {
    };

    public Portfolio(long portfolioId, String portfolioName, String description, double capitalAmt) {
        this.portfolioId = portfolioId;
        this.portfolioName = portfolioName;
        this.description = description;
        this.capitalAmt = capitalAmt;
    }

    public long getPortfolioId() {
        return portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public String getPortfolioDescription() {
        return description;
    }

    public double getPortfolioCapitalAmt() {
        return capitalAmt;
    }

    public User getUser() {
        return user;
    }

    public void setPortfolioId(long portfolioId) {
        this.portfolioId = portfolioId;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public void setPortfolioDescription(String portfolioDescription) {
        this.description = portfolioDescription;
    }

    public void setPortfolioCapitalAmt(double portfolioCapitalAmt) {
        this.capitalAmt = portfolioCapitalAmt;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
