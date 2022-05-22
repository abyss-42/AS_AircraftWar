package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.dao.Record;
import com.example.myapplication.dao.RecordDaoImple;

import java.util.ArrayList;
import java.util.List;

public class RankActivity extends AppCompatActivity {
    public static int WINDOW_WIDTH;
    public static int WINDOW_HEIGHT;
    private static int score = 0;
    private static RecordDaoImple recordDaoImple;
    ListView listView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("rank");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rank);
        listView = findViewById(R.id.rankList);
        TextView title = findViewById(R.id.rank_title);
        if(GameActivity.whichMode == "simple"){
            recordDaoImple = new RecordDaoImple("easy.txt",this);
            title.setText("Easy Rank");
        }else if(GameActivity.whichMode == "normal"){
            recordDaoImple = new RecordDaoImple("normal.txt",this);
            title.setText("Normal Rank");
        }else{
            recordDaoImple = new RecordDaoImple("hard.txt",this);
            title.setText("Hard Rank");
        }
        DisplayMetrics dm = getResources().getDisplayMetrics();
        WINDOW_WIDTH = dm.widthPixels;
        WINDOW_HEIGHT = dm.heightPixels;
        Record record = new Record("hhh",GameActivity.gameView.getScore());
        recordDaoImple.addRecord(record);
        List<Record> tableTata = recordDaoImple.printRecord();
//        Record record = new Record("hhh",100);
//        List<Record> test = new ArrayList<>();
//        test.add(record);
        RankAdapter rankAdapter = new RankAdapter(RankActivity.this,R.layout.rank_list,tableTata);
        listView.setAdapter(rankAdapter);

        getSupportActionBar().hide();
    }
}