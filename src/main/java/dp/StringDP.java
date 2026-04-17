package dp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StringDP {
	public enum Op { MATCH, SUBSTITUTE, INSERT, DELETE }

	/**
	 * LCS completo.
	 * Tiempo: O(n*m). Espacio: O(n*m).
	 *
	 * @param X primera cadena
	 * @param Y segunda cadena
	 * @return longitud de la subsecuencia comun mas larga
	 */
	public static int lcsLength(String X, String Y) {
		int m = X.length();
		int n = Y.length();
		int[][] dp = new int[m + 1][n + 1];
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				dp[i][j] = X.charAt(i - 1) == Y.charAt(j - 1)
						? dp[i - 1][j - 1] + 1
						: Math.max(dp[i - 1][j], dp[i][j - 1]);
			}
		}
		return dp[m][n];
	}

	/**
	 * Reconstruye la cadena LCS por backtracking.
	 * Tiempo: O(n*m). Espacio: O(n*m).
	 *
	 * @param X primera cadena
	 * @param Y segunda cadena
	 * @return una LCS valida entre X y Y
	 */
	public static String lcsString(String X, String Y) {
		int m = X.length();
		int n = Y.length();
		int[][] dp = new int[m + 1][n + 1];
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				dp[i][j] = X.charAt(i - 1) == Y.charAt(j - 1)
						? dp[i - 1][j - 1] + 1
						: Math.max(dp[i - 1][j], dp[i][j - 1]);
			}
		}

		StringBuilder sb = new StringBuilder();
		int i = m;
		int j = n;
		while (i > 0 && j > 0) {
			if (X.charAt(i - 1) == Y.charAt(j - 1)) {
				sb.append(X.charAt(i - 1));
				i--;
				j--;
			} else if (dp[i - 1][j] > dp[i][j - 1]) {
				i--;
			} else {
				j--;
			}
		}
		return sb.reverse().toString();
	}

	/**
	 * LCS en memoria optimizada con dos filas rotatorias.
	 * Tiempo: O(n*m). Espacio: O(min(n,m)).
	 *
	 * @param X primera cadena
	 * @param Y segunda cadena
	 * @return longitud de la LCS
	 */
	public static int lcsMemOpt(String X, String Y) {
		if (X.length() < Y.length()) {
			String tmp = X;
			X = Y;
			Y = tmp;
		}
		int m = X.length();
		int n = Y.length();
		int[] prev = new int[n + 1];
		int[] curr = new int[n + 1];
		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				curr[j] = X.charAt(i - 1) == Y.charAt(j - 1)
						? prev[j - 1] + 1
						: Math.max(prev[j], curr[j - 1]);
			}
			int[] tmp = prev;
			prev = curr;
			curr = tmp;
			Arrays.fill(curr, 0);
		}
		return prev[n];
	}

	/**
	 * Edit Distance clasico (Levenshtein).
	 * Tiempo: O(n*m). Espacio: O(n*m).
	 *
	 * @param X cadena origen
	 * @param Y cadena destino
	 * @return costo minimo para transformar X en Y
	 */
	public static int editDistance(String X, String Y) {
		int m = X.length();
		int n = Y.length();
		int[][] dp = new int[m + 1][n + 1];
		for (int i = 0; i <= m; i++) {
			dp[i][0] = i;
		}
		for (int j = 0; j <= n; j++) {
			dp[0][j] = j;
		}

		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (X.charAt(i - 1) == Y.charAt(j - 1)) {
					dp[i][j] = dp[i - 1][j - 1];
				} else {
					dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
				}
			}
		}
		return dp[m][n];
	}

	/**
	 * Reconstruye la secuencia de operaciones de un alineamiento optimo.
	 * Tiempo: O(n*m). Espacio: O(n*m).
	 *
	 * @param X cadena origen
	 * @param Y cadena destino
	 * @return lista de operaciones en orden de aplicacion
	 */
	public static List<Op> align(String X, String Y) {
		int m = X.length();
		int n = Y.length();
		int[][] dp = new int[m + 1][n + 1];
		for (int i = 0; i <= m; i++) {
			dp[i][0] = i;
		}
		for (int j = 0; j <= n; j++) {
			dp[0][j] = j;
		}

		for (int i = 1; i <= m; i++) {
			for (int j = 1; j <= n; j++) {
				if (X.charAt(i - 1) == Y.charAt(j - 1)) {
					dp[i][j] = dp[i - 1][j - 1];
				} else {
					dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
				}
			}
		}

		List<Op> ops = new ArrayList<>();
		int i = m;
		int j = n;
		while (i > 0 || j > 0) {
			if (i > 0 && j > 0 && X.charAt(i - 1) == Y.charAt(j - 1)) {
				ops.add(Op.MATCH);
				i--;
				j--;
			} else if (i > 0 && j > 0 && dp[i][j] == dp[i - 1][j - 1] + 1) {
				ops.add(Op.SUBSTITUTE);
				i--;
				j--;
			} else if (i > 0 && dp[i][j] == dp[i - 1][j] + 1) {
				ops.add(Op.DELETE);
				i--;
			} else {
				ops.add(Op.INSERT);
				j--;
			}
		}

		Collections.reverse(ops);
		return ops;
	}

	/**
	 * Edit Distance con memoria optimizada.
	 * Tiempo: O(n*m). Espacio: O(min(n,m)).
	 *
	 * @param X cadena origen
	 * @param Y cadena destino
	 * @return costo minimo de transformacion
	 */
	public static int editDistanceMemOpt(String X, String Y) {
		if (X.length() < Y.length()) {
			String tmp = X;
			X = Y;
			Y = tmp;
		}

		int m = X.length();
		int n = Y.length();
		int[] prev = new int[n + 1];
		int[] curr = new int[n + 1];

		for (int j = 0; j <= n; j++) {
			prev[j] = j;
		}

		for (int i = 1; i <= m; i++) {
			curr[0] = i;
			for (int j = 1; j <= n; j++) {
				if (X.charAt(i - 1) == Y.charAt(j - 1)) {
					curr[j] = prev[j - 1];
				} else {
					curr[j] = 1 + Math.min(prev[j - 1], Math.min(prev[j], curr[j - 1]));
				}
			}
			int[] tmp = prev;
			prev = curr;
			curr = tmp;
		}

		return prev[n];
	}
}
