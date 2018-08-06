package com.zoli.cruciascore2.score;

import android.widget.TextView;

public class ListViewHolder {

    TextView roundNumber, scoreP1, scoreP2, scoreP3, roundTimes;

    public ListViewHolder(TextView roundNumber, TextView scoreP1, TextView scoreP2, TextView scoreP3, TextView roundTimes) {
        this.roundNumber = roundNumber;
        this.scoreP1 = scoreP1;
        this.scoreP2 = scoreP2;
        this.scoreP3 = scoreP3;
        this.roundTimes = roundTimes;
    }

    public TextView getRoundNumber() {
        return roundNumber;
    }

    public void setRoundNumber(TextView roundNumber) {
        this.roundNumber = roundNumber;
    }

    public TextView getScoreP1() {
        return scoreP1;
    }

    public void setScoreP1(TextView scoreP1) {
        this.scoreP1 = scoreP1;
    }

    public TextView getScoreP2() {
        return scoreP2;
    }

    public void setScoreP2(TextView scoreP2) {
        this.scoreP2 = scoreP2;
    }

    public TextView getScoreP3() {
        return scoreP3;
    }

    public void setScoreP3(TextView scoreP3) {
        this.scoreP3 = scoreP3;
    }

    public TextView getRoundTimes() {
        return roundTimes;
    }

    public void setRoundTimes(TextView roundTimes) {
        this.roundTimes = roundTimes;
    }
}
