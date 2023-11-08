package gs.service.stock;

import java.util.List;

import gs.inputModel.StockInputModel;

public interface StockService {
    List<StockInputModel> getStock();

    List<StockInputModel> getStockByTicker(String ticker);

    List<StockInputModel> getStockByName(String name);

    void addStock(String ticker, String stockName);

}
