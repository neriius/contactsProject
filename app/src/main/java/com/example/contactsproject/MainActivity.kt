package com.example.contactsproject

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsproject.databinding.ActivityMainBinding
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var contactAdapter: ContactAdapter
    private lateinit var contactViewModel: ContactViewModel

    private var READ_CONTACTS_GRANTED = false;
    private val REQUEST_CODE_READ_CONTACTS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getContactPermissions()


        binding.contactsRecycleView.layoutManager = LinearLayoutManager(this)
        contactAdapter = ContactAdapter()
        binding.contactsRecycleView.adapter = contactAdapter

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)

        contactViewModel.getUsers().observe(this) {
            contactAdapter.setContacts(it)
        }
    }

    private fun getContactPermissions() {
        val hasReadContactPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)

        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            READ_CONTACTS_GRANTED = true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_CODE_READ_CONTACTS
            )
        }
    }
}