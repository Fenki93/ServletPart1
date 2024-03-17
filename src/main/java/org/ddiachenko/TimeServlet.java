package org.ddiachenko;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet(value = "/time")
public class TimeServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        String timezone = req.getParameter("timezone");

        if (timezone != null && !timezone.isEmpty()) {
            timezone = timezone.replace(" ", "+");
            resp.getWriter().write(formattedZoneDateTime(ZonedDateTime.now(ZoneId.of(timezone))));
        } else resp.getWriter().write(formattedDateTime(LocalDateTime.now()));
        resp.getWriter().close();
    }


    private String formattedDateTime(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss 'UTC'"));
    }

    private String formattedZoneDateTime(ZonedDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
    }
}