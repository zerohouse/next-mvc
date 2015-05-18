package next.mvc.response;

import next.mvc.http.Http;
import next.mvc.setting.Setting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 생성시 넘어온 Object를 JsonParsing하여 렌더링합니다. <br>
 * 
 */
public class Json implements Response {

	private final static Logger logger = LoggerFactory.getLogger(Json.class);

	private Object obj;

	public Json() {
	}

	public Json(Object object) {
		this.obj = object;
	}

	public void setJsonObj(Object jsonObj) {
		this.obj = jsonObj;
	}

	public Object getObject() {
		return obj;
	}

	public String getJsonString() {
		return Setting.getGson().toJson(obj);
	}

	@Override
	public void render(Http http) {
		http.setContentType("application/json");
		http.write(getJsonString());
		logger.debug(String.format("render : %s", getJsonString()));
	}

}
