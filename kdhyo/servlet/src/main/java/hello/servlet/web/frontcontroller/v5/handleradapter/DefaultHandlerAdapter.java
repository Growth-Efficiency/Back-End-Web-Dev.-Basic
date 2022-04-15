package hello.servlet.web.frontcontroller.v5.handleradapter;

import hello.servlet.web.frontcontroller.v5.MyHandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;
import java.util.ArrayList;
import java.util.List;

public class DefaultHandlerAdapter implements HandlerAdapter {

	private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

	public DefaultHandlerAdapter() {
		handlerAdapters.add(new ControllerV3HandlerAdapter());
		handlerAdapters.add(new ControllerV4HandlerAdapter());
	}

	@Override
	public MyHandlerAdapter getHandlerAdapter(Object handler) {
		for (MyHandlerAdapter adapter : handlerAdapters) {
			if (adapter.supports(handler)) {
				return adapter;
			}
		}
		throw new IllegalArgumentException("handler adapter 를 찾을 수 없습니다. handler = " + handler);
	}
	
}
