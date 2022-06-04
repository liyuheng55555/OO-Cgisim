package controller;

public class Algorithm {
    /**
     * Dijkstra�㷨������ʵ�֣�n^2��ʱ�临�Ӷ�������
     * @param cost  ���þ���
     * @param n     �����ٸ�λ��
     * @param beg   ��ʼλ��
     * @param end   ��ֹλ��
     * @return      ǰ������path
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
