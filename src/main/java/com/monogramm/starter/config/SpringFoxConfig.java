package com.monogramm.starter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springdoc.core.GroupedOpenApi;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class SpringFoxConfig {

  @Autowired
  private Environment env;

  @Bean
  public GroupedOpenApi apiDocket() {
    return GroupedOpenApi.builder()
            .group("default")
            .pathsToMatch("/**")
            .addOpenApiCustomiser(openApi -> openApi.info(getApiInfo()))
            .build();
  }

  private Info getApiInfo() {
    final String appName = env.getProperty("info.app.name");
    final String appDescription = env.getProperty("info.app.description");
    final String appVersion = env.getProperty("info.app.version");

    final String tosUrl = env.getProperty("info.tos.url");

    final String licenseName = env.getProperty("info.license.name");
    final String licenseUrl = env.getProperty("info.license.url");

    final String contactName = env.getProperty("info.contact.name");
    final String contactUrl = env.getProperty("info.contact.url");
    final String contactEmail = env.getProperty("info.contact.email");

    return new Info()
            .title(appName + " Documentation")
            .description(appDescription)
            .version(appVersion)
            .termsOfService(tosUrl)
            .license(new io.swagger.v3.oas.models.info.License().name(licenseName).url(licenseUrl))
            .contact(new Contact().name(contactName).url(contactUrl).email(contactEmail));
  }
}