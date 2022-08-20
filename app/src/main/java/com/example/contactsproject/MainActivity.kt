package com.example.contactsproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.contactsproject.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.contactsRecycleView.layoutManager = LinearLayoutManager(this)
        contactAdapter = ContactAdapter()
        binding.contactsRecycleView.adapter = contactAdapter

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        contactViewModel.getUsers().observe(this) {
            contactAdapter.setContacts(it)
        }

    }
}