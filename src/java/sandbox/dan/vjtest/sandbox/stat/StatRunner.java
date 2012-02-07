/*
 * Copyright (c) 2012 Alexander Dovzhikov <alexander.dovzhikov@gmail.com>.
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
 * StatRunner.java
 *
 * Created on 07.02.2012 17:28:50
 */

package dan.vjtest.sandbox.stat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Alexander Dovzhikov
 */
public class StatRunner implements GeneratorListener {
    private static final Logger log = LoggerFactory.getLogger(StatRunner.class);

    // guarded by this
    private List<Double> data = new ArrayList<>();
    private double sum = 0;
    private double sum2 = 0;
    private double min = Double.MAX_VALUE;
    private double max = Double.MIN_VALUE;

    public StatRunner(ExecutorService producers, ScheduledExecutorService reporters) {
        new Generator(2, 20, this, producers, 8);

        reporters.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                makeReport();
            }
        }, 1, 1, TimeUnit.SECONDS);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
        } finally {
            log.debug("Shutting producers down");
            producers.shutdownNow();
            log.debug("Shutting reporters down");
            reporters.shutdown();
            log.debug("OK");
        }
    }

    private void makeReport() {
        Report report;

        synchronized (this) {
            report = new Report(data, sum, sum2);
            data.clear();
            sum = 0;
            sum2 = 0;
            min = Double.MAX_VALUE;
            max = Double.MIN_VALUE;
        }

        report.process();
    }

    @Override
    public void eventGenerated(double value) {
        synchronized (this) {
            data.add(value);
            sum += value;
            sum2 += value * value;

            if (value < min)
                min = value;

            if (value > max)
                max = value;
        }
    }

    public static void main(String[] args) {
        ExecutorService producers = Executors.newFixedThreadPool(1);
        ScheduledExecutorService reporters = Executors.newScheduledThreadPool(1);
        new StatRunner(producers, reporters);
    }

    private static class Report {
        final double[] data;
        final double sum;
        final double sum2;

        private Report(List<Double> data, double sum, double sum2) {
            this.data = toArray(data);
            this.sum2 = sum2;
            this.sum = sum;
        }
        
        double[] toArray(List<Double> list) {
            int i = 0;
            double[] arr = new double[list.size()];
            
            for (Double d : list)
                arr[i++] = d;
            
            Arrays.sort(arr);

            return arr;
        }

        void process() {
            double min = getMin();
            double max = getMax();
            double avg = sum / data.length;
            double avg2 = sum2 / data.length;
            double stddev = Math.sqrt(avg2 - avg * avg);
            double median = getMedian();

            log.info(String.format("Min/max/median/avg/stddev: %.3f/%.3f/%.3f/%.3f/%.3f", min, max, median, avg, stddev));
        }

        double getMedian() {
            int len = data.length;

            if (len % 2 == 1) {
                return data[len / 2];
            } else if (len > 0) {
                return (data[len / 2] + data[len / 2 - 1]) / 2;
            } else {
                return Double.NaN;
            }
        }

        double getMin() {
            return (data.length > 0) ? data[0] : Double.NaN;
        }

        double getMax() {
            return (data.length > 0) ? data[data.length - 1] : Double.NaN;
        }
    }
}
