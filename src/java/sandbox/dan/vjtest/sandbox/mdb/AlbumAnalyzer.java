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
 * AlbumAnalyzer.java
 *
 * Created on 24.10.2011 16:43:38
 */

package dan.vjtest.sandbox.mdb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RecursiveAction;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author Alexander Dovzhikov
*/
public class AlbumAnalyzer extends RecursiveAction {
    private static final Logger log = LoggerFactory.getLogger(AlbumAnalyzer.class);
    private static final Pattern ALBUM_PATTERN = Pattern.compile("(\\d{4})\\s+(.*)");

    final DirectoryInfo dir;
    final String artist;

    public AlbumAnalyzer(DirectoryInfo dir, String artist) {
        this.dir = dir;
        this.artist = artist;
    }

    @Override
    protected void compute() {
        // album directory name should match '%year %album'
        String albumDir = dir.getName();
        Matcher matcher = ALBUM_PATTERN.matcher(albumDir);

        if (matcher.matches()) {
            MatchResult matchResult = matcher.toMatchResult();
            String year = matchResult.group(1);
            String album = matchResult.group(2);

            // all mp3 files have the corresponding artist name, year and album
            for (FSEntry entry : dir.children()) {
                if (entry instanceof Mp3FileInfo) {
                    Mp3FileInfo mp3 = (Mp3FileInfo) entry;

                    if (!artist.equals(mp3.getArtist())) {
                        log.error("For '{}': incorrect artist name: '{}', should be '{}'",
                                new Object[]{entry.getFullName(), mp3.getArtist(), artist});
                    }

                    if (!year.equals(mp3.getYear())) {
                        log.error("For '{}': incorrect year: '{}', should be '{}'",
                                new Object[]{entry.getFullName(), mp3.getYear(), year});
                    }

                    if (!album.equals(mp3.getAlbum())) {
                        log.error("For '{}': incorrect album: '{}', should be '{}'",
                                new Object[]{entry.getFullName(), mp3.getAlbum(), album});
                    }
                }
            }
        } else {
            log.error("Invalid album directory: '{}'", dir.getFullName());
        }
    }
}
