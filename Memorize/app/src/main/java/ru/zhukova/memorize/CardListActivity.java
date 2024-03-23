package ru.zhukova.memorize;

import androidx.appcompat.app.AppCompatActivity;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract;
import android.content.Intent;
import java.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;

public class CardListActivity extends AppCompatActivity {
    ListView userList;
    TextView header;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    SimpleCursorAdapter userAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_list);
        userList = findViewById(R.id.list);
        header = findViewById(R.id.header);
        databaseHelper = new DatabaseHelper(getApplicationContext());
        userList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ShowCardActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
    }
    protected void onResume() {
        super.onResume();
        db = databaseHelper.getReadableDatabase();
        //получаем данные из бд в виде курсора
        userCursor =  db.rawQuery("select * from "+ DatabaseHelper.TABLE, null);
        // определяем, какие столбцы из курсора будут выводиться в ListView
        String[] headers = new String[] {DatabaseHelper.COLUMN_ID, DatabaseHelper.COLUMN_FRONT};
        // создаем адаптер, передаем в него курсор
        userAdapter = new SimpleCursorAdapter(this, android.R.layout.two_line_list_item,
                userCursor, headers, new int[]{android.R.id.text1, android.R.id.text2}, 0);
        header.setText(getString(R.string.titleText) +  userCursor.getCount());
        userList.setAdapter(userAdapter);
    }
    public void onClick1(View view) {
        Intent intent = new Intent(CardListActivity.this, CreateCardActivity.class);
        startActivity(intent);
    }
    public void onClickEvent(View view)
    {
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2022, 10, 30, 7, 30);
        Calendar endTime = Calendar.getInstance();
        endTime.set(2022, 10, 30, 8, 30);
        Intent intent = new Intent(Intent.ACTION_INSERT).setData(Events.CONTENT_URI).putExtra(Events.TITLE, "Memorize!").putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                ;
        startActivity(intent);
    }
    public void showSettings(View view)
    {
        Intent intent = new Intent(this, MyPreferenceActivity.class);
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