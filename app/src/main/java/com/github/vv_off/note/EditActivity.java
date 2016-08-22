package com.github.vv_off.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;

public class EditActivity extends AppCompatActivity {

    public static final String KEY_ID = "id";
    public static final String KEY_MESSAGE = "message";

    private EditText mEditTextEdit;
    private DBHandler dbHandler = new DBHandler(this);
    int mID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mEditTextEdit = (EditText) findViewById(R.id.edit_text_edit);

        mID = getIntent().getIntExtra(KEY_ID, 0);
        String message = getIntent().getStringExtra(KEY_MESSAGE).toString();

        mEditTextEdit.setText(message);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(EditActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!mEditTextEdit.getText().toString().equals("")) {
            String date = MainDate.getDate();
            dbHandler.updateMessageDB(mID, mEditTextEdit.getText().toString(), date);
        }
    }


}
