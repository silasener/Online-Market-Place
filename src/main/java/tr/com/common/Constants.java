package tr.com.obss.jip.finalproject.common;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Constants {
    public static final String ADMIN_USER = "admin";
    public static final String SYSTEM_USER = "system";

    @UtilityClass
    public static class ResponseCodes {
        public static final Long SUCCESS = 100000L;

        public static final Long UNKNOWN_ERROR = 100001L;

        public static final Long USER_NOT_FOUND = 100002L;

        public static final Long ACCESS_DENIED = 100003L;
    }

    public static class Roles{
        public static final String ADMIN = "ROLE_ADMIN";
        public static final String USER = "ROLE_USER";
    }
}
