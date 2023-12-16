"use strict";
//변수에 활용한 인터페이스
const anyUser = {
    age: 100,
    name: '히밍',
};
//함수의 인자를 정의하는 인터페이스
function getUser(user) {
    console.log(user);
}
const user1 = {
    name: '영희'
};
const user2 = {
    age: 56,
    name: '철수'
};
getUser(user2); // 문법 오류 없이 정상적으로 인자가 전달 됨
