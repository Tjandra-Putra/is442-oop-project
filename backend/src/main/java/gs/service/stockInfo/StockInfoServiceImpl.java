package gs.service.stockInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.SourceType;
import org.springframework.stereotype.Service;

import gs.common.DataRequestModel;
import gs.common.RequestModel;
import gs.entity.Portfolio;
import gs.entity.StockInfo;
import gs.inputModel.PortfolioInputModel;
import gs.inputModel.StockInfoInputModel;
import gs.repository.StockInfoRepo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class StockInfoServiceImpl implements StockInfoService {

    @Resource
    public StockInfoRepo stockInfoRepo;

    private StockInfoInputModel inputModel(StockInfo data){
        StockInfoInputModel inputModel = new StockInfoInputModel();
        // inputModel.setTicker(String.valueOf(data.getTicker()));
        inputModel.setCountry(String.valueOf(data.getCountry()));
        inputModel.setCurrency(String.valueOf(data.getCurrency()));
        inputModel.setIndustry(String.valueOf(data.getIndustry()));
        inputModel.setSector(String.valueOf(data.getSector()));

        return inputModel;
    }

    public List<StockInfoInputModel> getStockInfo() {
        List<StockInfo> stockInfoQueryList = stockInfoRepo.getStockInfo();
        List<StockInfoInputModel> stockInfoList = new ArrayList<>();

        for (StockInfo data : stockInfoQueryList) {
            StockInfoInputModel inputModel = inputModel(data);
            stockInfoList.add(inputModel);
        }

        return stockInfoList;
    }

    public List<StockInfoInputModel> getStockInfoByTicker(String ticker){
        List<StockInfo> stockInfoQueryList = stockInfoRepo.getStockInfoByTicker(ticker);
        List<StockInfoInputModel> stockInfoList = new ArrayList<>();
        
        for (StockInfo data : stockInfoQueryList){
            // Logic to populate inputModel is missing here
            // Example: inputModel.setSomeProperty(data[0]);
            // Add inputModel to stockInfoList

            StockInfoInputModel inputModel = inputModel(data);
            stockInfoList.add(inputModel);
        }

        return stockInfoList;
    }
}