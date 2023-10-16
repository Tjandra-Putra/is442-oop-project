package gs.service.portfolio.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import gs.model.portfolio.Portfolio;
import gs.repository.PortfolioRepo;
import gs.service.portfolio.PortfolioService;
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

    public void addPortfolio(List<Portfolio> portfolio) throws Exception{
        List<Portfolio> portfolioList = portfolio;

        for (Portfolio data : portfolioList){
            portfolioRepo.save(data);
        }
    }
}
