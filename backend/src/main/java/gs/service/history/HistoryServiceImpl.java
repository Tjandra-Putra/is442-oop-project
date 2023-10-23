package gs.service.history;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.Resource;
import gs.entity.History;
import gs.entity.Stock;
import gs.inputModel.HistoryInputModel;
import gs.repository.HistoryRepo;
import gs.repository.StockRepo;


@Service
public class HistoryServiceImpl implements HistoryService {
    @Resource
    public HistoryRepo historyRepo;

    @Resource
    public StockRepo stockRepo;

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
        updateHistoryFromAPI(ticker); // Get data from API (if not exist in database
        List<History> historyQueryList = historyRepo.getHistoryByTicker(ticker);
        List<HistoryInputModel> historyList = new ArrayList<>();
        System.out.println("ticker: " + ticker);
        System.out.println("historyQueryList: " + historyQueryList);
        for (History history : historyQueryList){
            HistoryInputModel inputModel = inputModel(history);
            historyList.add(inputModel);
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

}
