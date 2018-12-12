/**
 * 
 */
package cn.rf.loan.server.aop;

import cn.rf.loan.server.annotation.Timed;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by Kotarou on 2017/1/11.
 */
@Aspect
@Component
public class TimedAOP {

	@Pointcut("@annotation(cc.ewell.esb.authenticating.server.annotation.Timed) || @target(cc.ewell.esb.authenticating.server.annotation.Timed)")
	public void annotationProcessor() {
	}

	@Pointcut("execution(public * cc.ewell.esb.authenticating.server..*.*(..))")
	public void publicMethod() {
	}

	@Around(value = "publicMethod() && annotationProcessor()")
	public Object count(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		final String methodName = proceedingJoinPoint.getSignature().getName();

		Long startTime = System.currentTimeMillis();
		Object result = proceedingJoinPoint.proceed();
		Long finishTime = System.currentTimeMillis();

		Signature signature = proceedingJoinPoint.getSignature();
		String[] packageName = signature.getDeclaringTypeName().split("\\.");
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < packageName.length; ++i) {
			if (i < packageName.length - 1) {
				stringBuilder.append(packageName[i].substring(0, 1));
			} else {
				stringBuilder.append(packageName[i]);
			}
			stringBuilder.append(".");
		}

		System.out.println("Executing: " + stringBuilder + signature.getName() + " took: " + (finishTime - startTime) + " ms");

		Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();

		if (method.getDeclaringClass().isInterface()) {
			method = proceedingJoinPoint.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
		}

		// 方法上的注解优先级比类上的注解高,可以覆盖类上注解的值
		Timed timed = null;
		if (method.isAnnotationPresent(Timed.class)) {
			//处理方法上的注解
			timed = method.getAnnotation(Timed.class);
			if (timed.displayArgs()) {
				logArgs(proceedingJoinPoint.getArgs());
			}
		} else {
			//处理类上面的注解
			Object target = proceedingJoinPoint.getTarget();
			if (target.getClass().isAnnotationPresent(Timed.class)) {
				timed = target.getClass().getAnnotation(Timed.class);
				if (timed.displayArgs()) {
					logArgs(proceedingJoinPoint.getArgs());
				}
			}
		}

		return result;
	}

	private void logArgs(Object[] args) {
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < args.length; ++i) {
			stringBuilder.append("[");
			stringBuilder.append(i);
			stringBuilder.append("]: ");
			stringBuilder.append(args[i].toString());

			if (i < args.length - 1) {
				stringBuilder.append(", ");
			}
		}

		if (!stringBuilder.toString().isEmpty())
			System.out.println("Argument List: " + stringBuilder);
		else
			System.out.println("Argument List: Empty");
	}
}
