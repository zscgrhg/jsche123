package com.example.jsche;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;
//https://blog.rabahi.net/?page_id=1689#Using_JSCH
//https://www.ibm.com/developerworks/cn/java/j-lo-sshauthentication/index.html
public class Mina {
    public static void main(String[] args) {
        try(SshClient client = SshClient.setUpDefaultClient()) {
            client.start();

            ClientSession session = client.connect("root", "47.88.5.214", 22)
                    .verify(7L, TimeUnit.SECONDS)
                    .getSession();

            session.addPasswordIdentity("");
            session.auth().verify(5L, TimeUnit.SECONDS);

            String response =
                    session.executeRemoteCommand("uptime",System.err,
                            Charset.defaultCharset());
            System.out.println(response);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
