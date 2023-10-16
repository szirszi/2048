package com.szaszisoft._2048.configurations;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AjpConfig {

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
    connector.setPort(ajpPort); // Set your AJP port as needed
    connector.setSecure(false);
    connector.setAllowTrace(false);

    // If you need to configure AJP-specific settings, you can access the protocol handler
    AbstractAjpProtocol protocol = (AbstractAjpProtocol) connector.getProtocolHandler();
    protocol.setSecretRequired(false); // For example, setting secretRequired

    return connector;
  }

  // Define your AJP port here, for example:
  private int ajpPort = 8009;
}
