package com.iot.server;

import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GwtRpcHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof RemoteServiceServlet;
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        RemoteServiceServlet gwtServlet = (RemoteServiceServlet) handler;

        // Delegate the request handling to GWT's RemoteServiceServlet
        gwtServlet.doPost(request, response);  // Delegate the entire request and response

        return null;  // No view, as GWT writes the response directly
    }

    @Override
    public long getLastModified(HttpServletRequest request, Object handler) {
        return -1;  // No caching for GWT RPC
    }
}



