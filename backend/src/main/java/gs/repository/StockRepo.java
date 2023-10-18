package gs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.CrudRepository;

import gs.entity.Stock;

public interface StockRepo extends JpaRepository<Stock, String>{

    @Query(value = "select * from stock;", nativeQuery = true)
    List<Object[]> getStock();
    
    @Query(value = "select * from stock where ticker = ?;", nativeQuery = true)
    List<Object[]> getStockByTicker(String ticker);

    @Query(value = "select * from stock where stock_name = ?;", nativeQuery = true)
    List<Object[]> getStockByName(String name);
}