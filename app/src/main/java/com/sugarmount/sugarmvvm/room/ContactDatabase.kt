package com.sugarmount.sugarmvvm.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase: RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {
        private var instance: ContactDatabase? = null

        fun getInstance(context: Context): ContactDatabase? {
            if(instance == null){
                synchronized(ContactDatabase::class){
                    instance = Room.databaseBuilder(context.applicationContext, ContactDatabase::class.java, "Contact")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return instance
        }
    }
}