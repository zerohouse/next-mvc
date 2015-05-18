package next.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 해당 메소드를 컨트롤러에서 before, after로 사용가능합니다.<br>
 * value를 지정하지 않으면 methodname이 키가 됩니다.
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface HttpMethod {

	String value() default "";
	
}
