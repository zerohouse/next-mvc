package next.mvc.parameter.inject;

import next.mvc.exception.RequiredParamNullException;
import next.mvc.http.Http;
import next.mvc.http.Store;

public interface Inject {

	public Object getParameter(Http http, Store store, Class<?> type, java.lang.reflect.Parameter obj) throws RequiredParamNullException;

	public boolean matches(Class<?> type, java.lang.reflect.Parameter obj);

}
