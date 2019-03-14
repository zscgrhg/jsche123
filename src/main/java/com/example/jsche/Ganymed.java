package com.example.jsche;


import java.util.List;

public class Ganymed {
    public static void main(String[] args) throws Exception{
        String userName = "root";
        String password = "";
        String host = "47.88.5.214";
        String path = "/root/lwk";

        SshClient sshClient = new SshClient();
        List<String> actual = sshClient.listFiles(userName, password, host, path);

        for (String s: actual) {
            System.out.println(s);
        }
    }
}
