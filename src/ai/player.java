package ai;

public interface player {
    void start();
    void putChess(int x, int y);
    void undo();
    void restart();
}
