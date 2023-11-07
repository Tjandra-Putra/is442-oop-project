package gs.service.stock;

import java.util.List;

import gs.common.RequestModel;
import gs.inputModel.StockInputModel;
import jakarta.servlet.http.HttpServletResponse;

public interface StockService {
    List<StockInputModel> getStock();

    List<StockInputModel> getStockByTicker(String ticker);

    List<StockInputModel> getStockByName(String name);

    void addStock(String ticker, String stockName);

    void addStock(HttpServletResponse response, RequestModel requestModel) throws Exception;
}
