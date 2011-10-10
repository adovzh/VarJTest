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
 * LocalVFile.java
 *
 * Created on 10.10.2011 16:20:36
 */

package dan.vjtest.sandbox.vfs;

import java.io.File;

/**
 * @author Alexander Dovzhikov
 */
class LocalVFile implements VFile {
    private final File file;

    LocalVFile(String path) {
        file = new File(path);
    }

    private LocalVFile(File file) {
        this.file = file;
    }

    public String getName() {
        return file.getName();
    }

    public boolean isDirectory() {
        return file.isDirectory();
    }

    public VFile[] listFiles() {
        File[] files = file.listFiles();
        int length = files.length;
        VFile[] vFiles = new VFile[length];

        for (int i = 0; i < length; i++) {
            vFiles[i] = new LocalVFile(files[i]);
        }

        return vFiles;
    }

    @Override
    public String toString() {
        return file.toString();
    }
}
