package org.ares.app.c5.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class CasProperties {

	@Value("${cas.server.host.url}")
	private String casServerUrl;

	@Value("${cas.server.host.login_url}")
	private String casServerLoginUrl;

	@Value("${cas.server.host.logout_url}")
	private String casServerLogoutUrl;

	@Value("${app.server.host.url}")
	private String appServerUrl;

	@Value("${app.login.url}")
	private String appLoginUrl;

	@Value("${app.logout.url}")
	private String appLogoutUrl;
}
