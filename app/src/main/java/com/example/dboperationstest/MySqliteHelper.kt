package com.example.dboperationstest

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

val DB_NAME = "studentdb"
var TABLE_NAME = "studentinfo"
val COL1 = "userName"
val COL2 = "age"
val COL3 = "phone"
class MySqliteHelper(context: Context): SQLiteOpenHelper(context,DB_NAME,null, 1) {
    var context = context
    override fun onCreate(db: SQLiteDatabase?) {
        var QUERY = "CREATE TABLE $TABLE_NAME ($COL1 VARCHAR(256), $COL2 VARCHAR(256), $COL3 VARCHAR(256))"
        db?.execSQL(QUERY)
        Toast.makeText(context, "Table created...", Toast.LENGTH_SHORT).show()
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) { }

    public fun insertRecord(student:StudentInfo){
        var db = this.writableDatabase

        var cv = ContentValues()
        cv.put(COL1,student.userName)
        cv.put(COL2,student.age)
        cv.put(COL3,student.phone)

        var res = db.insert(TABLE_NAME, null,cv)
        if(res == -1.toLong()){
            Toast.makeText(context, "Registration failed...", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context, "Registration Success...", Toast.LENGTH_SHORT).show()
        }
    }

    fun readData():MutableList<StudentInfo>{
        var db = this.readableDatabase
        var data = db.rawQuery("select * from $TABLE_NAME", null)
        var list = mutableListOf<StudentInfo>()
        if(data.moveToFirst()){
            do{
                var userName = data.getString(0)
                var age = data.getString(1)
                var phone = data.getString(2)

                var studentInfo = StudentInfo(userName, age, phone)
                list.add(studentInfo)
            }while(data.moveToNext())
        }
        return list
    }

    fun updateRecord(studentInfo:StudentInfo):Int{
        var db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL1, studentInfo.userName)
        cv.put(COL2, studentInfo.age)
        cv.put(COL3, studentInfo.phone)
        var res = db.update(TABLE_NAME, cv, "$COL3 = ${studentInfo.phone}", null)
        return res
    }

    fun deleteRecord(phone:String):Int{
        var db = this.writableDatabase
        var res = db.delete(TABLE_NAME, "$COL3 = $phone", null)
        return res
    }

}