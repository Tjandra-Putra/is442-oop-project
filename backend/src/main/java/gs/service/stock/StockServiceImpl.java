package gs.service.stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.SourceType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import gs.common.DataRequestModel;
import gs.common.RequestModel;
import gs.entity.Stock;
import gs.inputModel.StockInputModel;
import gs.repository.StockRepo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;


@Service
public class StockServiceImpl implements StockService {

    @Resource
    public StockRepo stockRepo;

    public List<StockInputModel> getStock(){
        List<Stock> stockQueryList = stockRepo.getStock();
        List<StockInputModel> stockList = new ArrayList<>();
        
        for (Stock data : stockQueryList){
            StockInputModel inputModel = new StockInputModel();

            inputModel.setTicker(String.valueOf(data.getTicker()));
            inputModel.setName(String.valueOf(data.getStockName()));
            
            stockList.add(inputModel);
        }
        return stockList;
    }

    public List<StockInputModel> getStockByTicker(String ticker){
        List<Stock> stockQueryList = stockRepo.getStockByTicker(ticker);
        List<StockInputModel> stockList = new ArrayList<>();
        
        for (Stock data : stockQueryList){
            StockInputModel inputModel = new StockInputModel();

            inputModel.setTicker(String.valueOf(data.getTicker()));
            inputModel.setName(String.valueOf(data.getStockName()));
            
            stockList.add(inputModel);
        }
        return stockList;
    }

    public List<StockInputModel> getStockByName(String name){
        List<Stock> stockQueryList = stockRepo.getStockByName(name);
        List<StockInputModel> stockList = new ArrayList<>();
        
        for (Stock data : stockQueryList){
            StockInputModel inputModel = new StockInputModel();

            inputModel.setTicker(String.valueOf(data.getTicker()));
            inputModel.setName(String.valueOf(data.getStockName()));
            
            stockList.add(inputModel);
        }
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

    public void addStock(HttpServletResponse response, RequestModel requestModel) throws Exception{
        // PAYLOAD
        // {
        //     "data": [
        //         {
        //             "fieldName": "ticker",
        //             "value": "IBM"
        //         },
        //         {
        //             "fieldName": "name",
        //             "value": "International Business Machines"
        //         }
        // }

        Stock newStock = new Stock();
        for (DataRequestModel fe : requestModel.getData()){
            System.out.println("=====DATA====");
            System.out.println(requestModel.getData());
            if (fe.getFieldName().equalsIgnoreCase("ticker")){
                System.out.println("=====TICKER====");
                System.out.println(fe.getValue());
                newStock.setTicker(fe.getValue());
            }

            if (fe.getFieldName().equalsIgnoreCase("name")){
                System.out.println("=====Name====");
                System.out.println(fe.getValue());
                newStock.setStockName(fe.getValue());
            }

            
        }
        try{
            stockRepo.save(newStock);
        }
        catch (Exception e){
            System.out.println("=====hERERE=====");
            System.out.println(e.getMessage());
        }
    }
    
}
