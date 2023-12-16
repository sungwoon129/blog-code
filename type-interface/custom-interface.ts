interface User {
    age: number;
    name: string;
  }
  
  //변수에 활용한 인터페이스
  const anyUser: User = {
    age: 100,
    name: '히밍',
  }
  

  //함수의 인자를 정의하는 인터페이스
function getUser(user: User) { // 인자의 데이터 타입을 제한하여 interface User의 데이터 구조만 인자로 받도록 함 
    console.log(user);
  }
  const user1 = {
    name: '영희'
  }
  const user2 = {
    age: 56,
    name: '철수'
  }
  
  getUser(user2) // 문법 오류 없이 정상적으로 인자가 전달 됨
  