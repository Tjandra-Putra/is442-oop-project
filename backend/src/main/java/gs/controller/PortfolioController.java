package gs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gs.common.ApiModel;
import gs.entity.Portfolio;
import gs.inputModel.portfolioInputModel;
import gs.inputModel.userInputModel;
import gs.service.portfolio.PortfolioService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "api/portfolio")

public class PortfolioController {

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/getPortfolio/{userid}")
    public ApiModel<ArrayList<portfolioInputModel>> getPortfolio(
        @PathVariable("userid") String userid
    ){
        return ApiModel.ok(portfolioService.getPortfolio(userid));
    }

    @GetMapping("/getPortfolio/{userid}/{portfolioId}")
    public ApiModel<ArrayList<portfolioInputModel>> getPortfolioById(
        @PathVariable("userid") String userid,
        @PathVariable("portfolioId") String portfolioId

    ){
        return ApiModel.ok(portfolioService.getPortfolioById(userid, portfolioId));
    }
    
    @PostMapping("/addPortfolio")
    public String addPortfolio(
        @RequestBody List<Portfolio> portfolio
    ) throws Exception{
        portfolioService.addPortfolio(portfolio);
        return null;
    }
}
