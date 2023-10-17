package com.szaszisoft._2048.configurations;

import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AjpConfig {
  // Define your AJP port here, for example:
  private int ajpPort = Integer.parseInt(System.getenv("AJP_PORT"));

  @Bean
  public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer2() {
    return server -> {
      if (server instanceof TomcatServletWebServerFactory) {
        ((TomcatServletWebServerFactory) server).addAdditionalTomcatConnectors(ajpConnector());
      }
    };
  }

  private Connector ajpConnector() {
    Connector connector = new Connector("AJP/1.3");
    connector.setScheme("http");
    connector.setPort(ajpPort);
    connector.setSecure(false);
    connector.setAllowTrace(false);

    AbstractAjpProtocol protocol = (AbstractAjpProtocol) connector.getProtocolHandler();
    protocol.setSecretRequired(false); // For example, setting secretRequired
    // protocol.setSecret("verysecret");
    return connector;
  }
}
