package com.example.contact

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contact.databinding.ActivityMainBinding
import com.example.contacts.ContactsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = ContactsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        APP_ACTIVITY = this
        init()
        CoroutineScope(Dispatchers.IO).launch { initContacts() }

    }

    @SuppressLint("Range")
    private fun initContacts() {
        if(checkPermission(READ_CONTACT)){
            val cursor = APP_ACTIVITY.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null,null)

            cursor?.let {
                while (it.moveToNext()){
                    val name = it.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    val phone = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val newContact = ContactsModel(name, phone)

                    adapter.addContacts(newContact)
                }
            }
            cursor?.close()
        }
    }

    private fun init(){
        binding.apply {
            rvContacts.layoutManager = LinearLayoutManager(this@MainActivity)
            rvContacts.adapter = adapter
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (ContextCompat.checkSelfPermission(APP_ACTIVITY, READ_CONTACT) == PackageManager.PERMISSION_GRANTED){
            initContacts()
        }
    }
}