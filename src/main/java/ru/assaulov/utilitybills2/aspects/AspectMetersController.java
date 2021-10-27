package ru.assaulov.utilitybills2.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import ru.assaulov.utilitybills2.exeptions.BaseException;
import ru.assaulov.utilitybills2.exeptions.ErrorType;
import ru.assaulov.utilitybills2.model.User;

@Aspect
@Component
public class AspectMetersController {

	private static final Logger LOGGER = LoggerFactory.getLogger(AspectMetersController.class);

	@Pointcut("execution(* ru.assaulov.utilitybills2.controllers.MetersController.*(..))")
	public void allMethods() {
	}

	@Around("allMethods()")
	public Object checkUserRequest(ProceedingJoinPoint  joinPoint) throws Throwable {
		LOGGER.info("Start checking user request is valid by login");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) auth.getPrincipal();
		LOGGER.debug("Auth user " + user.getLogin());
		String requestLogin = joinPoint.getArgs()[0].toString();
		LOGGER.debug("requestLogin " + requestLogin);
		if(!requestLogin.equals(user.getLogin())) {
			LOGGER.info("Authenticated user is not same in request");
			throw new BaseException(String.format(ErrorType.ENTITY_NOT_CURRENT_USER.getDescription(), "you send request from another user"));
		}
		LOGGER.info("Authenticated user is same in request");
		return  joinPoint.proceed();
	}
}