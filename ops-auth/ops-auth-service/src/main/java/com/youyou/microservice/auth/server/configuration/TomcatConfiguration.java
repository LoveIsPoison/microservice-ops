package com.youyou.microservice.auth.server.configuration;

import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author joy
 */
@Configuration
public class TomcatConfiguration  {
	
	
//	@Value("${jvmroute}")
//	private String jvmRoute;
	
//	@Value("${ajpport}")
//	private int ajpport;
	

	@Bean
	public EmbeddedServletContainerFactory servletContainer() throws Exception{

		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();
//		Connector ajpConnector = new Connector("AJP/1.3");
//		ajpConnector.setProtocol("AJP/1.3");
//		ajpConnector.setPort(ajpport);
//		ajpConnector.setProperty("jvmRoute", jvmRoute);
//		ajpConnector.setSecure(isAjpSecure());
//		ajpConnector.setAllowTrace(isAjpAllowTrace());
//		ajpConnector.setScheme(getAjpScheme());
//		tomcat.addAdditionalTomcatConnectors(ajpConnector);
		
		tomcat.addConnectorCustomizers(new MyTomcatConnectorCustomizer());  

		return tomcat;
	}
	// ... Get/Set
}

class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer  
{  
    public void customize(Connector connector)  
    {  
    	System.out.println("--------customize");
        Http11NioProtocol protocol = (Http11NioProtocol) connector.getProtocolHandler();  
        //设置最大连接数  
        protocol.setMaxConnections(3000);  
        //设置最大线程数  
        protocol.setMaxThreads(3000);  
        protocol.setConnectionTimeout(90000);  
    }  
}  
