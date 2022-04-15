package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.v5.handleradapter.DefaultHandlerAdapter;
import hello.servlet.web.frontcontroller.v5.handleradapter.HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.handlermapping.DefaultHandlerMapping;
import hello.servlet.web.frontcontroller.v5.handlermapping.HandlerMapping;

public class AppConfig {

	public HandlerMapping handlerMapping() {
		return new DefaultHandlerMapping();
	}

	public HandlerAdapter handlerAdapters() {
		return new DefaultHandlerAdapter();
	}
}
