interface User {
    age: number;
    name: string;
  }
  
  // 타입처럼 사용하는 인터페이스
  const anyUser: User = {
    age: 10,
    name: '철수',
  }

// 파라미터의 타입을 정하는 것처럼 활용
function getUser(user: User) {
    console.log(user);
  }
  const user1 = {
    name: '영희'
  }
  const user2 = {
    age: 26,
    name: '철수'
  }
  
  getUser(user2)


interface Window {
  title: string
}

interface Window {
  isOpen: boolean
}

// 같은 interface 명으로 Window를 다시 만든다면, 자동으로 확장이 된다.

const customWindow: Window = {
  title: "window",
  isOpen: false
}

console.log(customWindow);
