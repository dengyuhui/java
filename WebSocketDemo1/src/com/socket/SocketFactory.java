package com.socket;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
 
public class SocketFactory extends WebSocketServer {
	
	public SocketFactory(InetSocketAddress address) {
        super(address);
        System.out.println("地址"+address);
    }
	
    public SocketFactory(int port) throws UnknownHostException {
        super(new InetSocketAddress(port));
        System.out.println("端口"+port);
    }
    
    //触发连接事件
	@Override
	public void onOpen(WebSocket conn, ClientHandshake handshake) {
		System.out.println("服务端触发事件："+conn.getLocalSocketAddress().getHostString());
	}
	
	//客户端发送消息到服务器时触发事件
	@Override
	public void onMessage(WebSocket conn, String message) {
		System.out.println("客户端发送消息事件！");
		if(message.indexOf("regist%%") != -1){
			String userId = message.split(",")[1];
			WebSocketPool.addUser(userId, conn);
			System.out.println("注册UserId为 "+userId+" 的用户！");
		}else{
			String userId = getJsonValue("userId",message);
			String msg = getJsonValue("message",message);
			if(userId!=null && msg != null){
				System.out.println("为"+userId+"用户发送信息："+msg);
				WebSocket ws = WebSocketPool.getWebSocketByUser(userId);
				if(ws != null){
					ws.send(msg);
				}else{
					conn.send("对方不在线！");
				}
				
			}else{
				System.out.println("参数错误");
				conn.send("参数错误！");
			}
		}
		
	}
	
	//触发关闭事件
	@Override
	public void onClose(WebSocket conn, int code, String reason, boolean remote) {
		System.out.println("触发关闭事件！");
	}
	
	//异常触发时间
	@Override
	public void onError(WebSocket conn, Exception ex) {
		System.out.println("异常触发事件！");
	}
	
	private String getJsonValue(String paraName,String message){
		String params[] = message.split(",");
		for(String param:params){
			if(param.indexOf(paraName) != -1){
				return param.split(":")[1];
			}
		}
		return null;
	}
	
}
