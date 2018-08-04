package org.ares.app.c5.security.rbac.spring;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.Resource;

import org.ares.app.c5.security.rbac.dao.RbacMapper;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.PathMatcher;

@Component("uc_fisms")
public class UcFilterInvocationSecurityMetadataSourceBean implements FilterInvocationSecurityMetadataSource {

	@Override
	public Collection<ConfigAttribute> getAttributes(Object obj) throws IllegalArgumentException {
		Collection<ConfigAttribute> r=new ArrayList<ConfigAttribute>();
		String url = ((FilterInvocation)obj).getRequestUrl();
		rm.getAllRess().stream().filter(e->pathMatcher.match(e.getResurl(), url)).flatMap(e->e.getRoles().stream()).forEach(e->r.add(new SecurityConfig(e.getRolename())));
		return r;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true;
	}

	@Resource PathMatcher pathMatcher;
	@Resource RbacMapper rm;
}
