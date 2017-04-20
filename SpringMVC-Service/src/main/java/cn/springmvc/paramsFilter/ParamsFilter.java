/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cn.springmvc.paramsFilter;

import cn.springmvc.log.SpringMVCLogFactory;
import cn.springmvc.utils.Constant;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author WangW
 */
public class ParamsFilter implements Filter{
    
    private static final Logger log = SpringMVCLogFactory.getHttpLogger();
    
    private static final int SIZE_INPUT_BUFFER = 4096;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest    =  (HttpServletRequest)request;
        
        HttpServletResponse httpResponse  =   (HttpServletResponse)response;
        
        httpRequest.setCharacterEncoding("UTF-8");
        
        String requestURI = httpRequest.getRequestURI();
        
        String queryString = httpRequest.getQueryString();
        
        if (queryString != null) {
            
            requestURI += '?' + queryString;
        }
        
        Map params = getRequestParameters(httpRequest);
        
        Map headers = getRequestHeaders(httpRequest); 
        
        if(!headers.containsKey(Constant.HEADER_X_CLIENT_ADDRESS))
            
            headers.put(Constant.HEADER_X_CLIENT_ADDRESS, request.getRemoteAddr());
        
        byte[] requestData = null;
        
        try{
            
            requestData = getRequestContent(httpRequest);
            
        }catch(Exception e){
            
            httpResponse.sendError(httpResponse.SC_BAD_REQUEST);
            
            log.error("BAD REQUEST", e);
            
            return;
        }
        
        if (log.isDebugEnabled())
            
            log.debug("req:"+requestURI+",body:"+new String(requestData));
        
        chain.doFilter(request, response);

    }
    
    private Map getRequestParameters(HttpServletRequest request) {
        Map params = new HashMap();

        String paramName = null;
        for (Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
            paramName = (String) e.nextElement();
            params.put(paramName, request.getParameter(paramName));
        }
        if(log.isDebugEnabled()){
            StringBuffer sb = new StringBuffer();
            sb.append("param:");
            String paramValue = null;
            for (Enumeration e = request.getParameterNames(); e.hasMoreElements();) {
                paramName = (String) e.nextElement();
                paramValue = (String) request.getParameter(paramName);
                sb.append('&').append(paramName).append('=').append(paramValue);
            }
            log.debug(sb.toString());
        }
        return params;
    }
    
    private Map getRequestHeaders(HttpServletRequest request) {
        Map headers = new HashMap();
        String headerName = null;
        for (Enumeration e = request.getHeaderNames(); e.hasMoreElements();) {
            headerName = (String) e.nextElement();
            headers.put(headerName.toLowerCase(), request.getHeader(headerName));
        }
        return headers;
    }
    
    private byte[] getRequestContent(HttpServletRequest request)
            throws IOException {
        byte[] requestData = null;
        InputStream in = null;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        try {
            in = request.getInputStream();
            int contentLength = request.getContentLength();
            if (contentLength <= 0) {
                contentLength = SIZE_INPUT_BUFFER;
            }

            byte[] buf = new byte[contentLength];
            int c = 0;
            int b = 0;
            while ((c < buf.length) && (b = in.read(buf, c, buf.length - c)) >= 0) {
                c += b;
                if (c == contentLength) {
                    bout.write(buf);
                    buf = new byte[contentLength];
                    c = 0;
                }
            }
            if (c != 0) {
                bout.write(buf, 0, c);
            }
            requestData = bout.toByteArray();
            bout.flush();
        } catch (IOException iex) {
            throw iex;
        } finally {
            if (bout != null) {
                bout.close();
            }
            if (in != null) {
                in.close();
            }
        }

        return requestData;
    }

    @Override
    public void destroy() {
    }

}
