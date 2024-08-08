package chegur.filter;

import chegur.service.SessionService;
import chegur.util.CookieHandler;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebFilter(urlPatterns = {"/home", "/found-locations"})
public class ProtectedPathFilter implements Filter {
    private final SessionService sessionService = SessionService.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        Cookie[] cookies = httpRequest.getCookies();

        if (cookies == null) {
            sendUnauthorizedError(httpResponse);
            return;
        }

        Optional<String> sessionCookie = CookieHandler.getSessionCookie(cookies);

        if (sessionCookie.isPresent()) {
            if (sessionService.isSessionActive(sessionCookie.get())) {
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