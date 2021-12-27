package tictactoe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Player {
    double learningRate = 0.2;
    double decayGamma = 0.7;
    double exploreRate = 0.4;
    String name;
    State state;
    HashMap<State, Double> stateValues = new HashMap<>();

    public Player(String name) {
        this.name = name;
    }

    public double feedReward(State currState, State nextState) {
        double currValue = stateValues.get(currState);
        double nextValue = stateValues.get(nextState);
        if(!stateValues.containsKey(nextState)) {
            nextValue = 0.5;
            stateValues.put(nextState, nextValue);
        }
        currValue = currValue + learningRate * (decayGamma*nextValue - currValue);
        return 0.0;
    }

    public void nextStep(ArrayList<int[]> positions, State state, int symbol) {
        double rand = Math.random();
        if(rand < 0.4) {
            int len = positions.size();
            int nextPos = (int)(Math.random() * len);
        } else {

        }
    }
}
