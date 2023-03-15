package com.marcelomotta.task

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.SparseBooleanArray
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class HomeActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private var gson = Gson()
    lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()
        sharedPreferences = getSharedPreferences("lista_de_tarefas", Context.MODE_PRIVATE)
        val etTasks = findViewById<EditText>(R.id.et_task)
        val btnAdd = findViewById<Button>(R.id.button_add)
        val listViewItems = findViewById<ListView>(R.id.list_view_tasks)

        val itemList = getData();
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_multiple_choice, itemList);
        listViewItems.adapter = adapter;
        adapter.notifyDataSetChanged()

        btnAdd.setOnClickListener {
            itemList.add(etTasks.text.toString());
            listViewItems.adapter = adapter;
            adapter.notifyDataSetChanged()
            saveData(itemList)
            etTasks.text.clear()
        }

        findViewById<View>(R.id.btn_delete).setOnClickListener {
            val position: SparseBooleanArray = listViewItems.checkedItemPositions;
            val count = listViewItems.count;
            var item = count - 1;
            while(item >= 0){
                if(position.get(item)){
                    adapter.remove(itemList.get(item))
                }
                item--;
            }
            saveData(itemList)
            position.clear();
            adapter.notifyDataSetChanged()
        }

        val buttonOut = findViewById<Button>(R.id.btn_sair)
        buttonOut.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getData(): ArrayList<String> {
        val arrayJson = sharedPreferences.getString("tasks", null);
        return if(arrayJson.isNullOrEmpty()){
            arrayListOf();
        }else{
            gson.fromJson(arrayJson, object: TypeToken<ArrayList<String>>(){}.type)
        }
    }

    private fun saveData(array: ArrayList<String>){
        val arrayJson = gson.toJson(array);
        val editor = sharedPreferences.edit();
        editor.putString("tasks", arrayJson);
        editor.apply();
    }

}