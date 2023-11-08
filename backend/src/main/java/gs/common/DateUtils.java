package gs.common;

import java.text.SimpleDateFormat;
import java.util.Date;

import gs.entity.PortfolioStock;

public class DateUtils {

    public static String dateFormatter(Date buyDate, PortfolioStock individualStockQuery) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String buyDateFormatted = dateFormatter.format(individualStockQuery.getBuyDate());

        return buyDateFormatted;
    }

    public static String dateFormatter(Date buyDate) {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        String buyDateFormatted = dateFormatter.format(buyDate);

        return buyDateFormatted;
    }
    
}
