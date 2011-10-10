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
 * SimpleFormatter.java
 *
 * Created on 10.10.2011 11:48:16
 */

package dan.vjtest.sandbox.logging;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * This is a custom <code>Formatter</code> that formats the log record
 * as a single log line in the following format:
 * <pre>
 * [<i>LEVEL</i>] <i>NAME</i> <i>METHOD_NAME</i> <i>MESSAGE</i>
 * </pre>
 *
 * The log line can be appended with the stack trace of the exception
 * if it's included in the log record.
 *
 * @author Alexander Dovzhikov
 * @see Formatter
 * @see LogRecord
 */
public class SimpleFormatter extends Formatter {
    private static final String FORMAT_PATTERN = "[%s] %s%s: %s%n";

    /**
     * Formats the log record as a single log line in the following
     * format:
     * <pre>
     * [<i>LEVEL</i>] <i>NAME</i> <i>METHOD_NAME</i> <i>MESSAGE</i>
     * </pre>
     *
     * The log line can be appended with the stack trace of the
     * exception if it's included in the log record.
     *
     * @param record the log record to be formatted
     *
     * @return the formatted log record
     */
    @Override
    public String format(LogRecord record) {
        String level = record.getLevel().getLocalizedName();
        String name = (record.getSourceClassName() != null)
                ? record.getSourceClassName()
                : record.getLoggerName();
        String methodName = (record.getSourceMethodName() != null)
                ? ' ' + record.getSourceMethodName()
                : "";
        String message = formatMessage(record);
        String formattedMessage = String.format(FORMAT_PATTERN,
                level, name, methodName, message);

        if (record.getThrown() != null) {
            try {
                StringWriter sw = new StringWriter();
                PrintWriter pw = new PrintWriter(sw);
                record.getThrown().printStackTrace(pw);
                pw.close();
                formattedMessage += sw.toString();
            } catch (Exception ex) {
                // ignore
            }
        }

        return formattedMessage;
    }
}
