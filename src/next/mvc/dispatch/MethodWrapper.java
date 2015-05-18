package next.mvc.dispatch;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import next.mvc.annotation.parameters.JsonParameter;
import next.mvc.annotation.parameters.Parameter;
import next.mvc.annotation.parameters.SessionAttribute;
import next.mvc.annotation.parameters.Stored;
import next.mvc.annotation.parameters.UriValue;
import next.mvc.exception.RequiredParamNullException;
import next.mvc.http.Http;
import next.mvc.http.Store;
import next.mvc.upload.UploadFile;

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

	public Object execute(Http http, Store store) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			RequiredParamNullException {
		return method.invoke(instance, getParamArray(http, method, store));
	}

	@Override
	public String toString() {
		return method.getName();
	}

	public Object[] getParamArray(Http http, Method method, Store store) throws RequiredParamNullException {
		Class<?>[] types = method.getParameterTypes();
		java.lang.reflect.Parameter[] obj = method.getParameters();
		List<Object> parameters = new ArrayList<Object>();
		for (int i = 0; i < obj.length; i++) {
			if (types[i].equals(Http.class)) {
				parameters.add(http);
				continue;
			}
			if (types[i].equals(Store.class)) {
				parameters.add(store);
				continue;
			}
			if (types[i].equals(HttpServletRequest.class)) {
				parameters.add(http.getReq());
				continue;
			}
			if (types[i].equals(HttpServletResponse.class)) {
				parameters.add(http.getResp());
				continue;
			}
			if (types[i].equals(HttpSession.class)) {
				parameters.add(http.getReq().getSession());
				continue;
			}
			if (types[i].equals(HttpServletResponse.class)) {
				parameters.add(http.getResp());
				continue;
			}
			if (obj[i].isAnnotationPresent(Parameter.class)) {
				Parameter param = obj[i].getAnnotation(Parameter.class);
				String name = param.value();
				Object value = null;

				if (types[i].equals(String.class))
					value = http.getParameter(name);
				else if (types[i].equals(UploadFile.class))
					value = new UploadFile(http.getPart(name));
				else if (types[i].equals(Part.class))
					value = http.getPart(name);

				if (param.require() && value == null)
					throw new RequiredParamNullException(param.errorWhenParamNull());
				parameters.add(value);
				continue;
			}
			if (obj[i].isAnnotationPresent(JsonParameter.class)) {
				JsonParameter jparam = obj[i].getAnnotation(JsonParameter.class);
				String name = jparam.value();
				Object value = http.getJsonObject(types[i], name);
				if (jparam.require() && value == null)
					throw new RequiredParamNullException(jparam.errorWhenParamNull());
				parameters.add(value);
				continue;
			}
			if (obj[i].isAnnotationPresent(SessionAttribute.class)) {
				SessionAttribute session = obj[i].getAnnotation(SessionAttribute.class);
				String name = session.value();
				Object value = http.getSessionAttribute(Object.class, name);
				if (session.require() && value == null)
					throw new RequiredParamNullException(session.errorWhenSessionNull());
				parameters.add(value);
				continue;
			}
			if (obj[i].isAnnotationPresent(UriValue.class)) {
				UriValue uri = obj[i].getAnnotation(UriValue.class);
				parameters.add(http.getUriVariable(uri.value()));
				continue;
			}
			if (obj[i].isAnnotationPresent(Stored.class)) {
				Stored stored = obj[i].getAnnotation(Stored.class);
				if (stored.value().equals("")) {
					parameters.add(store.get(types[i]));
					continue;
				}
				parameters.add(store.get(stored.value()));
				continue;
			}
			parameters.add(null);
		}
		return parameters.toArray();
	}
}
