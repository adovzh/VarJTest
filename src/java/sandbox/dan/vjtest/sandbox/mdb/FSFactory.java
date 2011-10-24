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
 * FSFactory.java
 *
 * Created on 21.10.2011 12:56:22
 */

package dan.vjtest.sandbox.mdb;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

/**
 * @author Alexander Dovzhikov
 */
public class FSFactory {
    private static final ForkJoinPool POOL = new ForkJoinPool();

    FSEntry createFSEntry(File file, Reporter reporter) {
        if (reporter != null) {
            reporter.setLength(countFiles(file));
        }

        return POOL.invoke(new FSEntryBuilder(null, file, reporter));
    }

    private static int countFiles(File path) {
        int count = 1;

        if (path.isDirectory()) {
            for (File file : path.listFiles()) {
                count += countFiles(file);
            }
        }

        return count;
    }

    void analyzeArtists(Collection<DirectoryInfo> artistDirs, Reporter reporter) {
        if (reporter != null) {
            reporter.setLength(artistDirs.size());
        }

        Collection<ForkJoinTask<Void>> tasks = new ArrayList<>();

        for (DirectoryInfo artistDir : artistDirs) {
            tasks.add(POOL.submit(new ArtistAnalyzer(artistDir, reporter)));
        }

        for (ForkJoinTask<Void> task : tasks) {
            task.join();
        }
    }

}
