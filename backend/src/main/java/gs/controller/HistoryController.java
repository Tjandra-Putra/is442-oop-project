package gs.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import gs.common.*;
import gs.inputModel.*;
import gs.service.history.HistoryService;

@RestController
@RequestMapping(path = "api/stockHistory")

public class HistoryController {

    @Autowired
    private HistoryService HistoryService;

    @GetMapping("/getAllHistory")
    public ApiModel<ArrayList<HistoryInputModel>> getAllHistory(){
        return ApiModel.ok(HistoryService.getAllHistory());
    }

    @GetMapping("/getHistoryByTicker/{ticker}")
    public ApiModel<ArrayList<HistoryInputModel>> getHistoryByTicker(
        @PathVariable("ticker") String ticker
    ){
        return ApiModel.ok(HistoryService.getHistoryByTicker(ticker));
    }

    @GetMapping("/getWeeklyHistoryByTicker/{ticker}")
    public ApiModel<ArrayList<HistoryInputModel>> getWeeklyHistoryByTicker (
        @PathVariable("ticker") String ticker
    ){
        return ApiModel.ok(HistoryService.getWeeklyHistoryByTicker(ticker));
    }

    @GetMapping("/getYearlyClosingByTicker/{ticker}")
    public ApiModel<ArrayList<HistoryInputModel>> getYearlyClosingByTicker (
        @PathVariable("ticker") String ticker
    ){
        return ApiModel.ok(HistoryService.getYearlyClosingByTicker(ticker));
    }

    @PostMapping("/getStockHistoryPriceByDate/{ticker}/{buyDate}")
    public ApiModel<ArrayList<HistoryInputModel>> getStockHistoryPriceByDate (
        @PathVariable("ticker") String ticker,
        @PathVariable("buyDate") String buyDate
    ){
        return ApiModel.ok(HistoryService.getStockHistoryPriceByDate(ticker, buyDate));
    }

    // @GetMapping("getPortfolioValue/{portfolioId}")
    // public CustomApiModel<ArrayList<YearlyPriceInputmodel>> getPortfolioValue (
    //     @PathVariable("portfolioId") String portfolioId
    // ){
    //     return CustomApiModel.ok(HistoryService.getPortfolioValue(portfolioId));
    // }

    // @GetMapping("getPortfolioValue/{userId}")
    // public ApiModel<ArrayList<YearlyPriceInputmodel>> getPortfolioValue (
    //     @PathVariable("userId") String userId
    // ){
    //     return ApiModel.ok(HistoryService.getPortfolioValue(userId));
    // }

    @GetMapping("getAnnualPortfolioValue/{portfolioId}")
    public ApiModel<ArrayList<TreeMap<Integer,Double>>> getAnnualPortfolioValue (
        @PathVariable("portfolioId") String portfolioId
    ){
        return ApiModel.ok(HistoryService.getAnnualPortfolioValue(portfolioId));
    }

    @GetMapping("getMonthlyPortfolioValue/{portfolioId}")
    public ApiModel<ArrayList<TreeMap<Integer, TreeMap<Integer, Double>>>> getMonthlyPortfolioValue (
        @PathVariable("portfolioId") String portfolioId
    ){
        return ApiModel.ok(HistoryService.getMonthlyPortfolioValue(portfolioId));
    }

    @GetMapping("getQuarterlyPortfolioValue/{portfolioId}")
    public ApiModel<ArrayList<TreeMap<Integer, TreeMap<Integer, Double>>>> getQuarterlyPortfolioValue (
        @PathVariable("portfolioId") String portfolioId
    ){
        return ApiModel.ok(HistoryService.getQuarterlyPortfolioValue(portfolioId));
    }


}