package ru.zhukova.memorize;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MyPreferenceActivity extends PreferenceActivity  {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);
    }

}
