package com.example.contact

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.databinding.ContactItemBinding
import com.example.contacts.ContactsModel

class ContactsAdapter: RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>() {
    private val contactsList = ArrayList<ContactsModel>()

    class ContactsViewHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = ContactItemBinding.bind(item)
        fun bind(contacts: ContactsModel) = with(binding){
            tvName.text = contacts.name
            tvPhone.text = contacts.phone
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ContactsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        holder.bind(contactsList[position])
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    fun addContacts(contact: ContactsModel){
        contactsList.add(contact)
        notifyDataSetChanged()
    }
}