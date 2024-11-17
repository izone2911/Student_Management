import vn.edu.hust.studentman.StudentModel

// StudentManager.kt
class StudentManager {

  private val studentList: MutableList<StudentModel> = mutableListOf()

  fun getStudents(): List<StudentModel> {
    return studentList
  }

  fun addStudent(student: StudentModel) {
    studentList.add(student)
  }

  fun removeStudent(student: StudentModel) {
    studentList.remove(student)
  }
}