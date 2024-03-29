package gs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gs.entity.StockInfo;

public interface StockInfoRepo extends JpaRepository<StockInfo, String>{
    
    @Query(value = "select * from stock_info;", nativeQuery = true)
    List<StockInfo> getStockInfo();

    @Query(value = "select * from stock_info where ticker = ? ;", nativeQuery = true)
    StockInfo getStockInfoByTicker(String ticker);
    
}
