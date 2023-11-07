package gs.service.stockInfo;

import java.util.List;
import gs.inputModel.StockInfoInputModel;

public interface StockInfoService {
    List<StockInfoInputModel> getStockInfo();

    List<StockInfoInputModel> getStockInfoByTicker(String ticker);

    List<StockInfoInputModel> getStockInfoByPortfolio() throws Exception;

     List<StockInfoInputModel> updateStockInfoByPortfolio() throws Exception;
}
