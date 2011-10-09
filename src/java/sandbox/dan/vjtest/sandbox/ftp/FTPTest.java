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

        FTPClientConfig config = new FTPClientConfig(FTPClientConfig.SYST_UNIX);
        config.setServerLanguageCode("ru");
        ftp.configure(config);

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

            boolean result = ftp.changeWorkingDirectory("public");
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
