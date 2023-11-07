package gs.repository;

import java.util.List;
import org.springframework.data.jpa.repository.*;
import gs.entity.History;
import gs.entity.HistoryCompositeKey;

public interface HistoryRepo extends JpaRepository<History, HistoryCompositeKey>{

    @Query(value = "select * from history;", nativeQuery = true)
    List<History> getAllHistory();
    
    @Query(value = "select * from history where ticker = ?;", nativeQuery = true)
    List<History> getHistoryByTicker(String ticker);

    @Query(value = "SELECT * FROM history WHERE (date, ticker) IN (SELECT MAX(date), ticker FROM history WHERE ticker = ? GROUP BY EXTRACT(YEAR FROM date), ticker);", nativeQuery = true)
    List<History> getYearlyClosingByTicker(String ticker);

    @Query(value = "select * from history where date = ?;", nativeQuery = true)
    History getHistoryByDate(String date);

    @Query(value = "SELECT DISTINCT EXTRACT(YEAR FROM date) as year FROM history", nativeQuery = true)
    List<Integer> getUniqueYears();     

    @Query(value = "SELECT * FROM history WHERE ticker = ? AND EXTRACT(YEAR FROM date) = ?;", nativeQuery = true)
    List<History> getClosingPricesForYear(String ticker, int year);

    @Query(value = "SELECT h1.* FROM history h1 JOIN (SELECT EXTRACT(YEAR FROM date) AS year, EXTRACT(MONTH FROM date) AS month, MAX(date) AS max_date FROM history WHERE ticker = 'AAPL' GROUP BY EXTRACT(YEAR FROM date), EXTRACT(MONTH FROM date) ) h2 ON EXTRACT(YEAR FROM h1.date) = h2.year AND EXTRACT(MONTH FROM h1.date) = h2.month AND h1.date = h2.max_date WHERE h1.ticker = 'AAPL' ORDER BY h1.date;", nativeQuery = true)
    List<History> getMonthlyClosingPrices(String ticker);
   

}