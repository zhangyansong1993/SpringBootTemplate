package com.zys.commons;

import com.alibaba.fastjson.JSONObject;
import org.graylog2.syslog4j.Syslog;
import org.graylog2.syslog4j.SyslogConstants;
import org.graylog2.syslog4j.SyslogIF;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * syslog服务客户端
 */
public class MySyslogClient {
    private static final String HOST = "192.168.154.128";
    private static final int PORT = 514;

    public void generate() {
        SyslogIF syslog = Syslog.getInstance(SyslogConstants.TCP);
        syslog.getConfig().setHost(HOST);
        syslog.getConfig().setPort(PORT);

        StringBuffer buffer = new StringBuffer();
        buffer.append("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("message", buffer.toString());
        try {
            syslog.log(0, URLDecoder.decode(jsonObject.toString(), "utf-8"));
        } catch (UnsupportedEncodingException e) {
            System.out.println("generate log get exception " + e);
        }
    }

    public static void main(String[] args) {
        new MySyslogClient().generate();
    }
}
