package com.ufuk.clientRestPsqlApi.config;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ToString
public class ApplicationConfig {
  @Value("${application.name}")
  private String applicationName;

  @Value("${build.version}")
  private String buildVersion;

  @Value("${platform}")
  private String platform;
}
