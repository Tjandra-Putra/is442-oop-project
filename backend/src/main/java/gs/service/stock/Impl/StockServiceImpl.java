package gs.service.stock.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import gs.common.DataRequestModel;
import gs.common.RequestModel;
import gs.entity.stock.Stock;
import gs.inputModel.stockInputModel;
import gs.repository.StockRepo;
import gs.service.stock.StockService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;


@Service
public class StockServiceImpl implements StockService {

    @Resource
    protected StockRepo stockRepo;

    public List<stockInputModel> getStock(){
        List<Object[]> stockQueryList = stockRepo.getStock();
        List<stockInputModel> stockList = new ArrayList<>();
        
        for (Object[] data : stockQueryList){
            stockInputModel inputModel = new stockInputModel();

            inputModel.setTicker(String.valueOf(data[0]));
            inputModel.setIndustry(String.valueOf(data[1]));
            inputModel.setSector(String.valueOf(data[2]));
            inputModel.setCountry(String.valueOf(data[3]));
            inputModel.setName(String.valueOf(data[4]));
            
            stockList.add(inputModel);
        }
        return stockList;
    }

    public List<stockInputModel> getStockByTicker(String ticker){
        List<Object[]> stockQueryList = stockRepo.getStockByTicker(ticker);
        List<stockInputModel> stockList = new ArrayList<>();
        
        for (Object[] data : stockQueryList){
            stockInputModel inputModel = new stockInputModel();

            inputModel.setTicker(String.valueOf(data[0]));
            inputModel.setIndustry(String.valueOf(data[1]));
            inputModel.setSector(String.valueOf(data[2]));
            inputModel.setCountry(String.valueOf(data[3]));
            inputModel.setName(String.valueOf(data[4]));
            
            stockList.add(inputModel);
        }
        return stockList;
    }

    public List<stockInputModel> getStockByName(String name){
        List<Object[]> stockQueryList = stockRepo.getStockByName(name);
        List<stockInputModel> stockList = new ArrayList<>();
        
        for (Object[] data : stockQueryList){
            stockInputModel inputModel = new stockInputModel();

            inputModel.setTicker(String.valueOf(data[0]));
            inputModel.setIndustry(String.valueOf(data[1]));
            inputModel.setSector(String.valueOf(data[2]));
            inputModel.setCountry(String.valueOf(data[3]));
            inputModel.setName(String.valueOf(data[4]));
            
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
        //             "fieldName": "industry",
        //             "value": "COMPUTER & OFFICE EQUIPMENT"
        //         },
        //         {
        //             "fieldName": "sector",
        //             "value": "TECHNOLOGY"
        //         },
        //         {
        //             "fieldName": "country",
        //             "value": "USA"
        //         },
        //         {
        //             "fieldName": "name",
        //             "value": "International Business Machines"
        //         }
        // }

        for (DataRequestModel fe : requestModel.getData()){

            Stock newStock = new Stock();

            if (fe.getFieldName().equalsIgnoreCase("ticker")){
                System.out.println(fe.getValue());
                newStock.setTicker(fe.getValue());
            }

            if (fe.getFieldName().equalsIgnoreCase("industry")){
                System.out.println(fe.getValue());
                newStock.setIndustry(fe.getValue());
            }

            if (fe.getFieldName().equalsIgnoreCase("sector")){
                System.out.println(fe.getValue());
                newStock.setSector(fe.getValue());
            }

            if (fe.getFieldName().equalsIgnoreCase("country")){
                System.out.println(fe.getValue());
                newStock.setCountry(fe.getValue());
            }

            if (fe.getFieldName().equalsIgnoreCase("name")){
                System.out.println(fe.getValue());
                newStock.setName(fe.getValue());
            }

            stockRepo.save(newStock);

        }
    }
    
}
