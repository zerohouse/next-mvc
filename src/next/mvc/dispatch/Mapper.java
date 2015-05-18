package next.mvc.dispatch;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import next.build.exception.TypeDuplicateException;
import next.build.instance.InstancePool;
import next.mvc.annotation.After;
import next.mvc.annotation.Before;
import next.mvc.annotation.HttpMethod;
import next.mvc.annotation.HttpMethods;
import next.mvc.annotation.UriRouter;
import next.mvc.annotation.When;
import next.mvc.http.Http;
import next.mvc.http.Store;
import next.mvc.parameter.ParameterMaker;
import next.mvc.parameter.annotation.ParameterInject;
import next.mvc.parameter.inject.Inject;
import next.mvc.response.Json;
import next.mvc.response.Plain;
import next.mvc.response.Response;
import next.mvc.setting.Setting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Mapper {

	private static final Logger logger = LoggerFactory.getLogger(Mapper.class);

	private Map<String, MethodWrapper> methodMap;
	private UriMap uriMap;

	private InstancePool instancePool;
	private ParameterMaker parameterMaker;

	Mapper() throws TypeDuplicateException {
		instancePool = new InstancePool(Setting.getMapping().getBasePackage());
		instancePool.addClassAnnotations(UriRouter.class, HttpMethods.class, ParameterInject.class);
		instancePool.addMethodAnnotations(When.class, HttpMethod.class);
		instancePool.build();

		Set<Inject> set = new HashSet<Inject>();
		instancePool.getAnnotatedInstance(ParameterInject.class).forEach(inject -> {
			set.add((Inject) inject);
		});

		parameterMaker = new ParameterMaker(set);

		methodMap = new HashMap<String, MethodWrapper>();
		uriMap = new UriMap();
		makeMethodMap();
		makeUriMap();
	}

	private void makeMethodMap() {
		Setting.getReflections().getMethodsAnnotatedWith(HttpMethod.class).forEach(m -> {
			Object instance = instancePool.getInstance(m.getDeclaringClass());
			HttpMethod method = m.getAnnotation(HttpMethod.class);
			String methodName = method.value();
			if (methodName.equals(""))
				methodName = m.getName();
			methodMap.put(methodName, new MethodWrapper(instance, m));
		});
		logger.info(String.format("HttpMethod -> %s", methodMap.toString()));
	}

	private void makeUriMap() {
		Setting.getReflections().getMethodsAnnotatedWith(When.class).forEach(m -> {
			Class<?> declaringClass = m.getDeclaringClass();
			String[] prefix;
			Queue<MethodWrapper> methodList = new ConcurrentLinkedQueue<MethodWrapper>();
			String[] beforeClass = null;
			String[] afterClass = null;
			if (declaringClass.isAnnotationPresent(When.class)) {
				When mapping = declaringClass.getAnnotation(When.class);
				prefix = mapping.value();
			} else {
				prefix = new String[] { "" };
			}
			if (declaringClass.isAnnotationPresent(Before.class))
				beforeClass = declaringClass.getAnnotation(Before.class).value();
			if (declaringClass.isAnnotationPresent(After.class))
				afterClass = declaringClass.getAnnotation(After.class).value();
			When mapping = m.getAnnotation(When.class);
			String[] before = null;
			String[] after = null;
			if (m.isAnnotationPresent(Before.class))
				before = declaringClass.getAnnotation(Before.class).value();
			if (m.isAnnotationPresent(After.class))
				after = declaringClass.getAnnotation(After.class).value();

			addAll(methodList, beforeClass);
			addAll(methodList, before);
			methodList.add(new MethodWrapper(instancePool.getInstance(declaringClass), m));
			addAll(methodList, after);
			addAll(methodList, afterClass);
			for (int i = 0; i < prefix.length; i++)
				for (int j = 0; j < mapping.method().length; j++)
					for (int k = 0; k < mapping.value().length; k++) {
						String method = mapping.method()[j];
						String uri = prefix[i] + mapping.value()[k];
						UriKey urikey = new UriKey(method, uri);
						uriMap.put(urikey, methodList);
						logger.info(String.format("Mapping : %s -> %s", urikey.toString(), methodList.toString()));
					}
		});
	}

	private void addAll(Queue<MethodWrapper> methodList, String[] stringArray) {
		if (stringArray == null)
			return;
		for (int i = 0; i < stringArray.length; i++) {
			if (stringArray[i].equals(""))
				continue;
			MethodWrapper method;
			if (stringArray[i].charAt(0) == '!') {
				method = methodMap.get(stringArray[i].substring(1));
				if (method == null) {
					logger.warn(String.format("없는 Method [%s]를 제외하려고 했습니다.", stringArray[i]));
					continue;
				}

				if (!methodList.contains(method)) {
					logger.warn(String.format("실행할 메소드리스트에 추가되지 않은 Method [%s]를 제외하려고 했습니다.", stringArray[i]));
					continue;
				}
				methodList.remove(method);
			}
			method = methodMap.get(stringArray[i]);
			if (method == null) {
				logger.warn(String.format("없는 Method [%s]를 매핑하려고 했습니다.", stringArray[i]));
				continue;
			}
			methodList.add(methodMap.get(stringArray[i]));
		}
	}

	public void execute(UriKey url, Http http) {
		Queue<MethodWrapper> methods = uriMap.get(url, http);
		if (methods == null) {
			http.sendError(404);
			return;
		}
		logger.debug(String.format("%s -> %s", url, methods.toString()));
		Iterator<MethodWrapper> miter = methods.iterator();
		Store store = new Store(); // 생각을 좀 해봅시다..ㅇㅅㅇ;;
		try {
			while (miter.hasNext()) {
				MethodWrapper mw = miter.next();
				Object returned = mw.execute(parameterMaker.getParamArray(http, mw.getMethod(), store));

				if (returned == null)
					continue;
				if (Response.class.isAssignableFrom(returned.getClass())) {
					((Response) returned).render(http);
					return;
				}
				if (returned.getClass().equals(String.class)) {
					if (stringResponse(http, returned))
						return;
				}
				new Json(returned).render(http);
				return;
			}
			new Json().render(http);
		} catch (Exception e) {
			new Plain(e.getMessage()).render(http);
		}
	}

	private boolean stringResponse(Http http, Object returned) {
		String res = returned.toString();
		if (!res.contains(":")) {
			return false;
		}
		String[] str = res.split(":");
		if ("forward".equals(str[0])) {
			if (str.length == 1) {
				http.sendError(508);
				return true;
			}
			http.forword(str[1]);
			return true;
		}
		if ("redirect".equals(str[0])) {
			if (str.length == 1) {
				http.sendError(508);
				return true;
			}
			http.sendRedirect(str[1]);
			return true;
		}
		if ("error".equals(str[0])) {
			if (str.length == 3) {
				http.sendError(Integer.parseInt(str[1]), str[2]);
				return true;
			}
			http.sendError(Integer.parseInt(str[1]));
			return true;
		}
		return false;
	}

}
