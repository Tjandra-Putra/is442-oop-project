package gs.service.portfolio;

import java.util.List;

import gs.entity.Portfolio;

public interface PortfolioService {
    List<Object[]> getPortfolio();

    void addPortfolio(List<Portfolio> portfolio) throws Exception;  
    
    
}
