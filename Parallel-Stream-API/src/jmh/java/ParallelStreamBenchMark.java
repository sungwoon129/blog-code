package com.blog;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

@State(Scope.Thread) //벤치 마크에 사용되는 Argument의 상태를 Thread별로 초기화한다.
@BenchmarkMode(Mode.AverageTime) // 벤치마크 대상 메서드를 실행하는데 걸린 평균 시간 측정
@OutputTimeUnit(TimeUnit.MILLISECONDS)// 벤치마크 결과를 밀리초 단위로 출력
@Fork(value = 2, jvmArgs = {"-Xms4g", "-Xmx4g"})//4Gb의 힙 공간을 제공한 환경에서 두 번 벤치마크 수행
public class ParallelStreamBenchMark {
    private static final long N = 10_000_000L;

    @Benchmark //벤치마크 대상 메서드
    public long parallelRangeSum() {
        return LongStream.rangeClosed(1, N)
                .parallel()
                .reduce(0L, Long::sum);
    }

    @TearDown(Level.Invocation) // 매 번 벤치마크를 실행한 다음 가비지 컬렉터 동작 시도
    public void tearDown() {
        System.gc();
    }

    public static void main(String[] args) throws IOException, RunnerException {
        Options opt = new OptionsBuilder()
                .include(ParallelStreamBenchMark.class.getSimpleName())
                .warmupIterations(10)           // 사전 테스트 횟수
                .measurementIterations(10)      // 실제 측정 횟수
                .forks(1)                       //
                .build();
        new Runner(opt).run();                  // 벤치마킹 시작
    }

}
