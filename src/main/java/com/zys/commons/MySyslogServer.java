package com.zys.commons;

import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.server.*;

import java.net.SocketAddress;

/**
 * syslog服务端
 */
public class MySyslogServer {
    private static final String HOST = "192.168.154.128";
    private static final int PORT = 514;

    private void receiveSyslogMessage() throws Exception {
        SyslogServerIF server = SyslogServer.getInstance(SyslogConstants.TCP);
        SyslogServerConfigIF config = server.getConfig();
        config.setHost(HOST);
        config.setPort(PORT);
        config.addEventHandler(new SyslogServerSessionEventHandlerIF() {
            @Override
            public Object sessionOpened(SyslogServerIF syslogServerIF, SocketAddress socketAddress) {
                return null;
            }

            @Override
            public void event(Object o, SyslogServerIF syslogServerIF, SocketAddress socketAddress,
                              SyslogServerEventIF syslogServerEventIF) {
                System.out.println("receive from:" + socketAddress + "\tmessage" + syslogServerEventIF.getMessage());
            }

            @Override
            public void exception(Object o, SyslogServerIF syslogServerIF, SocketAddress socketAddress, Exception e) {

            }

            @Override
            public void sessionClosed(Object o, SyslogServerIF syslogServerIF, SocketAddress socketAddress, boolean b) {

            }

            @Override
            public void initialize(SyslogServerIF syslogServerIF) {

            }

            @Override
            public void destroy(SyslogServerIF syslogServerIF) {

            }
        });
        SyslogServer.getThreadedInstance(SyslogConstants.UDP);
        Thread.sleep(100000);
    }


    public static void main(String[] args) throws Exception {
        new MySyslogServer().receiveSyslogMessage();
    }
}
