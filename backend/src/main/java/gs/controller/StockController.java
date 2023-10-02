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
import gs.common.NullError;
import gs.common.RequestModel;
import gs.inputModel.stockInputModel;
import gs.model.stock.Stock;
import gs.model.user.User;
import gs.service.stock.StockService;
import gs.service.user.UserService;
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
    public ApiModel<ArrayList<stockInputModel>> getStock(){
        return ApiModel.ok(StockService.getStock());
    }

    @GetMapping("/getStockByTicker/{ticker}")
    public ApiModel<ArrayList<stockInputModel>> getStockByTicker(
        @PathVariable("ticker") String ticker
    ){
        return ApiModel.ok(StockService.getStockByTicker(ticker));
    }

    @GetMapping("/getStockByName/{name}")
    public ApiModel<ArrayList<stockInputModel>> getStockByName(
        @PathVariable("name") String name
    ){
        return ApiModel.ok(StockService.getStockByName(name));
    }

    @PostMapping("/addStock")
    public String addStock(
        @RequestBody RequestModel stocks
    ) throws Exception{
        StockService.addStock(response, stocks);
        return null;
    }
}