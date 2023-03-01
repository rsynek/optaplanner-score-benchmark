package org.optaplanner.score.benchmark.common;

import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

//@Fork(4)
//@Warmup(iterations = 8)
//@Measurement(iterations = 5)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.Throughput)
public abstract class AbstractBenchmark {


}
