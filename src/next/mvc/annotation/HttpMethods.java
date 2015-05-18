package next.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * HttpMethods 클래스를 지정합니다.
 * (HttpMethod는 Controller클래스 아래에 있어도 무방합니다.) 
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HttpMethods {

}
