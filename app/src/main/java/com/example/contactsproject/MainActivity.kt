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

class MainActivity : AppCompatActivity(), AddContactDialogFragment.OnContactSave {

    private lateinit var binding: ActivityMainBinding

    /**
     * adapter for contactViewModel
     */
    private lateinit var contactAdapter: ContactAdapter

    /**
     * View model for contacts recycle view
     */
    private lateinit var contactViewModel: ContactViewModel

    private val REQUEST_CODE_READ_CONTACTS = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //hide action bar
        supportActionBar?.hide()
        getContactPermissions()

        binding.contactsRecycleView.layoutManager = LinearLayoutManager(this)
        contactAdapter = ContactAdapter()
        binding.contactsRecycleView.adapter = contactAdapter

        contactViewModel = ViewModelProvider(this)[ContactViewModel::class.java]

        contactViewModel.getContactLiveData().observe(this) {
            contactAdapter.setContacts(it)
        }

        setAddContactButtonListener()
    }

    /**
     * When user click on add contact button starts AddContactDialog
     */
    private fun setAddContactButtonListener() {
        binding.addContactBtn.setOnClickListener() {
            AddContactDialogFragment().apply {
                show(supportFragmentManager, "addContactDialog")
            }
        }
    }

    /**
     * get permission for reading contacts in view model to load them from phone into recycle view
     */
    private fun getContactPermissions() {
        val hasReadContactPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)

        if (hasReadContactPermission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_CONTACTS),
                REQUEST_CODE_READ_CONTACTS
            )
        }
    }

    /**
     * adds contact into view model when AddContactDialogFragment saves data that user has written
     */
    override fun onContactSave(contactData: ContactData) {
        contactViewModel.addContact(contactData)
    }


}