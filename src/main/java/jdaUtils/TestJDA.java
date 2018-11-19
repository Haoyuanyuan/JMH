package jdaUtils;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
public class TestJDA {
	 
	static long millis = 24 * 3600 * 1000;
	public static void main(String[] args) throws RunnerException {
		 Options options = new OptionsBuilder().include(TestJDA.class.getName()).forks(1).build();
	     new Runner(options).run();
	}
	 
	 @Benchmark
	 @Threads(5)
	 public void runjoda(){
		 DateTime dateTime = new DateTime();
	 }
	 @Benchmark
	 @Threads(5)
	 public void runCalendar(){
		 Calendar calendar = Calendar.getInstance();
	 }
	 @Benchmark
	 @Threads(5)
	 public void runSystem(){
		 long result = System.currentTimeMillis() / millis;
	 }
}
