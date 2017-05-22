package com.springWsSecurity.SpringWSSecurity.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.endpoint.interceptor.PayloadLoggingInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.SimpleWsdl11Definition;

import java.util.List;
import java.util.Properties;

/**
 * Created by igaurav on 5/21/2017.
 */
@Configuration
@EnableWebMvc
public class WebServiceConfig extends WsConfigurerAdapter{

    @Bean
    public ServletRegistrationBean dispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        return new ServletRegistrationBean(servlet, "/ishantg/ws/*");
    }

    @Bean(name="helloworld")
    public SimpleWsdl11Definition defaultWsdl11Definition(){
        SimpleWsdl11Definition defaultWsdl11Definition = new SimpleWsdl11Definition();
        defaultWsdl11Definition.setWsdl(new ClassPathResource("META-INF/schemas/greeting.wsdl"));
        return defaultWsdl11Definition;
    }

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor(){
        Wss4jSecurityInterceptor wss4jSecurityInterceptor = new Wss4jSecurityInterceptor();
        wss4jSecurityInterceptor.setValidationActions("UsernameToken");
        wss4jSecurityInterceptor.setValidationCallbackHandler(simplePasswordValidationCallbackHandler());
        return wss4jSecurityInterceptor;
    }

    @Bean
    public SimplePasswordValidationCallbackHandler simplePasswordValidationCallbackHandler(){
        SimplePasswordValidationCallbackHandler simplePasswordValidationCallbackHandler = new SimplePasswordValidationCallbackHandler();
        Properties properties = new Properties();
        properties.setProperty("admin","secret");
        simplePasswordValidationCallbackHandler.setUsers(properties);
        return simplePasswordValidationCallbackHandler;
    }

    @Override
    public void addInterceptors(List interceptors){
        interceptors.add(securityInterceptor());
    }

}
