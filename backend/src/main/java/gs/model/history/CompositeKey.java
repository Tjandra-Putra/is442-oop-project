package gs.model.history;

import java.io.Serializable;
import java.util.Date;

public class CompositeKey implements Serializable {
    private String ticker;
    private Date date;
}