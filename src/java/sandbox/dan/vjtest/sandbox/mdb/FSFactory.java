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

import entagged.audioformats.AudioFile;
import entagged.audioformats.AudioFileIO;
import entagged.audioformats.Tag;
import entagged.audioformats.exceptions.CannotReadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Alexander Dovzhikov
 */
public class FSFactory {
    private static final Logger log = LoggerFactory.getLogger(FSFactory.class);
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

    static class FSEntryBuilder extends RecursiveTask<FSEntry> {
        final DirectoryInfo parent;
        final File root;
        final Reporter reporter;

        public FSEntryBuilder(DirectoryInfo parent, File file, Reporter reporter) {
            this.parent = parent;
            this.root = file;
            this.reporter = reporter;
        }

        @Override
        protected FSEntry compute() {
            if (reporter != null) {
                reporter.updateProgress(root.getAbsolutePath());
            }

            if (root.isDirectory()) {
                File[] files = root.listFiles();
                DirectoryInfo directoryInfo = new DirectoryInfo(parent, root.getName());
                Collection<FSEntryBuilder> tasks = new ArrayList<>(files.length);

                for (File file : files) {
                    tasks.add(new FSEntryBuilder(directoryInfo, file, reporter));
                }

                for (FSEntryBuilder task : invokeAll(tasks)) {
                    try {
                        directoryInfo.addChild(task.get());
                    } catch (InterruptedException | ExecutionException e) {
                        log.error(e.getMessage(), e);
                    }
                }

                return directoryInfo;
            } else if (root.getName().endsWith(".mp3")) {
                Mp3FileInfo mp3FileInfo = new Mp3FileInfo(parent, root.getName());

                try {
                    AudioFile audioFile = AudioFileIO.read(root);
                    log.debug("Successfully parsed mp3: {}", root);

                    Tag tag = audioFile.getTag();

                    mp3FileInfo.setArtist(tag.getFirstArtist());
                    mp3FileInfo.setAlbum(tag.getFirstAlbum());
                    mp3FileInfo.setTitle(tag.getFirstTitle());
                    mp3FileInfo.setYear(tag.getFirstYear());
                } catch (CannotReadException e) {
                    log.error("Error parsing mp3 file", e);
                }

                return mp3FileInfo;
            } else {
                return new FileInfo(parent, root.getName());
            }
        }
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

    static class ArtistAnalyzer extends RecursiveAction {
        final DirectoryInfo dir;
        final Reporter reporter;

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

    static class AlbumAnalyzer extends RecursiveAction {
        static final Pattern ALBUM_PATTERN = Pattern.compile("(\\d{4})\\s+(.*)");

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
}
