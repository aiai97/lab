package algorithms.dp;

public class SpellChecker {
    public static void main(String[] args) {
        String userInput = "iphne";
        String[] dictionary = {"iphone", "samsung", "pixel", "xiaomi"};

        String bestMatch = "";
        int minDistance = Integer.MAX_VALUE;

        for (String word : dictionary) {
            int dist = editDistance(userInput, word);
            if (dist < minDistance) {
                minDistance = dist;
                bestMatch = word;
            }
        }

        System.out.println("你想输入的是不是：" + bestMatch + "（编辑距离：" + minDistance + "）");
    }

    public static int editDistance(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) dp[i][0] = i;
        for (int j = 0; j <= n; j++) dp[0][j] = j;

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(
                            dp[i - 1][j - 1],
                            Math.min(dp[i - 1][j], dp[i][j - 1])
                    );
                }
            }
        }

        return dp[m][n];
    }
}
