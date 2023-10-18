package gs.service.portfolio;

import java.util.List;

import gs.common.ApiModel;
import gs.common.RequestModel;
import gs.entity.Portfolio;
import gs.inputModel.PortfolioInputModel;
import jakarta.servlet.http.HttpServletResponse;

public interface PortfolioService {
    List<PortfolioInputModel> getPortfolio(String userId);

    List<PortfolioInputModel> getPortfolioById(String userId, String portfolioId);

    ApiModel addPortfolio(HttpServletResponse response, RequestModel requestModel, ApiModel apiModel, String userId) throws Exception;
    
    ApiModel editPortfolio(HttpServletResponse response, RequestModel requestModel, ApiModel myApiModel, String userId, String portfolioId) throws Exception;

    ApiModel deletePortfolio(HttpServletResponse response, ApiModel myApiModel, String userId, String portfolioId);
    
    
}
