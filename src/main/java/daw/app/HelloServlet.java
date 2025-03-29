package daw.app;

import java.io.*;
import java.util.logging.Logger;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet("/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    private final Logger log=Logger.getLogger(HelloServlet.class.getName());

    public void init() {
        log.info("HelloServlet initialized");
        message = "Welcome DAW!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();

        String htmlContent="""
            <html>
            <body>
                <h1>Sample Servlet</h1>
                <h2>%s</h2>
                <br/>
                <a href="index.xhtml">Back</a>
            </body>
            </html>
        """.formatted(message);

        out.println(htmlContent);
        log.info("Petición GET recibida desde %s".formatted(request.getRemoteAddr()));
    }

}