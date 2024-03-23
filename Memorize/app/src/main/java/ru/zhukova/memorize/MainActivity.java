package ru.zhukova.memorize;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    protected TextView mHelloTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHelloTextView = findViewById(R.id.textView);
        mHelloTextView.setText(R.string.dev);

    }

    @SuppressLint("SetTextI18n")
    public void onClick(View view) {

        Intent intent = new Intent(MainActivity.this, CardListActivity.class);
        startActivity(intent);
    }
}