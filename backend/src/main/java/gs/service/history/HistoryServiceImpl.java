package gs.service.history;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.Port;

import java.util.Iterator;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.Resource;
import gs.entity.History;
import gs.entity.Portfolio;
import gs.entity.PortfolioStock;
import gs.entity.Stock;
import gs.inputModel.HistoryInputModel;
import gs.inputModel.YearlyPriceInputmodel;
import gs.inputModel.MonthlyPrice;
import gs.repository.HistoryRepo;
import gs.repository.PortfolioRepo;
import gs.repository.PortfolioStockRepo;
import gs.repository.StockRepo;



@Service
public class HistoryServiceImpl implements HistoryService {
    @Resource
    public HistoryRepo historyRepo;

    @Resource
    public StockRepo stockRepo;

    @Resource
    public PortfolioStockRepo portfolioStockRepo;

    @Resource PortfolioRepo portfolioRepo;

    // Input into inputModel
    private HistoryInputModel inputModel(History data){
        HistoryInputModel inputModel = new HistoryInputModel();
        inputModel.setTicker(data.getStock().getTicker());
        inputModel.setDate(String.valueOf(data.getDate()));
        inputModel.setOpenPrice(data.getOpenPrice());
        inputModel.setHighPrice(data.getHighPrice());
        inputModel.setLowPrice(data.getLowPrice());
        inputModel.setAdjClosePrice(data.getAdjClosePrice());

        return inputModel;
    }
    
    public List<HistoryInputModel> getAllHistory(){
        List<History> stockQueryList = historyRepo.getAllHistory();
        List<HistoryInputModel> stockList = new ArrayList<>();

        for (History data : stockQueryList){
            HistoryInputModel inputModel = inputModel(data);
            stockList.add(inputModel);
        }
        

        return stockList;

    }

    public List<HistoryInputModel> getHistoryByTicker(String ticker){
        List<HistoryInputModel> historyList = new ArrayList<>();

        try {
            updateHistoryFromAPI(ticker); // Get data from API (if not exist in database
            List<History> historyQueryList = historyRepo.getHistoryByTicker(ticker);
            System.out.println("ticker: " + ticker);
            System.out.println("historyQueryList: " + historyQueryList);
            for (History history : historyQueryList){
                HistoryInputModel inputModel = inputModel(history);
                historyList.add(inputModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return historyList;
    }
    
    public void updateHistoryFromAPI(String ticker){
        try {
            URL url = new URL("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=IBM&apikey=demo");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                reader.close();

                String jsonResponse = response.toString();
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode root = objectMapper.readTree(jsonResponse);
                JsonNode timeSeriesNode = root.path("Time Series (Daily)");

                timeSeriesNode.fields().forEachRemaining(entry -> {
                    String date = entry.getKey(); 
                    JsonNode data = entry.getValue(); 
    
                    Date dt = null;
                    try {
                        dt = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    String openPrice = data.get("1. open").asText();
                    String highPrice = data.get("2. high").asText();
                    String lowPrice = data.get("3. low").asText();
                    String adjClosePrice = data.get("5. adjusted close").asText();
                    
                    History history = new History(ticker, dt, Double.parseDouble(openPrice), Double.parseDouble(highPrice), Double.parseDouble(lowPrice), Double.parseDouble(adjClosePrice));
                    Stock stock = stockRepo.getStockByTicker(ticker).get(0);
                    history.setStock(stock);
                    historyRepo.save(history);

                });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<HistoryInputModel> getWeeklyHistoryByTicker(String ticker){
        updateWeeklyHistory(ticker);
        List<History> historyQueryList = historyRepo.getHistoryByTicker(ticker);
        List<HistoryInputModel> historyList = new ArrayList<>();
        for (History history : historyQueryList){
            HistoryInputModel inputModel = inputModel(history);
            historyList.add(inputModel);
        }

        return historyList;

    }

    public void updateWeeklyHistory(String ticker){
        String apiKey = "94ANM37S7U3Z5NHS";
        try{
              String url = "https://www.alphavantage.co/query?function=TIME_SERIES_WEEKLY_ADJUSTED&symbol=" + ticker + "&apikey=" + apiKey;
              HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                     .uri(URI.create(url))
                     .build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                    String responseBody = response.body();

                    JSONObject obj = new JSONObject(responseBody);

                    Iterator <String> keys = obj.getJSONObject("Weekly Adjusted Time Series").keys();
                    // looop through keys
                    while(keys.hasNext()) {
                        String key = keys.next();
                        // get value of key
                        // 
                        // use the key to get the value
                        JSONObject value = obj.getJSONObject("Weekly Adjusted Time Series").getJSONObject(key);
                        String date = key;
                        String inputticker = ticker;
                        double adjustedclosing = Double.parseDouble(value.getString("5. adjusted close"));
                        double high = Double.parseDouble(value.getString("2. high"));
                        double low = Double.parseDouble(value.getString("3. low"));
                        double opening = Double.parseDouble(value.getString("1. open")); 

                        // add to database using the input model
                        History history = new History(inputticker, new SimpleDateFormat("yyyy-MM-dd").parse(date), opening, high, low, adjustedclosing);
                        Stock stock = stockRepo.getStockByTicker(ticker).get(0);
                        history.setStock(stock);
                        historyRepo.save(history);

                    }

        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public List<HistoryInputModel> getYearlyClosingByTicker(String ticker) {
        List<History> historyQueryList = historyRepo.getYearlyClosingByTicker(ticker);
        List<HistoryInputModel> historyList = new ArrayList<>();

        for (History data : historyQueryList){
            HistoryInputModel inputModel = inputModel(data);
            historyList.add(inputModel);
        }
    
        return historyList;
    }

    public List<YearlyPriceInputmodel> getPortfolioValue(String userId) {
        List<Portfolio> portfolio = portfolioRepo.getPortfolioByUserId(userId);
        List<List<Double>> returnvalues = new ArrayList<>();
        List<String> Portfolionames = new ArrayList<>();
        for(Portfolio p : portfolio){
            Portfolionames.add(p.getPortfolioName());
        }
        List<Integer> years = historyRepo.getUniqueYears();
        for(Integer y : years){
            for(Portfolio p : portfolio){
                String portfolioId = String.valueOf(p.getPortfolioId());
                List<PortfolioStock> ps = portfolioStockRepo.getPortfolioStockByPortfolioId(portfolioId);
                List<Double> portfoliovalues = new ArrayList<>();
                for(PortfolioStock pstock : ps){
                    List<History> history = historyRepo.getClosingPricesForYear(pstock.getStock().getTicker(), y);
                    double total = 0;
                    for(History h : history){
                        total += h.getAdjClosePrice() * pstock.getQuantity();
                    }
                    if (!history.isEmpty()) {
                        portfoliovalues.add(total);
                    }
                }
                
                if (!portfoliovalues.isEmpty()) {
                    returnvalues.add(portfoliovalues);
                }
            }
            
        }
       
        YearlyPriceInputmodel inputModel = new YearlyPriceInputmodel();
        inputModel.setYears(years);
        inputModel.setPortfolioNames(Portfolionames);
        inputModel.setPortfolioValues(returnvalues);
        List<YearlyPriceInputmodel> inputModelList = new ArrayList<>();
        inputModelList.add(inputModel);
        return inputModelList;
    }

    public List<MonthlyPrice> getMonthlyPortfolioValue(String userId){
        List<Portfolio> portfolio = portfolioRepo.getPortfolioByUserId(userId);
        List<String> monthStrings = generateMonthStrings();
        List<List<Double>> returnvalues = new ArrayList<>();
        for(int m = 1 ; m < 13 ; m++){
            List<Double> portfoliovalues = new ArrayList<>();
            for(Portfolio p : portfolio){
                String portfolioId = String.valueOf(p.getPortfolioId());
                List<PortfolioStock> ps = portfolioStockRepo.getPortfolioStockByPortfolioId(portfolioId);     
                double total = 0;
                for(PortfolioStock pstock : ps){
                    List<History> history = historyRepo.getMonthlyClosingPrices(pstock.getStock().getTicker());
                    for(History h : history){
                        String dateString = h.getDate().toString();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                        try {
                            Date date = dateFormat.parse(dateString);
                            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
                            String month = monthFormat.format(date);
                            int monthInt = Integer.parseInt(month);
                            if (monthInt == m) {
                                total += h.getAdjClosePrice() * pstock.getQuantity();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }                        
                        
                    }
                    portfoliovalues.add(total);
                   
                }
            }
            returnvalues.add(portfoliovalues);
        }
        MonthlyPrice inputModel = new MonthlyPrice();
        inputModel.setMonths(monthStrings);
        inputModel.setPortfolioValues(returnvalues);
        List<MonthlyPrice> inputModelList = new ArrayList<>();
        inputModelList.add(inputModel);
        return inputModelList;

    }
    
    
    

    // create a function to generate the unique months in a string format in a year eg "jan", "feb" etc
    public List<String> generateMonthStrings(){
        List<String> monthStrings = new ArrayList<>();
        for(int i = 1; i <= 12; i++){
            if(i == 1){
                monthStrings.add("Jan");
            }
            else if(i == 2){
                monthStrings.add("Feb");
            }
            else if(i == 3){
                monthStrings.add("Mar");
            }
            else if(i == 4){
                monthStrings.add("Apr");
            }
            else if(i == 5){
                monthStrings.add("May");
            }
            else if(i == 6){
                monthStrings.add("Jun");
            }
            else if(i == 7){
                monthStrings.add("Jul");
            }
            else if(i == 8){
                monthStrings.add("Aug");
            }
            else if(i == 9){
                monthStrings.add("Sep");
            }
            else if(i == 10){
                monthStrings.add("Oct");
            }
            else if(i == 11){
                monthStrings.add("Nov");
            }
            else if(i == 12){
                monthStrings.add("Dec");
            }
        }
        return monthStrings;
    }



    


}
