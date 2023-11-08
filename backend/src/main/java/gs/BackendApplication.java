package gs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

import cronjob.stockAPI;
import java.util.Map;

import gs.service.stock.StockService;

@SpringBootApplication
@ComponentScan(basePackages = {"gs.controller", "gs.entity", "gs.repository", "gs.service", "gs.config" , "gs.auth", "gs.model", "cronjob"})
@RestController
public class BackendApplication implements CommandLineRunner {

	@Autowired
	protected StockService stockService;
	
	@Autowired
	protected stockAPI stockAPI;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Override
	public void run(String... args) {
		
		Map<String,String> allStocks = stockAPI.getAllStocks();
		allStocks.forEach((ticker, name) -> stockService.addStock(ticker, name));
	}

}
