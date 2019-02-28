package com.example.myapplication;


import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseLockedException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class LocalDatabase extends SQLiteOpenHelper {

    Context context2;

    public LocalDatabase(Context context, String db_name) {
        super(context, db_name, null, 1);
        context2 = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE  tbl_word_data (id int, Correct TEXT, Wrong TEXT)");
        //Toast.makeText(context2, "Table Created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


    }


}
