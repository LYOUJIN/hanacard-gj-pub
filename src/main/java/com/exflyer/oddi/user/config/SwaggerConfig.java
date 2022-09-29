package com.exflyer.oddi.user.config;

import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Slf4j
public class SwaggerConfig {

  private final String BASE_PACKAGE = "com.exflyer.oddi.user";


  @Bean
  public Docket allApiDoc() {
    return new Docket(DocumentationType.SWAGGER_2)
      .groupName("01.전체")
      .select()
      .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE))
      .paths(PathSelectors.any())
      .build()
      .securitySchemes(Arrays.asList(apiKey()))
      .securityContexts(Arrays.asList(securityContext()))
      ;
  }


  private ApiKey apiKey() {
    return new ApiKey("x-access-token", "x-access-token", "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder().securityReferences(defaultAuth()).build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Arrays.asList(new SecurityReference("x-access-token", authorizationScopes));
  }

}
