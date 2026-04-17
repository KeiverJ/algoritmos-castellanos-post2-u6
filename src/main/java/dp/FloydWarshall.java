package dp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FloydWarshall {
	public static final long INF = Long.MAX_VALUE / 2;

	/**
	 * All-pairs shortest paths.
	 * Tiempo: O(V^3). Espacio: O(V^2).
	 *
	 * @param w matriz de adyacencia (INF para ausencia de arista, 0 en diagonal)
	 * @return matriz de distancias minimas entre todos los pares
	 * @throws IllegalStateException si existe ciclo negativo alcanzable
	 */
	public static long[][] solve(long[][] w) {
		int v = w.length;
		long[][] dist = new long[v][v];
		for (int i = 0; i < v; i++) {
			dist[i] = w[i].clone();
		}

		for (int k = 0; k < v; k++) {
			for (int i = 0; i < v; i++) {
				for (int j = 0; j < v; j++) {
					if (dist[i][k] < INF && dist[k][j] < INF) {
						dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
					}
				}
			}
		}

		for (int i = 0; i < v; i++) {
			if (dist[i][i] < 0) {
				throw new IllegalStateException("Ciclo negativo detectado alcanzable desde el vertice " + i);
			}
		}

		return dist;
	}

	/**
	 * Verifica existencia de ciclos negativos alcanzables.
	 * Tiempo: O(V^3). Espacio: O(V^2).
	 *
	 * @param w matriz de adyacencia
	 * @return true si hay ciclo negativo; false en caso contrario
	 */
	public static boolean hasNegativeCycle(long[][] w) {
		try {
			solve(w);
			return false;
		} catch (IllegalStateException e) {
			return true;
		}
	}

	/**
	 * Reconstruye un camino minimo entre s y t usando matriz de sucesores.
	 * Tiempo: O(V^3) para precomputo + O(L) reconstruccion. Espacio: O(V^2).
	 *
	 * @param w matriz de adyacencia
	 * @param s vertice origen
	 * @param t vertice destino
	 * @return lista de vertices del camino minimo; lista vacia si no hay camino
	 * @throws IllegalStateException si existe ciclo negativo alcanzable
	 */
	public static List<Integer> reconstructPath(long[][] w, int s, int t) {
		int v = w.length;
		long[][] dist = new long[v][v];
		int[][] next = new int[v][v];

		for (int i = 0; i < v; i++) {
			for (int j = 0; j < v; j++) {
				dist[i][j] = w[i][j];
				next[i][j] = w[i][j] < INF ? j : -1;
			}
		}

		for (int k = 0; k < v; k++) {
			for (int i = 0; i < v; i++) {
				for (int j = 0; j < v; j++) {
					if (dist[i][k] < INF && dist[k][j] < INF) {
						long candidate = dist[i][k] + dist[k][j];
						if (candidate < dist[i][j]) {
							dist[i][j] = candidate;
							next[i][j] = next[i][k];
						}
					}
				}
			}
		}

		for (int i = 0; i < v; i++) {
			if (dist[i][i] < 0) {
				throw new IllegalStateException("Ciclo negativo detectado alcanzable desde el vertice " + i);
			}
		}

		if (next[s][t] == -1) {
			return Collections.emptyList();
		}

		List<Integer> path = new ArrayList<>();
		int current = s;
		path.add(current);
		while (current != t) {
			current = next[current][t];
			if (current == -1) {
				return Collections.emptyList();
			}
			path.add(current);
		}

		return path;
	}
}
