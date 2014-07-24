package com.kudrevatykh.integration.xmpp;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.ConnectionConfiguration.SecurityMode;
import org.jivesoftware.smack.XMPPException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.Environment;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.util.ReflectionUtils;

@Configuration
@EnableAutoConfiguration
@EnableIntegration
@ImportResource("classpath:/xmpp-context.xml")
public class Main {
	
	public static void main(String... args) {
		SpringApplication.run(Main.class, args);
	}
	
	  @Bean(name="server2.conn")
	  public XMPPConnection xmppConnection(final Environment environment) {
		ConnectionConfiguration config = new ConnectionConfiguration(environment.getProperty("server2.host"), 5222,
				environment.getProperty("server2.service"));
		config.setSecurityMode(SecurityMode.required);
		
		return new XMPPConnectionBean(config) {
			
			@Override
			public void onApplicationEvent(ContextRefreshedEvent event) {
				try {
					connect();
					login(environment.getProperty("server2.user"), environment.getProperty("server2.pass"), "proxy");
				} catch (XMPPException e) {
					ReflectionUtils.rethrowRuntimeException(e);
				}
			}

			@Override
			public void onApplicationEvent(ContextClosedEvent event) {
				disconnect();
			}
		};
		
	  }
	  
	  private static abstract class XMPPConnectionBean extends XMPPConnection implements ApplicationListener<ApplicationContextEvent> {

		public XMPPConnectionBean(ConnectionConfiguration config) {
			super(config);
		}

		public void onApplicationEvent(ApplicationContextEvent event) {
			if(event instanceof ContextRefreshedEvent) {
				onApplicationEvent((ContextRefreshedEvent)event);
			}
			
			if(event instanceof ContextClosedEvent) {
				onApplicationEvent((ContextClosedEvent)event);
			}
		}
		
		public abstract void onApplicationEvent(ContextRefreshedEvent event);
		
		public abstract void onApplicationEvent(ContextClosedEvent event);
		  
	  };

}
