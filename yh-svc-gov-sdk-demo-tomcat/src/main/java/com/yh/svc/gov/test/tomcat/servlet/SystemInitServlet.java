package com.yh.svc.gov.test.tomcat.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.net.URL;


/**
 * 系统初始化时的servlet
 * @author Alex Lu
 *
 */
public class SystemInitServlet extends HttpServlet{
    
    private static final Logger log = LoggerFactory.getLogger(SystemInitServlet.class);
    
	private static final long serialVersionUID = 4724299124899039939L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		
	
	}
	
	public  Resource[] getResourcesForClasspathByPath(String path) throws IOException {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		if(path == null){
			return null;
		}
		if(!path.startsWith("classpath*:")){
			path = "classpath*:"+path;
		}
		Resource[] resources = resolver.getResources(path);
		
		
		return resources;
	}
	
	
	public  URL findCurURL()throws Exception{
		
		Resource[] resources=getResourcesForClasspathByPath("WEB-INF/log4j.xml");
    	
    	for(Resource res:resources){
    		String path=res.getURL().getPath();
    		
    		String[] array=path.split("/");
    		
    		String jarName=array[array.length-2];
    		
    		if(jarName.startsWith("bizhub-impl")||jarName.startsWith("classes")){
    			return res.getURL();
    		}
    	}
    	
    	return resources[0].getURL(); 
		
	}
	
	
	
	
	
}
