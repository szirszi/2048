package com.szaszisoft._2048.configurations;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.catalina.Manager;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.session.ManagerBase;
import org.apache.catalina.session.StandardManager;
import org.apache.coyote.ajp.AbstractAjpProtocol;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatAjpConfig {

  private int ajpPort = Integer.parseInt(System.getenv("AJP_PORT"));

  @Bean
  public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
    return server -> {
      if (server instanceof TomcatServletWebServerFactory) {
        server.addAdditionalTomcatConnectors(ajpConnector());
      }

      server.addContextCustomizers(context -> {
        Manager manager = context.getManager();
        if (manager == null) {
          manager = new StandardManager();
          context.setManager(manager);
        }
        ((ManagerBase) context.getManager())
            .getEngine()
            .setJvmRoute(System.getenv("TOMCAT_JVMROUTE"));
      });
    };
  }

  private Connector ajpConnector() {
    Connector connector = new Connector("AJP/1.3");
    connector.setScheme("http");
    connector.setPort(8009);
    connector.setSecure(false);
    connector.setAllowTrace(false);

    AbstractAjpProtocol protocol = (AbstractAjpProtocol) connector.getProtocolHandler();
    protocol.setSecretRequired(false); // For example, setting secretRequired
    // protocol.setSecret("verysecret");
    try {
      protocol.setAddress(InetAddress.getByName( "0.0.0.0" ) );
    } catch (UnknownHostException e) {
      throw new RuntimeException(e);
    }


    return connector;
  }
}