package ece.aaa;

import ece.assign3.Action;
import ece.assign3.NeuralNet;

public class NNLearningAgent {
    private static final int NUM_INPUT_LAYERS = 5;
    private static final int NUM_HIDDEN_LAYERS = 10;
    private static final int NUM_OUTPUT_LAYERS = 1;
    private static final double NN_LEARNING_RATE = 0.2;
    private static final double NN_MOMENTUM = 0.9;
    private static final double NN_ALPHA = 0.1;
    private static final double NN_GAMMA = 0.9;
    private static final double NN_EXPLORATION_RATE = 0.99;
    private double[] NNPrevState = new double[5];
    private int NNPrevAction = 0;
    private int nextAction = 0;
    public NeuralNet[] nn;

    public NNLearningAgent(NeuralNet[] nn) {
        this.nn = nn;
    }

    public double NNLearn(double heading, double distance, double bearing, double isHitByBullet, double isHitWall, double reward) {
        double NNCurrStates[] = new double[5];
        NNCurrStates[0] = heading % 180;
        NNCurrStates[1] = distance % 10000;
        NNCurrStates[2] = bearing % Math.PI;
        NNCurrStates[3] = isHitByBullet;
        NNCurrStates[4] = isHitWall;
        nextAction = getMaxAction(NNCurrStates);

        double NN_NewVal = nn[nextAction].outputFor(NNCurrStates);
        double NN_PrevVal = nn[NNPrevAction].outputFor(NNPrevState);
        double error = NN_ALPHA * (reward + NN_GAMMA * NN_NewVal - NN_PrevVal);
        double NN_CorrectPrevVal = NN_PrevVal + error;
        nn[NNPrevAction].train(NNPrevState, NN_CorrectPrevVal);
        for(int i=0; i<NUM_INPUT_LAYERS; i++) {
            NNPrevState[i] = NNCurrStates[i];
        }
        NNPrevAction = nextAction;
        return error;
    }

    public int getNextAction(double heading, double distance, double bearing, double isHitByBullet, double isHitWall, double reward) {
        NNLearn(heading, distance, bearing, isHitByBullet, isHitWall, reward);
        if(Math.random() < NN_EXPLORATION_RATE) {
            return (int)(Math.random() * ece.cpen502.Action.ROBOT_NUM_ACTIONS);
        }
        return nextAction;
    }

    public int getMaxAction(double[] currState) {
        int nextAction = 0;
        for(int i = 0; i< Action.ROBOT_NUM_ACTIONS; i++) {
            if(nn[i].outputFor(currState) > nn[nextAction].outputFor(currState)) {
                nextAction = i;
            }
        }
        return nextAction;
    }

}
