package com.kiko.file.manager.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;

/**
 * 获取WebApplicationContext的一条途径
 */
@Component
public class ContextUtil implements InitializingBean, ApplicationContextAware, EnvironmentAware, ServletContextAware {

    private static ApplicationContext applicationContext;
    private static Environment environment;
    private static ServletContext servletContext;
    private static Map<String, String> systemProperties;

    private static String hostName;
    private static String hostIp;

    public static ServletContext getServletContext() {
        return servletContext;
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        ContextUtil.servletContext = servletContext;
    }

    public static String getContextPath() {
        return servletContext.getContextPath();
    }

    public static ServletContext getServletContext(String uripath) {
        return servletContext.getContext(uripath);
    }

    public static String getWebRootRealPath() {
        return servletContext.getRealPath("/");
    }

    public static String getRealPath(String path) {
        return servletContext.getRealPath(path);
    }

    public static String getNewlogoutUrl(String path) {
        String logoutUrl = path + servletContext.getContextPath();
        return logoutUrl;
    }

    public static ApplicationContext getAc() {
        return applicationContext;
    }

    public static void publishEvent(ApplicationEvent event) {
        applicationContext.publishEvent(event);
    }

    /**
     * 获取对象
     *
     * @param name
     * @return Object 一个以所给名字注册的bean的实例
     * @throws BeansException
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 获取类型为requiredType的对象
     *
     * @param clz
     * @return
     * @throws BeansException
     */
    public static <T> T getBean(Class<T> clz) throws BeansException {
        T result = (T) applicationContext.getBean(clz);
        return result;
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name
     * @return boolean
     */
    public static boolean containsBean(String name) {
        return applicationContext.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param name
     * @return boolean
     * @throws NoSuchBeanDefinitionException
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.isSingleton(name);
    }

    /**
     * @param name
     * @return Class 注册对象的类型
     * @throws NoSuchBeanDefinitionException
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     *
     * @param name
     * @return
     * @throws NoSuchBeanDefinitionException
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
        return applicationContext.getAliases(name);
    }

    /**
     * 获取访问者IP
     * <p>
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * <p>
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     *
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String addr = null;

        String[] ADDR_HEADER = {"X-Real-IP", "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP"};
        for (String header : ADDR_HEADER) {
            if (StringUtils.isEmpty(addr) || "unknown".equalsIgnoreCase(addr)) {
                addr = request.getHeader(header);
            } else {
                break;
            }
        }

        if (StringUtils.isEmpty(addr) || "unknown".equalsIgnoreCase(addr)) {
            addr = request.getRemoteAddr();
        } else {
            int i = addr.indexOf(",");
            if (i > 0) {
                addr = addr.substring(0, i);
            }
        }
        return addr;
    }

    public static String getHostName() {
        if (ContextUtil.hostName == null) {
            try {
                ContextUtil.hostName = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        return ContextUtil.hostName;
    }

    public static String getHostIp() {
        if (ContextUtil.hostIp == null) {
            try {
                ContextUtil.hostIp = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        return ContextUtil.hostIp;
    }

    public static Environment getEnvironment() {
        return environment;
    }

    @Override
    public void setEnvironment(Environment environment) {
        ContextUtil.environment = environment;
    }

    public static String getProperty(String key) {
        return environment.getProperty(key);
    }

    public static String getProperty(String key, String defaultValue) {
        return environment.getProperty(key, defaultValue);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ContextUtil.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (systemProperties == null || systemProperties.isEmpty()) {
            return;
        }

        Iterator<String> i = systemProperties.keySet().iterator();
        while (i.hasNext()) {
            String key = i.next();
            String value = systemProperties.get(key);

            System.setProperty(key, value);
        }
    }

    public void setSystemProperties(Map<String, String> systemProperties) {
        ContextUtil.systemProperties = systemProperties;
    }
}