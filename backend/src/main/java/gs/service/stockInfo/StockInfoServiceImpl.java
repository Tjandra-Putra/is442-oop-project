package gs.service.stockInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.SourceType;
import org.springframework.stereotype.Service;

import gs.common.DataRequestModel;
import gs.common.RequestModel;
import gs.entity.StockInfo;
import gs.inputModel.stockInfoInputModel;
import gs.repository.StockInfoRepo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class StockInfoServiceImpl implements StockInfoService {

    @Resource
    public StockInfoRepo stockInfoRepo;

    public List<stockInfoInputModel> getStockInfo(){
        List<Object[]> stockInfoQueryList = stockInfoRepo.getStockInfo();
        List<stockInfoInputModel> stockInfoList = new ArrayList<>();
        
        for (Object[] data : stockInfoQueryList){
            stockInfoInputModel inputModel = new stockInfoInputModel();
            // Logic to populate inputModel is missing here
            // Example: inputModel.setSomeProperty(data[0]);
            // Add inputModel to stockInfoList

            inputModel.setTicker(String.valueOf(data[0]));
            inputModel.setCountry(String.valueOf(data[1]));
            inputModel.setCurrency(String.valueOf(data[2]));
            inputModel.setIndustry(String.valueOf(data[3]));
            inputModel.setSector(String.valueOf(data[4]));
            stockInfoList.add(inputModel);
        }

        return stockInfoList;
    }
}