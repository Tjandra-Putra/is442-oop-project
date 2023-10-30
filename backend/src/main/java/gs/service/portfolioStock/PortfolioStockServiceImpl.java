package gs.service.portfolioStock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import gs.common.ApiModel;
import gs.common.DataRequestModel;
import gs.common.RequestModel;
import gs.entity.Portfolio;
import gs.entity.PortfolioStock;
import gs.entity.Stock;
import gs.entity.StockInfo;
import gs.entity.User;
import gs.inputModel.AllocationInputModel;
import gs.inputModel.PortfolioInputModel;
import gs.inputModel.PortfolioStockInputModel;
import gs.inputModel.AllocationInputModel;
import gs.repository.PortfolioRepo;
import gs.repository.PortfolioStockRepo;
import gs.repository.StockInfoRepo;
import gs.repository.StockRepo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class PortfolioStockServiceImpl implements PortfolioStockService{
    @Resource
    protected PortfolioStockRepo portfolioStockRepo;
    
    @Resource
    protected PortfolioRepo portfolioRepo;

    @Resource
    protected StockRepo stockRepo;

    @Resource
    protected StockInfoRepo stockInfoRepo;

    // inputModel fitting methood
    private PortfolioStockInputModel inputModel(PortfolioStock data){
        PortfolioStockInputModel inputModel = new PortfolioStockInputModel();
        inputModel.setTicker(data.getStock().getTicker());
        inputModel.setPortfolioId(data.getPortfolio().getPortfolioId());
        inputModel.setQuantity(data.getQuantity());
        inputModel.setBuyDate(dateFormatter(data.getBuyDate(), data));
        inputModel.setPrice(data.getPrice());

        return inputModel;
    }

    private String dateFormatter(Date buyDate, PortfolioStock individualStockQuery){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd");
        String buyDateFormatted = dateFormatter.format(individualStockQuery.getBuyDate());

        return buyDateFormatted;
    }

    public List<PortfolioStockInputModel> getPortfolioStock(String portfolioId){
        List<PortfolioStock> portfolioStockQueryList = portfolioStockRepo.getPortfolioStockByPortfolioId(portfolioId);

        List<PortfolioStockInputModel> portfolioStockList = new ArrayList<>();

        for (PortfolioStock data : portfolioStockQueryList) {
            PortfolioStockInputModel inputModel = inputModel(data);
            portfolioStockList.add(inputModel);
        }

        return portfolioStockList;
    }

    public List<AllocationInputModel> getPortfolioStockAllocation(String portfolioId){
        List<PortfolioStock> portfolioStockQueryList = portfolioStockRepo.getPortfolioStockByPortfolioId(portfolioId);

        double capitalAmt = portfolioStockQueryList.get(0).getPortfolio().getPortfolioCapitalAmt();

        double totalStockPercentage = 0;

        List<AllocationInputModel> stockAllocationList = new ArrayList<>();

        if (portfolioStockQueryList.size() > 0){
            for (PortfolioStock portfolioStock : portfolioStockQueryList){
                AllocationInputModel inputModel = new AllocationInputModel();
                inputModel.setAllocationName(portfolioStock.getStock().getTicker());

                double allocationPercentage = ((portfolioStock.getQuantity() * portfolioStock.getPrice()) / capitalAmt) * 100;

                inputModel.setPercentage(allocationPercentage);
                stockAllocationList.add(inputModel);
                totalStockPercentage += allocationPercentage;
            }
        }

        AllocationInputModel cashInputModel = new AllocationInputModel();
        cashInputModel.setAllocationName("Cash");
        cashInputModel.setPercentage(100 - totalStockPercentage);
        stockAllocationList.add(cashInputModel);
    
        return stockAllocationList;
    }



    public List<AllocationInputModel> getPortfolioStockIndustryAllocation(String portfolioId){
        List<PortfolioStock> portfolioStocksQueryList = portfolioStockRepo.getPortfolioStockByPortfolioId(portfolioId);

        int totalStock = portfolioStocksQueryList.size();

        List<AllocationInputModel> StockIndustryAllocation = new ArrayList<>();

        Map<String, Integer> industryCount = new HashMap<>();
        
        for (PortfolioStock portfolioStock : portfolioStocksQueryList) {
            StockInfo currentStock = stockInfoRepo.getStockInfoByTicker(portfolioStock.getStock().getTicker());

            String stockIndustry = currentStock.getIndustry();

            if (industryCount.get(stockIndustry) == null){
                industryCount.put(stockIndustry, 1);
            }

            else {
                industryCount.put(stockIndustry, industryCount.get(stockIndustry) + 1);
            }
        }

        for (Entry<String, Integer> industry : industryCount.entrySet()) {
            AllocationInputModel currentIndustry = new AllocationInputModel();

            currentIndustry.setAllocationName(industry.getKey());
            double allocationPercentage = (double) industry.getValue() / totalStock * 100;
            currentIndustry.setPercentage(allocationPercentage);

            StockIndustryAllocation.add(currentIndustry);
        }

        return StockIndustryAllocation;
    };

    public List<PortfolioStockInputModel> getPortfolioStockByTicker(String portfolioId, String ticker){
        PortfolioStock individualStockQuery = portfolioStockRepo.getIndividualStock(portfolioId, ticker).get(0);

        List<PortfolioStockInputModel> portfolioStockList = new ArrayList<>();

        PortfolioStockInputModel inputModel = inputModel(individualStockQuery);
        portfolioStockList.add(inputModel);

        return portfolioStockList;
    }
    
    public ApiModel addPortfolioStock(HttpServletResponse response, RequestModel requestModel, ApiModel apiModel, String portfolioId) throws DataAccessException, ParseException{
        try {
            PortfolioStock newPortfolioStock = new PortfolioStock();
            for (DataRequestModel fe : requestModel.getData()){    

                if (fe.getFieldName().equalsIgnoreCase("price")){
                    newPortfolioStock.setPrice(Double.parseDouble(fe.getValue()));
                }

                else if (fe.getFieldName().equalsIgnoreCase("quantity")){
                    newPortfolioStock.setQuantity(Integer.parseInt(fe.getValue()));
                }
                
                else if (fe.getFieldName().equalsIgnoreCase("ticker")){
                    Stock stockQuery = stockRepo.getStockByTicker(fe.getValue()).get(0);

                    newPortfolioStock.setStock(stockQuery);
                }

                else if (fe.getFieldName().equalsIgnoreCase("buyDate")){
                    String dateString = fe.getValue(); 
                    SimpleDateFormat dateConverter = new SimpleDateFormat("YYYY-MM-dd");
                    Date date = dateConverter.parse(dateString);

                    newPortfolioStock.setBuyDate(date);
                }
            }

            newPortfolioStock.setPortfolio(portfolioRepo.getPortfolioByPortfolioId(portfolioId));

            // save to db
            portfolioStockRepo.save(newPortfolioStock);

            // instantiate PortfolioStockInputModel
            PortfolioStockInputModel inputModel = inputModel(newPortfolioStock);

            apiModel.setMessage("Data saved successfully.");
            apiModel.setData(inputModel);
        }
            
        catch (DataAccessException ex) {
        // Log the exception for debugging
        // Optionally, rethrow as a custom exception
            // INPUT LOGGER for error messages
            System.out.println(ex.getMessage());
            apiModel.setMessage("An error occurred while performing the database operation.");
        }

        catch (ParseException ex) {
        // Log the exception for debugging
        // Optionally, rethrow as a custom exception
            // INPUT LOGGER for error messages
            System.out.println(ex.getMessage());
            apiModel.setMessage("An error occurred while performing the database operation.");
        }
                
        apiModel.setStatus(String.valueOf(response.getStatus()));

        return apiModel;
    }

    public ApiModel deletePortfolioStock(HttpServletResponse response, ApiModel apiModel, String portfolioId, String ticker) throws DataAccessException {
        try {
            PortfolioStock portfolioStockQuery = portfolioStockRepo.getIndividualStock(portfolioId, ticker).get(0);

            // delete row
            portfolioStockRepo.delete(portfolioStockQuery);

            // instantiate PortfolioStockInputModel
            PortfolioStockInputModel inputModel = inputModel(portfolioStockQuery);

            apiModel.setMessage("Data deleted successfully.");
            apiModel.setData(inputModel);
        }
            
        catch (DataAccessException ex) {
        // Log the exception for debugging
        // Optionally, rethrow as a custom exception
            // INPUT LOGGER for error messages
            System.out.println(ex.getMessage());
            apiModel.setMessage("An error occurred while performing the database operation.");
        }

        apiModel.setStatus(String.valueOf(response.getStatus()));

        return apiModel;
    }
}
