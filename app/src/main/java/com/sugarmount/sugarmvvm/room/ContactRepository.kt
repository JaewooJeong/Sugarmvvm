package com.sugarmount.sugarmvvm.room

import android.app.Application
import androidx.lifecycle.LiveData

class ContactRepository(application: Application) {
    private val contactDatabase = ContactDatabase.getInstance(
        application
    )!!
    private val contactDao: ContactDao = contactDatabase.contactDao()
    private val contacts: LiveData<List<Contact>> = contactDao.getAll()

    fun getAll(): LiveData<List<Contact>> {
        return contacts
    }

    fun insert(contact: Contact){
        try{
            Thread(Runnable {
                contactDao.insert(contact)
            }).start()
        }catch (e: Exception){
        }
    }

    fun delete(contact: Contact){
        try{
            Thread(Runnable {
                contactDao.delete(contact)
            }).start()
        }catch (e: Exception){
        }
    }
}