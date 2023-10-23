package gs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gs.entity.PortfolioStock;
import gs.entity.PortfolioStockId;

public interface PortfolioStockRepo extends JpaRepository<PortfolioStock, PortfolioStockId>{

    @Query(value = "select * from portfolio_stock where portfolio_id = ?;", nativeQuery = true)
    List<PortfolioStock> getPortfolioStockByPortfolioId(String portfolioId);

    @Query(value = "select * from portfolio_stock where portfolio_id = ? and ticker = ?;", nativeQuery = true)
    List<PortfolioStock> getIndividualStock(String portfolioId, String ticker);

}
