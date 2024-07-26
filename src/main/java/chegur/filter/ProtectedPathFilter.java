package chegur.filter;

import chegur.dto.UserSessionDto;
import chegur.service.UserSessionService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter(urlPatterns = {"/home", "/found-locations"})
public class ProtectedPathFilter implements Filter {
    private static final String JSESSIONID = "JSESSIONID";
    private final UserSessionService userSessionService = UserSessionService.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        Cookie[] cookies = httpRequest.getCookies();

        if (cookies == null) {
            sendUnauthorizedError(httpResponse);
            return;
        }

        Optional<Cookie> sessionCookie = Arrays.stream(cookies)
                .filter(cookie -> JSESSIONID.equals(cookie.getName()))
                .findFirst();

        if (sessionCookie.isPresent()) {
            UserSessionDto userSessionDto = userSessionService.getUserSession(sessionCookie.get().getValue());

            if (userSessionDto != null && !userSessionDto.isExpired()) {
                servletRequest.setAttribute("userData", userSessionDto.getUser());
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
        }

        sendUnauthorizedError(httpResponse);
    }

    private void sendUnauthorizedError(HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    }
}