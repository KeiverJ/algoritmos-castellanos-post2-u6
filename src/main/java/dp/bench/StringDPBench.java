package dp.bench;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import dp.StringDP;

public class StringDPBench {
	@State(Scope.Thread)
	public static class InputState {
		@Param({"100", "500", "1000"})
		public int length;

		public String x;
		public String y;

		@Setup(Level.Trial)
		public void setUp() {
			Random random = new Random(2026L + length);
			x = randomString(length, random);
			y = randomString(length, random);
		}

		private static String randomString(int n, Random random) {
			char[] chars = new char[n];
			for (int i = 0; i < n; i++) {
				chars[i] = (char) ('A' + random.nextInt(26));
			}
			return new String(chars);
		}
	}

	/**
	 * Benchmark de LCS completo.
	 * Tiempo teorico esperado: O(n^2) para n=m.
	 */
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public int lcsLengthBenchmark(InputState state) {
		return StringDP.lcsLength(state.x, state.y);
	}
}
