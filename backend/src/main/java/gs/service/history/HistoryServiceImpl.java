package gs.service.history;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import gs.entity.History;
import gs.inputModel.HistoryInputModel;
import gs.repository.HistoryRepo;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Resource
    public HistoryRepo historyRepo;

    // Input into inputModel
    private HistoryInputModel inputModel(History data){
        HistoryInputModel inputModel = new HistoryInputModel();
        inputModel.setTicker(data.getStock().getTicker());
        inputModel.setDate(String.valueOf(data.getDate()));
        inputModel.setOpenPrice(data.getOpenPrice());
        inputModel.setHighPrice(data.getHighPrice());
        inputModel.setLowPrice(data.getLowPrice());
        inputModel.setAdjClosePrice(data.getAdjClosePrice());

        return inputModel;
    }
    
    public List<HistoryInputModel> getAllHistory(){
        List<History> stockQueryList = historyRepo.getAllHistory();
        List<HistoryInputModel> stockList = new ArrayList<>();

        for (History data : stockQueryList){
            HistoryInputModel inputModel = inputModel(data);
            stockList.add(inputModel);
        }

        return stockList;

    }

    public List<HistoryInputModel> getHistoryByTicker(String ticker){
        History stockQueryList = historyRepo.getHistoryByTicker(ticker).get(0);

        List<HistoryInputModel> tickerList = new ArrayList<>();

        HistoryInputModel inputModel = inputModel(stockQueryList);
        tickerList.add(inputModel);

        return tickerList;
    }
    
}
