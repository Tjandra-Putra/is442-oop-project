package gs.service.portfolio;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import gs.entity.Portfolio;
import gs.inputModel.portfolioInputModel;
import gs.inputModel.userInputModel;
import gs.repository.PortfolioRepo;
import jakarta.annotation.Resource;

@Service
public class PortfolioServiceImpl implements PortfolioService{
    @Resource
    protected PortfolioRepo portfolioRepo;

    public List<portfolioInputModel> getPortfolio(String userid){
        List<Object[]> portfolioQueryList = portfolioRepo.getPortfolioByUserId(userid);
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

    public List<portfolioInputModel> getPortfolioById(String userid, String portfolioId){
        List<Object[]> portfolioQueryList = portfolioRepo.getPortfolioById(userid, portfolioId);
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

    public void addPortfolio(List<Portfolio> portfolio) throws Exception{
        List<Portfolio> portfolioList = portfolio;

        for (Portfolio data : portfolioList){
            portfolioRepo.save(data);
        }
    }
}
