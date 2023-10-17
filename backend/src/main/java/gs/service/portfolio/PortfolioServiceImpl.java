package gs.service.portfolio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import gs.common.ApiModel;
import gs.common.DataRequestModel;
import gs.common.RequestModel;
import gs.entity.Portfolio;
import gs.entity.User;
import gs.inputModel.portfolioInputModel;
import gs.inputModel.userInputModel;
import gs.repository.PortfolioRepo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class PortfolioServiceImpl implements PortfolioService{
    @Resource
    protected PortfolioRepo portfolioRepo;

    public List<portfolioInputModel> getPortfolio(String userId){
        List<Object[]> portfolioQueryList = portfolioRepo.getPortfolioByUserId(userId);
        List<portfolioInputModel> portfolioList = new ArrayList<>();

        for (Object[] data : portfolioQueryList) {
            portfolioInputModel inputModel = new portfolioInputModel();
            
            inputModel.setPortfolioId((Long) data[0]);
            inputModel.setCapitalAmt((double) data[1]);
            inputModel.setDescription(String.valueOf(data[2]));
            inputModel.setPortfolioName(String.valueOf(data[3]));
            inputModel.setUserId((Long) data[4]);

            portfolioList.add(inputModel);
            
        }
        return portfolioList;
    }

    public List<portfolioInputModel> getPortfolioById(String userId, String portfolioId){
        List<Object[]> portfolioQueryList = portfolioRepo.getPortfolioById(userId, portfolioId);
        List<portfolioInputModel> portfolioList = new ArrayList<>();

        for (Object[] data : portfolioQueryList) {
            portfolioInputModel inputModel = new portfolioInputModel();
            
            inputModel.setPortfolioId((Long) data[0]);
            inputModel.setCapitalAmt((double) data[1]);
            inputModel.setDescription(String.valueOf(data[2]));
            inputModel.setPortfolioName(String.valueOf(data[3]));
            inputModel.setUserId((Long) data[4]);

            portfolioList.add(inputModel);
            
        }
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
            System.out.println("=======HERE=========");
            portfolioRepo.save(newPortfolio);
            System.out.println("=======FAILED=========");
            // get ID
            portfolioInputModel inputModel = new portfolioInputModel();
            inputModel.setPortfolioId(newPortfolio.getPortfolioId());
            inputModel.setPortfolioName(newPortfolio.getPortfolioName());
            inputModel.setCapitalAmt(newPortfolio.getPortfolioCapitalAmt());
            inputModel.setDescription(newPortfolio.getPortfolioDescription());
            inputModel.setUserId(user.getUserId());

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
}
