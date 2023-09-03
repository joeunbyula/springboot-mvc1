package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletv3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerv3Map = new HashMap<>();

    public FrontControllerServletV3() {
        //해당 경로로 요청이 오면 지정해 놓은 controller 실행
        controllerv3Map.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerv3Map.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerv3Map.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletv3.service");

        //요청 URL
        String requestURI = request.getRequestURI();

        ControllerV3 controller = controllerv3Map.get(requestURI);
        if(controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //paramMap
        HashMap<String, String> paramMap = createParamMap(request);

        //viewResolver역할 생성(view를 찾아줌)
        ModelView mv = controller.process(paramMap);
        String viewName = mv.getViewName();//논리이름
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), request, response);

    }

    private static MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private static HashMap<String, String> createParamMap(HttpServletRequest request) {
        HashMap<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
