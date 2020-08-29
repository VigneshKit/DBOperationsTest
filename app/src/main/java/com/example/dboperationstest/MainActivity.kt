package com.example.dboperationstest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG = "main:"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        register.setOnClickListener {
            var userName = userName.text.toString()
            var age = age.text.toString()
            var phone = phone.text.toString()

            var studentInfo = StudentInfo(userName, age, phone)
            var mySqliteHelper = MySqliteHelper(this)

            mySqliteHelper.insertRecord(studentInfo)
        }

        readData.setOnClickListener {
            var mySqliteHelper = MySqliteHelper(this)
            var list = mySqliteHelper.readData()
            var str = "" // String Builder

            for(item in list){
                str+="${item.userName} : ${item.age}: ${item.phone} \n"
            }
            result.text = str
        }

        updateData.setOnClickListener {
            var userName = userName.text.toString()
            var age = age.text.toString()
            var phone = phone.text.toString()

            var studentInfo = StudentInfo(userName, age, phone)
            var mySQLiteOpenHelper = MySqliteHelper(this)
            var res = mySQLiteOpenHelper.updateRecord(studentInfo)

            if(res == -1){
                Toast.makeText(this, "Update failed", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Update Success", Toast.LENGTH_SHORT).show()
            }
        }

        deleteRecords.setOnClickListener {
            var mySqliteHelper = MySqliteHelper(this)
            var res = mySqliteHelper.deleteRecord(phone.text.toString())
            if(res == -1){
                Toast.makeText(this, "Deletion failed", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "$res  record deleted", Toast.LENGTH_SHORT).show()
            }
        }

    }
}