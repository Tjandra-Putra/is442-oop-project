package gs.service.stock;

import java.util.List;

import gs.common.RequestModel;
import gs.inputModel.stockInputModel;
import gs.model.stock.Stock;
import jakarta.servlet.http.HttpServletResponse;

public interface StockService {
    List<stockInputModel> getStock();

    List<stockInputModel> getStockByTicker(String ticker);

    List<stockInputModel> getStockByName(String name);

    void addStock(HttpServletResponse response, RequestModel requestModel) throws Exception;
}
