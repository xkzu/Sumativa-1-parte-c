package cl.duoc.sumativa1.app.partec.util;

public class ValidateUtil {

    private ValidateUtil() {
    }

    public static boolean isEmptyOrNull(String string) {
        return null == string || string.isEmpty();
    }
}
