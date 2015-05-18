package next.mvc.parameter.inject;

import java.lang.reflect.Parameter;

import next.mvc.exception.RequiredParamNullException;
import next.mvc.http.Http;
import next.mvc.http.Store;
import next.mvc.parameter.annotation.ParameterInject;
import next.mvc.parameter.annotation.StringParameter;

@ParameterInject
public class StringParameterInject implements Inject {

	@Override
	public Object getParameter(Http http, Store store, Class<?> type, Parameter obj) throws RequiredParamNullException {
		return http.getParameter(obj.getAnnotation(StringParameter.class).value());
	}

	@Override
	public boolean matches(Class<?> type, Parameter obj) {
		return obj.isAnnotationPresent(StringParameter.class);
	}
}
