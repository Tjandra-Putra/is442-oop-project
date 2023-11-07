package gs.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import gs.common.*;
import gs.inputModel.PortfolioInputModel;
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

    @GetMapping("/getPortfolio/{userId}")
    public ApiModel<ArrayList<PortfolioInputModel>> getPortfolio(
        @PathVariable("userId") String userId
    ){
        return ApiModel.ok(portfolioService.getPortfolio(userId));
    }

    @GetMapping("/getPortfolio/{userId}/{portfolioId}")
    public ApiModel<ArrayList<PortfolioInputModel>> getPortfolioById(
        @PathVariable("userId") String userId,
        @PathVariable("portfolioId") String portfolioId

    ){
        return ApiModel.ok(portfolioService.getPortfolioById(userId, portfolioId));
    }

    @PostMapping("/addPortfolio/{userId}")
    public ApiModel addPortfolio(
        @PathVariable("userId") String userId,
        @RequestBody RequestModel requestModel
    ) throws Exception{
        ApiModel myApiModel = new ApiModel();
        portfolioService.addPortfolio(response, requestModel, myApiModel, userId);
        
        return myApiModel;
    }

    @PutMapping("/editPortfolio/{userId}/{portfolioId}")
    public ApiModel editPortfolio(
        @PathVariable("userId") String userId,
        @PathVariable("portfolioId") String portfolioId,
        @RequestBody RequestModel requestModel
    ) throws Exception{
        ApiModel myApiModel = new ApiModel();
        portfolioService.editPortfolio(response, requestModel, myApiModel, userId, portfolioId);
        
        return myApiModel;
    }

    @DeleteMapping("/deletePortfolio/{userId}/{portfolioId}")
    public ApiModel deletePortfolio(
        @PathVariable("userId") String userId,
        @PathVariable("portfolioId") String portfolioId
    ) throws Exception{
        ApiModel myApiModel = new ApiModel();
        portfolioService.deletePortfolio(response, myApiModel, userId, portfolioId);

        return myApiModel;
    }
}
