/*
 * Copyright (c) 2011 Alexander Dovzhikov <alexander.dovzhikov@gmail.com>.
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
 * VFSTest.java
 *
 * Created on 10.10.2011 16:31:32
 */

package dan.vjtest.sandbox.vfs;

import dan.vjtest.sandbox.util.OptionBuilder;
import org.apache.commons.cli.*;

/**
 * @author Alexander Dovzhikov
 */
public class VFSTest {
    private String localPath = null;

    void parseOptions(String[] args) {
        Options options = new Options();

        OptionBuilder builder = OptionBuilder.getInstance();
        options.addOption(builder.withArgName("file").hasArg().withDescription("local file").create("file"));

        CommandLineParser parser = new GnuParser();

        try {
            CommandLine line = parser.parse(options, args);

            if (line.hasOption("file"))
                localPath = line.getOptionValue("file");
        } catch (ParseException e) {
            System.err.println("Parsing failed. Reason: " + e.getMessage());
            System.exit(-1);
        }
    }

    void go() {
        System.out.println("localPath: " + localPath);

        if (localPath == null)
            return;

        VFS vfs = new VFS();
        VFile vFile = vfs.getLocalFile(localPath);
        System.out.println("Path: " + vFile);

        VFile[] vFiles = vFile.listFiles();
        for (VFile file : vFiles) {
            System.out.println((file.isDirectory() ? "Directory" : "File") + " " + file);
        }
    }

    public static void main(String[] args) {
        VFSTest app = new VFSTest();
        app.parseOptions(args);
        app.go();
    }
}
