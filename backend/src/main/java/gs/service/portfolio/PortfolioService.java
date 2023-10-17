package gs.service.portfolio;

import java.util.List;

import gs.entity.Portfolio;
import gs.inputModel.portfolioInputModel;

public interface PortfolioService {
    List<portfolioInputModel> getPortfolio(String id);

    void addPortfolio(List<Portfolio> portfolio) throws Exception;  
    
    
}
