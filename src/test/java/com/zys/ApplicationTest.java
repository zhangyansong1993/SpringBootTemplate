package com.zys;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {
    Logger logger = LoggerFactory.getLogger(ApplicationTest.class);

    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Test
    public void test() {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:Sss");
        for (int i = 0; i < 100; i++) {
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("SRC_ACKNOWLEDGEMENTTIMESTAMP","");
            jsonObject.put("SRC_ALARM_NTIME","2020-07-08 18:08:32");
            jsonObject.put("SRC_PERCEIVEDSEVERITY","5");
            jsonObject.put("SRC_MANU_ALARMFLAG","3");
            jsonObject.put("SRC_EVENTTIME","2020-07-08 18:08:30");
            jsonObject.put("SRC_LOCATIONINFO","进程pid=20, 链 路集ID=4_7a770cf6954f4945aa1d071144010200_19_354e929e,链路ID=0");
            jsonObject.put("SRC_ALARMCODE","100155");
            jsonObject.put("SRC_ALARMID","329415");
            jsonObject.put("SRC_NMS_TYPE","8");
            jsonObject.put("SRC_SYNC_FLAG","3");
            jsonObject.put("SRC_ALARMSUBTYPE","");
            jsonObject.put("SRC_ALARM_NID","3");
            jsonObject.put("SRC_ALARM_PROBLEM","5");
            jsonObject.put("SRC_ID","HW5GC5eec7776d7e88a00067e8a1b000000054263");
            jsonObject.put("SRC_SYSTEM_TYPE","");
            jsonObject.put("SRC_REGION","44");
            jsonObject.put("SRC_ALARM_NO","100155");
            jsonObject.put("SRC_SYSTEM_DN","");
            jsonObject.put("SRC_ACKSTATUS","");
            jsonObject.put("SRC_IS_TEST","0");
            jsonObject.put("SRC_ALARMTITLE","");
            jsonObject.put("SRC_IP","10.142.157.190");
            jsonObject.put("SRC_EQUIPMENTNAME","GD-GZ-ZSC-RP0021B-A-5GC-HW-SMF211-B");
            jsonObject.put("SRC_INERTTIME","2020-07-08 18:08:32");
            jsonObject.put("SRC_SOURCE","NE");
            jsonObject.put("SRC_ALARMTYPE","1");
            jsonObject.put("SRC_CLEARANCEREPORTFLAG","1");
            jsonObject.put("SRC_IPADDRESS","17.108.15.18");
            jsonObject.put("SRC_STATE","1");
            jsonObject.put("SRC_NAME","fm-snmp-5gc-hwne-v1");
            jsonObject.put("SRC_INFO1","5");
            jsonObject.put("SRC_INFO2","5");
            jsonObject.put("SRC_INFO3","5");
            jsonObject.put("SRC_INFO4","5");
            jsonObject.put("SRC_INFO5","5");
            jsonObject.put("SRC_INFO6","5");
            jsonObject.put("SRC_INFO7","5");
            jsonObject.put("SRC_INFO8","5");
            jsonObject.put("SRC_INFO9","5");
            jsonObject.put("SRC_NECLASS","SMF");
            jsonObject.put("SRC_ORG_CLR_OPTR","5");
            jsonObject.put("SRC_APP_ID","106");
            jsonObject.put("SRC_VENDOR","HW");
            jsonObject.put("SRC_ALARMSID","");
            jsonObject.put("SRC_NETTYPE","5GC");
            jsonObject.put("SRC_ALARM_NTYPE","2");
            jsonObject.put("SRC_VENDOR_ALARMID","329415");
            jsonObject.put("SRC_SENDTIME","2020-07-08 18:08:32");
            jsonObject.put("SRC_MANU_ALARMTYPE","4");
            jsonObject.put("SRC_SYNC_NO","329415");
            jsonObject.put("SRC_ADDITIONALINFO","对端NF实例ID=7a770cf6-954f-4945-aa1d-071144010200,对端NF服务=4947a69a-f11b-4bc1-b9da-47c9c8692112,本端NF类型=SMF,本端IP地址=[240e:180:260:200::102],对端IP地址=[240e:180:260:100::a01,本端端口=24768,对端端口=8080,协议类型=tcp,传输协议=http,告警原因=link down reason 0x[ 600000000],告警源=sbim-pod-d76c68cbb-lzb4z");
            jsonObject.put("SRC_ADDITIONALTEXT","对端NF实例ID=7a770cf6-954f-4945-aa1d-071144010200,对端NF服务=4947a69a-f11b-4bc1-b9da-47c9c8692112,本端NF类型=SMF,本端IP地址=[240e:180:260:200::102],对端IP地址=[240e:180:260:100::a01,本端端口=24768,对端端口=8080,协议类型=tcp,传输协议=http,告警原因=link down reason 0x[ 600000000],告警源=sbim-pod-d76c68cbb-lzb4z");
            kafkaTemplate.send("iams-test", jsonObject.toJSONString());

       }


    }


}
