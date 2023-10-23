package gs.service.stockInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 import java.util.Iterator;
import java.util.Collections;

import org.hibernate.annotations.SourceType;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.json.JSONObject;




import gs.common.DataRequestModel;
import gs.common.RequestModel;
import gs.entity.Portfolio;
import gs.entity.PortfolioStock;
import gs.entity.Stock;
import gs.entity.StockInfo;
import gs.inputModel.PortfolioInputModel;
import gs.inputModel.StockInfoInputModel;
import gs.repository.PortfolioStockRepo;
import gs.repository.StockInfoRepo;
import gs.repository.StockRepo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.net.URI;


@Service
public class StockInfoServiceImpl implements StockInfoService {

    @Resource
    protected StockInfoRepo stockInfoRepo;

    @Resource
    protected StockRepo stockRepo;

    private StockInfoInputModel inputModel(StockInfo data){
        StockInfoInputModel inputModel = new StockInfoInputModel();
        inputModel.setTicker(String.valueOf(data.getTicker()));
        inputModel.setCountry(String.valueOf(data.getCountry()));
        inputModel.setCurrency(String.valueOf(data.getCurrency()));
        inputModel.setIndustry(String.valueOf(data.getIndustry()));
        inputModel.setSector(String.valueOf(data.getSector()));
        inputModel.setTodayPrice(Double.valueOf(data.getTodayPrice()));

        return inputModel;
    }

    public List<StockInfoInputModel> getStockInfo() {
        List<StockInfo> stockInfoQueryList = stockInfoRepo.getStockInfo();
        List<StockInfoInputModel> stockInfoList = new ArrayList<>();

        for (StockInfo data : stockInfoQueryList) {
            StockInfoInputModel inputModel = inputModel(data);
            stockInfoList.add(inputModel);
        }

        return stockInfoList;
    }

    public List<StockInfoInputModel> getStockInfoByTicker(String ticker){
        List<StockInfo> stockInfoQueryList = stockInfoRepo.getStockInfoByTicker(ticker);
        List<StockInfoInputModel> stockInfoList = new ArrayList<>();
        
        for (StockInfo data : stockInfoQueryList){
            // Logic to populate inputModel is missing here
            // Example: inputModel.setSomeProperty(data[0]);
            // Add inputModel to stockInfoList

            StockInfoInputModel inputModel = inputModel(data);
            stockInfoList.add(inputModel);
        }

        return stockInfoList;
    }

    @Resource
    public PortfolioStockRepo portfolioStockRepo;

    public List<StockInfoInputModel> getStockInfoByPortfolio() throws Exception {
        List<String> portfolioStocks = portfolioStockRepo.getTickerList();
        List<StockInfoInputModel> stockInfoList = new ArrayList<>();
        List<String> adjustedCloseList = new ArrayList<String>();
        String apiKey = "demo";
        for (String ticker : portfolioStocks) {  
                 String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=" + ticker + "&apikey=" + apiKey;

                 try{
                     HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                     .uri(URI.create(url))
                     .build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    String responseBody = response.body();

                    JSONObject obj = new JSONObject(responseBody);

    
                    Iterator<String> keys = obj.getJSONObject("Time Series (Daily)").keys();
                    List<String> keyList = new ArrayList<String>();
                    
                    while (keys.hasNext()) {
                        String key = keys.next();
                        keyList.add(key);
                    }

                    // Sort dates in descending order
                    Collections.sort(keyList, Collections.reverseOrder());
                    boolean firstAdjustedCloseStored = false;


                    // loop through the keylist and retrieve the values for each key
                    for (String key : keyList) {
                        System.out.println(key);
                    
                        JSONObject value = obj.getJSONObject("Time Series (Daily)").getJSONObject(key);
                        String adjustedClose = value.getString("5. adjusted close");
                    
                        if (!firstAdjustedCloseStored) {
                            adjustedCloseList.add(adjustedClose);
                            firstAdjustedCloseStored = true;
                            break;
                        }
                    }

                 }
                 catch (Exception e){
                    e.printStackTrace();
                 }
        }
        int counter = 0;
        for(String ticker : portfolioStocks){
             String url2 = "https://www.alphavantage.co/query?function=OVERVIEW&symbol=" + ticker  + "&apikey=" + apiKey;

             try{
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest Secondrequest = HttpRequest.newBuilder()
                        .uri(URI.create(url2))
                        .build();
                HttpResponse<String> secondResponse = client.send(Secondrequest, HttpResponse.BodyHandlers.ofString());

                String secondResponseBody = secondResponse.body();

                JSONObject secondObj = new JSONObject(secondResponseBody);

                String adjustedClose = adjustedCloseList.get(counter);
                counter = counter + 1;

                StockInfo newStockInfo = new StockInfo();
                Stock currentStock = stockRepo.getStockByTicker(ticker).get(0);
                newStockInfo.setStock(currentStock);
                newStockInfo.setCountry(secondObj.getString("Country"));
                newStockInfo.setCurrency(secondObj.getString("Currency"));
                newStockInfo.setIndustry(secondObj.getString("Industry"));
                newStockInfo.setSector(secondObj.getString("Sector"));
                newStockInfo.setTodayPrice(Double.parseDouble(adjustedClose));
                stockInfoRepo.save(newStockInfo);

             }
             catch(DataAccessException ex){
                System.out.println(ex.getMessage());
             }
                 
        }
        List<StockInfo> stockInfoQueryList = stockInfoRepo.getStockInfo();
        for (StockInfo data : stockInfoQueryList) {
            StockInfoInputModel inputModel = inputModel(data);
            stockInfoList.add(inputModel);
        }
        return stockInfoList;
    }
}