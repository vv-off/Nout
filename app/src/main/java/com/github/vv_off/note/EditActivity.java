package com.github.vv_off.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity{

    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "message";

    EditText mEditTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mEditTextView = (EditText)findViewById(R.id.edit_text_edit);

        int id = getIntent().getIntExtra(KEY_ID, 0);
        String message = getIntent().getStringExtra(KEY_MESSAGE).toString();

        mEditTextView.setText(message + " - " + id);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
