package gs.service.stock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.SourceType;
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
        List<Object[]> stockQueryList = stockRepo.getStock();
        List<StockInputModel> stockList = new ArrayList<>();
        
        for (Object[] data : stockQueryList){
            StockInputModel inputModel = new StockInputModel();

            inputModel.setTicker(String.valueOf(data[0]));
            inputModel.setName(String.valueOf(data[1]));
            
            stockList.add(inputModel);
        }
        return stockList;
    }

    public List<StockInputModel> getStockByTicker(String ticker){
        List<Object[]> stockQueryList = stockRepo.getStockByTicker(ticker);
        List<StockInputModel> stockList = new ArrayList<>();
        
        for (Object[] data : stockQueryList){
            StockInputModel inputModel = new StockInputModel();

            inputModel.setTicker(String.valueOf(data[0]));
            inputModel.setName(String.valueOf(data[1]));
            
            stockList.add(inputModel);
        }
        return stockList;
    }

    public List<StockInputModel> getStockByName(String name){
        List<Object[]> stockQueryList = stockRepo.getStockByName(name);
        List<StockInputModel> stockList = new ArrayList<>();
        
        for (Object[] data : stockQueryList){
            StockInputModel inputModel = new StockInputModel();

            inputModel.setTicker(String.valueOf(data[0]));
            inputModel.setName(String.valueOf(data[1]));
            
            stockList.add(inputModel);
        }
        return stockList;
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
