package com.zys.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 实时监测文件
 */
//@Component
public class ReadFile {

    private Logger log = LoggerFactory.getLogger(ReadFile.class);

    private long lastTimeFileSize = 0;

//    @Value("${source.path}")
    private String path;

//    @Value("${source.seconds}")
    private long seconds;

    /**
     * 实时读取指定文件的内容
     */
    @Bean
    public void realtimeShowLog() {
        try {
            File logFile = new File(path);
            //指定文件可读可写
            final RandomAccessFile randomAccessFile = new RandomAccessFile(logFile, "rw");
            //启动一个线程每xx秒钟读取新增的日志信息
            ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
            exec.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    try {
                        //获得变化部分的
                        randomAccessFile.seek(lastTimeFileSize);
                        String tmp = "";
                        while ((tmp = randomAccessFile.readLine()) != null) {
                            // 输出文件内容
                            String str = new String(tmp.getBytes("ISO8859-1"));
                            lastTimeFileSize = randomAccessFile.length();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 0, seconds, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("读取源文件异常", e);
        }
    }
}
