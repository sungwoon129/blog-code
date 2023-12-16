
interface Student {
    readonly studentId: number
    age: number,
    name: string,
    gender?: string,
    subject: string,
    pass: boolean,
  }
  
const getStudent = () : Student => ({
studentId: 12345,
age: 100,
name: 'heeming',
subject: 'English',
pass: false
})
  

const student_1 : Student = {
    studentId: 1,
    age: 23,
    name: 'sam',
    gender: 'male',
    subject: 'Math',
    pass: true,
    }

const passTest = (student: Student): void => {
student.pass = true; 
}

passTest(student_1);