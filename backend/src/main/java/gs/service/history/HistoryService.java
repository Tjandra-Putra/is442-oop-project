package gs.service.history;

import java.util.List;

import gs.inputModel.historyInputModel;

public interface HistoryService {
    List<historyInputModel> getAllHistory();
    List<historyInputModel> getHistoryByTicker(String ticker);
}
