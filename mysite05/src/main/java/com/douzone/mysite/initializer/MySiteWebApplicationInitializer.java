package com.douzone.mysite.initializer;

import javax.servlet.Filter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.douzone.mysite.config.AppConfig;
import com.douzone.mysite.config.WebConfig;

public class MySiteWebApplicationInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

	/* web.xml, context-parm (applicationContext.xml) */
	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class<?>[] {AppConfig.class};
	}

	/* web.xml, Dispatcher Servlet */
	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class<?>[] {WebConfig.class};
	}

	/* web.xml, servlet-mapping */
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}

	/* web.xml, Encoding Fliter */
	@Override
	protected Filter[] getServletFilters() {
		return new Filter[] {new CharacterEncodingFilter("UTF-8", false)};
	}

	/* GlobalExceptionHandler 처리 */
	@Override
	protected FrameworkServlet createDispatcherServlet(WebApplicationContext servletAppContext) {
		DispatcherServlet servlet = (DispatcherServlet)super.createDispatcherServlet(servletAppContext);
		servlet.setThrowExceptionIfNoHandlerFound(true);
		return servlet;
	}

	/*
	 * @Override public void onStartup(ServletContext servletContext) throws
	 * ServletException { super.onStartup(servletContext);
	 * 
	 * DispatcherServlet dispatcherServlet = new DispatcherServlet(new
	 * GenericWebApplicationContext()); ServletRegistration.Dynamic
	 * servletRegistration = servletContext.addServlet("spring", dispatcherServlet);
	 * servletRegistration.setLoadOnStartup(1);
	 * 
	 * dispatcherServlet.setThrowExceptionIfNoHandlerFound(true); }
	 */
	
	
	
	

}
