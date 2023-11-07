package gs.service.history;

import java.time.Month;
import java.util.List;
import java.util.Map;

import gs.entity.History;
import gs.entity.PortfolioStock;
import gs.inputModel.HistoryInputModel;
import gs.inputModel.MonthlyPrice;
import gs.inputModel.YearlyPriceInputmodel;

public interface HistoryService {
    
    List<HistoryInputModel> getAllHistory();

    List<HistoryInputModel> getHistoryByTicker(String ticker);

    List<HistoryInputModel> getWeeklyHistoryByTicker(String ticker);

    // List<PortfolioStock> getIndividualStock(String portfolioId, String ticker);

    List<HistoryInputModel> getYearlyClosingByTicker(String ticker);

    // Retrieve the portfolio values over the years -> working
    List<YearlyPriceInputmodel> getPortfolioValue(String userId);

    //  Retrieve the portfolio values over the months -> Done
    List<MonthlyPrice> getMonthlyPortfolioValue(String userId);

    List<MonthlyPrice> getQuarterlyPortfolioValue(String userId);

}
