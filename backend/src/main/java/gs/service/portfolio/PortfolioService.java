package gs.service.portfolio;

import java.util.List;

import gs.common.ApiModel;
import gs.common.RequestModel;
import gs.entity.Portfolio;
import gs.inputModel.portfolioInputModel;
import jakarta.servlet.http.HttpServletResponse;

public interface PortfolioService {
    List<portfolioInputModel> getPortfolio(String userId);

    List<portfolioInputModel> getPortfolioById(String userId, String portfolioId);

    ApiModel addPortfolio(HttpServletResponse response, RequestModel requestModel, ApiModel apiModel, String userId) throws Exception;  
    
    
}
