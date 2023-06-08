package com.example.aop.springaop.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.aop.springaop.filter.UniqueIdFilter;

import lombok.Data;

/*
@Configuration
@Data
public class UniqueIdFilterConfiguration {
	
	public static final String DEFAULT_HEADER_TOKEN = "correlationId";
	public static final String DEFAULT_MDC_UUID_TOKEN_KEY = "correlationId";

	private String responseHeader = DEFAULT_HEADER_TOKEN;
	private String mdcKey = DEFAULT_MDC_UUID_TOKEN_KEY;
	private String requestHeader = DEFAULT_HEADER_TOKEN;
	
	@Bean
	public FilterRegistrationBean<UniqueIdFilter> servletRegistrationBean() {
		final FilterRegistrationBean<UniqueIdFilter> registrationBean = new FilterRegistrationBean<>();
		final UniqueIdFilter log4jMDCFilterFilter = new UniqueIdFilter(responseHeader,mdcKey,requestHeader);
		registrationBean.setFilter(log4jMDCFilterFilter);
		registrationBean.setOrder(2);
		return registrationBean;
	}

}
*/

@Configuration
@Data
public class UniqueIdFilterConfiguration {

	public static final String MDC_KEY_ID = "id";
	public static final String MDC_KEY_IP_ADDRESS = "ipAddress";

	private String mdcKey = MDC_KEY_ID;
	private String mdcKeyIpAdd = MDC_KEY_IP_ADDRESS;

	@Bean
	public FilterRegistrationBean<UniqueIdFilter> servletRegistrationBean() {
		final FilterRegistrationBean<UniqueIdFilter> registrationBean = new FilterRegistrationBean<>();
		final UniqueIdFilter uniqueIdFilter = new UniqueIdFilter(mdcKey, mdcKeyIpAdd);
		registrationBean.setFilter(uniqueIdFilter);
		registrationBean.setOrder(2);
		return registrationBean;
	}
}
