package next.mvc.parameter.inject;

import java.lang.reflect.Parameter;

import javax.servlet.http.Part;

import next.mvc.exception.RequiredParamNullException;
import next.mvc.http.Http;
import next.mvc.http.Store;
import next.mvc.parameter.UploadFile;
import next.mvc.parameter.annotation.FileParameter;
import next.mvc.parameter.annotation.ParameterInject;

@ParameterInject
public class FileParameterInject implements Inject {

	@Override
	public Object getParameter(Http http, Store store, Class<?> type, Parameter obj) throws RequiredParamNullException {
		FileParameter param = obj.getAnnotation(FileParameter.class);
		String name = param.value();
		Object value = null;
		if (type.equals(UploadFile.class))
			value = new UploadFile(http.getPart(name));
		else if (type.equals(Part.class))
			value = http.getPart(name);
		if (param.require() && value == null)
			throw new RequiredParamNullException(param.errorWhenParamNull());
		return value;
	}

	@Override
	public boolean matches(Class<?> type, Parameter obj) {
		return obj.isAnnotationPresent(FileParameter.class);
	}
}
