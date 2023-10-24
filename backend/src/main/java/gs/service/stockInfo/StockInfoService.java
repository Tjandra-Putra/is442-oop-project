package gs.service.stockInfo;

import java.util.List;

import gs.common.RequestModel;
import gs.entity.StockInfo;
import gs.inputModel.StockInfoInputModel;
import jakarta.servlet.http.HttpServletResponse;

public interface StockInfoService {
    List<StockInfoInputModel> getStockInfo();

    List<StockInfoInputModel> getStockInfoByTicker(String ticker);

    List<StockInfoInputModel> getStockInfoByPortfolio() throws Exception;

     List<StockInfoInputModel> updateStockInfoByPortfolio() throws Exception;
}
