package gs.service.history;

import java.util.List;

import gs.entity.History;
import gs.entity.PortfolioStock;
import gs.inputModel.HistoryInputModel;

public interface HistoryService {
    
    List<HistoryInputModel> getAllHistory();

    List<HistoryInputModel> getHistoryByTicker(String ticker);

    List<HistoryInputModel> getWeeklyHistoryByTicker(String ticker);

    List<PortfolioStock> getIndividualStock(String portfolioId, String ticker);

    List<HistoryInputModel> getYearlyClosingByTicker(String ticker);
}
