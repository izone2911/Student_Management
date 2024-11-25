package vn.edu.hust.studentman

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.widget.Toast

// AddStudentActivity.kt
class AddStudentActivity : AppCompatActivity() {

    private lateinit var editTextName: EditText
    private lateinit var editTextAge: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_student)

        editTextName = findViewById(R.id.studentNameInput)
        editTextAge = findViewById(R.id.studentIdInput)
        val buttonAdd: Button = findViewById(R.id.buttonAdd)

        buttonAdd.setOnClickListener {
            val name = editTextName.text.toString()
            val age = editTextAge.text.toString().toIntOrNull()

            if (name.isNotBlank() && age != null) {
                val newStudent = StudentModel(name, age)
                val resultIntent = Intent()
                resultIntent.putExtra("newStudent", newStudent)
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Please enter valid name and age", Toast.LENGTH_SHORT).show()
            }
        }
    }
}