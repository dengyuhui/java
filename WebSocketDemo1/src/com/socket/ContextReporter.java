package com.socket;

import java.net.UnknownHostException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.java_websocket.WebSocketImpl;

public class ContextReporter implements ServletContextListener{

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		System.out.println("....................................................");
		System.out.println("开始了");
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		
		System.out.println("开始启动websocket");
        WebSocketImpl.DEBUG = false;
        int port = 8888;  // 端口随便设置，只要不跟现有端口重复就可以
        SocketFactory s = null;
        try {
            s = new SocketFactory(port);
        } catch (UnknownHostException e) {
            System.out.println("启动websocket失败！");
            e.printStackTrace();
        }
        s.start();
        
        System.out.println("启动websocket成功！");
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		System.out.println("结束了");
	}

}
