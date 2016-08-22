package com.github.vv_off.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.WindowManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    private DBHandler dbHandler = new DBHandler(this);
    private EditText mEditTextAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mEditTextAdd = (EditText) findViewById(R.id.edit_text_add);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!mEditTextAdd.getText().toString().equals("")) {
            String date = MainDate.getDate();
            dbHandler.addMessageDB(mEditTextAdd.getText().toString(), date);
        }
    }
}
