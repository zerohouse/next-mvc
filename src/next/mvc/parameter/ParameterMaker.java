package next.mvc.parameter;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import next.mvc.exception.RequiredParamNullException;
import next.mvc.http.Http;
import next.mvc.http.Store;
import next.mvc.parameter.inject.Inject;

public class ParameterMaker {
	private Queue<Inject> insertParameters;

	private Object getParameter(Http http, Store store, Class<?> type, Parameter obj) throws RequiredParamNullException {
		return insertParameters.stream().filter(insertParameter -> {
			return insertParameter.matches(type, obj);
		}).findFirst().get().getParameter(http, store, type, obj);
	}

	public Object[] getParamArray(Http http, Method method, Store store) throws RequiredParamNullException {
		Class<?>[] types = method.getParameterTypes();
		Parameter[] obj = method.getParameters();
		List<Object> parameters = new ArrayList<Object>();
		for (int i = 0; i < obj.length; i++) {
			parameters.add(getParameter(http, store, types[i], obj[i]));
		}
		return parameters.toArray();
	}

	public ParameterMaker(Set<Inject> inserts) {
		insertParameters = new ConcurrentLinkedQueue<Inject>();
		insertParameters.addAll(inserts);
	}

}
