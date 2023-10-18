package gs.service.portfolioStock;

import java.util.List;

import gs.inputModel.PortfolioStockInputModel;

public interface PortfolioStockService {
    
    List<PortfolioStockInputModel> getPortfolioStock(String portfolioId);
}
