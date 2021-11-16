
# Chapter 3. 탐색 알고리즘

## Introduction

 * 많은 정보를 효율적으로 탐색하는 것은 정보처리에 있어 꼭 필요한 기초 작업.
 * 수십 년 동안 다양한 응용 환경에서 효율성이 검증된 전통적인 탐색 알고리즘들을 알아보자.
 * 어떤 정보(또는 값)를 저장하고, 그 정보에 연결된 어떤 키를 통해 다시 해당 정보를 찾아내는 추상적인 메커니즘을 심볼 테이블이라 한다.
 * 심볼 테이블의 개념은 책 뒤의 색인과 닮아 있다. 국어 사전에서는 단어가 키가 되고, 그 단어에 대한 설명문이 그 키의 값(정보)이 된다.
 * 기본 API 정의와 기본적인 구현 두 가지를 살펴본 뒤, 심볼 테이블을 효율적으로 구현하기 위한 데이터 구조 세 가지를 알아보자.
   * 이진 탐색 트리 (Binary Search Tree)
   * 레드-블랙 트리 (Red-Black Tree)
   * 해시 테이블 (Hash Table)
   
## 3.1 심볼 테이블

### 정의

> `심볼 테이블`은 키/값 쌍에 대한 데이터 구조로, 삽입과 탐색을 지원한다. 
> 삽입(put)은 새로운 쌍을 테이블에 저장하는 것이고, 
> 탐색(get)은 주어진 키에 연관된 값을 찾는 것이다. 

> 여러 종류의 심볼 테이블 구현은 보통 get(), put()의 구현 방식과 그 기반 데이터 구조에 따라 특정지어진다.

 * 주요 목적 : 키를 값과 연관 시키는 것
 * 주요 기능
   * 키/값 쌍을 심볼 테이블에 등록
   * 저장된 키/값 쌍들 중 연관된 값을 탐색
   
### 응용 예시

| 응용 | 탐색의 목적 | 키 | 값 |
| --- | --- | --- | --- |
| 사전 | 단어의 정의 찾기 | 단어 | 정의문 |
| 색인 | 연관된 페이지 찾기 | 색인어 | 페이지 번호 목록 |
| 파일 공유 | 내려받을 음악 찾기 | 곡명 | 컴퓨터 ID |
| 계좌 관리 | 거래 처리 | 계좌 번호 | 상세 거래 내용 |
| 웹 검색 | 연관된 웹 페이지 찾기 | 키워드 | 웹 페이지 목록 |
| 컴파일러 | 타입과 값 찾기 | 변수 이름 | 타입과 값 |

### API

`public class ST<Key, Value>`   

| API | 설명 |
| --- | --- |
| ST() | 심볼 테이블을 생성한다. |
| void put(Key key, Value val) | 키/값 쌍을 테이블에 넣는다. 만약 값이 null이면 해당 키를 삭제한다. |
| Value get(Key key) | 키에 연관된 값을 얻는다. (키가 없으면 null을 리턴한다.) |
| void delete(Key key) | 키(와 값)를 테이블에서 삭제한다. |
| boolean contains(Key key) | 키와 연관된 값이 존재하는가? |
| boolean isEmpty() | 테이블이 비어 있는가? |
| int size() | 테이블에 저장된 키/값 쌍의 개수를 리턴한다. |
| Iterable<Key> keys() | 테이블에 저장된 모든 키를 리턴한다. |

 * 코드의 일관성, 명료성, 사용성을 높이기 위한 설계 요건
   * 제네릭 : 키를 별도의 타입으로 취급한다. 키가 Comparable을 지원하는 일반적인 경우의 기능 확장도 알아본다.
   * 중복 키 처리
     * 키 하나에는 하나의 값만 연관된다. (즉, 한 테이블에 중복 키는 존재하지 않는다.)
     * 클라이언트가 존재하는 키에 대해 키/값 쌍을 새로 삽입할 경우 새로 삽입되는 값이 기존 값을 대체한다.
     * 심볼 테이블을 배열로 생각하면, 키가 인덱스이고 값이 배열 항목이 된다. 이런 방식으로 동작하는 배열을 `추상 연관 배열`이라 한다.
   * Null 키 : 키는 절대 null이어서는 안된다. null을 키로 사용할 경우 RuntimeException이 발생한다.
   * Null 값 : 키에 연관된 값으로 null을 사용하지 않는다. 이러한 관례는 테이블에 존재하지 않는 키에 대해 get()이 null을 리턴한다는 API 정의를 지원한다.
     * contains(key) -> get(key) != null
     * delete(key) -> put(key, null)
   * 삭제
     * Lazy method : 해당 키를 null로 설정하고, 나중에 그런 키들을 삭제함.
     * Eager method : 테이블에서 키를 즉시 삭제함. 이 경우 null 값 설정을 통한 키 삭제라는 개념을 사용하지 않음.
   * 편의 메서드
     * ```void delete(Key key) { put(key null); }```
     * ```boolean contains(Key key) { return get(key) != null; }```
     * ```boolean isEmpty() { return size() == 0; }```
   * 반복자 : 클라이언트가 테이블의 모든 키/값 쌍들을 순회할 수 있도록 함.
     * Iterable<Key> 인터페이스를 구현함. -> iterator(), hasNext(), next() 구현.
     * Iterable<Key> 객체를 리턴하는 keys() 메서드를 정의함. 
   * 키의 동일성
     * 어떤 주어진 키가 심볼 테이블에 이미 존재하는지 여부를 검사하는 동작은 `객체의 동일성` 개념에 의존함. 
     * 즉, 키의 equals() 메서드를 사용하며, 클라이언트가 직접 정의한 타입의 키라면 직접 equals()를 오버라이딩함.
     * 키 타입을 정의할 때는 일관성 보증을 위해 불변 타입으로 정의하는 것이 바람직함. 

 ### 순차 심볼 테이블
 * Comparable 객체인 키를 사용해서 심볼 테이블의 키를 순차적으로(정렬된 상태로) 저장함.
 * put(), get() 작업이 더 효율적으로 수행됨.


`public class ST<Key extends Comparable<Key>, Value>`

| API | 설명 |
| --- | --- |
| ST() | 순차 심볼 테이블을 생성한다. |
| void put(Key key, Value val) | 키/값 쌍을 테이블에 넣는다. 만약 값이 null이면 해당 키를 삭제한다. |
| Value get(Key key) | 키에 연관된 값을 얻는다. (키가 없으면 null을 리턴한다.) |
| void delete(Key key) | 키(와 값)를 테이블에서 삭제한다. |
| boolean contains(Key key) | 키와 연관된 값이 존재하는가? |
| boolean isEmpty() | 테이블이 비어 있는가? |
| int size() | 테이블에 저장된 키/값 쌍의 개수를 리턴한다. |
| Key min() | 가장 작은 키를 리턴한다. |
| Key max() | 가장 큰 키를 리턴한다. |
| Key floor(Key key) | key보다 작거나 같은 가장 큰 키를 리턴한다. |
| Key ceiling(Key key) | key보다 크거나 같은 가장 작은 키를 리턴한다. |
| int rank(Key key) | key보다 작은 키의 개수를 리턴한다. |
| Key select(int k) | k번째 순위의 키를 리턴한다. |
| void deleteMin() | 가장 작은 키를 삭제한다. |
| void deleteMax() | 가장 큰 키를 삭제한다. |
| int size(Key lo, Key hi) | [lo..hi] 범위에 속하는 모든 키의 개수 |
| Iterable<Key> keys(Key lo, Key hi) | [lo..hi] 범위에 속하는 모든 키를 정렬된 상태로 리턴한다. |
| Iterable<Key> keys() | 테이블에 저장된 모든 키를 정렬된 상태로 리턴한다. |

 * 예외 상황 : 어떤 메서드가 키를 리턴하지 못할 상황이라면, 예외를 발생시키기로 한다.
 * 키의 동일성
   * 자바에서는 compareTo()와 equals()가 서로 일관성을 가지도록 하는 것이 보통의 관례이다.
   * 잠재적인 모호성 문제를 피하기 위해 순차 심볼 테이블을 구현할 때는 compareTo()만 사용한다.
 * 비용 모델
   * 탐색 비용 모델 : 심볼 테이블 구현의 성능을 알아볼 때 비교 연산의 횟수를 헤아린다.
   * 주어진 키가 심볼 테이블에 있는지 찾을 때, 비순차 심볼 테이블에서 equals()를 상요하든 순차 심볼 테이블에서 compareTo()를 사용하든 이 작업을 비교 연산으로서 비용 모델에 반영한다.
 
 ### 테스트 클라이언트의 예시

```java

public class TestClientExample {
    
   public static void main(String[] args) {
      ST<String, Integer> st;
      st = new ST<String, Integer>();

      for (int i = 0; !StdIn.isEmpty(); i++) {
         String key = StdIn.readString();
         st.put(key, i);
      }

      for (String s : st.keys()) {
         StdOut.println(s + " " + st.get(s));
      }
   }
   
}
```
```
키  S E A R C H E X A M  P  L  E
값  0 1 2 3 4 5 6 7 8 9 10 11 12

 비순차 예시       순차
 L  11           A  8
 P  10           C  4
 M  9            E  12
 X  7            H  5
 H  5            L  11
 C  4            M  9
 R  3            P  10
 A  8            R  3
 E  12           S  0
 S  0            X  7
```

### 성능 측정 클라이언트 예시

 * FrequencyCounter는 문자열의 나열을 입력받아 각 문자열의 등장 빈도를 헤아린 후, 전체 문자열을 순회하면서 가장 많이 등장한 단어를 출력한다.
 * 이 클라이언트는 사전(dictionary) 응용의 예시.
 * 주어진 텍스트에서 어떤 단어가 가장 많이 등장하는가라는 질문에 답을 구한다.
 * 큰 입력에 대한 성능을 분석할 때에는 두 가지 지표를 고려한다.
   * 매 단어의 입력이 탐색 키로 이용되기 때문에 중복을 포함하여 텍스트 전체 단어의 수가 얼마나 되는지가 영향을 미친다.
   * 서로 다른 단어의 수, 즉 단어의 종류가 몇 가지인지도 중요하게 고려해야 한다.
 * 위 두 가지 지표는 심볼 테이블의 최종 크기를 결정한다.

```
                 tinyTale.txt                 tale.txt                leipzig1M.txt
                 단어      종류                단어     종류             단어          종류
전체 단어          60       20                 135,639  10,679          21,191,455   534,580
8자 이상 단어       3        3                  14,350   5,131            4,239,597  299,593
10자 이상 단어      2        2                  4,582    2,260            1,610,829  165,555
```

```java

public class FrequencyCounter {
    
    public static void main(String[] args) {
        int minlen = Integer.parseInt(args[0]);
        ST<String, Integer> st = new ST<String, Integer>();
        while (!StdIn.isEmpty()) {
            // calculate frequency of each word
            String word = StdIn.readString();
            if (word.length() < minlen) continue;
            if (!st.contains(word)) {
                st.put(word, 1);
            } else {
                st.put(word, st.get(word) + 1);
            }
        }
        
        // find most frequent word
        String max = "";
        st.put(max, 0);
        for (String word : st.keys()) {
            if (st.get(word) > st.get(max)) {
                max = word;
            }
        }
        StdOut.println(max + " " + st.get(max));
    }
    
}

```

```
$ java FrequencyCounter 1 < tinyTale.txt
$ it 10

$ java FrequencyCounter 8 < tale.txt
$ business 122

$ java FrequencyCounter 10 < leipzig1M.txt
$ government 24763
```

 * 매우 큰 텍스트에 대해 FrequencyCounter 같은 응용프로그램을 수행하려면 효율적으로 구현된 심볼 테이블이 필요하다.
 * 많은 심볼 테이블 응용이 다음과 같은 특징을 공통으로 가지고 있다.
   * 탐색, 삽입 작업이 서로 섞여 있다.
   * 서로 다른 키의 개수가 적지 않다. (테이블 크기가 작지 않다.)
   * 삽입 작업보다 탐색 작업이 훨씬 더 많을 가능성이 크다.
   * 탐색, 삽입이 일어나는 형태를 예측할 수는 없지만 무작위스럽지는 않다.