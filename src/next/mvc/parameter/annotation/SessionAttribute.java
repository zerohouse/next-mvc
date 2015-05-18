package next.mvc.parameter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 세션 Object를 사용합니다.<br>
 * require일 경우, 해당 오브젝트가 없으면 에러를 발생시킵니다.
 * 
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface SessionAttribute {
	public static final String SESSION_NULL = "세션이 만료되었거나 잘못된 접근입니다.";

	String value();

	boolean require() default true;

	String errorWhenSessionNull() default SESSION_NULL;
}
