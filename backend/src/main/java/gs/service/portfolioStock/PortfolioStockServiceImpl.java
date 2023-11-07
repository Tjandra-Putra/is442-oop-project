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

import com.fasterxml.jackson.databind.ObjectMapper;

import gs.common.ApiModel;
import gs.common.DataRequestModel;
import gs.common.RequestModel;
import gs.common.RequestModel2;
import gs.entity.History;
import gs.entity.Portfolio;
import gs.entity.PortfolioStock;
import gs.entity.Stock;
import gs.entity.StockInfo;
import gs.entity.User;
import gs.inputModel.HistoryInputModel;
import gs.inputModel.PortfolioInputModel;
import gs.inputModel.PortfolioStockInputModel;
import gs.inputModel.AllocationInputModel;
import gs.repository.HistoryRepo;
import gs.repository.PortfolioRepo;
import gs.repository.PortfolioStockRepo;
import gs.repository.StockInfoRepo;
import gs.repository.StockRepo;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PortfolioStockServiceImpl implements PortfolioStockService {
    @Resource
    protected PortfolioStockRepo portfolioStockRepo;

    @Resource
    protected HistoryRepo historyRepo;

    @Resource
    protected PortfolioRepo portfolioRepo;

    @Resource
    protected StockRepo stockRepo;

    @Resource
    protected StockInfoRepo stockInfoRepo;

    // inputModel fitting methood
    private PortfolioStockInputModel inputModel(PortfolioStock data) {
        PortfolioStockInputModel inputModel = new PortfolioStockInputModel();
        inputModel.setTicker(data.getStock().getTicker());
        inputModel.setPortfolioId(data.getPortfolio().getPortfolioId());
        inputModel.setQuantity(data.getQuantity());
        inputModel.setBuyDate(dateFormatter(data.getBuyDate(), data));
        inputModel.setPrice(data.getPrice());

        return inputModel;
    }

    // inputModel fitting method for AllocationInputModel
    private AllocationInputModel allocationInputModel(String fieldKey, Integer fieldValue,
            int totalStockCount) {
        AllocationInputModel inputModel = new AllocationInputModel();

        inputModel.setAllocationName(fieldKey);
        double allocationPercentage = (double) fieldValue / totalStockCount * 100;
        inputModel.setPercentage(allocationPercentage);

        return inputModel;

    }

    private HistoryInputModel inputModel(History data) {
        HistoryInputModel inputModel = new HistoryInputModel();
        inputModel.setTicker(data.getStock().getTicker());
        inputModel.setDate(String.valueOf(data.getDate()));
        inputModel.setOpenPrice(data.getOpenPrice());
        inputModel.setHighPrice(data.getHighPrice());
        inputModel.setLowPrice(data.getLowPrice());
        inputModel.setAdjClosePrice(data.getAdjClosePrice());

        return inputModel;
    }

    private String dateFormatter(Date buyDate, PortfolioStock individualStockQuery) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("YYYY-MM-dd");
        String buyDateFormatted = dateFormatter.format(individualStockQuery.getBuyDate());

        return buyDateFormatted;
    }

    public List<PortfolioStockInputModel> getPortfolioStock(String portfolioId) {
        List<PortfolioStock> portfolioStockQueryList =
                portfolioStockRepo.getPortfolioStockByPortfolioId(portfolioId);

        List<PortfolioStockInputModel> portfolioStockList = new ArrayList<>();

        for (PortfolioStock data : portfolioStockQueryList) {
            PortfolioStockInputModel inputModel = inputModel(data);
            portfolioStockList.add(inputModel);
        }

        return portfolioStockList;
    }

    public List<AllocationInputModel> getPortfolioStockAllocation(String portfolioId) {
        List<PortfolioStock> portfolioStockQueryList =
                portfolioStockRepo.getPortfolioStockByPortfolioId(portfolioId);

        double capitalAmt = portfolioStockQueryList.get(0).getPortfolio().getPortfolioCapitalAmt();

        double totalStockPercentage = 0;

        List<AllocationInputModel> stockAllocationList = new ArrayList<>();

        if (portfolioStockQueryList.size() > 0) {
            for (PortfolioStock portfolioStock : portfolioStockQueryList) {
                AllocationInputModel inputModel = new AllocationInputModel();
                inputModel.setAllocationName(portfolioStock.getStock().getTicker());

                double allocationPercentage =
                        ((portfolioStock.getQuantity() * portfolioStock.getPrice()) / capitalAmt)
                                * 100;

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

    // industry allocation
    private Map<String, Integer> industryAllocation(Map<String, Integer> allocationMap,
            List<PortfolioStock> portfolioStocksQueryList) {

        for (PortfolioStock portfolioStock : portfolioStocksQueryList) {
            StockInfo currentStock =
                    stockInfoRepo.getStockInfoByTicker(portfolioStock.getStock().getTicker());

            String stockIndustry = currentStock.getIndustry();

            if (allocationMap.get(stockIndustry) == null) {
                allocationMap.put(stockIndustry, 1);
            }

            else {
                allocationMap.put(stockIndustry, allocationMap.get(stockIndustry) + 1);
            }
        }

        return allocationMap;
    }

    // country allocation
    private Map<String, Integer> countryAllocation(Map<String, Integer> allocationMap,
            List<PortfolioStock> portfolioStocksQueryList) {

        for (PortfolioStock portfolioStock : portfolioStocksQueryList) {
            StockInfo currentStock =
                    stockInfoRepo.getStockInfoByTicker(portfolioStock.getStock().getTicker());

            String stockIndustry = currentStock.getCountry();

            if (allocationMap.get(stockIndustry) == null) {
                allocationMap.put(stockIndustry, 1);
            }

            else {
                allocationMap.put(stockIndustry, allocationMap.get(stockIndustry) + 1);
            }
        }

        return allocationMap;
    }

    // currency allocation
    private Map<String, Integer> currencyAllocation(Map<String, Integer> allocationMap,
            List<PortfolioStock> portfolioStocksQueryList) {

        for (PortfolioStock portfolioStock : portfolioStocksQueryList) {
            StockInfo currentStock =
                    stockInfoRepo.getStockInfoByTicker(portfolioStock.getStock().getTicker());

            String stockIndustry = currentStock.getCurrency();

            if (allocationMap.get(stockIndustry) == null) {
                allocationMap.put(stockIndustry, 1);
            }

            else {
                allocationMap.put(stockIndustry, allocationMap.get(stockIndustry) + 1);
            }
        }

        return allocationMap;
    }

    public List<AllocationInputModel> getPortfolioStockTypeAllocation(String portfolioId,
            String allocationName) {
        List<PortfolioStock> portfolioStocksQueryList =
                portfolioStockRepo.getPortfolioStockByPortfolioId(portfolioId);

        int totalStockCount = portfolioStocksQueryList.size();

        List<AllocationInputModel> displayAllocation = new ArrayList<>();

        Map<String, Integer> allocationCount = new HashMap<>();

        if (allocationName == "Industry") {
            allocationCount = industryAllocation(allocationCount, portfolioStocksQueryList);
        }

        else if (allocationName == "Country") {
            allocationCount = countryAllocation(allocationCount, portfolioStocksQueryList);;
        }

        else if (allocationName == "Currency") {
            allocationCount = currencyAllocation(allocationCount, portfolioStocksQueryList);

        }

        for (Entry<String, Integer> allocationField : allocationCount.entrySet()) {

            AllocationInputModel currentAllocation = allocationInputModel(allocationField.getKey(),
                    allocationField.getValue(), totalStockCount);

            displayAllocation.add(currentAllocation);
        }

        return displayAllocation;
    };


    public List<PortfolioStockInputModel> getPortfolioStockByTicker(String portfolioId,
            String ticker) {
        PortfolioStock individualStockQuery =
                portfolioStockRepo.getIndividualStock(portfolioId, ticker).get(0);

        List<PortfolioStockInputModel> portfolioStockList = new ArrayList<>();

        PortfolioStockInputModel inputModel = inputModel(individualStockQuery);
        portfolioStockList.add(inputModel);

        return portfolioStockList;
    }



    public ApiModel addPortfolioStock(HttpServletResponse response, RequestModel2 requestModel2,
            ApiModel apiModel, String portfolioId) throws DataAccessException, ParseException {
        try {
            for (List<DataRequestModel> obj : requestModel2.getData()) {

                PortfolioStock newPortfolioStock = new PortfolioStock();

                for (DataRequestModel stockToAdd : obj) {
                    if (stockToAdd.getFieldName().equalsIgnoreCase("price")) {
                        newPortfolioStock.setPrice(Double.parseDouble(stockToAdd.getValue()));
                    }

                    else if (stockToAdd.getFieldName().equalsIgnoreCase("quantity")) {
                        newPortfolioStock.setQuantity(Integer.parseInt(stockToAdd.getValue()));
                    }

                    else if (stockToAdd.getFieldName().equalsIgnoreCase("ticker")) {
                        Stock stockQuery = stockRepo.getStockByTicker(stockToAdd.getValue()).get(0);

                        newPortfolioStock.setStock(stockQuery);
                    }

                    else if (stockToAdd.getFieldName().equalsIgnoreCase("buyDate")) {
                        String dateString = stockToAdd.getValue();
                        SimpleDateFormat dateConverter =
                                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
                        Date date = dateConverter.parse(dateString);

                        newPortfolioStock.setBuyDate(date);
                    }
                }

                newPortfolioStock
                        .setPortfolio(portfolioRepo.getPortfolioByPortfolioId(portfolioId));

                // save to db
                portfolioStockRepo.save(newPortfolioStock);
            }
            apiModel.setMessage("Data saved successfully.");
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

    public ApiModel deletePortfolioStock(HttpServletResponse response, ApiModel apiModel,
            String portfolioId, String ticker) throws DataAccessException {
        try {
            PortfolioStock portfolioStockQuery =
                    portfolioStockRepo.getIndividualStock(portfolioId, ticker).get(0);

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

    public List<HashMap<Integer, Double>> getAnnualReturns(String portfolioId) {

        List<HashMap<Integer, Double>> result = new ArrayList<>();
        HashMap<Integer, Double> annualCloseValue = new HashMap<>();

        List<PortfolioStock> portfolioStockQueryList =
                portfolioStockRepo.getPortfolioStockByPortfolioId(portfolioId);

        List<PortfolioStockInputModel> portfolioStockList = new ArrayList<>();

        for (PortfolioStock data : portfolioStockQueryList) {
            PortfolioStockInputModel inputModel = inputModel(data);
            portfolioStockList.add(inputModel);
        }


        for (PortfolioStockInputModel stock : portfolioStockList) {
            int quantity = stock.getQuantity();

            List<History> historyQueryList =
                    historyRepo.getYearlyClosingByTicker(stock.getTicker());
            List<HistoryInputModel> historyList = new ArrayList<>();

            for (History data : historyQueryList) {
                HistoryInputModel inputModel = inputModel(data);
                historyList.add(inputModel);
            }
            for (HistoryInputModel history : historyList) {
                String dateString = history.getDate();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
                LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);
                int year = dateTime.getYear();
                if (!annualCloseValue.containsKey(year)) {
                    annualCloseValue.put(year, history.getAdjClosePrice() * quantity);
                } else {
                    double currentValue = annualCloseValue.get(year);
                    annualCloseValue.put(year,
                            currentValue + history.getAdjClosePrice() * quantity);
                }

            }
        }

        for (int i = 2000; i < 2000 + annualCloseValue.size() - 1; i++) {
            double currentYear = annualCloseValue.get(i);
            double previousYear = annualCloseValue.get(i - 1);
            double percentReturn = (currentYear - previousYear) * 100 / previousYear;
            HashMap<Integer, Double> annualReturnPercent = new HashMap<>();
            annualReturnPercent.put(i, percentReturn);
            result.add(annualReturnPercent);
        }

        return result;

    }
}
