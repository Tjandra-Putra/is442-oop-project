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

    @Query(value = "SELECT * FROM history WHERE (date, ticker) IN (SELECT MAX(date), ticker FROM history WHERE ticker = ? GROUP BY EXTRACT(YEAR FROM date), ticker);", nativeQuery = true)
    List<History> getYearlyClosingByTicker(String ticker);

    @Query(value = "SELECT DISTINCT EXTRACT(YEAR FROM date) as year FROM history", nativeQuery = true)
    List<Integer> getUniqueYears();     

    @Query(value = "SELECT * FROM history WHERE ticker = ? AND EXTRACT(YEAR FROM date) = ?;", nativeQuery = true)
    List<History> getClosingPricesForYear(String ticker, int year);

    // Query to get the monthly closing prices for the latest year
    // @Query(value = "SELECT * FROM history WHERE ticker = ? AND EXTRACT(YEAR FROM date) = (SELECT MAX(EXTRACT(YEAR FROM date)) FROM history) ORDER BY date;", nativeQuery = true)
    // List<History> getMonthlyClosingPrices(String ticker);

    @Query(value = "SELECT h1.*\n" + //
            "FROM history h1\n" + //
            "JOIN (\n" + //
            "    SELECT\n" + //
            "        EXTRACT(YEAR FROM date) AS year,\n" + //
            "        EXTRACT(MONTH FROM date) AS month,\n" + //
            "        MAX(date) AS max_date\n" + //
            "    FROM history\n" + //
            "    WHERE ticker = ?1\n" + //
            "          AND EXTRACT(YEAR FROM date) = (SELECT MAX(EXTRACT(YEAR FROM date)) FROM history)\n" + //
            "    GROUP BY EXTRACT(YEAR FROM date), EXTRACT(MONTH FROM date)\n" + //
            ") h2 ON EXTRACT(YEAR FROM h1.date) = h2.year\n" + //
            "      AND EXTRACT(MONTH FROM h1.date) = h2.month\n" + //
            "      AND h1.date = h2.max_date\n" + //
            "WHERE h1.ticker = ?1\n" + //
            "ORDER BY h1.date;\n" + //
            "", nativeQuery = true)

    List<History> getMonthlyClosingPrices(String ticker);


    @Query(value = "SELECT h1.* FROM history h1 JOIN (SELECT YEAR(date) AS year, CEIL(MONTH(date) / 3) AS quarter, MAX(date) AS max_date FROM history WHERE ticker = ?1 GROUP BY YEAR(date), CEIL(MONTH(date) / 3)) h2 ON YEAR(h1.date) = h2.year AND CEIL(MONTH(h1.date) / 3) = h2.quarter AND h1.date = h2.max_date WHERE h1.ticker = ?1 ORDER BY h1.date;", nativeQuery = true)
    List<History> getQuarterlyPortfolioValue(String ticker);
   

}