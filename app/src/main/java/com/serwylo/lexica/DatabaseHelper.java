package com.serwylo.lexica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.serwylo.lexica.game.Game;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static int VERSION = 1;
    private static String TABLE_NAME = "game_history";
    private static String ID_COLUMN = "_id";
    private static String SCORE_COLUMN = "score";
    private static String Time_COLUMN = "time";
    private static String noTimeLimit_COLUMN = "noTimeLimit";
    private static String BoardSize_COLUMN = "boardSize";

    private static String SQL_CREATE = "CREATE TABLE "+ TABLE_NAME +"("+
            ID_COLUMN +" INTEGER PRIMARY KEY AUTOINCREMENT," +
            SCORE_COLUMN + " INTEGER," +
            Time_COLUMN + " INTEGER," +
            noTimeLimit_COLUMN +" INTEGER," +
            BoardSize_COLUMN + " INTEGER);";
    DatabaseHelper (Context context){
        super(context, "history.db", null, VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void put (Game game){
        ContentValues value = new ContentValues();
        value.put(SCORE_COLUMN, game.getScore());
        value.put(Time_COLUMN, game.getTimeRemaining());
        value.put(noTimeLimit_COLUMN, game.isInfinite());
        value.put(BoardSize_COLUMN,game.getBoardSize());

        getWritableDatabase().insert(TABLE_NAME, null, value); // 데이터베이스에 데이터값들을 쓰는 명령어
    }

    public List<GameResult> getAll(){
        List<GameResult> results = new ArrayList<>();

        Cursor query = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (query.moveToFirst()){
            do {
                int score = query.getInt(1);
                int timeRemaining = query.getInt(2);
                boolean noTimeLimnt = false;
                if (query.getInt(3)==1) noTimeLimnt = true;
                int boardSize = query.getInt(4);
                results.add(new GameResult(score, timeRemaining, noTimeLimnt, boardSize));
            } while (query.moveToNext());
        }

        return results;
    }

    public int getHighScore(int board) {
        int highScore = 0;
        Cursor query = getReadableDatabase().rawQuery("SELECT * FROM " + TABLE_NAME, null); // 데이터베이스에서 존재하는 모든 점수를 불러온다.
        if (query.moveToFirst()){
            do {
                int score = query.getInt(1);//점수랑 보드사이즈를 확인한다.
                int boardSize = query.getInt(4);// 보드사이즈가 불러온 보드사이즈와 일치하는지 확인
                if (board == boardSize && highScore < score)// 비교한점수가 현제 보드의 하이스코어보다 높을때 점수변경
                    highScore = score;
            } while (query.moveToNext());
        }
        return highScore;
    }

    class GameResult {
        private final int score;
        private final int timeRemaining;
        private final boolean noTimeLimit;
        private final int boardSize;

        GameResult(int score, int timeRemaining, boolean noTimeLimit, int boardSize) {
            this.score = score;
            this.timeRemaining = timeRemaining;
            this.noTimeLimit = noTimeLimit;
            this.boardSize = boardSize;
        }

        public int getScore() {
            return score;
        }

        public int getTimeRemaining() {
            return timeRemaining;
        }

        public boolean isNoTimeLimit() {
            return noTimeLimit;
        }

        public int getBoardSize() {
            return boardSize;
        }
    }
}
