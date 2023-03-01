package org.optaplanner.score.benchmark;

import java.io.IOException;

import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.ChainedOptionsBuilder;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class Main {

    public static void main(String[] args) throws RunnerException {

        ChainedOptionsBuilder options = new OptionsBuilder()
                .forks(4)
                .warmupIterations(7)
                .measurementIterations(5)
                .jvmArgs("-XX:+UseParallelGC", "-Xms2g", "-Xmx2g");

        new Runner(options.build()).run();
    }
}
