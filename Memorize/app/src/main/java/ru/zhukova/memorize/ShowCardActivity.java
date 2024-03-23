package ru.zhukova.memorize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.animation.AnimatorSet;
import android.animation.AnimatorInflater;
import android.view.View;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.webkit.WebSettings;
import android.widget.TextView;
import android.widget.Button;

public class ShowCardActivity extends AppCompatActivity {

    public TextView cardText;
    SharedPreferences prefs;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Button rememberButton;
    Cursor userCursor;
    AnimatorSet front_anim;
    AnimatorSet back_anim;
    boolean checkCard = true;
    long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_card);
        cardText = (TextView) findViewById(R.id.textView2);
        rememberButton = (Button) findViewById(R.id.button3);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        db = databaseHelper.getReadableDatabase();
        Bundle extras = getIntent().getExtras();
        userId = extras.getLong("id");
        if(userId != 0)
        {
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                    DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            cardText.setText(userCursor.getString(1));
            userCursor.close();
        }
        cardText.setCameraDistance(8000*(getResources().getDisplayMetrics().density));
        front_anim =(AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.card_flip_left_enter) ;
        back_anim =(AnimatorSet) AnimatorInflater.loadAnimator(getApplicationContext(), R.animator.card_flip_right_exit) ;
    }
    @Override
    protected void onResume() {

        float fSize = Float.parseFloat(prefs.getString(getString(R.string.pref_size), "20"));
        cardText.setTextSize(fSize);
        // открываем подключение
        db = databaseHelper.getReadableDatabase();
        if(userId == 0)
        {
            //получаем данные из бд в виде курсора
            userCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);
            while(userCursor.moveToNext()){
                String name = userCursor.getString(1);
                cardText.append("front: " + name  + "\n");
            }
        }
        super.onResume();
    }
    public void onClickRemember(View view)
    {
        Intent intent = new Intent(ShowCardActivity.this, CardListActivity.class);
        startActivity(intent);
    }
    public void onClickForgot(View view)
    {
        rememberButton.setText("СКРЫТЬ КАРТОЧКУ");
        if(userId != 0)
        {
            userCursor = db.rawQuery("select * from " + DatabaseHelper.TABLE + " where " +
                    DatabaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
            userCursor.moveToFirst();
            cardText.setText(userCursor.getString(2));
            userCursor.close();
        }
        if(checkCard)
        {
            front_anim.setTarget(cardText);
            back_anim.setTarget(cardText);
            back_anim.start();
            front_anim.start();
            checkCard = false;
        }
    }
    public void onClickShow(View view)
    {
        Intent intent = new Intent(ShowCardActivity.this, CardListActivity.class);
        startActivity(intent);
        db.delete(DatabaseHelper.TABLE, "_id = ?", new String[]{String.valueOf(userId)});
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }
}