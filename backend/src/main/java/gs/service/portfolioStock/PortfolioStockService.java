package gs.service.portfolioStock;

import java.util.List;

import gs.common.ApiModel;
import gs.common.RequestModel;
import gs.inputModel.AllocationInputModel;
import gs.inputModel.PortfolioStockInputModel;
import gs.inputModel.AllocationInputModel;
import jakarta.servlet.http.HttpServletResponse;

public interface PortfolioStockService {
    
    List<PortfolioStockInputModel> getPortfolioStock(String portfolioId);

    List<AllocationInputModel> getPortfolioStockAllocation(String portfolioId);

    List<AllocationInputModel> getPortfolioStockTypeAllocation(String portfolioId, String allocationCategory);

    List<PortfolioStockInputModel> getPortfolioStockByTicker(String portfolioId, String ticker);

    ApiModel addPortfolioStock(HttpServletResponse response, RequestModel requestModel, ApiModel apiModel, String portfolioId) throws Exception;

    ApiModel deletePortfolioStock(HttpServletResponse response, ApiModel apiModel, String portfolioId, String ticker) throws Exception;
}
