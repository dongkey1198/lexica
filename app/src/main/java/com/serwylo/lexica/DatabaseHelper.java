package com.serwylo.lexica;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.serwylo.lexica.game.Game;

import java.util.PriorityQueue;

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
            Time_COLUMN + " TEXT," +
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


}
