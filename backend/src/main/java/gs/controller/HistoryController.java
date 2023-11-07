package gs.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gs.common.ApiModel;
import gs.common.NullError;
import gs.common.RequestModel;
import gs.inputModel.HistoryInputModel;
import gs.inputModel.MonthlyPrice;
import gs.inputModel.YearlyPriceInputmodel;
import gs.inputModel.MonthlyPrice;
import gs.service.history.HistoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

    @GetMapping("/getHistoryByWeekTicker/{ticker}")
    public ApiModel<ArrayList<HistoryInputModel>> getWeeklyHistoryByTicker (
        @PathVariable("ticker") String ticker
    ){
        return ApiModel.ok(HistoryService.getWeeklyHistoryByTicker(ticker));
    }

    @GetMapping("getYearlyClosingByTicker/{ticker}")
    public ApiModel<ArrayList<HistoryInputModel>> getYearlyClosingByTicker (
        @PathVariable("ticker") String ticker
    ){
        return ApiModel.ok(HistoryService.getYearlyClosingByTicker(ticker));
    }

    // @GetMapping("getPortfolioValue/{portfolioId}")
    // public CustomApiModel<ArrayList<YearlyPriceInputmodel>> getPortfolioValue (
    //     @PathVariable("portfolioId") String portfolioId
    // ){
    //     return CustomApiModel.ok(HistoryService.getPortfolioValue(portfolioId));
    // }

    @GetMapping("getPortfolioValue/{userId}")
    public ApiModel<ArrayList<YearlyPriceInputmodel>> getPortfolioValue (
        @PathVariable("userId") String userId
    ){
        return ApiModel.ok(HistoryService.getPortfolioValue(userId));
    }

    @GetMapping("getMonthlyPortfolioValue/{userId}")
    public ApiModel<ArrayList<MonthlyPrice>> getMonthlyPortfolioValue (
        @PathVariable("userId") String userId
    ){
        return ApiModel.ok(HistoryService.getMonthlyPortfolioValue(userId));
    }

    @GetMapping("getQuarterlyPortfolioValue/{userId}")
    public ApiModel<ArrayList<MonthlyPrice>> getQuarterlyPortfolioValue (
        @PathVariable("userId") String userId
    ){
        return ApiModel.ok(HistoryService.getQuarterlyPortfolioValue(userId));
    }

    

    


}