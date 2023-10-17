package gs.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.jpa.repository.JpaRepository;


import gs.entity.History;

public interface HistoryRepo extends JpaRepository<History, String>{

    @Query(value = "select * from history;", nativeQuery = true)
    List<Object[]> getAllHistory();
    
    @Query(value = "select * from history where ticker = ?;", nativeQuery = true)
    List<Object[]> getHistoryByTicker(String ticker);
}