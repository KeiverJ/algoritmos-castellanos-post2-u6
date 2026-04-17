package dp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class StringDPTest {
    @Test
    void lcsWithEmptyStrings() {
        assertEquals(0, StringDP.lcsLength("", ""));
        assertEquals("", StringDP.lcsString("", ""));
        assertEquals(0, StringDP.lcsMemOpt("", ""));
    }

    @Test
    void lcsWithIdenticalStrings() {
        String s = "ALGORITMO";
        assertEquals(s.length(), StringDP.lcsLength(s, s));
        assertEquals(s, StringDP.lcsString(s, s));
        assertEquals(s.length(), StringDP.lcsMemOpt(s, s));
    }

    @Test
    void lcsWithNoCommonCharacters() {
        String x = "ABC";
        String y = "xyz";
        assertEquals(0, StringDP.lcsLength(x, y));
        assertEquals("", StringDP.lcsString(x, y));
        assertEquals(0, StringDP.lcsMemOpt(x, y));
    }

    @Test
    void lcsStringIsValidSubsequenceOfBoth() {
        String x = "ABCBDAB";
        String y = "BDCABA";
        String lcs = StringDP.lcsString(x, y);

        assertTrue(isSubsequence(lcs, x));
        assertTrue(isSubsequence(lcs, y));
        assertEquals(StringDP.lcsLength(x, y), lcs.length());
    }

    @Test
    void lcsMemOptMatchesFullDpForSeveralPairs() {
        String[][] cases = {
                {"ABCDEFGHIJ", "AEIMQUY"},
                {"XMJYAUZQWERT", "MZJAWXUERT"},
                {"DYNAMICPROGRAMMING", "ALGORITHMPROGRAM"},
                {"ABCDEFGHIJKLMNO", "NOPQRSTUVWXYZABCDE"},
                {"ABRACADABRAABRACADABRA", "BANANABREADBARACA"}
        };

        for (String[] pair : cases) {
            int full = StringDP.lcsLength(pair[0], pair[1]);
            int opt = StringDP.lcsMemOpt(pair[0], pair[1]);
            assertEquals(full, opt,
                    () -> "Fallo con par: [" + pair[0] + ", " + pair[1] + "]");
        }
    }

    private static boolean isSubsequence(String subseq, String source) {
        int i = 0;
        int j = 0;
        while (i < subseq.length() && j < source.length()) {
            if (subseq.charAt(i) == source.charAt(j)) {
                i++;
            }
            j++;
        }
        return i == subseq.length();
    }
}
