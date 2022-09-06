package com.example.contactsproject

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.contactsproject.databinding.ContactItemViewBinding
import java.util.*

class ContactAdapter() : RecyclerView.Adapter<ContactAdapter.ContactHolder>() {

    private var contactsData: ArrayList<ContactData> = ArrayList();

    class ContactHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = ContactItemViewBinding.bind(item)
        private lateinit var contactAdapter: ContactAdapter
        private lateinit var context: Context;

        fun bind(contact: ContactData) {

            binding.contactName.text = contact.contactName

            binding.phoneNumber.text = contact.phoneNumber

            binding.trashImageBtn.setOnClickListener() {
                contactAdapter.contactsData.removeAt(adapterPosition)
                contactAdapter.notifyItemRemoved(adapterPosition)
                Toast.makeText(context, Companion.CONTACT_REMOVED_TOAST_TEXT, Toast.LENGTH_SHORT).show()
            }

            Glide.with(itemView)
                .load(contact.contactIconUrl)
                .error(R.drawable.contacts_icon)
                .circleCrop()
                .into(binding.contactIcon)
        }

        fun linkAdapter(contactAdapter: ContactAdapter) {
            this.contactAdapter = contactAdapter
        }

        fun linkContext(context: Context) {
            this.context = context;
        }

        companion object {
            private const val CONTACT_REMOVED_TOAST_TEXT = "Contact has been removed"
        }
    }

    /**
     * Creates View from add_contact_layout to set it contact holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.contact_item_view, parent, false)
        val contactHolder = ContactHolder(view)
        contactHolder.linkAdapter(this)
        contactHolder.linkContext(parent.context)
        return contactHolder
    }

    override fun getItemCount(): Int {
        return contactsData.size
    }

    /**
     * Bind contact in position
     */
    override fun onBindViewHolder(holder: ContactHolder, position: Int) {
        holder.bind(contactsData[position])
    }

    fun setContacts(contact: ArrayList<ContactData>) {
        contactsData = contact
    }

}