package gs.service.stockInfo;

import java.util.List;

import gs.common.RequestModel;
import gs.entity.StockInfo;
import gs.inputModel.stockInfoInputModel;
import jakarta.servlet.http.HttpServletResponse;

public interface StockInfoService {
    List<stockInfoInputModel> getStockInfo();
}
