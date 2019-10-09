
### 1.2 Data Abstraction

 - Programming in Java is largely based on building data types. (object-oriented programming)
 - Data types: a set of values and a set of operations on those values.
 - Abstract data type (ADT): a data type whose internal representation is hidden from the client.
 - Object: an entity that can take on a data-type value. It has (state, identity, behavior)
 - Application programming interface (API): a specification of behavior of an abstract data type.
 - Client: a program that uses a data type.
 - Implementation: the code that implements the data type specified in an API.

##### 추상 데이터 타입의 설계

 - 추상 데이터 타입은 내부 데이터 표현방식을 외부의 클라이언트로부터 숨긴다.
 - 객체 지향의 설계에 고려해야 할 중요한 사항들
     1. 은닉화 : 구현부를 숨김으로써 클라이언트 코드의 개발과 데이터 타입의 구현을 서로 독립적으로 수행될 수 있게 하는 것이다. 
        이렇게 함으로써 미래의 클라이언트에 대한 가이드를 제시하며, 클라이언트 코드에 영향을 주지 않고 타입 구현의 개선사항을 
        적용할 수 있기 때문이다.
     2. API 설계 : 클라이언트가 요구하는 것만 제공하고 요구하지 않는 것은 제공하지 마라.
     3. 알고리즘과 추상 데이터 타입 : 알고리즘에 클라이언트에 어떤 것을 제공하는지 명확하게 기술, 알고리즘의 구현을 클라이언트와 
        분리, 계층적으로 추상화하여 잘 정의된 알고리즘을 다른 알고리즘의 개발에 활용할 수 있게 함.
     4. 인터페이스 상속
     5. 구현부의 상속 
     6. 문자열 변환 : toString() 구현
     7. Wrapper 타입 - auto-boxing
     8. 동일성 : equals(), hashcode() 구현
     9. 메모리 관리 : garbage collection
     10. 불변성
     11. 계약에 의한 설계 --> exception, error, assertion
     
     