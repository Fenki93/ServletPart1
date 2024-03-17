package org.ddiachenko;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.TimeZone;

@WebFilter(value = "/time")
public class TimezoneValidateFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {

        String timezone = req.getParameter("timezone").replace(" ", "+");
        if (timezone != null && !timezone.isEmpty()) {
            if (!isValidUtcOffset(timezone)) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().write("Invalid timezone");
                resp.getWriter().close();
                return;
            }
        } else {
            chain.doFilter(req, resp);
            return;
        }
        chain.doFilter(req, resp);
    }

    private boolean isValidUtcOffset(String timezone) {
        String[] parts = timezone.split("\\+");
        if (parts.length != 2) {
            return false;
        }
        try {
            int offset = Integer.parseInt(parts[1]);
            return offset >= -12 && offset <= 14;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}