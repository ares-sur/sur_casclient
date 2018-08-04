package org.ares.app.c5.cfg;

import javax.annotation.Resource;

import org.ares.app.c5.props.CasProperties;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.cas.authentication.CasAuthenticationProvider;
import org.springframework.security.cas.web.CasAuthenticationEntryPoint;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity // 启用web权限
@EnableGlobalMethodSecurity(prePostEnabled = false) // 启用|停止方法验证
public class WebSecCfg extends WebSecurityConfigurerAdapter {

	/**
	 * 定义认证用户信息获取来源，密码校验规则等 // inMemoryAuthentication 从内存中获取 //
	 * auth.inMemoryAuthentication().withUser("chengli").password("123456").roles("USER")
	 * // .and().withUser("admin").password("123456").roles("ADMIN");
	 * 
	 * // jdbcAuthentication从数据库中获取，但是默认是以security提供的表结构 // usersByUsernameQuery
	 * 指定查询用户SQL // authoritiesByUsernameQuery 指定查询权限SQL //
	 * auth.jdbcAuthentication().dataSource(dataSource).usersByUsernameQuery(query).authoritiesByUsernameQuery(query);
	 * 
	 * // 注入userDetailsService，需要实现userDetailsService接口 //
	 * auth.userDetailsService(userDetailsService);
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		super.configure(auth);
		auth.authenticationProvider(casAuthenticationProvider);
	}
	
	/*@Bean*/
	public CasAuthenticationFilter casAuthenticationFilter(/*AuthenticationManager authenticationManager*/) throws Exception {
		CasAuthenticationFilter casaf = new CasAuthenticationFilter();
		casaf.setAuthenticationManager(authenticationManager());
		casaf.setFilterProcessesUrl(casProperties.getAppLoginUrl());
		return casaf;
	}

	/** 定义安全策略 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()// 配置安全策略
				// .antMatchers("/","/hello").permitAll()//定义/请求不需要验证
				.antMatchers("/static/**").permitAll() // 不拦截静态资源
				.antMatchers("/api/**").permitAll() // 不拦截对外API
				.anyRequest().authenticated()// 其余的所有请求都需要验证
				.and().logout().permitAll()// 定义logout不需要验证
				.and().formLogin();// 使用form表单登录

		http.exceptionHandling().authenticationEntryPoint(casAuthenticationEntryPoint).and()
				.addFilter(casAuthenticationFilter())
				.addFilterBefore(uc_fsi, FilterSecurityInterceptor.class)//added byl ly
				.addFilterBefore(casLogoutFilter, LogoutFilter.class)
				.addFilterBefore(singleSignOutFilter, CasAuthenticationFilter.class);

		// http.csrf().disable(); //禁用CSRF
	}
	
	@Resource CasProperties casProperties;
	@Resource CasAuthenticationEntryPoint casAuthenticationEntryPoint;
	//@Resource CasAuthenticationFilter casAuthenticationFilter;
	@Resource LogoutFilter casLogoutFilter;
	@Resource SingleSignOutFilter singleSignOutFilter;
	@Resource CasAuthenticationProvider casAuthenticationProvider;
	@Resource FilterSecurityInterceptor uc_fsi;
	
	final Logger log = LoggerFactory.getLogger(this.getClass());

}
