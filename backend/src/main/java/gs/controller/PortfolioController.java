package gs.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gs.common.ApiModel;
import gs.common.RequestModel;
import gs.entity.Portfolio;
import gs.inputModel.PortfolioInputModel;
import gs.inputModel.UserInputModel;
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

    @PutMapping("/editPortfolio/capitalAmt/{userId}/{portfolioId}")
    public ApiModel editPortfolioCapitalAmt(
        @PathVariable("userId") String userId,
        @PathVariable("portfolioId") String portfolioId,
        @RequestBody RequestModel requestModel
    ) throws Exception{
        ApiModel myApiModel = new ApiModel();
        portfolioService.editPortfolioCapitalAmt(response, requestModel, myApiModel, userId, portfolioId);
        
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
