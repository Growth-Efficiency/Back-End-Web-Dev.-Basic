package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v5.handleradapter.HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.handlermapping.HandlerMapping;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

	private final HandlerMapping handlerMapping;
	private final HandlerAdapter handlerAdapters;

	public FrontControllerServletV5() {
		AppConfig appConfig = new AppConfig();

		this.handlerMapping = appConfig.handlerMapping();
		this.handlerAdapters = appConfig.handlerAdapters();
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Object handler = getHandler(request);
		if (handler == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		MyHandlerAdapter adapter = handlerAdapters.getHandlerAdapter(handler);
		ModelView mv = adapter.handle(request, response, handler);

		String viewName = mv.getViewName();
		MyView view = viewResolver(viewName);

		view.render(mv.getModel(), request, response);
	}

	private Object getHandler(HttpServletRequest request) {
		String requestURI = request.getRequestURI();
		return handlerMapping.getHandler(requestURI);
	}

	private MyView viewResolver(String viewName) {
		return new MyView("/WEB-INF/views/" + viewName + ".jsp");
	}

}
