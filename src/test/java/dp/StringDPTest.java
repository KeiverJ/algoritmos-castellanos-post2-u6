package dp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

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

    @Test
    void editDistanceWithEmptyLeftString() {
        String y = "PROGRAMACION";
        assertEquals(y.length(), StringDP.editDistance("", y));
    }

    @Test
    void editDistanceWithIdenticalStrings() {
        String s = "DINAMICA";
        assertEquals(0, StringDP.editDistance(s, s));
    }

    @Test
    void editDistanceKittenSittingIsThree() {
        assertEquals(3, StringDP.editDistance("kitten", "sitting"));
    }

    @Test
    void alignmentOpsAreConsistentWithEditDistance() {
        String x = "intention";
        String y = "execution";
        List<StringDP.Op> ops = StringDP.align(x, y);

        long edits = ops.stream()
                .filter(op -> op == StringDP.Op.SUBSTITUTE
                        || op == StringDP.Op.INSERT
                        || op == StringDP.Op.DELETE)
                .count();

        assertEquals(StringDP.editDistance(x, y), edits);
    }

    @Test
    void editDistanceMemOptMatchesFullDpForSeveralPairs() {
        String a1 = "ABCDEFGHIJKLMNOPQRST";
        String b1 = "UVWXYZABCDEFGHIJKLMN";
        String a2 = "dinamicprogrammingeditdistanceexample";
        String b2 = "dynamicprogrammingdistanceexamples";
        String a3 = "thequickbrownfoxjumpsoverthelazydog";
        String b3 = "thequackbrownboxjumpedoverthecrazyhog";
        String a4 = "aaaaaaaaaabbbbbbbbbbccccccccccdddddddddd";
        String b4 = "aaaabbbbccccddddeeeeffffgggghhhhiiii";
        String a5 = "algoritmosdeoptimizacionyprogramaciondinamica";
        String b5 = "algoritmosprogramaciondinamicaoptimizada";

        String[][] cases = {
                {a1, b1},
                {a2, b2},
                {a3, b3},
                {a4, b4},
                {a5, b5}
        };

        for (String[] pair : cases) {
            int full = StringDP.editDistance(pair[0], pair[1]);
            int opt = StringDP.editDistanceMemOpt(pair[0], pair[1]);
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
