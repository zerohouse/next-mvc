package next.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 지정된 메소드들을 실행 후 실행합니다.<br>
 * HttpMethod로 지정된 메소드를 실행가능합니다.
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface After {
	String[] value() default "";
}
