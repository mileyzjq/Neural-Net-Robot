package tictactoe;

import tictactoe.Player;

import java.util.ArrayList;

public class State {
    int N = 3;
    Player p1, p2;
    int[][] board;
    int symbol = 1;
    boolean isEnd;

    public State(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.board = new int[N][N];
    }

    public int winner() {
        for(int i=0; i<N; i++) {
            int sum1 = board[i][0] + board[i][1] + board[i][2];
            int sum2 = board[0][i] + board[1][i] + board[2][i];
            if(sum1 == 3 || sum2 == 3) {
                isEnd = true;
                return 1;
            }
            if(sum1 == -3 || sum2 == -3) {
                isEnd = true;
                return -1;
            }
        }
        int diag_sum1 = board[0][0] + board[1][1] + board[2][2];
        int diag_sum2 = board[2][0] + board[1][1] + board[0][2];
        if(diag_sum1 == 3 || diag_sum2 == 3) {
            isEnd = true;
            return 1;
        }
        if(diag_sum1 == -3 || diag_sum2 == -3) {
            isEnd = true;
            return -1;
        }
        return 0;
    }

    public ArrayList<int[]> getAvailablePositions() {
        ArrayList<int[]> positions = new ArrayList<>();
        for(int i=0; i<N; i++) {
            for(int j=0; j<N; j++) {
                if(board[i][j] == 0) {
                    positions.add(new int[]{i, j});
                }
            }
        }
        return positions;
    }

    public void updateState(int x, int y) {
        board[x][y] = this.symbol;
        this.symbol *= -1;
    }


}
