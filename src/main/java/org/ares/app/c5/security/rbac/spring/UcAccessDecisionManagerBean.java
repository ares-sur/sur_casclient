package org.ares.app.c5.security.rbac.spring;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

@Component
public class UcAccessDecisionManagerBean implements AccessDecisionManager {

	/**
	 * auth CasAuthenticationToken--{Principal,Authenticated,Authorities...}
	 * cas is:[admin, soft]
	 * this method is called multiple times by accessing an url,why?
	 */
	@Override
	public void decide(Authentication auth, Object obj, Collection<ConfigAttribute> cas)throws AccessDeniedException, InsufficientAuthenticationException {
		if( cas == null) 
			return;
		String access_url=((FilterInvocation)obj).getRequestUrl();
		log.info("auth is:"+auth);
		log.info("cas is:"+cas);
		if(cas.stream().filter(e->{
			return auth.getAuthorities().stream().filter(ga->e.getAttribute().trim().equals(ga.getAuthority().trim())).count()>0;
		}).count()>0) 
			return;
		throw new AccessDeniedException(access_url+" -- The current access url lacks access!");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	final Logger log = LoggerFactory.getLogger(this.getClass());
}
