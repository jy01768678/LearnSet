package com.lorin.algorithm.leetcode;

/**
 * Created by lorin on 2018/4/30.
 */
public class RegularMatching {

    /**
     * @param s: A string
     * @param p: A string includes "." and "*"
     *
     * @return: A boolean
     */
    public static boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }

        boolean[][] memo = new boolean[s.length()][p.length()];
        boolean[][] visited = new boolean[s.length()][p.length()];

        return isMatchHelper(s, 0, p, 0, memo, visited);
    }

    private static boolean isMatchHelper(String s, int sIndex,
                                         String p, int pIndex,
                                         boolean[][] memo,
                                         boolean[][] visited) {
        // "" == ""
        if (pIndex == p.length()) {
            return sIndex == s.length();
        }

        if (sIndex == s.length()) {
            return isEmpty(p, pIndex);
        }

        if (visited[sIndex][pIndex]) {
            return memo[sIndex][pIndex];
        }

        char sChar = s.charAt(sIndex);
        char pChar = p.charAt(pIndex);
        boolean match;

        // consider a* as a bundle
        if (pIndex + 1 < p.length() && p.charAt(pIndex + 1) == '*') {
            match = isMatchHelper(s, sIndex, p, pIndex + 2, memo, visited) ||
                    charMatch(sChar, pChar) && isMatchHelper(s, sIndex + 1, p, pIndex, memo, visited);
        } else {
            match = charMatch(sChar, pChar) &&
                    isMatchHelper(s, sIndex + 1, p, pIndex + 1, memo, visited);
        }

        visited[sIndex][pIndex] = true;
        memo[sIndex][pIndex] = match;
        return match;
    }

    private static boolean charMatch(char sChar, char pChar) {
        return sChar == pChar || pChar == '.';
    }

    private static boolean isEmpty(String p, int pIndex) {
        for (int i = pIndex; i < p.length(); i += 2) {
            if (i + 1 >= p.length() || p.charAt(i + 1) != '*') {
                return false;
            }
        }
        return true;
    }

    public static boolean isMatch2(String s, String p) {
        char[] ss = s.toCharArray();
        char[] pp = p.toCharArray();
        boolean[][] match = new boolean[s.length() + 1][p.length() + 1];

        for (int i = 0; i <= s.length(); i++) {
            for (int j = 0; j <= p.length(); j++) {
                if (i == 0 && j == 0) {
                    match[i][j] = true;
                    continue;
                }

                if (j == 0) {
                    match[i][j] = false;
                    continue;
                }

                match[i][j] = false;
                if (pp[j - 1] != '*') {
                    if (i > 0 && (ss[i - 1] == pp[j - 1] || pp[j - 1] == '.')) {
                        match[i][j] |= match[i - 1][j - 1];
                    }
                } else {
                    if (j > 1) {
                        match[i][j] |= match[i][j - 2];
                    }

                    if (i > 0 && (pp[j - 2] == '.' || ss[i - 1] == pp[j - 2])) {
                        match[i][j] |= match[i - 1][j];
                    }
                }
            }
        }

        return match[s.length()][p.length()];
    }

    public static boolean isMatch4(String s, String p) {
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;
        for (int i = 1; i <= p.length(); i++) {
            //need to do i-2 >= 0 check because in Lintcode, it has testcase which provides invalid p string, like "*a.*", no character before *
            if (p.charAt(i - 1) == '*' && i - 2 >= 0 && dp[0][i - 2]) {
                dp[0][i] = true;
            }
        }
        printArray(dp);
        for (int i = 1; i <= s.length(); i++) {
            char sc = s.charAt(i - 1);
            for (int j = 1; j <= p.length(); j++) {
                char pc = p.charAt(j - 1);
                if (pc == '*' && j - 2 >= 0) {
                    if (p.charAt(j - 2) != '.' && p.charAt(j - 2) != s.charAt(i - 1)) {
                        //take x* as 0 match
                        dp[i][j] = dp[i][j - 2];
                    } else {
                        //3 different case, 0 match, 1 match, multiple match
                        dp[i][j] = dp[i - 1][j] || dp[i][j - 2] || dp[i - 1][j - 2]; //dp[i][j-1] is the same

                    }
                } else if (sc == pc || pc == '.') {
                    dp[i][j] = dp[i - 1][j - 1];
                }
            }

        }
        return dp[s.length()][p.length()];
    }

    /**
     * 为了记忆之前对比的结果
     * @param s
     * @param p
     * @return
     */
    public static boolean isMatch3(String s, String p) {

        if (s == null || p == null) {
            return false;
        }
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;
        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) == '*' && dp[0][i-1]) {
                dp[0][i+1] = true;
            }
        }
        printArray(dp);
        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < p.length(); j++) {
                if (p.charAt(j) == '.' || p.charAt(j) == s.charAt(i)) {
                    dp[i + 1][j + 1] = dp[i][j];
                }
                if (p.charAt(j) == '*') {
                    if (p.charAt(j - 1) != s.charAt(i) && p.charAt(j - 1) != '.') {
                        dp[i + 1][j + 1] = dp[i + 1][j - 1];
                    } else {
                        dp[i + 1][j + 1] = (dp[i + 1][j] || dp[i][j + 1] || dp[i + 1][j - 1]);
                    }
                }
            }
        }
        printArray(dp);
        return dp[s.length()][p.length()];
    }

    public static void printArray(boolean[][] V) {
        System.out.println("printArray:");
        int rows = V.length;
        int cols = V[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(V[i][j] + " ");
            }
            System.out.println("");

        }
        System.out.println("");
    }

    public static void main(String[] args) {

        String s = "aab";
        String p = "c*a*b";

        System.out.println(isMatch3(s, p));
    }
}
