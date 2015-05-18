package next.mvc.http;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.mvc.setting.Setting;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class HttpImpl implements Http {

	private final static Logger logger = LoggerFactory.getLogger(HttpImpl.class);

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private Map<String, String> uriValues;

	@Override
	public String getParameter(String name) {
		return req.getParameter(name);
	}

	@Override
	public HttpServletRequest getReq() {
		return req;
	}

	@Override
	public HttpServletResponse getResp() {
		return resp;
	}

	@Override
	public <T> T getJsonObject(Class<T> cLass, String name) {
		Gson gson = Setting.getGson();
		try {
			return gson.fromJson(req.getParameter(name), cLass);
		} catch (JsonSyntaxException e) {
			logger.warn(e.getMessage());
			return null;
		}
	}

	@Override
	public <T> T getJsonObject(Class<T> cLass) {
		Gson gson = Setting.getGson();
		try {
			return gson.fromJson(gson.toJson(req.getParameterMap()), cLass);
		} catch (JsonSyntaxException e) {
			logger.warn(e.getMessage());
			return null;
		}
	}

	public HttpImpl(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	@Override
	public void forword(String path) {
		if (path.equals(""))
			sendError(508);
		RequestDispatcher rd = req.getRequestDispatcher(path);
		try {
			rd.forward(req, resp);
		} catch (ServletException e) {
			logger.warn(e.getMessage());
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
	}

	@Override
	public void setContentType(String type) {
		resp.setContentType(type);
	}

	@Override
	public void write(String string) {
		try {
			resp.getWriter().write(string);
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
	}

	@Override
	public void putUriValue(String key, String uriVariable) {
		if (uriValues == null)
			uriValues = new HashMap<String, String>();
		uriValues.put(key, uriVariable);
	}

	@Override
	public String getUriValue(String key) {
		if (uriValues == null)
			return null;
		return uriValues.get(key);
	}

	@Override
	public void setCharacterEncoding(String encording) {
		try {
			req.setCharacterEncoding(encording);
			resp.setCharacterEncoding(encording);
		} catch (UnsupportedEncodingException e) {
			logger.warn(e.getMessage());
		}
	}

	@Override
	public void sendNotFound() {
		try {
			resp.sendError(404, req.getRequestURI());
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
	}

	@Override
	public void setSessionAttribute(String name, Object value) {
		req.getSession().setAttribute(name, value);
	}

	@Override
	public void removeSessionAttribute(String name) {
		req.getSession().removeAttribute(name);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T getSessionAttribute(Class<T> cLass, String name) {
		return (T) req.getSession().getAttribute(name);
	}

	@Override
	public Object getSessionAttribute(String name) {
		return req.getSession().getAttribute(name);
	}

	@Override
	public void sendRedirect(String location) {
		if (location.equals(""))
			sendError(508);
		try {
			resp.sendRedirect(location);
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
	}

	@Override
	public void sendError(int errorNo) {
		try {
			resp.sendError(errorNo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendError(int errorNo, String errorMesage) {
		try {
			resp.sendError(errorNo, errorMesage);
		} catch (IOException e) {
			logger.warn(e.getMessage());
		}
	}

	@Override
	public void setAttribute(String key, Object value) {
		req.setAttribute(key, value);
	}

	@Override
	public Object getAttribute(String key) {
		return req.getAttribute(key);
	}

	@Override
	public int getUriValueSize() {
		if (uriValues == null)
			return 0;
		return uriValues.size();
	}

	@Override
	public Collection<Part> getParts() {
		try {
			return req.getParts();
		} catch (IOException | ServletException e) {
			logger.warn(e.getMessage());
		}
		return null;
	}

	@Override
	public Part getPart(String name) {
		try {
			return req.getPart(name);
		} catch (IOException | ServletException e) {
			logger.warn(e.getMessage());
		}
		return null;
	}

}
