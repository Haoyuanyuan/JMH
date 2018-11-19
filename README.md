# jmh
关于JMH（Java Microbenchmark Harness）微基准测试的使用说明
# JMH使用说明
## 一、JMH环境构建
使用Eclipse中启动测试，需要添加m2e插件支持
[Code Tools: jmh](https://openjdk.java.net/projects/code-tools/jmh/)   
[JMH - Java Microbenchmark Harness](http://tutorials.jenkov.com/java-performance/jmh.html)    
[JMH使用说明](https://blog.csdn.net/lxbjkben/article/details/79410740)
### 1.使用maven工程，直接在项目中引入相应的jar包
```
<dependency>
    <groupId>org.openjdk.jmh</groupId>
    <artifactId>jmh-core</artifactId>
    <version>1.21</version>
</dependency>
<dependency>
    <groupId>org.openjdk.jmh</groupId>
    <artifactId>jmh-generator-annprocess</artifactId>
    <version>1.21</version>
    <scope>provided</scope>
</dependency>
```
### 2.直接用JMH的archetype模板创建
```
mvn archetype:generate  -DinteractiveMode=false -DarchetypeGroupId=org.openjdk.jmh  -DarchetypeArtifactId=jmh-java-benchmark-archetype  -DgroupId=org.sample   -DartifactId=JMHTest  -Dversion=1.21
```
## 二、写相应的测试用例
### 1. 编写测试主类
```
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
```
## 三、运行测试用例
### 1.直接打包
```
mvn clean install

java  -jar  target/testJmh-0.0.1-SNAPSHOT.jar
上面这种方式会直接在控制台上输出运行中的输出
java  -jar  target/testJmh-0.0.1-SNAPSHOT.jar > test.log
这种命令会将所有的输出输出到test.log文件中
```
这种运行方式前必须修改pom文件:添加以下配置
```
<build>
        <plugins>
           <plugin>
		        <groupId>org.apache.maven.plugins</groupId>
		        <artifactId>maven-shade-plugin</artifactId>
		        <version>1.2.1</version>
		        <executions>
		            <execution>
		                <phase>package</phase>
		                <goals>
		                     <goal>shade</goal>
		                </goals>
		                    <configuration>
		                        <transformers>
		                            <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
		                                <mainClass>jdaUtils.TestJDA</mainClass>
		                            </transformer>
		                        </transformers>
		                    </configuration>
		            </execution>
		        </executions>
		     </plugin>
        </plugins>
    </build>
```
上面的代码就是为了在使用maven打包的时候添加主清单属性（jar执行时候的入口类）
### 2.在IDE中直接运行

往往在写用例的时候，可以不写main方法，直接在方法上写个Junit的Test注解 ，功能和main相同。

## 四、运行结果
```
# JMH version: 1.21
# VM version: JDK 1.8.0_144, Java HotSpot(TM) 64-Bit Server VM, 25.144-b01
# VM invoker: C:\Program Files\Java\jre1.8.0_144\bin\java.exe
# VM options: -Dfile.encoding=UTF-8
# Warmup: 5 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 5 threads, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: jdaUtils.TestJDA.runCalendar

# Run progress: 0.00% complete, ETA 00:05:00
# Fork: 1 of 1
# Warmup Iteration   1: 438.779 ±(99.9%) 6.320 ns/op
# Warmup Iteration   2: 472.068 ±(99.9%) 4.428 ns/op
# Warmup Iteration   3: 447.899 ±(99.9%) 0.347 ns/op
# Warmup Iteration   4: 441.445 ±(99.9%) 5.978 ns/op
# Warmup Iteration   5: 448.156 ±(99.9%) 5.817 ns/op
Iteration   1: 439.934 ±(99.9%) 3.578 ns/op
Iteration   2: 438.816 ±(99.9%) 5.134 ns/op
Iteration   3: 437.722 ±(99.9%) 1.672 ns/op
Iteration   4: 438.682 ±(99.9%) 3.299 ns/op
Iteration   5: 437.516 ±(99.9%) 3.021 ns/op


Result "jdaUtils.TestJDA.runCalendar":
  438.534 ±(99.9%) 3.732 ns/op [Average]
  (min, avg, max) = (437.516, 438.534, 439.934), stdev = 0.969
  CI (99.9%): [434.802, 442.266] (assumes normal distribution)


# JMH version: 1.21
# VM version: JDK 1.8.0_144, Java HotSpot(TM) 64-Bit Server VM, 25.144-b01
# VM invoker: C:\Program Files\Java\jre1.8.0_144\bin\java.exe
# VM options: -Dfile.encoding=UTF-8
# Warmup: 5 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 5 threads, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: jdaUtils.TestJDA.runSystem

# Run progress: 33.33% complete, ETA 00:03:20
# Fork: 1 of 1
# Warmup Iteration   1: 4.633 ±(99.9%) 0.141 ns/op
# Warmup Iteration   2: 4.598 ±(99.9%) 0.090 ns/op
# Warmup Iteration   3: 4.748 ±(99.9%) 0.094 ns/op
# Warmup Iteration   4: 4.726 ±(99.9%) 0.133 ns/op
# Warmup Iteration   5: 4.746 ±(99.9%) 0.099 ns/op
Iteration   1: 4.718 ±(99.9%) 0.079 ns/op
Iteration   2: 4.769 ±(99.9%) 0.155 ns/op
Iteration   3: 4.751 ±(99.9%) 0.191 ns/op
Iteration   4: 4.736 ±(99.9%) 0.122 ns/op
Iteration   5: 4.749 ±(99.9%) 0.124 ns/op


Result "jdaUtils.TestJDA.runSystem":
  4.744 ±(99.9%) 0.074 ns/op [Average]
  (min, avg, max) = (4.718, 4.744, 4.769), stdev = 0.019
  CI (99.9%): [4.671, 4.818] (assumes normal distribution)


# JMH version: 1.21
# VM version: JDK 1.8.0_144, Java HotSpot(TM) 64-Bit Server VM, 25.144-b01
# VM invoker: C:\Program Files\Java\jre1.8.0_144\bin\java.exe
# VM options: -Dfile.encoding=UTF-8
# Warmup: 5 iterations, 10 s each
# Measurement: 5 iterations, 10 s each
# Timeout: 10 min per iteration
# Threads: 5 threads, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: jdaUtils.TestJDA.runjoda

# Run progress: 66.67% complete, ETA 00:01:40
# Fork: 1 of 1
# Warmup Iteration   1: 19.817 ±(99.9%) 1.410 ns/op
# Warmup Iteration   2: 19.592 ±(99.9%) 1.053 ns/op
# Warmup Iteration   3: 19.577 ±(99.9%) 0.746 ns/op
# Warmup Iteration   4: 19.627 ±(99.9%) 1.182 ns/op
# Warmup Iteration   5: 19.293 ±(99.9%) 0.917 ns/op
Iteration   1: 19.495 ±(99.9%) 0.598 ns/op
Iteration   2: 19.520 ±(99.9%) 0.731 ns/op
Iteration   3: 19.403 ±(99.9%) 1.037 ns/op
Iteration   4: 19.396 ±(99.9%) 0.537 ns/op
Iteration   5: 19.678 ±(99.9%) 0.607 ns/op


Result "jdaUtils.TestJDA.runjoda":
  19.498 ±(99.9%) 0.440 ns/op [Average]
  (min, avg, max) = (19.396, 19.498, 19.678), stdev = 0.114
  CI (99.9%): [19.059, 19.938] (assumes normal distribution)


# Run complete. Total time: 00:05:01

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

Benchmark                     Mode  Cnt    Score   Error  Units
jdaUtils.TestJDA.runCalendar  avgt    5  438.534 ± 3.732  ns/op
jdaUtils.TestJDA.runSystem    avgt    5    4.744 ± 0.074  ns/op
jdaUtils.TestJDA.runjoda      avgt    5   19.498 ± 0.440  ns/op

```
#### 遇到的问题
- 修改了项目代码后没有进行打包
```
Exception in thread "main" No benchmarks to run; check the include/exclude regexps.
	at org.openjdk.jmh.runner.Runner.internalRun(Runner.java:268)
	at org.openjdk.jmh.runner.Runner.run(Runner.java:209)
	at jdaUtils.TestJDA1.main(TestJDA1.java:24)
```
    解决方式：重新打包  mvn clean install
```
    ERROR: org.openjdk.jmh.runner.RunnerException: ERROR: Exception while trying to acquire the JMH lock (C:\Windows\/jmh.lock): Access is denied, exiting. Use -Djmh.ignoreLock=true to forcefully continue.
    at org.openjdk.jmh.runner.Runner.run(Runner.java:213)
    at org.openjdk.jmh.Main.main(Main.java:71)
```
    解决方式：mvn clean install
    
### JMH注解的含
@Benchmark：用于标识需要进行基准测试的方法 
@BenchmarkMode ：用于指定性能数据的格式。主要用的有吞吐量或者平均时间。 
```
    @BenchmarkMode(Mode.Throughput) 统计单位时间内的方法被调用次数
    @BenchmarkMode(Mode.AverageTime) 统计单位时间内方法每次执行的平均时间
    @BenchmarkMode(Mode.SampleTime) 程序执行的过程中随机采样
    @BenchmarkMode(Mode.SingleShotTime) 只运行一次，也可以不进行预热操作
    @BenchmarkMode(Mode.ALL) 所有模式下的数据都会统计
```
@OutputTimeUnit：用于指定输出的时间单位。 
```
    @OutputTimeUnit(TimeUnit.DAYS)
    @OutputTimeUnit(TimeUnit.HOURS)
    @OutputTimeUnit(TimeUnit.MINUTES)
    @OutputTimeUnit(TimeUnit.SECONDS)  统计的时间为一分钟，具体统计的是什么由BenchmarkMode决定
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    @OutputTimeUnit(TimeUnit.NANOSECONDS) 
```

@Warmup：用于对预热迭代进行配置，包括iterations配置预热次数，time配置预热时间, timeUnit配置时间单位，以及batchSize执行次数。    
@Measurement：用于对测试迭代进行配置，各参数含义跟Warmup一样。   
@Threads：用于配置测试时的线程数。   
@Fork：允许开发人员指定所要 Fork 出的 Java 虚拟机的数目。    
@State：用于标识程序的状态，其中：
```
    Scope.Thread：默认的State，每个测试线程分配一个实例；
    Scope.Benchmark：所有测试线程共享一个实例，用于测试有状态实例在多线程共享下的性能；
    Scope.Group：每个线程组共享一个实例；    
```
@Setup和@TearDown：用于测试前对程序状态的初始化以及测试后对程序状态的恢复或者校验。
