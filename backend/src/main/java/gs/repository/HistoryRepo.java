package gs.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import gs.entity.History;
import gs.entity.HistoryCompositeKey;

public interface HistoryRepo extends JpaRepository<History, HistoryCompositeKey>{

    @Query(value = "select * from history;", nativeQuery = true)
    List<History> getAllHistory();
    
    @Query(value = "select * from history where ticker = ?;", nativeQuery = true)
    List<History> getHistoryByTicker(String ticker);
}