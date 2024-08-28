package tr.com.utils;

import lombok.experimental.UtilityClass;

import java.util.Objects;
import java.util.Random;

@UtilityClass
public class StringUtils {

    public static final String EMPTY = "";

    public static Boolean isBlank(String str) {
        return Objects.isNull(str) || str.trim().isEmpty();
    }

    public static Boolean isNotBlank(String str) {
        return !isBlank(str);
    }

}
