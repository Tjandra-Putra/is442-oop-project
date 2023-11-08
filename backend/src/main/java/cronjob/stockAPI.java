package cronjob;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class stockAPI {

    @Value("${api.key}")
    private String apiKey;

    public stockAPI() {}

    public Map<String,String> getAllStocks() {
        String url = "https://www.alphavantage.co/query?function=LISTING_STATUS&apikey=demo";
        Map<String,String> stocks = new HashMap<String,String>();

        try {
            URL csvUrl = new URL(url);
            URLConnection connection = csvUrl.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                // System.out.println(line);
                // Process each line (assuming CSV format)
                String[] values = line.split(",");
                // Do something with the values
                String type = values[3];
                if (type.equals("Stock")) {
                    String ticker = values[0];
                    String name = values[1];
                    // System.out.println(ticker);
                    // System.out.println(name);
                    stocks.put(ticker, name);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stocks;
    }
}
