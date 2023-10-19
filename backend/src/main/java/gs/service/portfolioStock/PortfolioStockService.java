package gs.service.portfolioStock;

import java.util.List;

import gs.common.ApiModel;
import gs.common.RequestModel;
import gs.inputModel.PortfolioStockInputModel;
import gs.inputModel.StockAllocationInputModel;
import jakarta.servlet.http.HttpServletResponse;

public interface PortfolioStockService {
    
    List<PortfolioStockInputModel> getPortfolioStock(String portfolioId);

    List<StockAllocationInputModel> getPortfolioStockAllocation(String portfolioId);

    List<PortfolioStockInputModel> getPortfolioStockByTicker(String portfolioId, String ticker);

    ApiModel addPortfolioStock(HttpServletResponse response, RequestModel requestModel, ApiModel apiModel, String portfolioId) throws Exception;
}
