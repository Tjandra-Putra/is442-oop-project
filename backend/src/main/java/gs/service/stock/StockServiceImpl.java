package gs.service.stock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import gs.entity.Stock;
import gs.inputModel.StockInputModel;
import gs.repository.StockRepo;
import jakarta.annotation.Resource;


@Service
public class StockServiceImpl implements StockService {

    @Resource
    public StockRepo stockRepo;

    private StockInputModel inputModel(Stock data){
        StockInputModel inputModel = new StockInputModel();
        inputModel.setTicker(String.valueOf(data.getTicker()));
        inputModel.setName(String.valueOf(data.getStockName()));

        return inputModel;
    }

    public List<StockInputModel> getStock(){
        List<Stock> stockQueryList = stockRepo.getStock();
        List<StockInputModel> stockList = new ArrayList<>();
        
        for (Stock data : stockQueryList){
            StockInputModel inputModel = inputModel(data);   
            stockList.add(inputModel);
        }
        return stockList;
    }

    public List<StockInputModel> getStockByTicker(String ticker){
        Stock stockQueryList = stockRepo.getStockByTicker(ticker);
        List<StockInputModel> stockList = new ArrayList<>();
        
        StockInputModel inputModel = inputModel(stockQueryList);   
        stockList.add(inputModel);

        return stockList;
    }

    public List<StockInputModel> getStockByName(String name){
        Stock stockQueryList = stockRepo.getStockByName(name);
        List<StockInputModel> stockList = new ArrayList<>();
        
        StockInputModel inputModel = inputModel(stockQueryList);   
        stockList.add(inputModel);

        return stockList;
    }

    public void addStock(String ticker, String stockName) {
        Stock newStock = new Stock();
        newStock.setTicker(ticker);
        newStock.setStockName(stockName);

        try {
            stockRepo.save(newStock);
        }
        catch (DataAccessException e){
            System.out.println(e.getMessage());
        }
    }
}
