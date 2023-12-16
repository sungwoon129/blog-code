"use strict";
const getStudent = () => ({
    studentId: 12345,
    age: 100,
    name: 'heeming',
    subject: 'English',
    pass: false
});
const student_1 = {
    studentId: 1,
    age: 23,
    name: 'sam',
    gender: 'male',
    subject: 'Math',
    pass: true,
};
const passTest = (student) => {
    student.pass = true;
};
passTest(student_1);
