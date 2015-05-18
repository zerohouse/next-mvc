package next.mvc.parameter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * request의 파라미터를 JSON으로 파싱합니다.<br>
 * require일 경우, 해당 오브젝트가 없으면 에러를 발생시킵니다.
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface JsonParameter {
	
	public static final String PARAM_NULL = "필수 파라미터가 빠졌습니다.";
	
	String value();

	boolean require() default true;

	String errorWhenParamNull() default PARAM_NULL;
}
