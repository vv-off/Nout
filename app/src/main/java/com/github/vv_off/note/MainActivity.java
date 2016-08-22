package com.github.vv_off.note;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listView;
    private DBHandler dbHandler = new DBHandler(this);
    private Button mAddButton;

    private ArrayList<HashMap<String, String>> myArrList = new ArrayList<HashMap<String, String>>();
    private HashMap<String, String> map;
    private int postionElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list_view);
        mAddButton = (Button) findViewById(R.id.add_button);
        mAddButton.setOnClickListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //обработка нажатия на пукт List
                //запуск activity редактирования выбранного пункта
                postionElement = position;
                startIntentEdit(postionElement);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                postionElement = position;
                return false;
            }
        });

        registerForContextMenu(listView);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int pos;
        switch (item.getItemId()) {
            case R.id.delete_item:
                //Зписываем позицию выбранного пункта ListView
                int id = dbHandler.getAllMessageDB().get(postionElement).getId();
                dbHandler.delMessageDB(id);
                fillingListView();
                break;
            case R.id.open_item:
                startIntentEdit(postionElement);
                break;
            case R.id.clone_item:
                String cloneMessage = dbHandler.getAllMessageDB().get(postionElement).getMessage();
                String cloneDate = MainDate.getDate();
                dbHandler.addMessageDB(cloneMessage, cloneDate);
                fillingListView();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        this.finishAffinity();
    }

    @Override
    public void onResume() {
        super.onResume();
        fillingListView();
    }

    public void fillingListView() {
        myArrList.clear();
        for (int i = 0; i < dbHandler.getAllMessageDB().size(); i++) {
            map = new HashMap<String, String>();
            map.put("Message", dbHandler.getAllMessageDB().get(i).getMessage());
            map.put("Date", dbHandler.getAllMessageDB().get(i).getDate());
            myArrList.add(map);

            SimpleAdapter adapter = new SimpleAdapter(this, myArrList, R.layout.list_view_main,
                    new String[]{"Message", "Date"},
                    new int[]{R.id.lv_message, R.id.lv_date});
            listView.setAdapter(adapter);
        }
    }

    public void startIntentEdit(int position) {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra(EditActivity.KEY_ID, dbHandler.getAllMessageDB().get(position).getId());
        intent.putExtra(EditActivity.KEY_MESSAGE, dbHandler.getAllMessageDB().get(position).getMessage());
        startActivity(intent);
    }
}
