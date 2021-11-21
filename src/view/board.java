package view;

import ai.AI;
import ai.player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class board extends JPanel implements Observer{
    player ai = null;
    static final int gap = 30;
    public board() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = round(e.getX());
                int y = round(e.getY());
                System.out.println("人：第" + y + "行,第" + x + "列");
                if(ai != null && !AI.isAITurn) {
                    if(x < AI.size && x >= 0 && y < AI.size && y >= 0 && AI.chessBoard[y][x] == 0) {
                        ai.putChess(x,y);
                        System.out.println("");
                        AI.isAITurn = true;
                        ai.start();//进入ai回合
                        repaint();
                    }
                }
                if(ai != null && AI.isFinished){
                    JOptionPane.showMessageDialog(null,"游戏结束");
                }
            }
        });
        setBackground(Color.CYAN);
    }

    private int round(int x) {
        return (x-gap/2) / gap;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for(int i = 1; i <= 15; i++){
            g.drawLine(gap,i*gap,gap*15,i*gap);
        }
        for (int i = 1; i <= 15; i++) {
            g.drawLine(i*gap,gap,i*gap,gap*15);
        }

        for(int i = 0; i < 15; i++){
            for(int j = 0; j < 15; j++){
                if(AI.isBlack){
                    if(AI.chessBoard[j][i] == -1){
                        g.setColor(Color.BLACK);
                        g.fillOval((i+1)*gap-gap/2,(j+1)*gap-gap/2,gap,gap);
                    }else if(AI.chessBoard[j][i] == 1){
                        g.setColor(Color.WHITE);
                        g.fillOval((i+1)*gap-gap/2,(j+1)*gap-gap/2,gap,gap);
                    }
                }else{
                    if(AI.chessBoard[j][i] == 1){
                        g.setColor(Color.BLACK);
                        g.fillOval((i+1)*gap-gap/2,(j+1)*gap-gap/2,gap,gap);
                    }else if(AI.chessBoard[j][i] == -1){
                        g.setColor(Color.WHITE);
                        g.fillOval((i+1)*gap-gap/2,(j+1)*gap-gap/2,gap,gap);
                    }
                }
            }
        }
    }

    @Override
    public void update(String state) {
        if ("restart".equals(state)) {
            ai.restart();
        }else if("retract".equals(state)){
            ai.undo();
        }
        repaint();
    }

    public void setAI(player ai){
        this.ai = ai;
    }
}
