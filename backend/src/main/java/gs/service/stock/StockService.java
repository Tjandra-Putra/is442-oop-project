package gs.service.stock;

import java.util.List;

import gs.common.RequestModel;
import gs.entity.Stock;
import gs.inputModel.stockInputModel;
import jakarta.servlet.http.HttpServletResponse;

public interface StockService {
    List<stockInputModel> getStock();

    List<stockInputModel> getStockByTicker(String ticker);

    List<stockInputModel> getStockByName(String name);

    void addStock(String ticker, String stockName);

    void addStock(HttpServletResponse response, RequestModel requestModel) throws Exception;
}
