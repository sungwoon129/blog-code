
interface SumFunc { // sum 함수를 만들기 전 데이터 타입을 정의할 interface 선언
    (a: number, b:number): number; //인자와 리턴값의 데이터 타입을 정의
  }

  const sumNum: SumFunc = function (a: number, b: number) {
    return a + b;
  }
  var result = sumNum(5,10);
  console.log(result); 
  
  
  
  
  
  

  