package next.mvc.response;

import next.mvc.http.Http;

/**
 * plain String을 리턴합니다.<br>
 */
public class Plain implements Response {
	private String body;

	public Plain(String body) {
		this.body = body;
	}

	@Override
	public void render(Http http) {
		http.write(body);
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void addBody(String body) {
		this.body += body;
	}

}
