package com.sugarmount.sugarmvvm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sugarmount.sugarmvvm.adapter.ContactAdapter
import com.sugarmount.sugarmvvm.room.Contact
import com.sugarmount.sugarmvvm.viewModel.ContactViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var contactViewModel: ContactViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set contactItemClick & contactItemLongClick lambda
        val adapter =
            ContactAdapter({ contact ->
                // put extras of contact info & start addActivity
                val intent = Intent(
                    this,
                    AddActivity::class.java
                )
                intent.putExtra(
                    AddActivity.EXTRA_CONTACT_NAME,
                    contact.name
                )
                intent.putExtra(
                    AddActivity.EXTRA_CONTACT_NUMBER,
                    contact.number
                )
                intent.putExtra(
                    AddActivity.EXTRA_CONTACT_ID,
                    contact.id
                )
                startActivity(intent)
            }, { contact ->
                deleteDialog(contact)
            })

        val lm = LinearLayoutManager(this)
        main_recycleview.adapter = adapter
        main_recycleview.layoutManager = lm
        main_recycleview.setHasFixedSize(true)

        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        contactViewModel.getAll().observe(this, Observer {
            // Update UI
            adapter.setContacts(it!!)
        })

        main_button.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    private fun deleteDialog(contact: Contact) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected contact?")
            .setNegativeButton("NO") { _, _ -> }
            .setPositiveButton("YES") { _, _ ->
                contactViewModel.delete(contact)
            }
        builder.show()
    }
}
