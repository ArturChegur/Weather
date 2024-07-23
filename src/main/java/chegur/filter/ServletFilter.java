package chegur.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

@WebFilter("/*")
public class ServletFilter implements Filter {
    private static final Set<String> UNAUTHORIZED_PATH = Set.of("/Weather/home", "/Weather/found-locations");


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletResponse.setContentType("text/html");
//
//        String uri = ((HttpServletRequest) servletRequest).getRequestURI();
//        if (isUnauthorizedPath(uri) && isUserLoggedIn(servletRequest)) {
//            ((HttpServletResponse) servletResponse).sendRedirect("login");
//        } else {
            filterChain.doFilter(servletRequest, servletResponse);
//        }

    }

    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        return ((HttpServletRequest) servletRequest).getSession(false) == null;
    }

    private boolean isUnauthorizedPath(String uri) {
        System.out.println(uri);
        System.out.println( UNAUTHORIZED_PATH.stream().anyMatch(uri::startsWith));
        return UNAUTHORIZED_PATH.stream().anyMatch(uri::startsWith);
    }
}
