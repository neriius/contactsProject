package com.example.contactsproject

import android.annotation.SuppressLint
import android.app.Application
import android.provider.ContactsContract
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import java.util.*

@SuppressLint("Range")
class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private var contacts: MutableLiveData<ArrayList<ContactData>> =
        MutableLiveData<ArrayList<ContactData>>()

    init {
        val list: ArrayList<ContactData> = ArrayList()

        val contactCursor = application.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        if (contactCursor != null) {
            while (contactCursor.moveToNext()) {

                val contactName: String =
                    contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val contactPhone: String =
                    contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                list.add(ContactData("contactIcon", contactName, contactPhone))
            }
            contactCursor.close()
        }

        contacts.value = list
    }

    fun getUsers(): MutableLiveData<ArrayList<ContactData>> {
        return contacts;
    }

}