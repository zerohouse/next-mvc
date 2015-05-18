package next.mvc.parameter.inject;

import java.lang.reflect.Parameter;

import next.mvc.http.Http;
import next.mvc.http.Store;
import next.mvc.parameter.annotation.ParameterInject;
import next.mvc.parameter.annotation.UriValue;

@ParameterInject
public class UriValueInject implements Inject {

	@Override
	public Object getParameter(Http http, Store store, Class<?> type, Parameter obj) {
		return http.getUriValue(obj.getAnnotation(UriValue.class).value());
	}

	@Override
	public boolean matches(Class<?> type, Parameter obj) {
		return obj.isAnnotationPresent(UriValue.class);
	}
}
