package com.example.contactsproject

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import java.util.*

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private var contacts: MutableLiveData<ArrayList<ContactData>> =
        MutableLiveData<ArrayList<ContactData>>()

    init {


        val list: ArrayList<ContactData> = ArrayList()
        val picturesHttpLinks: Array<String> =
            application.resources.getStringArray(R.array.pictures_links)

        for (i in 0..picturesHttpLinks.size - 1) {
            list.add(ContactData(picturesHttpLinks[i], i.toString(), i.toString()))
        }
        contacts.value = list
    }

    fun getUsers(): MutableLiveData<ArrayList<ContactData>> {
        return contacts;
    }

}