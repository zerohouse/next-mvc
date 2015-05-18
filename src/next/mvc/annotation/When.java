package next.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * 클래스를 Uri와 매핑합니다.<br>
 * 지정된 메소드들을 먼저, 혹은 나중에 실행합니다.<br>
 * 
 * 모든 파라미터를 받을때 {}와 *를 사용합니다.
 * ex) &#064;Mapping("/{variableName}/*");
 * 
 * {}를 사용하면 변수를 꺼낼 수 있습니다.
 * 
 * &#064;Mapping("/{abc}");
 * http.getUriVariable("abc");
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.METHOD })
public @interface When {
	String[] value() default "";

	String[] method() default "GET";
}