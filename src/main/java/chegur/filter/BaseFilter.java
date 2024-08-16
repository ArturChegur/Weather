package chegur.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@WebFilter("/*")
public class BaseFilter implements Filter {
    private static final Set<String> ALLOWED_PATH = Set.of(
            "/Weather/home",
            "/Weather/found-locations",
            "/Weather/login",
            "/Weather/register",
            "/Weather/images/background.png",
            "/Weather/css/home-found-locations.css",
            "/Weather/css/login-registration.css");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String currentPath = httpRequest.getRequestURI();

        if (currentPath == null || !isAllowedPath(currentPath)) {
            httpResponse.sendError(HttpServletResponse.SC_NOT_FOUND, "Sorry, the page you are looking for does not exist.");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private static boolean isAllowedPath(String uri) {
        return ALLOWED_PATH.stream().anyMatch(uri::equals);
    }
}
