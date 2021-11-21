package ai;

public class AIFactory {
    public player createAI(String type){
        if("AlphaBetaDFS".equals(type)){
            return new AlphaBetaDFS();
        }

        return null;
    }
}
