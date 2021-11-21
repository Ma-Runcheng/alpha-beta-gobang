package ai;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class AI implements player{
    public static boolean isBlack = false;  //true---ai先手， false---玩家先手(默认)
    public static int size = 15;
    public static int[][] chessBoard = new int[size][size]; //ai--- -1，玩家----1
    public static boolean isAITurn = false;//true---ai回合， false---玩家回合(默认)
    protected static int searchDeep = 4;
    protected static Set<Point> toJudge = new HashSet<>(); // ai可能会下棋的点
    private static final int[] dr = new int[]{-1,1,-1,1,0,0,-1,1}; // 方向向量
    private static final int[] dc = new int[]{1,-1,-1,1,-1,1,0,0}; //方向向量
    protected static final int MAX = Integer.MAX_VALUE;
    protected static final int MIN = Integer.MIN_VALUE;
    public static boolean isFinished = false;
    public static List<Point> history = new ArrayList<>();
    int c = 0;

    public AI() {
        initChessBoard();
        toJudge.clear();
        isAITurn = false;
    }

    abstract void DFS(int deep, Node node, int alpha, int beta, int[][] board);

    //ai博弈入口
    public void start(){
        //0.如果isBlack为true，则ai先手
        if(isBlack && c == 0){
            System.out.println("ai先手");
            putChess(7,7);
            c++;
            isAITurn = false;
        }else{
            //1.设置isAITurn为false，进入ai回合
            isAITurn = true;
            //2.分析棋局dfs获得最高得分位置Point
            Node node = new Node();
            int[][] board  = new int[size][size];
            for (int i = 0; i < size; i++) {
                System.arraycopy(chessBoard[i], 0, board[i], 0, size);
            }
            DFS(0,node,MIN,MAX,board);
            //3.ai下棋，putChess(下棋并检查是否结束)
            Point bestPos = node.bestChild.pos;
            System.out.println("ai位置：第"+bestPos.x+"行"+",第"+bestPos.y+"列");
            putChess(bestPos.x, bestPos.y);
            toJudge.remove(new Point(bestPos.x,bestPos.y));
            //4.ai回合结束，isAITurn为真，返回玩家回合
            isAITurn = false;
        }
    }

    public void putChess(int x, int y) {
        if(!isAITurn){
            //人
            chessBoard[y][x] = 1;
            toJudge.remove(new Point(x,y));
            for (int i = 0; i < 8; i++) {
                Point next = new Point(x+dc[i], y+dr[i]);
                if(next.x >= 0 && next.x <size && next.y >= 0 && next.y < size && chessBoard[next.y][next.x] == 0){
                    toJudge.add(next);
                }
            }
        }else{
            //AI
            chessBoard[y][x] = -1;
        }
        history.add(new Point(x,y));
        if(isEnd(x,y,chessBoard)){
            isFinished = true;
            System.out.println("游戏结束");
        }
    }

    protected static boolean isEnd(int x, int y,int[][] board) {
        // 判断一行是否五子连珠
        int cnt=1;
        int col=x,row=y;
        while(--col >= 0 && board[row][col] == board[y][x]) ++cnt;
        col = x; row = y;
        while(++col < size && board[row][col] == board[y][x]) ++cnt;
        if(cnt >= 5){
            return true;
        }
        // 判断一列是否五子连珠
        col = x; row = y;
        cnt = 1;
        while(--row >= 0 && board[row][col] == board[y][x]) ++cnt;
        col=x;row=y;
        while(++row < size && board[row][col] == board[y][x]) ++cnt;
        if(cnt>=5){
            return true;
        }
        // 判断左对角线是否五子连珠
        col=x;row=y;
        cnt=1;
        while(--col>=0 && --row>=0 && board[row][col]==board[y][x]) ++cnt;
        col=x;row=y;
        while(++col<size && ++row<size && board[row][col]==board[y][x]) ++cnt;
        if(cnt>=5) return cnt >= 5;
        // 判断右对角线是否五子连珠
        col=x;row=y;
        cnt=1;
        while(++row<size && --col>=0 && board[row][col]==board[y][x]) ++cnt;
        col=x;row=y;
        while(--row>=0 && ++col<size && board[row][col]==board[y][x]) ++cnt;
        return cnt >= 5;
    }

    public void restart(){
        initChessBoard();
        history.clear();
        isFinished = false;
        toJudge.clear();
    }

    public void undo(){
        System.out.println("悔棋");
        if(history.size() > 0){
            Point p1 = history.remove(history.size()-1);
            Point p2 = history.remove(history.size()-1);
            toJudge.remove(p1);
            toJudge.remove(p2);
            chessBoard[p1.y][p1.x] = 0;
            chessBoard[p2.y][p2.x] = 0;
            isFinished = false;
        }
    }

    private void initChessBoard() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                chessBoard[i][j] = 0;
            }
        }
    }

}
