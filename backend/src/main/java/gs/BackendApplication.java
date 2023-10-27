package gs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

import cronjob.stockAPI;
import java.util.Map;

import gs.repository.StockRepo;
import gs.service.stock.StockService;
import gs.service.stock.StockServiceImpl;
import jakarta.annotation.Resource;
import gs.repository.StockRepo;

@SpringBootApplication
@ComponentScan(basePackages = {"gs.controller", "gs.entity", "gs.repository", "gs.service", "gs.config" , "gs.auth", "gs.model"})
@RestController
public class BackendApplication {

	@Autowired
	protected StockService stockService;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	// @Override
	// public void run(String... args) {
	// 	stockAPI stocks = new stockAPI();
	// 	Map<String,String> allStocks = stocks.getAllStocks();
	// 	allStocks.forEach((ticker, name) -> stockService.addStock(ticker, name));
	// }

}
