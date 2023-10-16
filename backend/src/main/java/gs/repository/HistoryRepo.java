package gs.repository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import gs.model.history.History;

public interface HistoryRepo extends CrudRepository<History, String>{

    @Query(value = "select * from history;", nativeQuery = true)
    List<Object[]> getAllHistory();
    
    @Query(value = "select * from history where ticker = ?;", nativeQuery = true)
    List<Object[]> getHistoryByTicker(String ticker);
}