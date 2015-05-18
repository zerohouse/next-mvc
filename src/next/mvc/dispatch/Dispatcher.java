package next.mvc.dispatch;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import next.build.exception.TypeDuplicateException;
import next.mvc.http.Http;
import next.mvc.http.HttpImpl;
import next.mvc.setting.Setting;

public class Dispatcher extends HttpServlet {

	private static final long serialVersionUID = -2929326068606297558L;
	private Mapper mapper;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Http http = new HttpImpl(req, resp);
		String encording = Setting.getMapping().getCharacterEncoding();
		if (encording != null && !"".equals(encording))
			http.setCharacterEncoding(encording);
		mapper.execute(new UriKey(req.getMethod(), req.getRequestURI()), http);
	}

	@Override
	public void init() throws ServletException {
		try {
			mapper = new Mapper();
		} catch (TypeDuplicateException e) {
			e.printStackTrace();
		}
		CONTEXT_PATH = getServletContext().getRealPath(java.io.File.separator) + java.io.File.separator;
		ServletRegistration.Dynamic dispatcher = (Dynamic) getServletContext().getServletRegistration("Dispatcher");
		dispatcher.setMultipartConfig(Setting.getMapping().getUpload().getMultipartConfig());

	}

	public static String CONTEXT_PATH;

}