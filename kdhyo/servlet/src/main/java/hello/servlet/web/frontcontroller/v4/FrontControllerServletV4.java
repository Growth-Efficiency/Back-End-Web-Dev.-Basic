package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

	private Map<String, ControllerV4> controllerV4Map = new HashMap<>();

	public FrontControllerServletV4() {
		controllerV4Map.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
		controllerV4Map.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
		controllerV4Map.put("/front-controller/v4/members", new MemberListControllerV4());
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String requestURI = request.getRequestURI();

		// 호출된 URI 가 유효한지 확인
		ControllerV4 controller = controllerV4Map.get(requestURI);
		if (controller == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// 파라미터 정보 받아오기 (POST 일 때는..?)
		Map<String, String> paramMap = createParamMap(request);

		// 프론트 컨트롤러에서 직접 모델 생성 후 컨트롤러에 전달
		Map<String, Object> model = new HashMap<>();
		// 컨트롤러 로직 수행 (uri 반환 하게 됨 + 인수인 모델에 데이터를 담아온다.)
		String viewName = controller.process(paramMap, model);

		// 실제 jsp 경로 추출
		MyView view = viewResolver(viewName);

		// view rendering + request model setting
		view.render(model, request, response);
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
