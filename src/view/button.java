package view;

import ai.AI;
import ai.player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class button extends JPanel implements Observable {
    player ai = null;
    List<Observer> observerList = new ArrayList<>();

    public JButton restart = new JButton("重新开始");
    public JButton retract = new JButton("悔棋");
    public JButton second = new JButton("玩家后手");

    public button() {
        setLayout(new GridLayout(20,1,0,5));
        setBackground(Color.pink);
        add(restart);
        add(retract);
        add(second);
        restart.addMouseListener(new restartListener());
        retract.addMouseListener(new retractListener());
        second.addMouseListener(new secondListener());
    }

    @Override
    public void Notify(String state) {
        for(Observer o : observerList){
            o.update(state);
        }
    }

    @Override
    public void register(Observer observer) {
        observerList.add(observer);
    }


    class restartListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            Notify("restart");
        }
    }

    class retractListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
            Notify("retract");
        }
    }

    class secondListener extends MouseAdapter{
        @Override
        public void mouseClicked(MouseEvent e) {
                AI.isAITurn = true;
                AI.isBlack = true;
                ai.start();
                Notify("second");
        }
    }

    public void setAI(player ai){
        this.ai = ai;
    }
}
