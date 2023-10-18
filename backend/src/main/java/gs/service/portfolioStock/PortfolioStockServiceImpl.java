package gs.service.portfolioStock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import gs.entity.PortfolioStock;
import gs.inputModel.PortfolioStockInputModel;
import gs.repository.PortfolioStockRepo;
import jakarta.annotation.Resource;

@Service
public class PortfolioStockServiceImpl implements PortfolioStockService{
    @Resource
    protected PortfolioStockRepo portfolioStockRepo;

    public List<PortfolioStockInputModel> getPortfolioStock(String portfolioId){
        List<PortfolioStock> portfolioStockQueryList = portfolioStockRepo.getPortfolioStockByPortfolioId(portfolioId);

        List<PortfolioStockInputModel> portfolioStockList = new ArrayList<>();

        for (PortfolioStock data : portfolioStockQueryList) {
            PortfolioStockInputModel inputModel = new PortfolioStockInputModel();
            inputModel.setTicker(data.getStock().getTicker());
            inputModel.setPortfolioId(data.getPortfolio().getPortfolioId());
            inputModel.setQuantity(data.getQuantity());
            inputModel.setBuyDate(data.getBuyDate());
            inputModel.setPrice(data.getPrice());

            portfolioStockList.add(inputModel);
        }

        return portfolioStockList;
    }
    
}
