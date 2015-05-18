package next.mvc.parameter.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Store에 저장된 Object를 꺼냅니다. <br>
 * Store.put(Object)또는 <br>
 * Store.put(key ,Object)를 통해 저장합니다.
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Stored {

	String value() default "";
}
