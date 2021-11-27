package ai;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AlphaBetaDFS extends AI{

    private static final int[] dr = new int[]{-1,1,-1,1,0,0,-1,1}; // 方向向量
    private static final int[] dc = new int[]{1,-1,-1,1,-1,1,0,0}; //方向向量

    @Override
    void DFS(int deep, Node root, int alpha, int beta, int[][] board){
        if(deep == searchDeep){
            root.mark = getMark(board);
            return ;
        }
        List<Point> judgeList = new ArrayList<>(toJudge);
        for(Point p : judgeList){
            Node node = new Node(p);
            root.addChild(node);
            board[p.y][p.x] = ((deep&1)==0) ? -1 : 1;
            if(AI.isEnd(p.x, p.y, board)){
                root.bestChild = node;
                root.mark = MAX * (-board[p.y][p.x]);
                board[p.y][p.x] = 0;
                return ;
            }
            boolean[] flags = new boolean[8];
            Arrays.fill(flags, true);
            for (int i = 0; i < 8; i++) {
                Point next = new Point(p.x+dc[i],p.y+dr[i]);
                if(0 <= next.x && next.x < size && 0 <= next.y && next.y < size
                        && board[next.y][next.x] == 0){
                    if(!toJudge.contains(next)){
                        toJudge.add(next);
                    } else flags[i] = false;
                }
            }
            toJudge.remove(p);
            DFS(deep+1,root.getLastChild(),alpha,beta,board);
            board[p.y][p.x] = 0;
            toJudge.add(p);
            for(int i = 0; i < 8; i++)
                if(flags[i])
                    toJudge.remove(new Point(p.x+dc[i],p.y+dr[i]));
            // alpha beta剪枝
            // min层  deep为奇数
            if((deep&1)==1){
                if(root.bestChild == null || root.getLastChild().mark < root.bestChild.mark){
                    root.bestChild = root.getLastChild();
                    root.mark = root.bestChild.mark;
                    if(root.mark <= MIN)
                        root.mark += deep;
                    beta=Math.min(root.mark,beta);
                }
                if(root.mark <= alpha)
                    return;
            }
            // max层  deep为偶数
            else{
                if(root.bestChild == null || root.getLastChild().mark > root.bestChild.mark){
                    root.bestChild = root.getLastChild();
                    root.mark = root.bestChild.mark;
                    if(root.mark == MAX)
                        root.mark -= deep;
                    alpha = Math.max(root.mark,alpha);
                }
                if(root.mark >= beta)
                    return;
            }
        }
    }


    private static int getMark(int[][] board) {
        int res = 0;
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(board[i][j] != 0){
                    // 行
                    boolean flag1 = false, flag2 = false;
                    int x = j, y = i;
                    int cnt = 1;
                    int col = x, row = y;
                    while(--col >= 0 && board[row][col] == board[i][j]) cnt++;
                    if(col >= 0 && board[row][col] == 0) flag1 = true;
                    col = x; row = y;
                    while(++col < size && board[row][col]==board[i][j]) cnt++;
                    if(col < size && board[row][col] == 0) flag2 = true;

                    if(flag1 && flag2)
                        res += (-board[i][j]) * cnt * cnt;
                    else if(flag1 || flag2) res += (-board[i][j]) * cnt * cnt / 4;
                    if(cnt >= 5) res = MAX * (-board[i][j]);
                    // 列
                    col=x;row=y;
                    cnt=1;flag1=false;flag2=false;
                    while(--row>=0 && board[row][col]==board[y][x]) ++cnt;
                    if(row>=0 && board[row][col]==0) flag1=true;
                    col=x;row=y;
                    while(++row<size && board[row][col]==board[y][x]) ++cnt;
                    if(row<size && board[row][col]==0) flag2=true;

                    if(flag1 && flag2)
                        res += (-board[i][j]) * cnt * cnt;
                    else if(flag1 || flag2)
                        res+=(-board[i][j])*cnt*cnt/4;
                    if(cnt>=5) res=MAX*(-board[i][j]);
                    // 左对角线
                    col=x;row=y;
                    cnt=1;flag1=false;flag2=false;
                    while(--col>=0 && --row>0 && board[row][col]==board[y][x]) ++cnt;
                    if(col>=0 && row>=0 && board[row][col]==0) flag1=true;
                    col=x;row=y;
                    while(++col<size && ++row<size && board[row][col]==board[y][x]) ++cnt;
                    if(col<size && row<size && board[row][col]==0) flag2=true;

                    if(flag1 && flag2)
                        res+=(-board[i][j])*cnt*cnt;
                    else if(flag1 || flag2) res+=(-board[i][j])*cnt*cnt/4;
                    if(cnt>=5) res=MAX*(-board[i][j]);
                    // 右对角线
                    col=x;row=y;
                    cnt=1;flag1=false;flag2=false;
                    while(++row<size && --col>=0 && board[row][col]==board[y][x]) ++cnt;
                    if(row<size && col>=0 && board[row][col]==0) flag1=true;
                    col=x;row=y;
                    while(--row>=0 && ++col<size && board[row][col]==board[y][x]) ++cnt;
                    if(row>=0 && col<size && board[i][j]==0) flag2=true;

                    if(flag1 && flag2)
                        res+=(-board[i][j])*cnt*cnt;
                    else if(flag1 || flag2) res+=(-board[i][j])*cnt*cnt/4;
                    if(cnt>=5) res=MAX*(-board[i][j]);

                }
            }
        }
        return res;
    }
}
