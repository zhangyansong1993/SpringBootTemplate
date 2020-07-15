package com.zys.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class test implements InitializingBean {

    private static Logger logger = LoggerFactory.getLogger(test.class);


    @Override
    public void afterPropertiesSet() throws Exception {
        testF f=new testF("GD-GZ-JCX-S-7.CN2","JX-NC-ECL-S-2.CN2");
        testF g=new testF("SX-DT-SNL-S-1.CN2","JX-GZ-NM-S-2.CN2");
        testF h=new testF("FJ-FZ-SNL-S-1.CN2","US-NY-HUD-F-4.CN2");
        testF i=new testF("JX-PX-BFJ-S-2.CN2","JX-GZ-NM-S-2.CN2");
        testF j=new testF("LN-DL-LHL-S-1.CN2","JX-GZ-NM-S-2.CN2");
        new Thread(f,"线程A").start();
        new Thread(g,"线程B").start();
        new Thread(h,"线程C").start();
        new Thread(i,"线程D").start();
        new Thread(j,"线程E").start();

    }

}

class testF implements Runnable {
    private String Startip;
    private String Endip;

    testF(String Startip, String Endip) {
        this.Endip=Endip;
        this.Startip=Startip;
    }
    testF(){

    }
    @Override
    public void run() {
        restService rs = new restService();
        rs.Trace(Startip,Endip);
    }
}

class restService {

    public String date() {
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:Sss");
        return sp.format(new Date());

    }

    public void Trace(String Startip, String Endip) {
        String name = Thread.currentThread().getName();
        JSONObject jsona = new JSONObject();
        jsona.put("typename", "name");
        jsona.put("Startip", Startip);
        jsona.put("Endip", Endip);
        try {
            System.out.println(name + ":开始调用Trace:" + date());
            System.out.println(name + ":参数:" + jsona.toJSONString());
            URL resturl = new URL("http://172.16.51.137:13800/Serv/DevIpQry");
            HttpURLConnection conn = (HttpURLConnection) resturl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setAllowUserInteraction(false);
            PrintStream ps = new PrintStream(conn.getOutputStream());
            ps.print(jsona.toJSONString());
            ps.close();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line, resultStr = "";
            while (null != (line = bReader.readLine())) {
                resultStr += line;
            }
            bReader.close();
            System.out.println(name + ":Trace接口反回:" + resultStr);
            System.out.println(name + ":结束调用Trace:" + date());
            String TracertIP = "";
            JSONObject jsonc = JSONObject.parseObject(resultStr);
            JSONObject jsond = jsonc.getJSONObject("outpara");
            JSONObject jsone = jsond.getJSONObject("useinfo");
            JSONArray jsonf = jsone.getJSONArray("TraceInfo");
            System.out.println(name + ":开始查询设备名:" + date());
            for (int i = 0; i < jsonf.size(); i++) {
                JSONObject jsonb = new JSONObject();
                JSONObject jsong = jsonf.getJSONObject(i);
                TracertIP = jsong.getString("TracertIP");
                jsonb.put("Devip", TracertIP);
                deviceiptoname(jsonb);
            }
            System.out.println(name + ":查询设备名结束:" + date());
        } catch (Exception e) {

        }
    }

    public void deviceiptoname(JSONObject jsonObject) {
        String name = Thread.currentThread().getName();
        try {
            System.out.println(name + ":设备接口参数:" + jsonObject.toJSONString());
            URL resturl = new URL("http://172.16.51.137:13800/python/deviceiptoname");
            HttpURLConnection conn = (HttpURLConnection) resturl.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);
            conn.setAllowUserInteraction(false);
            PrintStream ps = new PrintStream(conn.getOutputStream());
            ps.print(jsonObject.toJSONString());
            ps.close();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line, resultStr = "";
            while (null != (line = bReader.readLine())) {
                resultStr += line;
            }
            bReader.close();
            System.out.println(name + ":设备接口反回:" + resultStr);
        } catch (Exception e) {

        }
    }


}