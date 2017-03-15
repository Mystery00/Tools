package com.mystery0.toolsdemo;

import android.os.Bundle;

import com.mystery0.tools.TextDialogPreference.TextDialogPreference;

@SuppressWarnings("deprecation")
public class PreferenceActivity extends AppCompatPreferenceActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preference);

        TextDialogPreference preference = (TextDialogPreference) findPreference(getString(R.string.testPreference));
        preference.setText("content");
        preference.setSource("source");
    }
}
