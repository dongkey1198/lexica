package com.serwylo.lexica;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    List <DatabaseHelper.GameResult> results;

    HistoryAdapter(List<DatabaseHelper.GameResult> results){
        this.results = results;
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.history_list, viewGroup, false);

        HistoryViewHolder vh = new HistoryViewHolder(view);

        vh.score = (TextView) view.findViewById(R.id.score_text);
        vh.infinite =(TextView) view.findViewById(R.id.infinite_text);
        vh.boardType = (TextView) view.findViewById(R.id.boardSize_text);
        return vh;
    }

    @Override
    public void onBindViewHolder(HistoryViewHolder vh, int i) {
        if (i == 0){
            vh.score.setText("Score:");
            vh.infinite.setText("Game Type:");
            vh.boardType.setText("Board Size:");
        } else {
            DatabaseHelper.GameResult result = results.get(i - 1);

            vh.score.setText(Integer.toString(result.getScore()));
            if (result.isNoTimeLimit()) {
                vh.infinite.setText("\"Infinite\"");
            } else {
                vh.infinite.setText("\"Normal\"");
            }
            vh.boardType.setText(Integer.toString(result.getBoardSize()));
        }
    }
    @Override
    public int getItemCount() {
        return results.size() + 1;
    }

    class HistoryViewHolder extends RecyclerView.ViewHolder{
        TextView score, infinite, boardType;

        public HistoryViewHolder(View itemView) {
            super(itemView);
        }
    }
}
