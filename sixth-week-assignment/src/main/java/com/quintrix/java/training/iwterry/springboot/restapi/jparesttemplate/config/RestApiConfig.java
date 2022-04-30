package com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.config;

import com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.httprequestservice.CommentService;
import com.quintrix.java.training.iwterry.springboot.restapi.jparesttemplate.httprequestservice.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@PropertySource(value = "classpath:secret.properties", ignoreResourceNotFound = true)
public class RestApiConfig {
  @Bean
  public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
    return restTemplateBuilder
      .setConnectTimeout(Duration.ofMinutes(5L))
      .setReadTimeout(Duration.ofMinutes(5L))
      .build();
  }

  @Bean
  public CommentService commentService(
    RestTemplate restTemplate,
    @Value("${gorest.api.access.token:}") String accessToken
  ) {
    return new CommentServiceImpl(restTemplate, accessToken);
  }
}
