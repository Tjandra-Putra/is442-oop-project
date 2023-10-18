package gs.entity;

import java.io.Serializable;

public class PortfolioStockId implements Serializable {
    private String ticker;
    private Long portfolioId;

    public PortfolioStockId(){};

    public PortfolioStockId(String ticker, Long portfolioId){
        this.ticker = ticker;
        this.portfolioId = portfolioId;
    }
}
