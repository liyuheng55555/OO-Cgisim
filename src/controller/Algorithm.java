package controller;

public class Algorithm {
    /**
     * Dijkstra算法的数组实现，n^2的时间复杂度用着先
     * @param cost  费用矩阵
     * @param n     共多少个位置
     * @param beg   起始位置
     * @param end   终止位置
     * @return      前驱数组path
     */
    static int[] dijkstra(int[][] cost, int n, int beg, int end) {
        final int inf = 0x3f3f3f3f;
        int[] path = new int[n];
        int[] vis = new int[n];
        int[] lowCost = new int[n];
        int i,j,min;
        for (i=0; i<n; i++) {
            vis[i] = 0;
            lowCost[i] = cost[beg][i];
            path[i] = beg;
        }
        vis[beg] = 1;
        lowCost[beg] = 0;
        path[beg] = -1;
        int pre = beg;
        for (i=1; i<n; i++) {
            for (j=0; j<n; j++) {
                if (vis[j]==0 && lowCost[pre]+cost[pre][j] < lowCost[j]) {
                    lowCost[j] = lowCost[pre]+cost[pre][j];
                    path[j] = pre;
                }
            }
            min = inf;
            for (j=0; j<n; j++) {
                if (vis[j]==0 && lowCost[j]<min) {
                    min = lowCost[j];
                    pre = j;
                }
            }
            vis[pre] = 1;
            if (pre==end)
                break;
        }
        return path;
    }
}
