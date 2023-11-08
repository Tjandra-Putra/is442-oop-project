package gs.service.history;

import java.util.*;
import gs.inputModel.*;


public interface HistoryService {
    
    List<HistoryInputModel> getAllHistory();

    List<HistoryInputModel> getHistoryPriceByTicker(String ticker);
    
    List<HistoryInputModel> getHistoryByTicker(String ticker);

    List<HistoryInputModel> getWeeklyHistoryByTicker(String ticker);

    List<HistoryInputModel> getStockHistoryPriceByDate(String ticker, String buyDate);

    List<HistoryInputModel> getYearlyClosingByTicker(String ticker);

    // Retrieve the portfolio values over the years -> working
    // List<YearlyPriceInputmodel> getPortfolioValue(String userId);

    ArrayList<TreeMap<Integer, Double>> getAnnualPortfolioValue(String portfolioId);

    //  Retrieve the portfolio values over the months -> Done
    ArrayList<TreeMap<Integer, TreeMap<Integer, Double>>> getMonthlyPortfolioValue(String portfolioId);

    //  Retrieve the portfolio values over quarters
    ArrayList<TreeMap<Integer, TreeMap<Integer, Double>>> getQuarterlyPortfolioValue(String portfolioId);

}
