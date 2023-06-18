package com.example.sqlite

import android.app.Activity
import android.app.AlertDialog
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText


class MainActivity() : Activity(), View.OnClickListener {
    var Rollno: EditText? = null
    var Name: EditText? = null
    var Marks: EditText? = null
    var Insert: Button? = null
    var Delete: Button? = null
    var Update: Button? = null
    var View: Button? = null
    var ViewAll: Button? = null
    var db: SQLiteDatabase? = null

    /** Called when the activity is first created.  */
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Rollno = findViewById<View>(R.id.Rollno) as EditText
        Name = findViewById<View>(R.id.Name) as EditText
        Marks = findViewById<View>(R.id.Marks) as EditText
        Insert = findViewById<View>(R.id.Insert) as Button
        Delete = findViewById<View>(R.id.Delete) as Button
        Update = findViewById<View>(R.id.Update) as Button
        View = findViewById<View>(R.id.View) as Button
        ViewAll = findViewById<View>(R.id.ViewAll) as Button
        Insert!!.setOnClickListener(this)
        Delete!!.setOnClickListener(this)
        Update!!.setOnClickListener(this)
        View!!.setOnClickListener(this)
        ViewAll!!.setOnClickListener(this)

        // Creating database and table
        db = openOrCreateDatabase("StudentDB", MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR,name VARCHAR,marks VARCHAR);")
    }

    override fun onClick(view: View) {
        // Inserting a record to the Student table
        if (view === Insert) {
            // Checking for empty fields
            if (Rollno!!.text.toString().trim { it <= ' ' }.length == 0 || Name!!.text.toString()
                    .trim { it <= ' ' }.length == 0 || Marks!!.text.toString()
                    .trim { it <= ' ' }.length == 0
            ) {
                showMessage("Error", "Please enter all values")
                return
            }
            db!!.execSQL(
                "INSERT INTO student VALUES('" + Rollno!!.text + "','" + Name!!.text +
                        "','" + Marks!!.text + "');"
            )
            showMessage("Success", "Record added")
            clearText()
        }
        // Deleting a record from the Student table
        if (view === Delete) {
            // Checking for empty roll number
            if (Rollno!!.text.toString().trim { it <= ' ' }.length == 0) {
                showMessage("Error", "Please enter Rollno")
                return
            }
            val c =
                db!!.rawQuery("SELECT * FROM student WHERE rollno='" + Rollno!!.text + "'", null)
            if (c.moveToFirst()) {
                db!!.execSQL("DELETE FROM student WHERE rollno='" + Rollno!!.text + "'")
                showMessage("Success", "Record Deleted")
            } else {
                showMessage("Error", "Invalid Rollno")
            }
            clearText()
        }
        // Updating a record in the Student table
        if (view === Update) {
            // Checking for empty roll number
            if (Rollno!!.text.toString().trim { it <= ' ' }.length == 0) {
                showMessage("Error", "Please enter Rollno")
                return
            }
            val c =
                db!!.rawQuery("SELECT * FROM student WHERE rollno='" + Rollno!!.text + "'", null)
            if (c.moveToFirst()) {
                db!!.execSQL(
                    ("UPDATE student SET name='" + Name!!.text + "',marks='" + Marks!!.text +
                            "' WHERE rollno='" + Rollno!!.text + "'")
                )
                showMessage("Success", "Record Modified")
            } else {
                showMessage("Error", "Invalid Rollno")
            }
            clearText()
        }
        // Display a record from the Student table
        if (view === View) {
            // Checking for empty roll number
            if (Rollno!!.text.toString().trim { it <= ' ' }.length == 0) {
                showMessage("Error", "Please enter Rollno")
                return
            }
            val c =
                db!!.rawQuery("SELECT * FROM student WHERE rollno='" + Rollno!!.text + "'", null)
            if (c.moveToFirst()) {
                Name!!.setText(c.getString(1))
                Marks!!.setText(c.getString(2))
            } else {
                showMessage("Error", "Invalid Rollno")
                clearText()
            }
        }
        // Displaying all the records
        if (view === ViewAll) {
            val c = db!!.rawQuery("SELECT * FROM student", null)
            if (c.count == 0) {
                showMessage("Error", "No records found")
                return
            }
            val buffer = StringBuffer()
            while (c.moveToNext()) {
                buffer.append("Rollno: " + c.getString(0) + "\n")
                buffer.append("Name: " + c.getString(1) + "\n")
                buffer.append("Marks: " + c.getString(2) + "\n\n")
            }
            showMessage("Student Details", buffer.toString())
        }
    }

    fun showMessage(title: String?, message: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.show()
    }

    fun clearText() {
        Rollno!!.setText("")
        Name!!.setText("")
        Marks!!.setText("")
        Rollno!!.requestFocus()
    }
}