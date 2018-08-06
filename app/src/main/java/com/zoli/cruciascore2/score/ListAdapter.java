package com.zoli.cruciascore2.score;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.zoli.cruciascore2.R;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class ListAdapter extends ArrayAdapter {

    private static final int TWO_PLAYER = 0;
    private Context context;

    private ArrayList<ListViewItem> objects;

    ListAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.objects = (ArrayList) objects;
        this.context = context;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        return objects.get(position).getType();
    }

    @NonNull
    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ListViewHolder viewHolder;
        ListViewItem listViewItem = objects.get(position);
        int listViewItemType = getItemViewType(position);


        if (convertView == null) {
            if (listViewItemType == TWO_PLAYER) {
                Log.d("ListViewItemType", "TWO_PLAYER");
                //Log.d("ListViewItemType", "Position: " + position);
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.two_player_row, null);

                TextView textViewRoundNumber = (TextView) convertView.findViewById(R.id.round_number);
                TextView textViewPlayer1 = (TextView) convertView.findViewById(R.id.score_player1);
                TextView textViewPlayer2 = (TextView) convertView.findViewById(R.id.score_player2);
                TextView textViewDoubleRound = (TextView) convertView.findViewById(R.id.double_round_indicator);

                if (position == 0) {
                    customizeFirstRow(textViewRoundNumber, textViewPlayer1, textViewPlayer2, null, textViewDoubleRound);
                } else {
                    textViewDoubleRound.setText("10");
                    textViewDoubleRound.setCompoundDrawablesWithIntrinsicBounds(R.drawable.double_round_icon, 0, 0, 0);
                }

                viewHolder = new ListViewHolder(textViewRoundNumber, textViewPlayer1, textViewPlayer2, null, textViewDoubleRound);

                convertView.setTag(viewHolder);
            } else {
                Log.d("ListViewItemType", "THREE_PLAYER");
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.three_player_row, null);

                TextView textViewRoundNumber = (TextView) convertView.findViewById(R.id.round_number);
                TextView textViewPlayer1 = (TextView) convertView.findViewById(R.id.score_player1);
                TextView textViewPlayer2 = (TextView) convertView.findViewById(R.id.score_player2);
                TextView textViewPlayer3 = (TextView) convertView.findViewById(R.id.score_player3);
                TextView textViewDoubleRound = (TextView) convertView.findViewById(R.id.double_round_indicator);

                if (position == 0) {
                    customizeFirstRow(textViewRoundNumber, textViewPlayer1, textViewPlayer2, textViewPlayer3, textViewDoubleRound);
                } else {
                    textViewDoubleRound.setText("10");
                    textViewPlayer1.setText("10");
                    textViewPlayer2.setText("10");
                    textViewDoubleRound.setCompoundDrawablesWithIntrinsicBounds(R.drawable.double_round_icon, 0, 0, 0);
                }

                viewHolder = new ListViewHolder(textViewRoundNumber, textViewPlayer1, textViewPlayer2, textViewPlayer3, textViewDoubleRound);

                convertView.setTag(viewHolder);

                viewHolder.getScoreP3().setText(listViewItem.getScoreP3());
            }
        } else {
            viewHolder = (ListViewHolder) convertView.getTag();
        }

        viewHolder.getRoundNumber().setText(listViewItem.getRoundNumber());
        viewHolder.getScoreP1().setText(listViewItem.getScoreP1());
        viewHolder.getScoreP2().setText(listViewItem.getScoreP2());

        viewHolder.getRoundTimes().setText(listViewItem.getRoundTimes());

        return convertView;
    }

    private void customizeFirstRow(TextView roundNumber, TextView scoreP1, TextView scoreP2, TextView scoreP3, TextView roundTimes) {
        roundNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.double_round_icon_size));
        //roundTimes.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
    }

    // This makes the list items disabled
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

}
