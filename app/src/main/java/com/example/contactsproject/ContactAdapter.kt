package com.example.contactsproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.contactsproject.databinding.ContactItemViewBinding
import java.util.*

class ContactAdapter() : RecyclerView.Adapter<ContactAdapter.ContactHolder>() {

    private var contactsData: ArrayList<ContactData> = ArrayList();

    class ContactHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ContactItemViewBinding.bind(item)

        fun bind(contact: ContactData) {
            binding.contactPhoneNumber.text = contact.contactPhone
            binding.Career.text = contact.career
            Glide.with(itemView)
                .load("http://developer.alexanderklimov.ru/android/images/android_cat.jpg")
                .into(binding.contactIcon)
        }

        fun getContactIconView(): ImageView {
            return binding.contactIcon
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.contact_item_view, parent, false)
        return ContactHolder(view)
    }

    override fun getItemCount(): Int {
        return contactsData.size
    }

    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(contactsData[position])
    }

    fun setContacts(contact: ArrayList<ContactData>) {
        contactsData = contact
        notifyDataSetChanged()
    }

}