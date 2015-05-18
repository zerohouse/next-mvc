package next.mvc.response;

import java.util.HashMap;
import java.util.Map;

import next.mvc.http.Http;
import next.mvc.setting.Setting;

/**
 * path의 JSP를 리턴합니다.<br>
 * put하여 jstl을 사용할 수 있습니다.
 */
public class Jsp implements Response {

	private String jspFileName;

	private static final String PATH = Setting.getMapping().getJspPath();

	private Map<String, Object> variables = new HashMap<String, Object>();

	public Jsp() {
	}

	public void setJspFileName(String jspFileName) {
		this.jspFileName = jspFileName;
	}

	public Jsp(String jspFileName) {
		this.jspFileName = jspFileName;
	}

	/**
	 * JSP에 변수를 설정합니다.
	 * <p>
	 *
	 * @param key
	 *            사용할 변수의 key
	 * @param obj
	 *            사용할 변수
	 */
	public void put(String key, Object obj) {
		variables.put(key, obj);
	}

	public Object get(String key) {
		return variables.get(key);
	}

	@Override
	public void render(Http http) {
		for (Map.Entry<String, Object> entry : variables.entrySet()) {
			http.setAttribute(entry.getKey(), entry.getValue());
		}
		http.forword(PATH + jspFileName);
	}

	public String getJspFileName() {
		return jspFileName;
	}

	@Override
	public String toString() {
		return "Jsp [" + jspFileName + "] variables : " + variables;
	}

}
