package gs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gs.entity.Portfolio;

public interface PortfolioRepo extends JpaRepository<Portfolio, Long>{
    
    @Query(value = "select * from portfolio where user_id = ?;", nativeQuery = true)
    List<Object[]> getPortfolioById(String id);

}
