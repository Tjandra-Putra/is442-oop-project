package gs.inputModel;

import java.io.Serializable;

public class StockInputModel implements Serializable {

    private String ticker;
    private String name;

    public String getTicker() {
        return this.ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
