package gs.service.portfolio.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import gs.model.user.User;
import gs.repository.PortfolioRepo;
import gs.repository.UserRepo;
import gs.service.user.UserService;
import jakarta.annotation.Resource;

@Service
public class PortfolioServiceImpl implements PortfolioService{
    @Resource
    protected PortfolioRepo portfolioRepo;

    public List<Object[]> getPortfolio(){
        List<Object[]> PortfolioList = portfolioRepo.getPortfolio();

        for (Object[] data : PortfolioList) {
            System.out.println(data);
        }
        return PortfolioList;
    }

    public void addPortfolio(List<Portfolio> portfolios) throws Exception{
        List<Portfolio> portfoliosList = portfolios;

        for (Portfolio data : portfoliosList){
            portfolioRepo.save(data);
        }
    }
}
