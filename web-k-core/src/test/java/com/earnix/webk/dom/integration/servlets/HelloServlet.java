package com.earnix.webk.dom.integration.servlets;

import com.earnix.webk.dom.integration.TestServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HelloServlet extends BaseServlet {
    public static final String Url = TestServer.map(HelloServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType(TextHtml);
        res.setStatus(HttpServletResponse.SC_OK);

        String doc = "<p>Hello, World!";
        res.getWriter().write(doc);
    }
}
