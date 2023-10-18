package gs.service.portfolio;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import gs.common.ApiModel;
import gs.common.DataRequestModel;
import gs.common.RequestModel;
import gs.entity.Portfolio;
import gs.entity.User;
import gs.inputModel.PortfolioInputModel;
import gs.inputModel.UserInputModel;
import gs.repository.PortfolioRepo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class PortfolioServiceImpl implements PortfolioService{
    @Resource
    protected PortfolioRepo portfolioRepo;

    // inputModel fitting methood
    private PortfolioInputModel inputModel(Portfolio data){
        PortfolioInputModel inputModel = new PortfolioInputModel();
        inputModel.setPortfolioId((Long) data.getPortfolioId());
        inputModel.setCapitalAmt((double) data.getPortfolioCapitalAmt());
        inputModel.setDescription(String.valueOf(data.getPortfolioDescription()));
        inputModel.setPortfolioName(String.valueOf(data.getPortfolioName()));
        inputModel.setUserId((Long) data.getUser().getUserId());

        return inputModel;
    }

    public List<PortfolioInputModel> getPortfolio(String userId){
        List<Portfolio> portfolioQueryList = portfolioRepo.getPortfolioByUserId(userId);
        List<PortfolioInputModel> portfolioList = new ArrayList<>();

        for (Portfolio data : portfolioQueryList) {
            PortfolioInputModel inputModel = inputModel(data);
            portfolioList.add(inputModel);
            
        }
        return portfolioList;
    }

    public List<PortfolioInputModel> getPortfolioById(String userId, String portfolioId){
        Portfolio portfolioQueryList = portfolioRepo.getPortfolioById(userId, portfolioId).get(0);
        
        List<PortfolioInputModel> portfolioList = new ArrayList<>();
        PortfolioInputModel inputModel = inputModel(portfolioQueryList);
        portfolioList.add(inputModel);
            
        return portfolioList;
    }

    public ApiModel addPortfolio(HttpServletResponse response, RequestModel requestModel, ApiModel apiModel, String userId) throws DataAccessException{
        try {
            Portfolio newPortfolio = new Portfolio();
            for (DataRequestModel fe : requestModel.getData()){    

                if (fe.getFieldName().equalsIgnoreCase("capitalAmt")){
                    newPortfolio.setPortfolioCapitalAmt(Double.parseDouble(fe.getValue()));
                }

                if (fe.getFieldName().equalsIgnoreCase("description")){
                    newPortfolio.setPortfolioDescription(fe.getValue());
                }
                
                if (fe.getFieldName().equalsIgnoreCase("portfolioName")){
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
        // Optionally, rethrow as a custom exception
            // INPUT LOGGER for error messages
            System.out.println(ex.getMessage());
            apiModel.setMessage("An error occurred while performing the database operation.");
        }
                
        // }

        System.out.println("==========STATUS==================");
        System.out.println(response.getStatus());
        apiModel.setStatus(String.valueOf(response.getStatus()));

        System.out.println("==========ERROR MESSAGE==================");
        // System.out.println(response.get);

        return apiModel;
    }

    public ApiModel editPortfolioCapitalAmt(HttpServletResponse response, RequestModel requestModel, ApiModel myApiModel, String userId, String portfolioId) throws DataAccessException {
        try {
            Portfolio existingPortfolio = portfolioRepo.getPortfolioById(userId, portfolioId).get(0);

            boolean isNewCapitalAmt = requestModel.getData().get(0).getFieldName().equalsIgnoreCase("capitalAmt");

            if (isNewCapitalAmt){
                String fe = requestModel.getData().get(0).getValue();
                existingPortfolio.setPortfolioCapitalAmt(Double.parseDouble(fe));
            }

            // save to db
            portfolioRepo.save(existingPortfolio);

            PortfolioInputModel inputModel = inputModel(existingPortfolio);

            myApiModel.setMessage("Data updated successfully.");
            myApiModel.setData(inputModel);
        }
            
        catch (DataAccessException ex) {
        // Log the exception for debugging
        // Optionally, rethrow as a custom exception
            // INPUT LOGGER for error messages
            System.out.println(ex.getMessage());
            myApiModel.setMessage("An error occurred while performing the database operation.");
        }
                
        System.out.println("==========STATUS==================");
        System.out.println(response.getStatus());
        myApiModel.setStatus(String.valueOf(response.getStatus()));

        System.out.println("==========ERROR MESSAGE==================");
        // System.out.println(response.get);

        return myApiModel;
    }

    public ApiModel deletePortfolio(HttpServletResponse response, ApiModel myApiModel,String userId, String portfolioId) throws DataAccessException {
        try {
            Portfolio existingPortfolio = portfolioRepo.getPortfolioById(userId, portfolioId).get(0);

            // if (existingPortfolio){
            //     String fe = requestModel.getData().get(0).getValue();
            //     existingPortfolio.setPortfolioCapitalAmt(Double.parseDouble(fe));
            // }

            // save to db
            portfolioRepo.delete(existingPortfolio);
            PortfolioInputModel inputModel = inputModel(existingPortfolio);

            myApiModel.setMessage("Data deleted successfully.");
            myApiModel.setData(inputModel);
        }
            
        catch (DataAccessException ex) {
        // Log the exception for debugging
        // Optionally, rethrow as a custom exception
            // INPUT LOGGER for error messages
            System.out.println(ex.getMessage());
            myApiModel.setMessage("An error occurred while performing the database operation.");
        }
                
        System.out.println("==========STATUS==================");
        System.out.println(response.getStatus());
        myApiModel.setStatus(String.valueOf(response.getStatus()));

        System.out.println("==========ERROR MESSAGE==================");
        // System.out.println(response.get);

        return myApiModel;
    }
}
