package com.example.jsche;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.client.subsystem.sftp.SftpClient;
import org.apache.sshd.client.subsystem.sftp.impl.DefaultSftpClient;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.EnumSet;
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

            DefaultSftpClient sftp=new DefaultSftpClient(session);


            sftp.mkdir("/root/lwk/test/abc");

            SftpClient.Handle h = sftp.open("/root/lwk/test/test.txt", EnumSet.of(SftpClient.OpenMode.Write));
            byte[] d = "0123456789\n".getBytes();
            sftp.write(h, 0, d, 0, d.length);
            sftp.write(h, d.length, d, 0, d.length);

            SftpClient.Attributes attrs = sftp.stat(h);
            System.out.println(attrs);

            sftp.close(h);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
