package next.mvc.test;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

@SuppressWarnings("deprecation")
public class HttpSessionForTest implements HttpSession {

	@Override
	public long getCreationTime() {
		return 0;
	}

	@Override
	public String getId() {
		return null;
	}

	@Override
	public long getLastAccessedTime() {
		return 0;
	}

	@Override
	public ServletContext getServletContext() {
		return null;
	}

	@Override
	public void setMaxInactiveInterval(int interval) {

	}

	@Override
	public int getMaxInactiveInterval() {
		return 0;
	}

	private Map<String, Object> value = new HashMap<String, Object>();
	private Map<String, Object> attribute = new HashMap<String, Object>();

	@Override
	public Object getAttribute(String name) {
		return attribute.get(name);
	}

	@Override
	public Object getValue(String name) {
		return value.get(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return null;
	}

	@Override
	public String[] getValueNames() {
		return null;
	}

	@Override
	public void setAttribute(String name, Object value) {
		attribute.put(name, value);
	}

	@Override
	public void putValue(String name, Object value) {
		this.value.put(name, value);
	}

	@Override
	public void removeAttribute(String name) {
		attribute.remove(name);
	}

	@Override
	public void removeValue(String name) {
		value.remove(name);
	}

	@Override
	public void invalidate() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isNew() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public HttpSessionContext getSessionContext() {
		// TODO Auto-generated method stub
		return null;
	}

}
