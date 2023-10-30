package gs.service.history;

import java.util.List;

import gs.inputModel.HistoryInputModel;

public interface HistoryService {
    
    List<HistoryInputModel> getAllHistory();

    List<HistoryInputModel> getHistoryByTicker(String ticker);

    List<HistoryInputModel> getWeeklyHistoryByTicker(String ticker);
}
