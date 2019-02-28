package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.os.Build;
import android.provider.SyncStateContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.ID;

public class MainActivity extends AppCompatActivity {

    public TextView text1, text2, text3;
    public AutoCompleteTextView autocomplete;
    public ImageView search;
    ArrayAdapter<String> myAdapter;


    LocalDatabase db;
    SQLiteDatabase sqdb_write, sqdb_read;


    // just to add some initial value
    String[] item = new String[]{"Please search..."};


    @SuppressLint("WrongViewCast")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text1 = findViewById(R.id.text1);
        text2 = findViewById(R.id.text2);
        text3 = findViewById(R.id.text3);
        autocomplete = findViewById(R.id.autocomplete);
        search = findViewById(R.id.search);


        ///USING FONT

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/SolaimanLipi.ttf");
        text1.setTypeface(font);
        text2.setTypeface(font);
        text3.setTypeface(font);


        //DATABASE

        db = new LocalDatabase(this, "DataDictonary.db");
        sqdb_write = db.getWritableDatabase();
        sqdb_read = db.getReadableDatabase();


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String query_value = autocomplete.getText().toString().trim();


                String search_query = "SELECT * FROM tbl_word_data WHERE Correct LIKE '%" + query_value + "%' OR  Wrong LIKE '%" + query_value + "%'";


                Cursor search_data_find = sqdb_read.rawQuery(search_query, null);

                //Toast.makeText(MainActivity.this, query_value.toString() + " - " +String.valueOf(search_data_find.getCount()) + " - " + search_query, Toast.LENGTH_LONG).show();

                arrangetable(search_data_find);


            }

        });


        String query = "select * from tbl_word_data";

        Cursor read_data = sqdb_read.rawQuery(query, null);


        arrangetable(read_data);

        //sqdb_write.execSQL("Delete from tbl_word_data");

    }


    /////SEARCH BOX READ DATA FROM DATABASE

    public String[] getItemsFromDb(String searchTerm) {

        // add items on the array dynamically
        List<Myobject> products = this.read(searchTerm);
        int rowCount = products.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (Myobject record : products) {

            item[x] = record.objectName;
            x++;
        }

        return item;
    }

    public List<Myobject> read(String searchTerm) {

        List<Myobject> recordlist = new ArrayList<Myobject>();

        sqdb_read = db.getReadableDatabase();
        String query = "select * from tbl_word_data";

        Cursor read_data = sqdb_read.rawQuery(query, null);


        if (read_data.moveToFirst()) {
            do {


                String objectName = read_data.getString(read_data.getColumnIndex(String.valueOf(this)));
                Myobject myObject = new Myobject(objectName);


                recordlist.add(myObject);

            } while (read_data.moveToNext());
        }

        read_data.close();
        db.close();


        return recordlist;

    }

    public void arrangetable(Cursor read_data) {

        if (read_data.moveToFirst()) {

            String results = "";
            int i = 1;

            TableLayout tableLayout = findViewById(R.id.table1);

            while (tableLayout.getChildCount() > 1) {
                tableLayout.removeView(tableLayout.getChildAt(tableLayout.getChildCount() - 1));
            }


            do {

                TableRow tableRow = new TableRow(tableLayout.getContext());
                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
                tableRow.setLayoutParams(layoutParams);

                TextView textView0 = new TextView(tableRow.getContext());
                textView0.setTextSize(16);
                textView0.setPadding(10, 5, 5, 5);
                textView0.setText(String.valueOf(i++));
                tableRow.addView(textView0, 0);

                TextView textView1 = new TextView(tableRow.getContext());
                textView1.setText(read_data.getString(1));
                textView1.setTextSize(16);
                textView1.setPadding(5, 5, 5, 5);
                tableRow.addView(textView1, 1);

                TextView textView2 = new TextView(tableRow.getContext());
                textView2.setText(read_data.getString(2));
                textView2.setTextSize(16);
                textView2.setPadding(5, 5, 5, 5);
                tableRow.addView(textView2, 2);

                tableLayout.addView(tableRow);


                //results += read_data.getString(0) + ", " + read_data.getString(1) + ", " + read_data.getString(2) + "\n";


            }
            while (read_data.moveToNext());

            read_data.close();
            //Toast.makeText(this, results.toString(), Toast.LENGTH_SHORT).show();


        }

    }


}





