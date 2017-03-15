package com.mystery0.tools.TextDialogPreference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mystery0.tools.Logs.Logs;
import com.mystery0.tools.R;

import static android.content.ContentValues.TAG;


public class TextDialogPreference extends DialogPreference
{
    private View view;
    private String text;
    private String source;
    private TextView textView;
    private TextView sourceView;

    public TextDialogPreference(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        setDialogLayoutResource(R.layout.mystery0_text_preference);
        setPositiveButtonText(R.string.mystery0_preference_content);
        setNegativeButtonText(R.string.mystery0_preference_source);
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    @SuppressLint("InflateParams")
    @Override
    protected View onCreateDialogView()
    {
        super.onCreateDialogView();
        if (view != null) return view;
        view = LayoutInflater.from(getContext()).inflate(
                R.layout.mystery0_text_preference, null);
        return view;
    }

    @Override
    protected void onBindDialogView(View view)
    {
        super.onBindDialogView(view);

        textView = (TextView) view.findViewById(R.id.mystery0_text_preference_view_content);
        sourceView = (TextView) view.findViewById(R.id.mystery0_text_preference_view_source);
    }

    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue)
    {
        if (restorePersistedValue)
        {
            text = getPersistedString("test");
        } else
        {
            text = (String) defaultValue;
        }
    }

    @Override
    protected void onDialogClosed(boolean positiveResult)
    {
        super.onDialogClosed(positiveResult);
        Logs.i(TAG, "onDialogClosed: " + positiveResult);
    }

    @Override
    protected Object onGetDefaultValue(TypedArray a, int index)
    {
        return a.getString(index);
    }

    @Override
    protected void showDialog(Bundle state)
    {
        super.showDialog(state);
        textView.setText(text);
        sourceView.setText(source);
    }
}
