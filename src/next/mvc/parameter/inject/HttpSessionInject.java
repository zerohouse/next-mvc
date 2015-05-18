package next.mvc.parameter.inject;


import java.lang.reflect.Parameter;

import javax.servlet.http.HttpSession;

import next.mvc.http.Http;
import next.mvc.http.Store;
import next.mvc.parameter.annotation.ParameterInject;

@ParameterInject
public class HttpSessionInject implements Inject {

	@Override
	public Object getParameter(Http http, Store store, Class<?> type, Parameter obj) {
		return http.getReq().getSession();
	}

	@Override
	public boolean matches(Class<?> type, Parameter obj) {
		return type.equals(HttpSession.class);

	}
}
