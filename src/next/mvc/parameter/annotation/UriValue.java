package next.mvc.parameter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Uri변수를 사용합니다.<br>
 * &#064;Mapping("variableName")으로 지정된 경우<br>
 * &#064;UriVariable("variableName") String uriVariable <br>
 * 처럼 사용가능합니다.
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface UriValue {

	String value();

}
