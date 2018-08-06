package com.zoli.cruciascore2.score;

public class ListViewItem {

    private String roundNumber, scoreP1, scoreP2, scoreP3, roundTimes;
    private int type;

    public ListViewItem(String round, String scoreP1, String scoreP2, String scoreP3, String roundTimes, int type) {
        this.roundNumber = round;
        this.scoreP1 = scoreP1;
        this.scoreP2 = scoreP2;
        this.scoreP3 = scoreP3;
        this.roundTimes = roundTimes;
        this.type = type;
    }

    public String getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(String round) {
        this.roundNumber = round;
    }

    public String getScoreP1() {
        return scoreP1;
    }

    public void setScoreP1(String scoreP1) {
        this.scoreP1 = scoreP1;
    }

    public String getScoreP2() {
        return scoreP2;
    }

    public void setScoreP2(String scoreP2) {
        this.scoreP2 = scoreP2;
    }

    public String getScoreP3() {
        return scoreP3;
    }

    public void setScoreP3(String scoreP3) {
        this.scoreP3 = scoreP3;
    }

    public String getRoundTimes() {
        return roundTimes;
    }

    public void setRoundTimes(String roundTimes) {
        this.roundTimes = roundTimes;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
