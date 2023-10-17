package gs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gs.entity.Portfolio;
import gs.service.portfolio.PortfolioService;

@RestController
@RequestMapping(path = "api/portfolio")

public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/getPortfolio")
    public List<Object[]> getPortfolio(){
        return portfolioService.getPortfolio();
    }
    
    @PostMapping("/addPortfolio")
    public String addPortfolio(
        @RequestBody List<Portfolio> portfolio
    ) throws Exception{
        portfolioService.addPortfolio(portfolio);
        return null;
    }
}
