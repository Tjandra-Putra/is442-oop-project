package gs.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gs.common.ApiModel;
import gs.inputModel.StockInputModel;
import gs.service.stock.StockService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "api/stock")

public class StockController {

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private StockService StockService;

    @GetMapping("/getStock")
    public ApiModel<ArrayList<StockInputModel>> getStock(){
        return ApiModel.ok(StockService.getStock());
    }

    @GetMapping("/getStockByTicker/{ticker}")
    public ApiModel<ArrayList<StockInputModel>> getStockByTicker(
        @PathVariable("ticker") String ticker
    ){
        return ApiModel.ok(StockService.getStockByTicker(ticker));
    }

    @GetMapping("/getStockByName/{name}")
    public ApiModel<ArrayList<StockInputModel>> getStockByName(
        @PathVariable("name") String name
    ){
        return ApiModel.ok(StockService.getStockByName(name));
    }

}