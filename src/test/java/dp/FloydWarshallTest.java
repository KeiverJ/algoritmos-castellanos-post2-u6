package dp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class FloydWarshallTest {
    @Test
    void knownGraphDistancesShouldMatchExpectedValues() {
        long I = FloydWarshall.INF;
        long[][] w = {
                {0, 3, I, 7},
                {8, 0, 2, I},
                {5, I, 0, 1},
                {2, I, I, 0}
        };

        long[][] dist = FloydWarshall.solve(w);

        long[][] expected = {
                {0, 3, 5, 6},
                {5, 0, 2, 3},
                {3, 6, 0, 1},
                {2, 5, 7, 0}
        };

        for (int i = 0; i < expected.length; i++) {
            for (int j = 0; j < expected.length; j++) {
                assertEquals(expected[i][j], dist[i][j],
                        "Distancia incorrecta en (" + i + "," + j + ")");
            }
        }
    }

    @Test
    void graphWithNegativeCycleShouldThrowException() {
        long I = FloydWarshall.INF;
        long[][] w = {
                {0, 1, I},
                {I, 0, -1},
                {-1, I, 0}
        };

        assertThrows(IllegalStateException.class, () -> FloydWarshall.solve(w));
        assertTrue(FloydWarshall.hasNegativeCycle(w));
    }

    @Test
    void disconnectedVerticesShouldRemainInf() {
        long I = FloydWarshall.INF;
        long[][] w = {
                {0, 2, I},
                {I, 0, I},
                {I, I, 0}
        };

        long[][] dist = FloydWarshall.solve(w);
        assertEquals(I, dist[1][2]);
        assertEquals(I, dist[2][0]);
    }

    @Test
    void reconstructedPathShouldBeValidAndMatchShortestDistance() {
        long I = FloydWarshall.INF;
        long[][] w = {
                {0, 3, I, 7},
                {8, 0, 2, I},
                {5, I, 0, 1},
                {2, I, I, 0}
        };

        long[][] dist = FloydWarshall.solve(w);
        List<Integer> path = FloydWarshall.reconstructPath(w, 0, 3);

        assertIterableEquals(List.of(0, 1, 2, 3), path);
        assertTrue(isValidPath(path, w));
        assertEquals(dist[0][3], pathCost(path, w));
    }

    private static boolean isValidPath(List<Integer> path, long[][] w) {
        if (path.isEmpty()) {
            return false;
        }
        for (int i = 0; i < path.size() - 1; i++) {
            int u = path.get(i);
            int v = path.get(i + 1);
            if (w[u][v] >= FloydWarshall.INF) {
                return false;
            }
        }
        return true;
    }

    private static long pathCost(List<Integer> path, long[][] w) {
        long total = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            total += w[path.get(i)][path.get(i + 1)];
        }
        return total;
    }
}
