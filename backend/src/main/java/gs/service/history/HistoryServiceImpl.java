package gs.service.history;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;


import gs.inputModel.historyInputModel;
import gs.repository.HistoryRepo;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Resource
    public HistoryRepo historyRepo;
    
    public List<historyInputModel> getAllHistory(){
        List<Object[]> stockQueryList = historyRepo.getAllHistory();
        List<historyInputModel> stockList = new ArrayList<>();

        for (Object[] data : stockQueryList){
            historyInputModel inputModel = new historyInputModel();
            
            inputModel.setTicker(String.valueOf(data[2]));
            inputModel.setDate(String.valueOf(data[1]));
            inputModel.setAdjClosePrice((double) (data[0]));

            stockList.add(inputModel);
        }

        return stockList;

    }

    public List<historyInputModel> getHistoryByTicker(String ticker){
        List<Object[]> stockQueryList = historyRepo.getHistoryByTicker(ticker);
        List<historyInputModel> stockList = new ArrayList<>();
        
        for (Object[] data : stockQueryList){
            historyInputModel inputModel = new historyInputModel();

            inputModel.setTicker(String.valueOf(data[0]));
            inputModel.setDate(String.valueOf(data[1]));
            inputModel.setAdjClosePrice(Integer.parseInt(String.valueOf(data[2])));

            stockList.add(inputModel);
        }
        return stockList;
    }
    
}
