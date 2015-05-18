package next.mvc.dispatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import next.mvc.exception.RequiredParamNullException;

public class MethodWrapper {

	private Object instance;
	private Method method;

	public MethodWrapper(Object instance, Method method) {
		this.method = method;
		this.instance = instance;
	}

	public Method getMethod() {
		return method;
	}

	public Object execute(Object[] parameterArray) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			RequiredParamNullException {
		return method.invoke(instance, parameterArray);
	}

	@Override
	public String toString() {
		return method.getName();
	}
}
