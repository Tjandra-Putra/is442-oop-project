package gs.service.history;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.time.*;
import java.util.Comparator;
import java.util.Set;

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

    public ArrayList<TreeMap<Integer, TreeMap<Integer, Double>>> getMonthlyPortfolioValue(String portfolioId){

        ArrayList<TreeMap<Integer, TreeMap<Integer, Double>>> result = new ArrayList<>();
        TreeMap<Integer, TreeMap<Integer, Double>> yearly = new TreeMap<>();

        // Loop through all years found in the History data
        List<Integer> years = historyRepo.getUniqueYears();
        for (Integer y : years) {

            TreeMap<Integer, Double> monthlyValue = new TreeMap<>();

            // Loop though all months of the year
            for (int m = 1 ; m < 13 ; m++) {

                double currentMonthValue = 0;

                // Loop through the individual stocks of the portfolio
                List<PortfolioStock> portfolioStocks = portfolioStockRepo.getPortfolioStockByPortfolioId(portfolioId);
                for (PortfolioStock ps : portfolioStocks) {

                    String ticker = ps.getStock().getTicker();
                    List<History> history = historyRepo.getMonthlyClosingPrices(ticker);

                    for (History h : history) {

                        String dateString = h.getDate().toString();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                        try {
                            Date date = dateFormat.parse(dateString);
                            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                            SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
                            String year = yearFormat.format(date);
                            String month = monthFormat.format(date);
                            int yearInt = Integer.parseInt(year);
                            int monthInt = Integer.parseInt(month);
                            if (monthInt == m && yearInt == y) {
                                currentMonthValue += h.getAdjClosePrice() * ps.getQuantity();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }                      
                }

                monthlyValue.put(m, currentMonthValue);

            }
            
            yearly.put(y, monthlyValue);

        }

        Set<Integer> yearSet = yearly.keySet();
        for (Integer year : yearSet) {
            TreeMap<Integer, TreeMap<Integer, Double>> yearlyData = new TreeMap<>();
            yearlyData.put(year, yearly.get(year));
            result.add(yearlyData);
        }

        return result;
        
    }


    public ArrayList<TreeMap<Integer, TreeMap<Integer, Double>>> getQuarterlyPortfolioValue(String portfolioId){

        ArrayList<TreeMap<Integer, TreeMap<Integer, Double>>> result = new ArrayList<>();
        TreeMap<Integer, TreeMap<Integer, Double>> yearly = new TreeMap<>();

        // Loop through all years found in the History data
        List<Integer> years = historyRepo.getUniqueYears();
        for (Integer y : years) {

            TreeMap<Integer, Double> monthlyValue = new TreeMap<>();

            // Loop though all months of the year
            for (int m = 1 ; m < 13 ; m++) {

                if (m % 3 ==0) {

                    double currentMonthValue = 0;

                    // Loop through the individual stocks of the portfolio
                    List<PortfolioStock> portfolioStocks = portfolioStockRepo.getPortfolioStockByPortfolioId(portfolioId);
                    for (PortfolioStock ps : portfolioStocks) {

                        String ticker = ps.getStock().getTicker();
                        List<History> history = historyRepo.getMonthlyClosingPrices(ticker);

                        for (History h : history) {

                            String dateString = h.getDate().toString();
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                            try {
                                Date date = dateFormat.parse(dateString);
                                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
                                SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
                                String year = yearFormat.format(date);
                                String month = monthFormat.format(date);
                                int yearInt = Integer.parseInt(year);
                                int monthInt = Integer.parseInt(month);
                                if (monthInt == m && yearInt == y) {
                                    currentMonthValue += h.getAdjClosePrice() * ps.getQuantity();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }                      
                    }

                    monthlyValue.put(m, currentMonthValue);

                }

            }
            
            yearly.put(y, monthlyValue);

        }

        Set<Integer> yearSet = yearly.keySet();
        for (Integer year : yearSet) {
            TreeMap<Integer, TreeMap<Integer, Double>> yearlyData = new TreeMap<>();
            yearlyData.put(year, yearly.get(year));
            result.add(yearlyData);
        }

        return result;
        
    }

}
