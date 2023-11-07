package gs.inputModel;

import java.io.Serializable;
import java.util.List;

public class MonthlyPrice implements Serializable{
    
    private List<String> months;
    private List<List<Double>> portfolioValues;
    
    public List<String> getMonths() {
        return this.months;
    }

    public void setMonths(List<String> months) {
        this.months = months;
    }

    public List<List<Double>> getPortfolioValues() {
        return this.portfolioValues;
    }

    public void setPortfolioValues(List<List<Double>>portfolioValues) {
        this.portfolioValues = portfolioValues;
    }
    
    
}
