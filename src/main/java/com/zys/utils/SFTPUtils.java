package com.zys.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.zys.bean.SFTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * SFTP链接操作模板
 */
//@Component
public class SFTPUtils {

//    @Value("${sftp.host}")
    public String host;
//    @Value("${sftp.port}")
    public int port;
//    @Value("${sftp.user}")
    public String user;
//    @Value("${sftp.pass}")
    public String pass;
//    @Value("${sftp.path}")
    public String path;

    private static Logger log = LoggerFactory.getLogger(SFTPUtils.class);


    public void main(String[] args) throws Exception {
        SFTP mysftp = null;
        try {
            host = "192.168.154.128";
            port = 22;
            user = "mysftp";
            pass = "zys!@#456..";
            mysftp = getConnect();
            ChannelSftp sftp = mysftp.getSftp();
            File file = new File("D:\\opt\\snps\\bgd\\（粤通网安监测〔2021〕1993号）普通企业-网络安全威胁监测处置通知-亚鸿世纪测试单位-恶意IP地址.pdf");
//            uploadFile(sftp, file, "/upload/log");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            disConn(mysftp);
        }
    }

    /**
     * 获取链接创建会话
     *
     * @return
     * @throws Exception
     */
    public SFTP getConnect() throws Exception {
        SFTP s = new SFTP();
        Session session = null;
        Channel channel = null;
        ChannelSftp sftp = null;// sftp操作类
        JSch jsch = new JSch();
        session = jsch.getSession(user, host, port);
        session.setPassword(pass);
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no"); // 不验证 HostKey
        session.setConfig(config);
        try {
            session.connect();
        } catch (Exception e) {
            if (session.isConnected())
                session.disconnect();
            log.error("连接服务器失败,请检查主机[" + host + "],端口[" + port
                    + "],用户名[" + user + "],密码[" + pass + "],是否正确,以上信息正确的情况下请检查网络连接是否正常或者请求被防火墙拒绝.", e);
        }
        channel = session.openChannel("sftp");
        try {
            channel.connect();
        } catch (Exception e) {
            if (channel.isConnected())
                channel.disconnect();
            log.error("连接服务器失败,请检查主机[" + host + "],端口[" + port
                    + "],用户名[" + user + "],密码[" + pass + "],是否正确,以上信息正确的情况下请检查网络连接是否正常或者请求被防火墙拒绝.", e);
        }
        sftp = (ChannelSftp) channel;
        s.setChannel(channel);
        s.setSession(session);
        s.setSftp(sftp);
        return s;
    }

    /**
     * 断开连接
     */
    public void disConn(SFTP sftp) {
        if (sftp != null) {
            if (null != sftp.getSftp()) {
                sftp.getSftp().disconnect();
                sftp.getSftp().exit();
                sftp.setSftp(null);
            }
            if (null != sftp.getChannel()) {
                sftp.getChannel().disconnect();
                sftp.setChannel(null);
            }
            if (null != sftp.getSession()) {
                sftp.getSession().disconnect();
                sftp.setSession(null);
            }
        }
    }

    /**
     * 列出目录下所有文件/文件夹
     *
     * @param cs
     * @param path
     * @return
     */
    public List<String> listFile(ChannelSftp cs, String path) {
        List<String> files = new ArrayList<>();
        try {
            Vector ls = cs.ls(path);
            Iterator it = ls.iterator();
            while (it.hasNext()) {
                String fileName = ((ChannelSftp.LsEntry) it.next()).getFilename();
                files.add(fileName);
            }
        } catch (Exception e) {
            log.error("列出文件列表异常", e);
        }
        return files;
    }


    /**
     * 上传文件
     *
     * @param cs
     * @return
     */
    public boolean uploadFile(ChannelSftp cs, String text) throws IOException {
        ByteArrayInputStream in = null;
        try {
            long l = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());
            String newPath = path + "/" + date;
            boolean dexist = isExist(cs, newPath);
            if (!dexist) {
                createDir(cs, path, date);
            }
            in = new ByteArrayInputStream(text.getBytes());
            cs.cd(newPath);
            //上传前判断文件是否存在，存在重新获取当前时间戳上传
            String fileName = l + ".log";
            while (isExist(cs, newPath + "/" + fileName)) {
                l = System.currentTimeMillis();
                fileName = l + ".log";
            }
            cs.put(in, fileName);
            cs.rename(fileName, fileName + ".ok");
        } catch (Exception e) {
            log.error("上传SFTP异常", e);
            return false;
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return true;
    }


    /**
     * 列出目录下所有文件/文件夹
     *
     * @param cs
     * @param path
     * @return
     */
    public boolean createDir(ChannelSftp cs, String path, String dir) {
        try {
            cs.cd(path);
            cs.mkdir(dir);
        } catch (Exception e) {
            log.error("创建目录异常", e);
            return false;
        }
        return true;
    }

    /**
     * 路径/文件是否存在
     *
     * @param cs
     * @param path
     * @return
     */
    public boolean isExist(ChannelSftp cs, String path) {
        try {
            cs.lstat(path);
        } catch (Exception e) {
            log.info("文件夹/文件不存在,创建文件/文件夹");
            return false;
        }
        return true;
    }

    /**
     * 删除文件
     *
     * @param cs
     * @param path
     * @param file
     * @return
     */
    public boolean delFile(ChannelSftp cs, String path, String file) {
        try {
            cs.cd(path);
            cs.rm(file);
        } catch (Exception e) {
            log.error("删除文件失败", e);
            return false;
        }
        return true;
    }


    /**
     * 删除路径
     *
     * @param cs
     * @param path
     * @return
     */
    public boolean delPath(ChannelSftp cs, String path) {
        try {
            cs.rmdir(path);
        } catch (Exception e) {
            log.error("删除路径失败", e);
            return false;
        }
        return true;
    }

    /**
     * 改文件名
     *
     * @param cs
     * @param path
     * @return
     */
    public boolean rename(ChannelSftp cs, String path, String oldName, String newName) {
        try {
            cs.cd(path);
            cs.rename(oldName, newName);
        } catch (Exception e) {
            log.error("改文件名失败", e);
            return false;
        }
        return true;
    }

}
