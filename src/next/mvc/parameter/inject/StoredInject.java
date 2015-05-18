package next.mvc.parameter.inject;

import java.lang.reflect.Parameter;

import next.mvc.http.Http;
import next.mvc.http.Store;
import next.mvc.parameter.annotation.ParameterInject;
import next.mvc.parameter.annotation.Stored;

@ParameterInject
public class StoredInject implements Inject {

	@Override
	public Object getParameter(Http http, Store store, Class<?> type, Parameter obj) {
		Stored stored = obj.getAnnotation(Stored.class);
		if (stored.value().equals("")) {
			return store.get(type);
		}
		return store.get(stored.value());
	}

	@Override
	public boolean matches(Class<?> type, Parameter obj) {
		return obj.isAnnotationPresent(Stored.class);
	}
}
