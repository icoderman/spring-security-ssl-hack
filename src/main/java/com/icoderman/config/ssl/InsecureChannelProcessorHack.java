package com.icoderman.config.ssl;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.channel.InsecureChannelProcessor;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.Collection;

@Component
public class InsecureChannelProcessorHack extends InsecureChannelProcessor {

	@Override
	public void decide(FilterInvocation invocation, Collection<ConfigAttribute> config) throws IOException, ServletException {
		for (ConfigAttribute attribute : config) {
			if (supports(attribute)) {
				if ("https".equals(invocation.getHttpRequest().getHeader("X-Forwarded-Proto"))) {
					getEntryPoint().commence(invocation.getRequest(),
							invocation.getResponse());
				}
			}
		}
	}
}
