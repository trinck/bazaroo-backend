package org.mts.notificationservice.utilities;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;

public class HeaderRemovingResponseWrapper extends HttpServletResponseWrapper {

    public HeaderRemovingResponseWrapper(HttpServletResponse response) {
        super(response);
    }

    @Override
    public void addHeader(String name, String value) {
        if ("Access-Control-Allow-Origin".equalsIgnoreCase(name)) {
            // Ne rien ajouter
            return;
        }
        super.addHeader(name, value);
    }

    @Override
    public void setHeader(String name, String value) {
        if ("Access-Control-Allow-Origin".equalsIgnoreCase(name)) {
            // Ne rien définir
            return;
        }
        super.setHeader(name, value);
    }
}