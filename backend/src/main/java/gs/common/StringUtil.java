package gs.common;

public class StringUtil {
    public static String valueOf(Object obj) {
        String myStr = String.valueOf(obj);
        return "null".equals(myStr) ? "" : myStr;
    }
}
