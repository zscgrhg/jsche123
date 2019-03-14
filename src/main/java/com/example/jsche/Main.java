package com.example.jsche;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws Exception{
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");

        JSch jsch = new JSch();
        Session session=jsch.getSession("root", "47.88.5.214", 22);

        session.setPassword("ss33448877!!");
        session.setConfig(config);
        session.connect();


        ChannelExec channel=(ChannelExec) session.openChannel("exec");
        BufferedReader reader=new BufferedReader(new InputStreamReader(channel.getInputStream()));
        channel.setCommand("uptime");
        channel.setErrStream(System.err);
        channel.setInputStream(null);
        channel.connect();

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

        channel.disconnect();


        ChannelSftp channelSftp= (ChannelSftp) session.openChannel("sftp");
        channelSftp.connect();

        channelSftp.put(new FileInputStream("D:\\pjapp\\jsche\\pom.xml"), "/root/lwk/test/pom.xml");
        channelSftp.disconnect();


        if(session != null && session.isConnected()) {
            session.disconnect();
        }
    }
}
