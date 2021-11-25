package com.guilhermeoscp.apisistemaescolar.adapter;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// Configuração da Paginação, Padrão é 10 como está no código em "PageRequest.of(0, 10)" mas pode ser alterado
@Configuration
public class ApiWebAdapter implements WebMvcConfigurer {

	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		PageableHandlerMethodArgumentResolver phmar = new PageableHandlerMethodArgumentResolver();
		phmar.setFallbackPageable(PageRequest.of(0, 10));
		argumentResolvers.add(phmar);
	}
}
