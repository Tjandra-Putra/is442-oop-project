package gs.inputModel;

import java.io.Serializable;
import java.util.List;

public class YearlyPriceInputmodel implements Serializable {

    private List<Integer> years;
    private List<String> portfolioNames;
    private List<List<Double>> portfolioValues;

    public List<Integer> getYears() {
        return this.years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public List<String> getPortfolioNames() {
        return this.portfolioNames;
    }

    public void setPortfolioNames(List<String> portfolioNames) {
        this.portfolioNames = portfolioNames;
    }

    public List<List<Double>> getPortfolioValues() {
        return this.portfolioValues;
    }

    public void setPortfolioValues(List<List<Double>> portfolioValues) {
        this.portfolioValues = portfolioValues;
    }


}
