package view;

import ai.AIFactory;
import ai.player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI extends JFrame {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 700;
    private player ai = null;
    private board board = null;
    private button button = null;
    private JMenuBar menuBar = null;
    private AIFactory factory = new AIFactory();

    public void setBoard(view.board board) {
        this.board = board;
    }

    public void setButton(view.button button) {
        this.button = button;
        this.button.register(board);
    }

    public void setAI(player ai){
        this.ai = ai;
        board.setAI(ai);
        button.setAI(ai);
    }

    public void setMenuBar(JMenuBar menuBar){
        this.menuBar = menuBar;
        JMenu algorithmMenu = new JMenu("算法选择");
        JMenuItem AlphaBetaDFS = new JMenuItem("AlphaBetaDFS");
        algorithmMenu.add(AlphaBetaDFS);
        AlphaBetaDFS.addActionListener(new AloSelectListener());

        this.menuBar.add(algorithmMenu);
    }

    public UI() {
        setTitle("五子棋游戏");
        setSize(WIDTH,HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBoard(new board());
        setButton(new button());
        setMenuBar(new JMenuBar());
        add(board,BorderLayout.CENTER);
        add(button, BorderLayout.EAST);
        setJMenuBar(menuBar);
        setVisible(true);
        setResizable(false);
    }

    class AloSelectListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JMenuItem item = (JMenuItem) e.getSource();
            String type = item.getText();
            System.out.println(type);
            setAI(factory.createAI(type));
        }
    }
}
