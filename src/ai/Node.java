package ai;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Node {
    public int mark;
    public Point pos;
    public List<Node> children = new ArrayList<>();
    public Node bestChild = null;

    public Node() {
        mark = 0;
    }

    public Node(Point pos) {
        mark = 0;
        this.pos = pos;
    }

    public Node(int x, int y) {
        this.pos = new Point(x,y);
    }

    public void setPos(int x, int y){
        this.pos.x = x;
        this.pos.y = y;
    }

    public void addChild(Node node){
        children.add(node);
    }

    public Node getLastChild(){
        return children.get(children.size()-1);
    }

    public void setPoint(Point now) {
        this.pos = now;
    }
}
