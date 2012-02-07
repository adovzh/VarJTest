/*
 * Copyright (c) 2012 Alexander Dovzhikov <alexander.dovzhikov@gmail.com>.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY ALEXANDER DOVZHIKOV ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL dovzhikov OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * FTPTest.java
 *
 * Created on 07.02.2012 17:10:59
 */

package dan.vjtest.sandbox.ftp;

import org.apache.commons.net.ProtocolCommandEvent;
import org.apache.commons.net.ProtocolCommandListener;
import org.apache.commons.net.ftp.*;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Dovzhikov
 */
public class FTPTest {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.err.println("Invalid arguments list");
            System.exit(-1);
        }
        
        String host = args[0];
        String login = args[1];
        String password = args[2];

        FTPClient ftp = new FTPClient();
        ftp.addProtocolCommandListener(new ProtocolCommandListener() {
            public void protocolCommandSent(ProtocolCommandEvent event) {
                writeEvent(System.out, event);
            }

            public void protocolReplyReceived(ProtocolCommandEvent event) {
                writeEvent(System.out, event);
            }

            private void writeEvent(PrintStream out, ProtocolCommandEvent event) {
                out.print(event.getMessage());
                out.flush();
            }
        });

        ftp.setControlEncoding("UTF-8");

        try {
            int reply;

            ftp.connect(host);
            System.out.println("Connected to " + host + ".");

            reply = ftp.getReplyCode();

            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(-1);
            }
        } catch (IOException e) {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e1) {
                    // do nothing
                }
            }

            System.err.println("Could not connect to server");
            e.printStackTrace();
            System.exit(-1);
        }

        boolean error = false;

        __main:
        try {
            if (!ftp.login(login, password)) {
                ftp.logout();
                error = true;
                break __main;
            }

            System.out.println("Remote system is " + ftp.getSystemName());

            ftp.enterLocalPassiveMode();

            boolean result = ftp.changeWorkingDirectory("music");
            System.out.println("Result: " + result);

            FTPFile[] ftpFiles = ftp.listFiles();
            List<FTPFile> dirs = new ArrayList<FTPFile>();
            List<FTPFile> files = new ArrayList<FTPFile>();

            for (FTPFile ftpFile : ftpFiles) {
                List<FTPFile> dest = (ftpFile.isDirectory()) ? dirs : files;
                dest.add(ftpFile);
            }

            reportFiles("Directories", dirs);
            reportFiles("Files", files);
            
            ftp.logout();
        } catch (FTPConnectionClosedException e) {
            error = true;
            System.err.println("Server closed connection");
            e.printStackTrace();
        } catch (IOException e) {
            error = true;
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException e) {
                    // do nothing
                }
            }
        }

        System.exit((error) ? -1 : 0);
    }

    private static void reportFiles(String name, List<FTPFile> files) {
        System.out.println(name + ":");

        for (FTPFile file : files) {
            System.out.println(file);
        }
    }
}
