package ru.zhukova.memorize;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.os.Handler;
import android.widget.TextView;
import android.content.Intent;
import android.content.ContentValues;
import android.content.Context;
import android.widget.EditText;
public class CreateCardActivity extends AppCompatActivity {
    EditText frontSideOfCard, reverseSideOfCard;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    long userId=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);
        frontSideOfCard = (EditText) findViewById(R.id.frontSideCard);
        reverseSideOfCard = (EditText) findViewById(R.id.reverseSideCard);
        databaseHelper = new DatabaseHelper(getApplicationContext());
    }
    @Override
    protected void onResume()
    {
        // открываем подключение
        db = databaseHelper.getReadableDatabase();
        super.onResume();
    }
    public void onClickSave(View view)
    {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_FRONT, frontSideOfCard.getText().toString());
        cv.put(DatabaseHelper.COLUMN_REVERSE, reverseSideOfCard.getText().toString());
        db.insert(DatabaseHelper.TABLE, null, cv);
        Intent intent = new Intent(CreateCardActivity.this, CardListActivity.class);
        startActivity(intent);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        userCursor.close();
    }
}