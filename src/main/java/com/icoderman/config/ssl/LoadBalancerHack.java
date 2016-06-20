package com.icoderman.config.ssl;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.access.channel.ChannelDecisionManagerImpl;

import java.util.Arrays;

@Configuration
public class LoadBalancerHack implements BeanPostProcessor {

	@Autowired
	SecureChannelProcessorHack secureChannelProcessorHack;

	@Autowired
	InsecureChannelProcessorHack insecureChannelProcessorHack;

	//@Value("${behind.loadbalancer}")
	//boolean behindLoadBalancer;

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		if (/*behindLoadBalancer &&*/ bean instanceof ChannelDecisionManagerImpl) {
			System.out.println("********* Post-processing " + beanName);
			((ChannelDecisionManagerImpl) bean).setChannelProcessors(Arrays.asList(insecureChannelProcessorHack, secureChannelProcessorHack));
		}
		return bean;
	}

}
