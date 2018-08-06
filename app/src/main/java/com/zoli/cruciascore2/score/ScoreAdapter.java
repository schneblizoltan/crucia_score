package com.zoli.cruciascore2.score;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zoli.cruciascore2.R;

import java.util.ArrayList;

public class ScoreAdapter extends RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder> {

    private static final int TWO_PLAYER = 0;
    private ArrayList<ListViewItem> scoreList;
    private int mode;

    ScoreAdapter(ArrayList<ListViewItem> scoreList, int mode) {
        this.scoreList = scoreList;
        this.mode = mode;
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem;

        if(mode == TWO_PLAYER) {
            layoutIdForListItem = R.layout.two_player_row;
        } else {
            layoutIdForListItem = R.layout.three_player_row;
        }

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);

        return new ScoreViewHolder(view, scoreList);
    }

    @Override
    public void onBindViewHolder(ScoreViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        if(this.scoreList == null) {
            return 0;
        }
        return scoreList.size();
    }

    public void setScoreList(ArrayList<ListViewItem> scoreList) {
        this.scoreList = scoreList;
        notifyDataSetChanged();
    }

    class ScoreViewHolder extends RecyclerView.ViewHolder {

        private View itemView;
        private ArrayList<ListViewItem> scoreList;
        private TextView textViewRoundNumber, textViewPlayer1, textViewPlayer2, textViewPlayer3, textViewDoubleRound;

        ScoreViewHolder(View itemView, ArrayList<ListViewItem> scoreList) {
            super(itemView);
            this.itemView = itemView;
            this.scoreList = scoreList;

            textViewRoundNumber = (TextView) itemView.findViewById(R.id.round_number);
            textViewPlayer1 = (TextView) itemView.findViewById(R.id.score_player1);
            textViewPlayer2 = (TextView) itemView.findViewById(R.id.score_player2);
            textViewPlayer3 = null;
            textViewDoubleRound = (TextView) itemView.findViewById(R.id.double_round_indicator);
        }

        void bind(int position) {
            ListViewItem listViewItem = scoreList.get(position);

            if(listViewItem.getType() != ScoreAdapter.TWO_PLAYER) {
                textViewPlayer3 = (TextView) itemView.findViewById(R.id.score_player3);
                textViewPlayer3.setText(listViewItem.getScoreP3());
            }

            if(position == 0) {
                customizeFirstRow(textViewRoundNumber);
            }

            textViewRoundNumber.setText(listViewItem.getRoundNumber());
            textViewPlayer1.setText(listViewItem.getScoreP1());
            textViewPlayer2.setText(listViewItem.getScoreP2());
            textViewDoubleRound.setText(listViewItem.getRoundTimes());
        }

        private void customizeFirstRow(TextView roundNumber) {
            roundNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, itemView.getContext().getResources().getDimension(R.dimen.double_round_icon_size));
        }

    }

}
