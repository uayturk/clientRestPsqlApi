package com.ufuk.clientRestPsqlApi;

import com.ufuk.clientRestPsqlApi.config.ApplicationConfig;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableScheduling
public class ClientRestPsqlApiApplication {

  private final ApplicationConfig applicationConfig;

  @Autowired
  public ClientRestPsqlApiApplication(ApplicationConfig applicationConfig) {
    this.applicationConfig = applicationConfig;
  }


  public static void main(String[] args) {
    SpringApplication.run(ClientRestPsqlApiApplication.class,args);
  }

  @Bean
  Docket swaggerSpringMvcPlugin() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiInfo())
        .directModelSubstitute(LocalDateTime.class, String.class)
        .directModelSubstitute(LocalDate.class, String.class)
        .select().apis(RequestHandlerSelectors.basePackage("com.ufuk"))
        .build();
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title(applicationConfig.getApplicationName())
        .description("<i>Current platform: </i><b>" + applicationConfig.getPlatform() + "</b>")
        .version(applicationConfig.getBuildVersion())
        .build();
  }

}
