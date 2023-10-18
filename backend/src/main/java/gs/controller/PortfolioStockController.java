package gs.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gs.common.ApiModel;
import gs.inputModel.PortfolioStockInputModel;
import gs.service.portfolioStock.PortfolioStockService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "api/portfolioStock")
public class PortfolioStockController {
    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private PortfolioStockService portfolioStockService;

    @GetMapping("/getPortfolioStock/{portfolioId}")
    public ApiModel<ArrayList<PortfolioStockInputModel>> getPortfolioStock(
        @PathVariable("portfolioId") String portfolioId
    ){
        return ApiModel.ok(portfolioStockService.getPortfolioStock(portfolioId));
    }

    
    @GetMapping("/getPortfolioStock/{portfolioId}/{ticker}")
    public ApiModel<ArrayList<PortfolioStockInputModel>> getPortfolioStockByTicker(
        @PathVariable("portfolioId") String portfolioId,
        @PathVariable("ticker") String ticker
    ){
        return ApiModel.ok(portfolioStockService.getPortfolioStockByTicker(portfolioId, ticker));
    }
}
