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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ListView listView;
    Button mAddButton;
    ArrayList<Message> messageArrayList = new ArrayList<>();
    ArrayList<HashMap<String, String>> myArrList = new ArrayList<HashMap<String, String>>();
    HashMap<String, String> map;
    private int postionElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ///// пока заполняем вручну
        //// в дальнейшем будет использоваться БД
        for(int i=0;i<20;i++) {
            messageArrayList.add(i, new Message(i,"Запись " + i,"01.01.2016"));
        }
        ///////////
        for(int i=0;i<messageArrayList.size();i++) {
            map = new HashMap<String, String>();
            map.put("Message", messageArrayList.get(i).getMessage());
            map.put("Date", messageArrayList.get(i).getDate());
            myArrList.add(map);
        }

        listView = (ListView)findViewById(R.id.list_view);
        mAddButton = (Button)findViewById(R.id.add_button);
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

        SimpleAdapter adapter = new SimpleAdapter(this, myArrList, R.layout.list_view_main,
                new String[] {"Message", "Date"},
                new int[] {R.id.lv_message, R.id.lv_date});
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this,AddActivity.class);
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int pos;
        switch (item.getItemId()){
            case R.id.delete_item:

                //Зписываем позицию выбранного пункта ListView
                int id = messageArrayList.get(postionElement).getId();
                String message = messageArrayList.get(postionElement).getMessage();
                //После убрать Toast и добавить код удаления из БД
                String messageToast = "Удаляется соббщение - " + message + " - " + id + "!";
                Toast.makeText(getApplicationContext(),messageToast,Toast.LENGTH_SHORT).show();
                break;
            case R.id.open_item:
                startIntentEdit(postionElement);
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

    public void startIntentEdit(int position){
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        intent.putExtra(EditActivity.KEY_ID,messageArrayList.get(position).getId());
        intent.putExtra(EditActivity.KEY_MESSAGE,messageArrayList.get(position).getMessage());
        startActivity(intent);
    }
}
