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
import gs.entity.Stock;
import gs.entity.User;
import gs.inputModel.StockInfoInputModel;
// import gs.inputModel.stockInputModel;
import gs.service.stockInfo.StockInfoService;
import gs.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "api/StockInfo")
public class StockInfoController {
    
    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private StockInfoService StockInfoService;

    @GetMapping("/getStockInfo")
    public ApiModel<ArrayList<StockInfoInputModel>> getStockInfo(){
        return ApiModel.ok(StockInfoService.getStockInfo());
    }

    @GetMapping("/getStockInfo/ticker/{ticker}")
    public ApiModel<ArrayList<StockInfoInputModel>> getStockInfoByTicker(@PathVariable String ticker){
        return ApiModel.ok(StockInfoService.getStockInfoByTicker(ticker));
    }

    @GetMapping("/getStockInfo/portfolio")
    public ApiModel<ArrayList<StockInfoInputModel>> getStockInfoByPortfolio(){
        return ApiModel.ok(StockInfoService.getStockInfoByPortfolio());
    }
}
