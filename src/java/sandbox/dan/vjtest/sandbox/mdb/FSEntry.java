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
 * FSEntry.java
 *
 * Created on 20.10.2011 19:09:22
 */

package dan.vjtest.sandbox.mdb;

import java.util.Collection;

/**
 * @author Alexander Dovzhikov
 */
public abstract class FSEntry {
    private final DirectoryInfo parent;
    private final String name;

    public FSEntry(DirectoryInfo parent, String name) {
        this.parent = parent;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public String getFullName() {
        StringBuilder sb = new StringBuilder();

        if (parent != null) {
            sb.append(parent.getFullName()).append('/');
        }

        return sb.append(getName()).toString();
    }

    public int getLevel() {
        int level = 0;
        FSEntry p = parent;

        while (p != null) {
            level++;
            p = p.parent;
        }

        return level;
    }

    public abstract boolean isDirectory();

    public abstract Collection<FSEntry> children();

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("FSEntry");
        sb.append("{name='").append(getFullName()).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
