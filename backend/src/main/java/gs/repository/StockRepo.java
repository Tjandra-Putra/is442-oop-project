package gs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import gs.model.stock.Stock;

public interface StockRepo extends CrudRepository<Stock, String>{

    @Query(value = "select * from stock;", nativeQuery = true)
    List<Object[]> getStock();
    
    @Query(value = "select * from stock where ticker = ?;", nativeQuery = true)
    List<Object[]> getStockByTicker(String ticker);

    @Query(value = "select * from stock where name = ?;", nativeQuery = true)
    List<Object[]> getStockByName(String name);
}