package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

	private Map<String, ControllerV3> controllerV3Map = new HashMap<>();

	public FrontControllerServletV3() {
		controllerV3Map.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
		controllerV3Map.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
		controllerV3Map.put("/front-controller/v3/members", new MemberListControllerV3());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();

		// 호출된 URI 가 유효한지 확인
		ControllerV3 controller = controllerV3Map.get(requestURI);
		if (controller == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// 파라미터 정보 받아오기 (POST 일 때는..?)
		Map<String, String> paramMap = createParamMap(request);
		// 컨트롤러 로직 수행 (비지니스 로직 + uri 반환 하게 됨)
		ModelView mv = controller.process(paramMap);

		// 실제 jsp 경로 추출
		String viewName = mv.getViewName();
		MyView view = viewResolver(viewName);

		// view rendering + request model setting
		view.render(mv.getModel(), request, response);
	}

	private Map<String, String> createParamMap(HttpServletRequest request) {
		Map<String, String> paramMap = new HashMap<>();
		request.getParameterNames().asIterator()
			.forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
		return paramMap;
	}

	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}

}
