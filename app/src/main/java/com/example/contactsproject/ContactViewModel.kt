package com.example.contactsproject

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.pm.PackageManager
import android.provider.ContactsContract
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.collections.ArrayList

@SuppressLint("Range")
class ContactViewModel(_application: Application) : AndroidViewModel(_application) {
    /**
     * List that keeps all contacts that will be shown in recycle view
     */
    private var _contacts: MutableLiveData<ArrayList<ContactData>> =
        MutableLiveData<ArrayList<ContactData>>()

    private val appApplication: Application = _application

    /**
     * Array with urls on images to set them on contacts loaded from phone
     */
    private val standardContactIconsURL by lazy {
        listOf<String>(
            "https://images.unsplash.com/photo-1582233479366-6d38bc390a08?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8ZmFjZXN8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1601288496920-b6154fe3626a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8ZmFjZXN8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1552374196-c4e7ffc6e126?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8ZmFjZXN8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60",
            "https://images.unsplash.com/photo-1489424731084-a5d8b219a5bb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8OHx8ZmFjZXN8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"
        )
    }

    init {
        _contacts.value = ArrayList()
        loadContacts()
    }

    /**
     * If user add permission to read contacts from phone
     * Function reads every contact from phone and gets name and phone number of the contact
     * Creates a new ContactData object with this data and set icon from array of url on icons
     */
    private fun loadContacts() {
        val hasReadContactPermission =
            ContextCompat.checkSelfPermission(appApplication, Manifest.permission.READ_CONTACTS)

        if (hasReadContactPermission != PackageManager.PERMISSION_GRANTED) {
            return
        }

        val contactCursor = appApplication.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        var iconsHeadPointer = 0
        if (contactCursor != null) {
            while (contactCursor.moveToNext()) {
                val contactName: String =
                    contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val contactPhone: String =
                    contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

                _contacts.value?.add(
                    ContactData(
                        standardContactIconsURL[iconsHeadPointer++ % standardContactIconsURL.size],
                        contactName,
                        contactPhone
                    )
                )
                if (iconsHeadPointer + 1 > standardContactIconsURL.size) {
                    iconsHeadPointer = 0
                }
            }

            contactCursor.close()
        }
    }

    /**
     * add a new contact in list
     */
    fun addContact(contactData: ContactData) {
        val contactsList = _contacts.value
        contactsList?.add(contactData)
        _contacts.value = contactsList
    }

    fun getContactLiveData(): LiveData<ArrayList<ContactData>> {
        return _contacts
    }

}