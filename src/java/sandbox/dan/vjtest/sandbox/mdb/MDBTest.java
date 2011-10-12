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
 * MDBTest.java
 *
 * Created on 12.10.2011 15:57:07
 */

package dan.vjtest.sandbox.mdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexander Dovzhikov
 */
public class MDBTest {
    private static final Logger log = LoggerFactory.getLogger(MDBTest.class);

    private final File path;

    public MDBTest(String path) {
        this.path = new File(path);
    }

    public void go() {
        List<File> dirs = new ArrayList<File>();
        List<File> files = new ArrayList<File>();

        for (File f : path.listFiles()) {
            if (f.isDirectory()) {
                dirs.add(f);
            } else if (f.isFile()) {
                files.add(f);
            }
        }

        for (File f : files) {
            log.warn("Unexpected file: {}", f);
        }

        for (File dir : dirs) {
            checkArtistDir(dir);
        }
    }

    private void checkArtistDir(File dir) {
        log.info("Checking directory: {}", dir);

        String artist = dir.getName();
        log.debug("Artist: {}", artist);
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            log.error("Provide an argument: path");
            System.exit(-1);
        }

        log.debug("Current thread name: {}", Thread.currentThread().getName());

        MDBTest app = new MDBTest(args[0]);
        app.go();
    }
}