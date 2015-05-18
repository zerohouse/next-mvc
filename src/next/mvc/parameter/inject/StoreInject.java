package next.mvc.parameter.inject;

import java.lang.reflect.Parameter;

import next.mvc.http.Http;
import next.mvc.http.Store;
import next.mvc.parameter.annotation.ParameterInject;

@ParameterInject
public class StoreInject implements Inject {

	@Override
	public Object getParameter(Http http, Store store, Class<?> type, Parameter obj) {
		return store;
	}

	@Override
	public boolean matches(Class<?> type, Parameter obj) {
		return type.equals(Store.class);
	}

}
