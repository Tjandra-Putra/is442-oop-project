package gs.service.portfolio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import gs.common.ApiModel;
import gs.common.DataRequestModel;
import gs.common.RequestModel;
import gs.entity.Portfolio;
import gs.entity.PortfolioStock;
import gs.entity.User;
import gs.inputModel.PortfolioInputModel;
import gs.repository.PortfolioRepo;
import gs.repository.PortfolioStockRepo;
import gs.repository.StockInfoRepo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class PortfolioServiceImpl implements PortfolioService {
    @Resource
    protected PortfolioRepo portfolioRepo;

    @Resource
    protected PortfolioStockRepo portfolioStockRepo;

    @Resource
    protected StockInfoRepo stockInfoRepo;

    // inputModel fitting methood
    private PortfolioInputModel inputModel(Portfolio data) {
        PortfolioInputModel inputModel = new PortfolioInputModel();
        long portfolioId = data.getPortfolioId();

        inputModel.setPortfolioId(portfolioId);
        inputModel.setCapitalAmt((double) data.getPortfolioCapitalAmt());
        inputModel.setDescription(String.valueOf(data.getPortfolioDescription()));
        inputModel.setPortfolioName(String.valueOf(data.getPortfolioName()));
        inputModel.setUserId((Long) data.getUser().getUserId());

        double portfolioValue = 0.0;

        List<PortfolioStock> portofolioStockQueryList =
                portfolioStockRepo.getPortfolioStockByPortfolioId(String.valueOf(portfolioId));

        for (PortfolioStock portfolioStock : portofolioStockQueryList) {
            portfolioValue += portfolioValueCalculation(portfolioStock.getQuantity(), stockInfoRepo
                    .getStockInfoByTicker(portfolioStock.getStock().getTicker()).getTodayPrice());
        }

        inputModel.setPortfolioValue(portfolioValue);

        return inputModel;
    }

    // caluclate porfolio's stock value
    private double portfolioValueCalculation(int quantity, double todayPrice) {
        return quantity * todayPrice;
    }

    public List<PortfolioInputModel> getPortfolio(String userId) {
        List<Portfolio> portfolioQueryList = portfolioRepo.getPortfolioByUserId(userId);
        List<PortfolioInputModel> portfolioList = new ArrayList<>();

        for (Portfolio data : portfolioQueryList) {
            PortfolioInputModel inputModel = inputModel(data);

            portfolioList.add(inputModel);

        }
        return portfolioList;
    }

    public List<PortfolioInputModel> getPortfolioById(String userId, String portfolioId) {
        Portfolio portfolioQueryList = portfolioRepo.getPortfolioById(userId, portfolioId);

        List<PortfolioInputModel> portfolioList = new ArrayList<>();
        PortfolioInputModel inputModel = inputModel(portfolioQueryList);
        portfolioList.add(inputModel);

        return portfolioList;
    }

    public ApiModel addPortfolio(HttpServletResponse response, RequestModel requestModel,
            ApiModel apiModel, String userId) throws DataAccessException {
        try {
            Portfolio newPortfolio = new Portfolio();
            for (DataRequestModel fe : requestModel.getData()) {

                if (fe.getFieldName().equalsIgnoreCase("capitalAmt")) {
                    newPortfolio.setPortfolioCapitalAmt(Double.parseDouble(fe.getValue()));
                }

                if (fe.getFieldName().equalsIgnoreCase("description")) {
                    newPortfolio.setPortfolioDescription(fe.getValue());
                }

                if (fe.getFieldName().equalsIgnoreCase("portfolioName")) {
                    newPortfolio.setPortfolioName(fe.getValue());
                }
            }

            User user = new User();
            user.setUserId(Long.parseLong(userId));

            newPortfolio.setUser(user);

            // save to db
            portfolioRepo.save(newPortfolio);

            // instantiate PortfolioInputModel
            PortfolioInputModel inputModel = inputModel(newPortfolio);

            apiModel.setMessage("Data saved successfully.");
            apiModel.setData(inputModel);
        }

        catch (DataAccessException ex) {
            // Log the exception for debugging
            apiModel.setMessage("An error occurred while performing the database operation.");
        }

        apiModel.setStatus(String.valueOf(response.getStatus()));
        return apiModel;
    }

    public ApiModel editPortfolio(HttpServletResponse response, RequestModel requestModel,
            ApiModel myApiModel, String userId, String portfolioId) throws DataAccessException {
        try {
            Portfolio existingPortfolio =
                    portfolioRepo.getPortfolioById(userId, portfolioId);

            for (DataRequestModel fe : requestModel.getData()) {

                if (fe.getFieldName().equalsIgnoreCase("capitalAmt")) {
                    existingPortfolio.setPortfolioCapitalAmt(Double.parseDouble(fe.getValue()));
                }

                if (fe.getFieldName().equalsIgnoreCase("description")) {
                    existingPortfolio.setPortfolioDescription(fe.getValue());
                }

                if (fe.getFieldName().equalsIgnoreCase("portfolioName")) {
                    existingPortfolio.setPortfolioName(fe.getValue());
                }
            }

            // save to db
            portfolioRepo.save(existingPortfolio);

            PortfolioInputModel inputModel = inputModel(existingPortfolio);

            myApiModel.setMessage("Data updated successfully.");
            myApiModel.setData(inputModel);
        }

        catch (DataAccessException ex) {
            // Log the exception for debugging
            myApiModel.setMessage("An error occurred while performing the database operation.");
        }

        myApiModel.setStatus(String.valueOf(response.getStatus()));

        return myApiModel;
    }

    public ApiModel deletePortfolio(HttpServletResponse response, ApiModel myApiModel,
            String userId, String portfolioId) throws DataAccessException {
        try {
            Portfolio existingPortfolio =
                    portfolioRepo.getPortfolioById(userId, portfolioId);

            // save to db
            portfolioRepo.delete(existingPortfolio);
            PortfolioInputModel inputModel = inputModel(existingPortfolio);

            myApiModel.setMessage("Data deleted successfully.");
            myApiModel.setData(inputModel);
        }

        catch (DataAccessException ex) {
            // Log the exception for debugging
            myApiModel.setMessage("An error occurred while performing the database operation.");
        }

        myApiModel.setStatus(String.valueOf(response.getStatus()));

        return myApiModel;
    }
}
