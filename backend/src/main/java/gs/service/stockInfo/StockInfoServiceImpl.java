package gs.service.stockInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Collections;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.json.JSONObject;
import gs.entity.Stock;
import gs.entity.StockInfo;
import gs.inputModel.StockInfoInputModel;
import gs.repository.PortfolioStockRepo;
import gs.repository.StockInfoRepo;
import gs.repository.StockRepo;
import jakarta.annotation.Resource;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
        StockInfo stockInfoQueryList = stockInfoRepo.getStockInfoByTicker(ticker);
        List<StockInfoInputModel> stockInfoList = new ArrayList<>();
        
        StockInfoInputModel inputModel = inputModel(stockInfoQueryList);
        stockInfoList.add(inputModel);

        return stockInfoList;
    }

    @Resource
    public PortfolioStockRepo portfolioStockRepo;

    public List<StockInfoInputModel> getStockInfoByPortfolio() throws Exception {
        System.out.println("===REACHED====");
        List<String> portfolioStocks = portfolioStockRepo.getTickerList();
        System.out.println("====GET STOCK INFO====");
        System.out.println(portfolioStocks.size());
        List<StockInfoInputModel> stockInfoList = new ArrayList<>();
        List<String> adjustedCloseList = new ArrayList<String>();
        String apiKey = "94ANM37S7U3Z5NHS";
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
                Stock currentStock = stockRepo.getStockByTicker(ticker);
                newStockInfo.setStock(currentStock);
                newStockInfo.setCountry(secondObj.getString("Country"));
                newStockInfo.setCurrency(secondObj.getString("Currency"));
                newStockInfo.setIndustry(secondObj.getString("Industry"));
                newStockInfo.setSector(secondObj.getString("Sector"));
                newStockInfo.setTodayPrice(Double.parseDouble(adjustedClose));
                System.out.println("======GET STOCK INFO======");
                System.out.println(currentStock.getStockName());
                System.out.println(currentStock.getTicker());
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


    
    public List<StockInfoInputModel> updateStockInfoByPortfolio() throws Exception{
        List<String> portfolioStocks = portfolioStockRepo.getTickerList();
        System.out.println("====UPDATE=====");
        System.out.println(portfolioStocks.size());
        List<StockInfoInputModel> stockInfoList = new ArrayList<>();
        List<String> adjustedCloseList = new ArrayList<String>();
        String apiKey = "94ANM37S7U3Z5NHS";
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
                    
                        JSONObject value = obj.getJSONObject("Time Series (Daily)").getJSONObject(key);
                        String adjustedClose = value.getString("5. adjusted close");
                    
                        if (!firstAdjustedCloseStored) {
                            adjustedCloseList.add(adjustedClose);
                        
                            StockInfo currentStock = stockInfoRepo.getStockInfoByTicker(ticker);
                            // NPE currentStock
                            currentStock.setTodayPrice(Double.parseDouble(adjustedClose));
                            stockInfoRepo.save(currentStock);
                            break;
                        }
                    }

                 }
                 catch (Exception e){
                    e.printStackTrace();
                 }
        }

        List<StockInfo> stockInfoQueryList = stockInfoRepo.getStockInfo();
        for (StockInfo data : stockInfoQueryList) {
            StockInfoInputModel returnModel = inputModel(data);
            stockInfoList.add(returnModel);
        }
        return stockInfoList;
    };
}