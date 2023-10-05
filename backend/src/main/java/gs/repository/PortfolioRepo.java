package gs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import gs.model.portfolio.Portfolio;

public interface PortfolioRepo extends CrudRepository<Portfolio, Integer>{
    
    @Query(value = "select * from portfolio;", nativeQuery = true)
    List<Object[]> getPortfolio();

}
