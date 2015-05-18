package next.mvc.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import next.mvc.http.Http;
import next.mvc.setting.Setting;

import com.google.gson.Gson;

public class HttpForTest implements Http {

	Map<String, String> parameters = new HashMap<String, String>();

	@Override
	public String getParameter(String name) {
		return parameters.get(name);
	}

	public String setParameter(String name, String value) {
		return parameters.put(name, value);
	}

	@Override
	public <T> T getJsonObject(Class<T> cLass, String name) {
		Gson gson = Setting.getGson();
		return gson.fromJson(parameters.get(name), cLass);
	}

	@Override
	public <T> T getJsonObject(Class<T> cLass) {
		Gson gson = Setting.getGson();
		return gson.fromJson(gson.toJson(parameters), cLass);
	}

	private String path;

	@Override
	public void forword(String path) {
		this.path = path;
	}

	private String contentType;

	@Override
	public void setContentType(String type) {
		this.contentType = type;
	}

	private String httpResult = new String();

	@Override
	public void write(String string) {
		httpResult += string;
	}

	private Map<String, String> uriVariables = new HashMap<String, String>();

	@Override
	public void putUriValue(String key, String uriVariable) {
		uriVariables.put(key, uriVariable);
	}

	@Override
	public String getUriValue(String key) {
		return uriVariables.get(key);
	}

	private String characterEncoding;

	@Override
	public void setCharacterEncoding(String encording) {
		characterEncoding = encording;
	}

	@Override
	public void sendNotFound() {
		errorNo = 404;
	}

	private Map<String, Object> sessionAttribute = new HashMap<String, Object>();

	@Override
	public void setSessionAttribute(String name, Object value) {
		sessionAttribute.put(name, value);
	}

	@Override
	public void removeSessionAttribute(String name) {
		sessionAttribute.remove(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getSessionAttribute(Class<T> cLass, String name) {
		return (T) sessionAttribute.get(name);
	}

	@Override
	public Object getSessionAttribute(String name) {
		return sessionAttribute.get(name);
	}

	private String redirect;

	@Override
	public void sendRedirect(String location) {
		redirect = location;
	}

	private Integer errorNo;

	@Override
	public void sendError(int errorNo) {
		this.errorNo = errorNo;
	}

	@Override
	public void sendError(int errorNo, String errorMesage) {
		this.errorNo = errorNo;
	}

	@Override
	public String toString() {
		String result = "";
		if (!parameters.isEmpty())
			result += "parameters=" + parameters + ", ";
		if (path != null)
			result += "path=" + path + ", ";
		if (contentType != null)
			result += " contentType=" + contentType + ", ";
		if (!httpResult.equals(""))
			result += "httpResult=" + httpResult + ", ";
		if (!uriVariables.isEmpty())
			result += "uriVariables=" + uriVariables + ", ";
		if (characterEncoding != null)
			result += "characterEncoding=" + characterEncoding + ", ";
		if (!sessionAttribute.isEmpty())
			result += "sessionAttribute=" + sessionAttribute + ", ";
		if (redirect != null)
			result += "redirect=" + redirect + ", ";
		if (errorNo != null)
			result += "errorNo=" + errorNo + ", ";
		if (!attribute.isEmpty())
			result += "attribute=" + attribute;
		return result;
	}

	Map<String, Object> attribute = new HashMap<String, Object>();

	@Override
	public Object getAttribute(String key) {
		return attribute.get(key);
	}

	@Override
	public void setAttribute(String key, Object value) {
		attribute.put(key, value);
	}

	@Override
	public HttpServletRequest getReq() {
		return null;
	}

	@Override
	public HttpServletResponse getResp() {
		return null;
	}

	@Override
	public int getUriValueSize() {
		return uriVariables.size();
	}

	@Override
	// [TODO] 구현
	public Part getPart(String name) {
		return null;
	}

	@Override
	public Collection<Part> getParts() {
		return null;
	}

}
