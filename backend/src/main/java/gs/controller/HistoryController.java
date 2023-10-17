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
import gs.inputModel.historyInputModel;
import gs.service.history.HistoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(path = "api/stockHistory")

public class HistoryController {

    @Autowired
    private HistoryService HistoryService;

    @GetMapping("/getAllHistory")
    public ApiModel<ArrayList<historyInputModel>> getAllHistory(){
        return ApiModel.ok(HistoryService.getAllHistory());
    }

    @GetMapping("/getHistoryByTicker/{ticker}")
    public ApiModel<ArrayList<historyInputModel>> getHistoryByTicker(
        @PathVariable("ticker") String ticker
    ){
        return ApiModel.ok(HistoryService.getHistoryByTicker(ticker));
    }
}
