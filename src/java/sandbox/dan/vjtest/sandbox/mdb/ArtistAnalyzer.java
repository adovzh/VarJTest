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
 * ArtistAnalyzer.java
 *
 * Created on 24.10.2011 16:41:18
 */

package dan.vjtest.sandbox.mdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.RecursiveAction;

/**
* @author Alexander Dovzhikov
*/
public class ArtistAnalyzer extends RecursiveAction {
    private static final Logger log = LoggerFactory.getLogger(ArtistAnalyzer.class);

    private final DirectoryInfo dir;
    private final Reporter reporter;

    public ArtistAnalyzer(DirectoryInfo dir, Reporter reporter) {
        this.dir = dir;
        this.reporter = reporter;
    }

    @Override
    protected void compute() {
        if (reporter != null) {
            reporter.updateProgress(dir.getFullName());
        }

        log.info("Processing directory: {}", dir);

        String artist = dir.getName();

        if (isSpecialArtistDir(artist)) {
            log.debug("Skipping special dir: {}", dir.getFullName());
        } else {
            log.debug("Artist: {}", artist);

            Collection<FSEntry> children = dir.children();
            Collection<RecursiveAction> subTasks = new ArrayList<>(children.size());

            for (FSEntry entry : children) {
                if (entry.isDirectory()) {
                    subTasks.add(new AlbumAnalyzer((DirectoryInfo) entry, artist));
                }
            }

            invokeAll(subTasks);
        }
    }

    private boolean isSpecialArtistDir(String dirName) {
        return "__NEW".equals(dirName) || "Various Artists".equals(dirName);
    }
}
