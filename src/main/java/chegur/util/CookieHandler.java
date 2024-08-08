package chegur.util;

import jakarta.servlet.http.Cookie;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Optional;

@UtilityClass
public class CookieHandler {
    private static final String JSESSIONID = "JSESSIONID";

    public static Optional<String> getSessionCookie(Cookie[] cookies) {
        Optional<Cookie> sessionId = Arrays.stream(cookies)
                .filter(cookie -> JSESSIONID.equals(cookie.getName()))
                .findFirst();

        return sessionId.map(Cookie::getValue);
    }
}
