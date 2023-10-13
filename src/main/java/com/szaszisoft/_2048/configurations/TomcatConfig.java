package com.szaszisoft._2048.configurations;

import org.apache.catalina.Manager;
import org.apache.catalina.session.ManagerBase;
import org.apache.catalina.session.StandardManager;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

  @Bean
  public WebServerFactoryCustomizer<TomcatServletWebServerFactory> servletContainer() {
    return (tomcat) -> {
      tomcat.addContextCustomizers((context) -> {
        Manager manager = context.getManager();
        if (manager == null) {
          manager = new StandardManager();
          context.setManager(manager);
        }
        String jvmRoute = ((ManagerBase) context.getManager()).getEngine().getJvmRoute();
        System.out.println("Current JvmRoute: " + jvmRoute);
        ((ManagerBase) context.getManager()).getEngine().setJvmRoute(System.getenv("TOMCAT_JVMROUTE"));
        jvmRoute = ((ManagerBase) context.getManager()).getEngine().getJvmRoute();
        System.out.println("Current JvmRoute: " + jvmRoute);
      });
    };
  }
}
