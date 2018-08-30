package org.xhtmlrenderer.dom.integration.servlets;

import org.xhtmlrenderer.dom.integration.TestServer;
import org.xhtmlrenderer.dom.parser.CharacterReaderTest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class InterruptedServlet extends BaseServlet {
    public static final String Url = TestServer.map(InterruptedServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType(TextHtml);
        res.setStatus(HttpServletResponse.SC_OK);

        StringBuilder sb = new StringBuilder();
        sb.append("<title>Something</title>");
        while (sb.length() <= CharacterReaderTest.maxBufferLen) {
            sb.append("A suitable amount of data. \n");
        }
        String data = sb.toString();

        res.setContentLength(data.length() * 2);

        res.getWriter().write(data);

    }
}
