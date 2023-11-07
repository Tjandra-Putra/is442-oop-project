package gs.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gs.common.ApiModel;
import gs.common.RequestModel;
import gs.common.RequestModel2;
import gs.inputModel.AllocationInputModel;
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
            @PathVariable("portfolioId") String portfolioId) {
        return ApiModel.ok(portfolioStockService.getPortfolioStock(portfolioId));
    }

    @GetMapping("/getPortfolioStockAllocation/{portfolioId}")
    public ApiModel<ArrayList<AllocationInputModel>> getPortfolioStockAllocation(
            @PathVariable("portfolioId") String portfolioId) {
        return ApiModel.ok(portfolioStockService.getPortfolioStockAllocation(portfolioId));
    }

    @GetMapping("/getPortfolioStockIndustryAllocation/{portfolioId}")
    public ApiModel<ArrayList<AllocationInputModel>> getPortfolioStockIndustryAllocation(
            @PathVariable("portfolioId") String portfolioId) {
        return ApiModel
                .ok(portfolioStockService.getPortfolioStockTypeAllocation(portfolioId, "Industry"));
    }

    @GetMapping("/getPortfolioStockCountryAllocation/{portfolioId}")
    public ApiModel<ArrayList<AllocationInputModel>> getPortfolioStockCountryAllocation(
            @PathVariable("portfolioId") String portfolioId) {
        return ApiModel
                .ok(portfolioStockService.getPortfolioStockTypeAllocation(portfolioId, "Country"));
    }

    @GetMapping("/getPortfolioStockCurrencyAllocation/{portfolioId}")
    public ApiModel<ArrayList<AllocationInputModel>> getPortfolioStockCurrencyAllocation(
            @PathVariable("portfolioId") String portfolioId) {
        return ApiModel
                .ok(portfolioStockService.getPortfolioStockTypeAllocation(portfolioId, "Currency"));
    }


    @GetMapping("/getPortfolioStock/{portfolioId}/{ticker}")
    public ApiModel<ArrayList<PortfolioStockInputModel>> getPortfolioStockByTicker(
            @PathVariable("portfolioId") String portfolioId,
            @PathVariable("ticker") String ticker) {
        return ApiModel.ok(portfolioStockService.getPortfolioStockByTicker(portfolioId, ticker));
    }

    @PostMapping("/addPortfolioStock/{portfolioId}")
    public ApiModel<ArrayList<PortfolioStockInputModel>> getPortfolioStockByTicker(
            @PathVariable("portfolioId") String portfolioId,
            @RequestBody RequestModel2 requestModel2) throws Exception {
        ApiModel myApiModel = new ApiModel();
        portfolioStockService.addPortfolioStock(response, requestModel2, myApiModel, portfolioId);

        return myApiModel;
    }

    @DeleteMapping("/deletePortfolioStock/{portfolioId}/{ticker}")
    public ApiModel<ArrayList<PortfolioStockInputModel>> deletePortfolioStock(
            @PathVariable("portfolioId") String portfolioId, @PathVariable("ticker") String ticker)
            throws Exception {
        ApiModel apiModel = new ApiModel();
        portfolioStockService.deletePortfolioStock(response, apiModel, portfolioId, ticker);

        return apiModel;
    }

    @GetMapping("/getAnnualReturns/{portfolioId}")
    public ApiModel<ArrayList<HashMap<Integer,Double>>> getAnnualReturns(
        @PathVariable("portfolioId") String portfolioId
    ){
        return ApiModel.ok(portfolioStockService.getAnnualReturns(portfolioId));
    }

}
