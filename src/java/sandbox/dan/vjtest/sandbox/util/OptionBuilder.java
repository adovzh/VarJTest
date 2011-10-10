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
 * OptionBuilder.java
 *
 * Created on 10.10.2011 16:53:11
 */

package dan.vjtest.sandbox.util;

import org.apache.commons.cli.Option;

/**
 * @author Alexander Dovzhikov
 */
public class OptionBuilder {
    private static final OptionBuilder instance = new OptionBuilder();

    private OptionBuilder() {
    }

    public static OptionBuilder getInstance() {
        return instance;
    }

    public OptionBuilder withArgName(String name) {
        org.apache.commons.cli.OptionBuilder.withArgName(name);
        return this;
    }

    public OptionBuilder hasArg() {
        org.apache.commons.cli.OptionBuilder.hasArg();
        return this;
    }

    public OptionBuilder withDescription(String newDescription) {
        org.apache.commons.cli.OptionBuilder.withDescription(newDescription);
        return this;
    }

    public Option create(String opt) {
        return org.apache.commons.cli.OptionBuilder.create(opt);
    }
}
