package gs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gs.entity.Portfolio;

public interface PortfolioRepo extends JpaRepository<Portfolio, Long>{
    
    @Query(value = "select * from portfolio where user_id = ?;", nativeQuery = true)
    List<Portfolio> getPortfolioByUserId(String userid);

    @Query(value = "select * from portfolio where user_id = ? and portfolio_id = ?;", nativeQuery = true)
    Portfolio getPortfolioById(String userid, String portfolioId);

    @Query(value = "select * from portfolio where portfolio_id = ?;", nativeQuery = true)
    Portfolio getPortfolioByPortfolioId(String portfolioId);

}
