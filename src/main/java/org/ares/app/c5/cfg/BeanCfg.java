package org.ares.app.c5.cfg;

import javax.annotation.Resource;

import org.ares.app.c5.props.CasProperties;
import org.ares.app.c5.security.rbac.spring.UserDetailsServiceBean;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

@Configuration
public class BeanCfg {

	//--------------------------------------------spring security cas-------------------------------------------------//
	/** 认证的入口 */
	@Bean
	public CasAuthenticationEntryPoint casAuthenticationEntryPoint() {
		CasAuthenticationEntryPoint casAuthenticationEntryPoint = new CasAuthenticationEntryPoint();
		casAuthenticationEntryPoint.setLoginUrl(casProperties.getCasServerLoginUrl());
		casAuthenticationEntryPoint.setServiceProperties(serviceProperties);
		return casAuthenticationEntryPoint;
	}

	/** 指定service相关信息 */
	@Bean
	public ServiceProperties serviceProperties() {
		ServiceProperties serviceProperties = new ServiceProperties();
		//log.info(casProperties.getAppServerUrl() + casProperties.getAppLoginUrl());
		serviceProperties.setService(casProperties.getAppServerUrl() + casProperties.getAppLoginUrl());
		serviceProperties.setAuthenticateAllArtifacts(true);
		return serviceProperties;
	}

	/** CAS认证过滤器 */
	/*@Bean*/
	public CasAuthenticationFilter casAuthenticationFilter(AuthenticationManager authenticationManager) throws Exception {
		CasAuthenticationFilter casaf = new CasAuthenticationFilter();
		casaf.setAuthenticationManager(authenticationManager);
		casaf.setFilterProcessesUrl(casProperties.getAppLoginUrl());
		return casaf;
	}

	/** cas 认证 Provider */
	@Bean
	public CasAuthenticationProvider casAuthenticationProvider() {
		CasAuthenticationProvider casAuthenticationProvider = new CasAuthenticationProvider();
		casAuthenticationProvider.setAuthenticationUserDetailsService(customUserDetailsService);
		// casAuthenticationProvider.setUserDetailsService(customUserDetailsService());
		// //这里只是接口类型，实现的接口不一样，都可以的。
		casAuthenticationProvider.setServiceProperties(serviceProperties);
		casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator);
		casAuthenticationProvider.setKey("casAuthenticationProviderKey");
		return casAuthenticationProvider;
	}

	/*
	 * @Bean public UserDetailsService customUserDetailsService(){ return new
	 * CustomUserDetailsService(); }
	 */

	/** 用户自定义的AuthenticationUserDetailsService */
	@Bean
	public AuthenticationUserDetailsService<CasAssertionAuthenticationToken> customUserDetailsService() {
		return new UserDetailsServiceBean();
	}

	@Bean
	public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
		return new Cas20ServiceTicketValidator(casProperties.getCasServerUrl());
	}

	/** 单点登出过滤器 */
	@Bean
	public SingleSignOutFilter singleSignOutFilter() {
		SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
		singleSignOutFilter.setCasServerUrlPrefix(casProperties.getCasServerUrl());
		singleSignOutFilter.setIgnoreInitConfiguration(true);
		return singleSignOutFilter;
	}

	/** 请求单点退出过滤器 */
	@Bean
	public LogoutFilter casLogoutFilter() {
		LogoutFilter logoutFilter = new LogoutFilter(casProperties.getCasServerLogoutUrl(),
				new SecurityContextLogoutHandler());
		logoutFilter.setFilterProcessesUrl(casProperties.getAppLogoutUrl());
		return logoutFilter;
	}
	//--------------------------------------------spring security cas-------------------------------------------------//
	
	
	//--------------------------------------------------------------------------------------------------------------//
	//--------------------------------------------------------------------------------------------------------------//
	//--------------------------------------------------------------------------------------------------------------//
	//--------------------------------------------------------------------------------------------------------------//
	@Bean
	public PathMatcher pathMatcher() {
		return new AntPathMatcher();
	}
	
	@Resource
	CasProperties casProperties;
	@Resource
	AuthenticationUserDetailsService<CasAssertionAuthenticationToken> customUserDetailsService;
	@Resource
	ServiceProperties serviceProperties;
	@Resource
	CasAuthenticationEntryPoint casAuthenticationEntryPoint;
	@Resource
	Cas20ServiceTicketValidator cas20ServiceTicketValidator;
	
}
