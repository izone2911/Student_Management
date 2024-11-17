package vn.edu.hust.studentman

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class StudentAdapter(val students: MutableList<StudentModel>,
                     val onEditClick: (StudentModel) -> Unit,
                     val onDeleteClick: (StudentModel) -> Unit): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
  class StudentViewHolder(itemView: View,
                          private val onEditClick: (StudentModel) -> Unit,
                          private val students: MutableList<StudentModel>,
                          private val onDeleteClick: (StudentModel) -> Unit): RecyclerView.ViewHolder(itemView) {
    val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
    val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
    val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
    val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)


    init {
      imageEdit.setOnClickListener {
        val student = students[adapterPosition]
        onEditClick(student)
      }
      imageRemove.setOnClickListener {
        val student = students[adapterPosition]
        onDeleteClick(student)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
    val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item,
       parent, false)
    return StudentViewHolder(itemView, onEditClick, students, onDeleteClick)
  }

  override fun getItemCount(): Int = students.size

  override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
    val student = students[position]

    holder.textStudentName.text = student.studentName
    holder.textStudentId.text = student.studentId
  }

  fun addStudent(student: StudentModel) {
    students.add(student)
    notifyItemInserted(students.size - 1) // Thông báo cho adapter rằng có item mới
  }

  fun removeStudent(position: Int) {
    students.removeAt(position)
    notifyItemRemoved(position) // Thông báo cho adapter rằng một item đã bị xóa
  }
}