package vn.edu.hust.studentman

import StudentManager
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

  private lateinit var studentManager: StudentManager
  private lateinit var adapter: StudentAdapter

  val students = mutableListOf(
    StudentModel("Nguyễn Văn An", "SV001"),
    StudentModel("Trần Thị Bảo", "SV002"),
    StudentModel("Lê Hoàng Cường", "SV003"),
    StudentModel("Phạm Thị Dung", "SV004"),
    StudentModel("Đỗ Minh Đức", "SV005"),
    StudentModel("Vũ Thị Hoa", "SV006"),
    StudentModel("Hoàng Văn Hải", "SV007"),
    StudentModel("Bùi Thị Hạnh", "SV008"),
    StudentModel("Đinh Văn Hùng", "SV009"),
    StudentModel("Nguyễn Thị Linh", "SV010"),
    StudentModel("Phạm Văn Long", "SV011"),
    StudentModel("Trần Thị Mai", "SV012"),
    StudentModel("Lê Thị Ngọc", "SV013"),
    StudentModel("Vũ Văn Nam", "SV014"),
    StudentModel("Hoàng Thị Phương", "SV015"),
    StudentModel("Đỗ Văn Quân", "SV016"),
    StudentModel("Nguyễn Thị Thu", "SV017"),
    StudentModel("Trần Văn Tài", "SV018"),
    StudentModel("Phạm Thị Tuyết", "SV019"),
    StudentModel("Lê Văn Vũ", "SV020")
  )

  val studentAdapter = StudentAdapter(students, { student ->
    showEditStudentDialog(student) // Gọi hàm hiển thị dialog chỉnh sửa
  },{ student ->
    showDeleteStudentDialog(student)
  })



  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)


    findViewById<RecyclerView>(R.id.recycler_view_students).run {
      adapter = studentAdapter
      layoutManager = LinearLayoutManager(this@MainActivity)
    }

    val btnAddNew = findViewById<Button>(R.id.btn_add_new)
    btnAddNew.setOnClickListener {
      showAddStudentDialog()
    }
  }


  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.option_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.action_add -> {
        // Mở Activity để thêm sinh viên mới
        val intent = Intent(this, AddStudentActivity::class.java)
        startActivityForResult(intent, ADD_STUDENT_REQUEST)
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == ADD_STUDENT_REQUEST && resultCode == Activity.RESULT_OK) {
      val newStudent = data?.getSerializableExtra("newStudent") as StudentModel
      students.add(newStudent)
      studentAdapter.notifyDataSetChanged()
    }
  }

  companion object {
    const val ADD_STUDENT_REQUEST = 1
  }


  private fun showAddStudentDialog() {

    val dialog = AlertDialog.Builder(this)
    val inflater = layoutInflater
    val dialogView = inflater.inflate(R.layout.dialog_add_student, null)
    dialog.setView(dialogView)

    val nameInput = dialogView.findViewById<EditText>(R.id.studentNameInput)
    val idInput = dialogView.findViewById<EditText>(R.id.studentIdInput)

    dialog.setPositiveButton("Add") { _, _ ->
      val name = nameInput.text.toString()
      val id = idInput.text.toString()
      if (name.isNotEmpty() && id.isNotEmpty()) {
        val student = StudentModel(name, id)
        studentAdapter.addStudent(student)
      }
    }
    dialog.setNegativeButton("Cancel", null)
    dialog.show()
  }

  private fun showEditStudentDialog(student: StudentModel) {
    val dialog = AlertDialog.Builder(this)
    val inflater = layoutInflater
    val dialogView = inflater.inflate(R.layout.dialog_add_student, null)
    dialog.setView(dialogView)

    val nameInput = dialogView.findViewById<EditText>(R.id.studentNameInput)
    val idInput = dialogView.findViewById<EditText>(R.id.studentIdInput)

    // Điền thông tin hiện tại vào EditText
    nameInput.setText(student.studentName)
    idInput.setText(student.studentId)

    dialog.setPositiveButton("Save") { _, _ ->
      val newName = nameInput.text.toString()
      val newId = idInput.text.toString()
      if (newName.isNotEmpty() && newId.isNotEmpty()) {
        val updatedStudent = StudentModel(newName, newId)
        val position = students.indexOf(student)
        students[position] = updatedStudent // Cập nhật danh sách sinh viên
        studentAdapter.notifyItemChanged(position) // Thông báo cho adapter
      }
    }
    dialog.setNegativeButton("Cancel", null)
    dialog.show()
  }

  private fun showDeleteStudentDialog(student: StudentModel) {
    val dialog = AlertDialog.Builder(this)
    dialog.setTitle("Xác Nhận Xóa")
    dialog.setMessage("Bạn có chắc chắn muốn xóa sinh viên ${student.studentName}?")

    dialog.setPositiveButton("Xóa") { _, _ ->
      val position = students.indexOf(student)
      if (position != -1) {
        studentAdapter.removeStudent(position) // Gọi hàm xóa sinh viên
      }
    }

    dialog.setNegativeButton("Hủy", null)
    dialog.show()
  }
}