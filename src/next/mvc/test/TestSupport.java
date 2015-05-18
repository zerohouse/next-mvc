package next.mvc.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import next.mvc.http.Http;

public class TestSupport {

	
	public static Http getHttp(){
		return new HttpForTest();
	}
	public static HttpSession getHttpSession(){
		return new HttpSessionForTest();
	}
	public static HttpServletRequest getHttpServletRequest(){
		return new HttpServletRequestForTest();
	}
	public static HttpServletResponse getHttpServletResponse(){
		return new HttpServletResponseForTest();
	}
}
