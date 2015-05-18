package next.mvc.parameter.inject;

import java.lang.reflect.Parameter;

import javax.servlet.http.HttpServletRequest;

import next.mvc.http.Http;
import next.mvc.http.Store;
import next.mvc.parameter.annotation.ParameterInject;

@ParameterInject
public class HttpServletRequestInject implements Inject {

	@Override
	public Object getParameter(Http http, Store store, Class<?> type, Parameter obj) {
		return http.getReq();
	}

	@Override
	public boolean matches(Class<?> type, Parameter obj) {
		return type.equals(HttpServletRequest.class);

	}

}
