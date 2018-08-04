package org.ares.app.c5.security.rbac.spring;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.stereotype.Component;

@Component("uc_si")
public class UcSecurityInterceptorBean extends FilterSecurityInterceptor {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation( request, response, chain );
		invoke(fi);
	}
	
	public void invoke( FilterInvocation fi ) throws IOException, ServletException{
		InterceptorStatusToken  token = super.beforeInvocation(fi);
		try{
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		}finally{
			super.afterInvocation(token, null);
		}
	}

	@Override
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return uc_fisms;
	}
	
	/*UcAccessDecisionManagerBean component*/
	@Autowired
    public void setAccessDecisionManager(AccessDecisionManager adm) {
        super.setAccessDecisionManager(adm);
    }

	@Resource FilterInvocationSecurityMetadataSource uc_fisms;
}
