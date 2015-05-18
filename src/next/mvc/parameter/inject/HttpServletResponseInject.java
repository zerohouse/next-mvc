package next.mvc.parameter.inject;

import java.lang.reflect.Parameter;

import javax.servlet.http.HttpServletResponse;

import next.mvc.http.Http;
import next.mvc.http.Store;
import next.mvc.parameter.annotation.ParameterInject;

@ParameterInject
public class HttpServletResponseInject implements Inject {

	@Override
	public Object getParameter(Http http, Store store, Class<?> type, Parameter obj) {
		return http.getResp();
	}

	@Override
	public boolean matches(Class<?> type, Parameter obj) {
		return type.equals(HttpServletResponse.class);

	}
}
