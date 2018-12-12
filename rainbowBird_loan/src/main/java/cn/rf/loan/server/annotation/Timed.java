/**
 * 
 */
package cn.rf.loan.server.annotation;

import java.lang.annotation.*;

/**
 * Created by Kotarou on 2017/1/11.
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Timed {
	boolean displayArgs() default false;
}
