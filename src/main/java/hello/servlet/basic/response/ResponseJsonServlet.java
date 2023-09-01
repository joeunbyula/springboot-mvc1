package hello.servlet.basic.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.servlet.basic.HelloData;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //content-type: application/json
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8"); //json은 인코딩이 기본으로 utf-8이므로 굳이 설정안해줘도된다.

        HelloData helloData = new HelloData();
        helloData.setAge(20);
        helloData.setUsername("kim");

        //{"username":"kim", "age":20}
        String s = objectMapper.writeValueAsString(helloData);
        response.getWriter().write(s);

    }
}
