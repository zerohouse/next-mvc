package next.mvc;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import next.mvc.setting.Setting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 디스패쳐를 등록하는 리스너 클래스입니다.<br>
 * 매핑의 세팅을 읽어와 매핑된 URL들을 등록합니다.
 */
@WebListener
public class Next implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(Next.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		Object mappings = Setting.getMapping().getMappings();
		ServletRegistration.Dynamic dispatcher = sc.addServlet("Dispatcher", "next.mvc.dispatch.Dispatcher");
		ServletRegistration defaultDispatcher = sc.getServletRegistration("default");
		dispatcher.setLoadOnStartup(1);

		if (mappings.getClass().equals(String.class)) {
			register(mappings.toString(), dispatcher, defaultDispatcher);
			return;
		}
		if (mappings.getClass().equals(ArrayList.class)) {
			@SuppressWarnings("unchecked")
			List<String> array = (List<String>) mappings;
			array.forEach(each -> {
				register(each, dispatcher, defaultDispatcher);
			});
			return;
		}
	}

	private void register(String mapping, ServletRegistration.Dynamic dispatcher, ServletRegistration defaultDispatcher) {
		if (mapping.toString().charAt(0) == '!') {
			String map = mapping.toString().substring(1);
			defaultDispatcher.addMapping(map);
			logger.info(String.format("%s URL이 제외되었습니다.", map));
			return;
		}
		dispatcher.addMapping(mapping.toString());
		logger.info(String.format("%s URL이 매핑되었습니다.", mapping));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

}