package next.mvc.http;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public interface Http {

	String getParameter(String name);

	<T> T getJsonObject(Class<T> cLass, String name);

	<T> T getJsonObject(Class<T> cLass);

	void forword(String path);

	void setContentType(String type);

	void write(String string);

	void putUriValue(String key, String uriVariable);

	String getUriValue(String key);

	void setCharacterEncoding(String encording);

	void sendNotFound();

	void setSessionAttribute(String name, Object value);

	void removeSessionAttribute(String name);

	<T> T getSessionAttribute(Class<T> cLass, String name);

	Object getSessionAttribute(String name);

	void sendRedirect(String location);

	void sendError(int errorNo);

	void sendError(int errorNo, String errorMesage);

	void setAttribute(String key, Object value);

	Object getAttribute(String key);

	HttpServletRequest getReq();

	HttpServletResponse getResp();

	int getUriValueSize();

	Part getPart(String name);

	Collection<Part> getParts();

}
