package gs.service.portfolio;

import java.util.List;

import gs.model.portfolio.Portfolio;

public class PortfolioService {
    List<Object[]> getPortfolio();

    void addPortfolio(List<Portfolio> portfolios) throws Exception;    
}
