package next.mvc.parameter.inject;

import java.lang.reflect.Parameter;

import next.mvc.exception.RequiredParamNullException;
import next.mvc.http.Http;
import next.mvc.http.Store;
import next.mvc.parameter.annotation.JsonParameter;
import next.mvc.parameter.annotation.ParameterInject;

@ParameterInject
public class JsonParameterInject implements Inject {

	@Override
	public Object getParameter(Http http, Store store, Class<?> type, Parameter obj) throws RequiredParamNullException {
		JsonParameter jparam = obj.getAnnotation(JsonParameter.class);
		String name = jparam.value();
		Object value = http.getJsonObject(type, name);
		if (jparam.require() && value == null)
			throw new RequiredParamNullException(jparam.errorWhenParamNull());
		return value;
	}

	@Override
	public boolean matches(Class<?> type, Parameter obj) {
		return obj.isAnnotationPresent(JsonParameter.class);
	}
}
