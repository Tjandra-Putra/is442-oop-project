package gs.service.portfolioStock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private String dateFormatter(Date buyDate, PortfolioStock individualStockQuery){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd");
        String buyDateFormatted = dateFormatter.format(individualStockQuery.getBuyDate());

        return buyDateFormatted;
    }

    public List<PortfolioStockInputModel> getPortfolioStock(String portfolioId){
        List<PortfolioStock> portfolioStockQueryList = portfolioStockRepo.getPortfolioStockByPortfolioId(portfolioId);

        List<PortfolioStockInputModel> portfolioStockList = new ArrayList<>();

        for (PortfolioStock data : portfolioStockQueryList) {
            PortfolioStockInputModel inputModel = new PortfolioStockInputModel();
            inputModel.setTicker(data.getStock().getTicker());
            inputModel.setPortfolioId(data.getPortfolio().getPortfolioId());
            inputModel.setQuantity(data.getQuantity());
            inputModel.setBuyDate(dateFormatter(data.getBuyDate(), data));
            inputModel.setPrice(data.getPrice());

            portfolioStockList.add(inputModel);
        }

        return portfolioStockList;
    }

    public List<PortfolioStockInputModel> getPortfolioStockByTicker(String portfolioId, String ticker){
        PortfolioStock individualStockQuery = portfolioStockRepo.getIndividualStock(portfolioId, ticker).get(0);

        List<PortfolioStockInputModel> portfolioStockList = new ArrayList<>();

        PortfolioStockInputModel inputModel = new PortfolioStockInputModel();
        inputModel.setTicker(individualStockQuery.getStock().getTicker());
        inputModel.setPortfolioId(individualStockQuery.getPortfolio().getPortfolioId());
        inputModel.setQuantity(individualStockQuery.getQuantity());
        
        inputModel.setBuyDate(dateFormatter(individualStockQuery.getBuyDate(), individualStockQuery));

        inputModel.setPrice(individualStockQuery.getPrice());

        portfolioStockList.add(inputModel);

        return portfolioStockList;
    }
    
}
