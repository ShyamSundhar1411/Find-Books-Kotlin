package com.shyam.searchyt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var searchB = findViewById<Button>(R.id.searchButton)
        var search_term = findViewById<EditText>(R.id.searchBox)
        searchB.setOnClickListener {
            var searchIntent = Intent(getApplicationContext(),SearchActivity::class.java)
            searchIntent.putExtra("Searched_Term",search_term.text.toString())
            startActivity(searchIntent)
        }
    }
}