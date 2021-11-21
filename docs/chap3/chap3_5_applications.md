
# 3.5 응용

 * 심볼 테이블은 빠른 탐색 알고리즘을 제공하는 필수적인 역할을 해오고 있다.
 * 이 절에서는 아래와 같은 대표적인 응용 예들을 살펴보자.
   * 사전 및 색인 클라이언트
   * 여러 파일들의 역순 색인을 생성해 내는 색인 클라이언트
   * 심볼 테이블을 이용한 희소 행렬 표현
 * 이 밖에도 그래프를 표현하거나 문자열을 처리하는 등의 알고리즘에도 심볼 테이블이 사용된다.
 * 유용한 도구로 심볼 테이블을 추상화하고 컴포넌트로 만드는 것을 생각해보자.

## 1. 어떤 심볼 테이블 구현을 사용해야 하는가?

| 데이터구조 | 최악 조건 search | 최악 조건 insert | 평균 search hit | 평균 insert | 키 인터페이스 | 메모리(바이트) |
| ------ | --- | --- | --- | --- | --- | --- |
| 순차 탐색(비 순차 리스트) | N | N | N/2 | N | equals() | 48N |
| 이진 탐색(순차 배열) | logN | 2N | logN | N/2 | compareTo() | 16N |
| 이진 트리 탐색(BST) | N | N | 1.39logN | 1.39logN | compareTo() | 64N |
| 2-3 트리 탐색(레드-블랙 BST) | 2logN | 2logN | 1.00logN | 1.00logN | compareTo() | 64N |
| 개별 체이닝(리스트 배열) | N | N | N/(2M) | N/M | equals(), hashCode() | 48N + 32M |
| 선형 탐지(병렬 배열) | N | N | < 1.50 | < 2.50 | equals(), hashCode() | 32N ~ 128N |

 * 보통의 전형적인 응용상황이라면 해시 테이블과 이진 탐색 트리 사이에서 알고리즘을 선택해야 한다는 것이 자명하다.
 * BST에 비해 해싱이 더 나은 점은 코드가 단순하고, 최적의 탐색 시간(상수)을 보여준다는 것이다.
 * 단, 이러한 성능은 효율적인 해시 함수를 만들 수 있다는 조건 하에 유효하다.
 * 해싱에 비해 BST가 더 나은 점은 인터페이스가 더 단순하게 추상화된다는 것과 레드-블랙 BST의 경우 최악 조건 성능을 보증할 수 있으며 더 넓은 범위의 작업들(rank, select, sort, 범위 탐색 등)을 지원한다는 것이다.
 * 몇몇이 유별나게 중요한 경우가 아니라면 경험적으로 해싱을 선택하게 된다.
 * 이 장에서는 예외적인 선택을 하는 경우를 같이 알아본다.
   * 키가 매우 긴 문자열인 경우 레드-블랙 BST보다 더 유연하고 심지어 해싱보다 빠른 데이터 구조를 만들 수 있다.
 * 몇 가지 맞춤형 수정을 가하면 다른 응용에도 유용하게 활용할 수 있다.

 * 기본 타입
   * 정수 숫자 키와 부동소수점 숫자 값을 키/값 쌍으로 하는 심볼 테이블을 생각해보자.
   * 표준적인 상황이라면 키와 값이 Wrapper 타입 Integer와 Double로서 저장된다.
   * 이렇게 되면 키/값 쌍에 접근하기 위해 두 개의 참조 변수 메모리가 필요하다. 
   * 수조 번의 탐색과 수백만 개의 키라면 문제가 되기 때문에 Key 타입 대신 primitive 타입을 이용하면 참조 하나를 절약하는 방법을 취할 수 있다.
   * 키와 연관된 값 또한 기본 타입인 경우 참조 하나를 더 절약할 수 있다.
   * 성능이 매우 중요한 상황이라면 이러한 기본 타입 활용을 고려해볼 만하다.
 * 중복 키
   * 심볼 테이블 구현에서는 중복 키의 존재 가능성을 특별히 신경 써야 한다.
   * 많은 응용에서 같은 키에 여러 값이 연관되는 것을 요구할 때가 있다.
   * 가령, 전자상거래 처리 시스템이라면 많은 거래 내역이 한 명의 고객, 즉 한 개의 키에 연관되어 있을 수 있다.
   * 그동안의 설명에서는 중복 키 문제의 관리 책임은 클라이언트가 지는 것으로 해왔다.
   * 데이터 구조를 수정하여 키/값 쌍이 중복된 키를 가질 수 있도록 구현하는 여러 방법이 존재한다.
     * 중복 키가 탐색되었을 때 복수의 연관된 값들 중 하나를 임의로 리턴할 수도 있고, 모든 값을 리턴하는 메서드를 추가할 수도 있다.
   * 중복 키를 허용하도록 그간 살펴보았던 심볼 테이블 구현들의 데이터 구조를 수정하는 것은 어렵지 않다. 
   * 다만 레드-블랙 BST의 경우에는 약간 까다로울 수 있다.
 * 자바 라이브러리
   * `java.util.TreeMap`과 `java.util.HashMap`은 각각 레드-블랙 BST와 개별 체이닝에 기반한 심볼 테이블 구현이다.
   * TreeMap은 순차 심볼-테이블 API와 rank(), select() 메서드를 직접적으로 지원하지는 않지만, 그러한 작업들을 효율적으로 수행하는 코드의 구현에 필요한 도구들을 제공한다.
   * HashMap은 가변 크기 배열을 사용해 부하 비율이 75% 정도가 되도록 강제한다.
   * `java.util.IdentityHashMap`은 "객체의 동일성" 대신 "참조의 동일성"을 이용한다. LinearProbingHashSymbolTable을 부하 비욜 2/3으로 사용하는 것과 유사하다.
 
## 2. SET(집합) API
 * 어떤 심볼 테이블 클라이언트는 키만 존재하고 값은 필요 없을 수도 있다.
 * 중복 키는 허용하지 않기로 했기 때문에 다음과 같은 API들로 필요한 작업들이 만족된다.
   * SET() : 빈 집합 생성
   * void add(Key key) : 집합에 키 추가
   * void delete(Key key) : 집합에서 키 제거
   * boolean contains(Key key) : 주어진 키가 집합에 존재하는가?
   * boolean isEmpty() : 집합이 비어있는가?
   * int size() : 집합에 포함된 키의 개수
   * String toString() : 집합에 대한 문자열 표현
 * SET에 합집합, 교집합, 여집합과 같은 일반적인 집합 연산을 추가하려면 좀 더 정교한 API가 필요하다.
   * 예를 들어 여집합 연산은 모든 가능한 키 값의 범위를 정의할 수 있는 방법이 있어야 한다.
 * 다른 심볼 테이블과 마찬가지로 SET도 순차/비순차 버전이 있다.
 * 만약 키가 Comparable 타입이라면 min(), max(), floor(), ceiling(), deleteMin(), deleteMax(), rank(), select()와 같은 메서드 등 순차 키를 위한 API들을 완전하게 구비할 수 있다.
 * 순차/비순차 버전 각각에 대한 이름을 SET과 HashSET으로 하기로 한다.
 

### 2.1 dedup(de-duplication; 중복 제거)
 * SET 또는 HashSET의 전형적인 응용 예로 입력으로부터 중복된 값들을 제거하는 클라이언트가 있을 수 있다.
 * 입력받은 문자열 키 집합을 보관하고 있으면서 새로 입력된 키가 이미 집합 안에 존재하면 무시하고, 존재하지 않으면 집합에 저장하고 출력한다.
 * 이렇게 하면 표준 입력으로 들어온 키가 표준 출력으로 출력하되 중복된 키는 출력되지 않게 된다.
 * 이러한 과정은 입력 스트림에서 들어오는 서로 다른 키의 양에 비례하는 추가적인 메모리를 소요한다.
 * 하지만 중복이 포함된 전체 키보다 훨씬 적은 양이다.

```java
public class DeDup {
   public static void main(String[] args) {
      HashSet<String> set = new HashSet<>();
      while (!StdIn.isEmpty()) {
          String key = StdIn.readString();
          if (!set.contains(key)) {
              set.add(key);
              StdOut.println(key);
          }
      }
   }
}
```

### 2.2 화이트 리스트와 블랙 리스트
 * 또 다른 전형적인 필터 응용 예로 별도의 파일에 키를 정해두고 입력으로부터 들어오는 키들 중 통과시킬 것을 선별하는데 이용하는 방식이 있다.
 * 가장 단순한 예는 "좋은" 키들을 정해놓는 `화이트 리스트`방식이다.
 * 화이트 리스트는 표준 출력으로 내보내지 않을 목록으로 사용할 수도 있고, 내보낼 목록으로 사용할 수도 있다.
 * `블랙 리스트`는 정반대의 접근 방법으로, "나쁜" 키들을 정해서 파일에 저장해 두고 필터링 방법으로 활용할 수 있다.
 * 전형적인 블랙 리스트 응용 예는 신용카드 회사에서 도난 카드를 걸러내거나 인터넷 라우터에서 방화벽을 통과시킬 패킷을 가려내는 경우 등이다.
 * 인터넷 라우터에서 블랙 리스트 방식을 사용하는 이유는 화이트 리스트 방식을 사용하기에는 허용할 주소 목록이 너무나 크고, 입력 스트림 용량을 제한할 수도 없는데 반해 빠른 응답성을 만족해야 하기 때문이다.

```java
public class WhiteFilter {
    public static void main(String[] args) {
        HashSet<String> set = new HashSet<>();
        In in = new In(args[0]);
        while (!in.isEmpty()) {
            set.add(in.readString());
        }
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            if (set.contains(word)) StdOut.println(word);
        }
    }
}

public class BlackFilter {
   public static void main(String[] args) {
      HashSet<String> set = new HashSet<>();
      In in = new In(args[0]);
      while (!in.isEmpty()) {
         set.add(in.readString());
      }
      while (!StdIn.isEmpty()) {
         String word = StdIn.readString();
         if (!set.contains(word)) StdOut.println(word);
      }
   }
}
```

## 3. 사전 클라이언트
 * 가장 기본적인 형태의 심볼 테이블 클라이언트는 연속해서 키를 삽입하여 테이블을 생성한 후, 그것을 기반으로 키 탐색 서비스를 제공하는 것이다.
 * 심볼 테이블을 동적으로 바뀔 수 있는 사전으로 활용하는 경우도 많이 있다.
 * 즉, 정보를 조회하는 것 뿐만 아니라 업데이트 하는 것도 쉽게 수행된다.
 * 응용 예시
   * 전화번호부 (key: 성명, value: 전화번호부)
   * 사전 (key: 단어, value: 단어의 정의)
   * 재무 정보 (key: 주식 종목 이름, value: 현재 가격), (key: 거래자 이름, value: 계죄번호), (key: 학번, value: 성적)
   * 유전체학(Genomics)
     * 심볼이 핵심적인 역할을 한다. DNA 핵산 성분을 A, C, T, G라는 네 가지 심볼로 표현한다.
     * 코돈(3중 핵산)과 아미노산의 관계; TTA는 류신, TCT는 세린 등으로 연관
     * 아미노산의 나열을 단백질과 연관 짓는다.
     * 유전체를 연구하는 사람들은 일상적으로 여러 종류의 심볼 테이블을 활용하여 지식을 구조화한다.
   * 실험 데이터 : 실험 데이터를 다루기 위해 심볼 테이블을 이용하여 효율적으로 데이터에 접근하고 구조화한다.
   * 컴파일러 : 숫자 대신 작업 명령과 메모리 위치(변수)에 심볼 이름을 부여, 심볼 테이블을 이용해 숫자와 이름을 연관시킴.
     * 프로그램 크기가 커짐에 따라 심볼 테이블 연산 시간이 프로그램 개발의 병목점으로 나타나 심볼 테이블 데이터 구조와 알고리즘을 개발하는 동인이 됨.
   * 파일 시스템
     * 컴퓨팅 시스템은 심볼 테이블을 이용하여 정기적으로 데이터를 정리하여 파일 시스템을 유지 관리한다.
     * 파일 시스템에 사용하는 심볼 테이블 예로 파일명과 그 내용을 연관시킨다.
   * 인터넷 DNS(domain name service)
     * 사람이 이해할 수 있는 도메인 이름과 IP 주소를 연관시켜 인터넷 상의 정보를 연결할 수 있는 기반이 되어준다.
     * 이 시스템을 이용해 사람이 쉽게 인터넷 주소를 사용하면서도 기계가 효율적으로 숫자 주소를 처리할 수 있다.
     * 인터넷 라우터에서 발생되는 초당 심볼 테이블 참조 횟수는 전 세계적으로 어마어마한 숫자이다.
     * 따라서 당연하게도 성능이 대단히 중요하다.
     * 또한 실시간으로 컴퓨터가 새롭게 인터넷에 연결되기 때문에 인터넷 라우터의 심볼 테이블은 동적으로 변경될 수 있어야 한다.
   * CSV(comma-separated value) 파일 형식
     * 아래 프로그램은 주어진 csv 포맷 데이터를 읽어 키/값 쌍 집합을 만들고 주어진 키에 연관된 값들을 내보낸다.

```java
public class LookupCSV {
    public static void main(String[] args) {
        In in = new In(args[0]);
        int keyField = Integer.parseInt(args[1]);
        int valField = Integer.parseInt(args[2]);
        SymbolTable<String, String> symbolTable = new SymbolTable<>();
        while (in.hasNextLine()) {
            String line = in.readLin();
            String[] tokens = line.split(",");
            String key = tokens[keyField];
            String val = tokens[valField];
            symbolTable.put(key, val);
        }
        while (!StdIn.isEmpty()) {
            String query = StdIn.readString();
            if (symbolTable.contains(query)) StdOut.println(symbolTable.get(query));
        }
    }
}
```

## 4. 색인(indexing) 클라이언트
 * 사전은 키 하나에 값 하나가 연관된다는 것을 가정한다는 것이 특징이다.
 * 따라서 각각의 키에 값을 하나씩 할당하여 연관-배열을 추상화하는 SymbolTable 데이터 타입을 바로 사용하면 된다.
 * 일반적으로는 어떤 키 하나에 여러 개의 값이 연관될 수 있다.
   * 예를 들어 각각의 코돈(codon)은 한 개의 아미노산에 연관되지만, 각각으 아미노산은 여러 개의 코돈에 연관된다.
 * 앞으로 키 하나에 여러 가지 값을 연관시킨 심볼 테이블을 `색인`이라 부르기로 한다.
 * 아래는 몇 가지 추가적인 예이다.
   * 상거래 정보 : 거래 계좌 번호가 키, 해당 계좌가 등장하는 거래 목록이 값이 된다.
   * 웹 검색 : 하나의 검색어마다 웹 사이트의 집합이라는 하나의 값이 연관된다.
   * 영화와 배우 : 영화 이름이 키, 출연 배우 목록이 값이 된다. 
 * 이러한 경우 여러 값들을 하나의 데이터 구조(가령 queue)에 저장하고, 그 데이터 구조를 값으로 하여 키를 연관시킴으로써 쉽게 색인을 만들 수 있다.
 * 반전된 색인
   * `반전된 색인`이라는 용어는 값을 이용해서 키를 찾는 경우를 의미한다.
   * 매우 큰 데이터를 대상으로 관심 있는 키의 위치를 알아내야 할 때가 있다.
   * 그러한 경우는 get()과 put() 작업이 뒤섞여 일어나는 전형적인 응용 예이다.
   * 이 때도 키를 그와 연관된 값이 저장된 위치의 집합(Set)과 연관시킨다.
   * 값이 저장된 위치는 응용 환경에 따라 달라진다.
     * 책이라면 페이지 목록
     * 프로그램이라면 줄 번호
     * 유전체라면 염기 서열에서의 순번

```java
public class LookupIndex {
    public static void main(String[] args) {
        In in = new In(args[0]); // 색인 데이터베이스
        String sp = args[1];     // 구분자
        SymbolTable<String, Queue<String>> st = new SymbolTable<String, Queue<String>>();
        SymbolTable<String, Queue<String>> ts = new SymbolTable<String, Queue<String>>();
        while (in.hasNextLine()) {
            String[] a = in.readLine().split(sp);
            String key = a[0];
            for (int i = 0; i < a.length; i++) {
                String val = a[i];
                if (!st.contains(key)) st.put(key, new Queue<String>());
                if (!ts.contains(val)) st.put(val, new Queue<String>());
                st.get(key).enqueue(val);
                ts.get(val).enqueue(key);
            }
        }
        while (!StdIn.isEmpty()) {
            String query = StdIn.readLine();
            if (st.contains(query)) {
                for (String s : st.get(query)) {
                    StdOut.println("  " + s);
                }
            }
            if (ts.contains(query)) {
                for (String s : ts.get(query)) {
                    StdOut.println("  " + s);
                }
            }
        }
    }
}
```

```java
import java.io.File;

public class FileIndex {
    public static void main(String[] args) {
        SymbolTable<String, Set<File>> st = new SymbolTable<String, Set<File>>();
        for (String filename : args) {
            File file = new File(filename);
            In in = new In(file);
            while (!in.isEmpty()) {
                String word = in.readString();
                if (!st.contains(word)) st.put(word, new Set<File>());
                Set<File> set = st.get(word);
                set.add(file);
            }
        }
        while (!StdIn.isEmpty()) {
            String query = StdIn.readString();
            if (st.contains(query)) {
                for (File file : st.get(query)) {
                    StdOut.println("  " + file.getName());
                }
            }
        }
    }
}
```

## 5. 희소 벡터(Sparse Vectors)
 * 희소 벡터는 심볼 테이블이 과학 연구와 수학 계산에 어떠한 중요한 역할을 하는지 보여준다.
 * 기초적인 계산이 병목점이 되는 실질적인 응용 상황을 알아보고 난 후, 심볼 테이블을 이용해 그 병목점을 제거하고 더욱 큰 문제도 다룰 수 있게 되는 것을 알아보자.
 * 우리가 알아볼 계산은 구글의 페이지 랭크 알고리즘의 근간이 되는 연산인 행렬과 벡터의 곱셈 연산이다.
 * 행렬과 벡터의 곱셈 연산은 그 결과로 벡터가 나온다. 
 * 행렬의 크기를 N x N, 곱하는 벡터의 크기를 N이라 하면, N개의 결과 벡터 항목 각각에 대해 N번의 곱셈이 필요하기 때문에 전체 수행 시간은 N^2에 비례한다.
 * 또한 행렬을 저장하기 위한 공간 소요량도 N^2에 비례한다.
 * 구글의 경우 N이 인터넷 상 모든 웹페이지의 개수가 된다. 페이지 랭크 알고리즘이 만들어지던 시기에 N은 수백, 수천억에 달했고 지금은 훨씬 더 크다.
 * 다행히도, 다루어야 할 행렬이 희소 행렬인 경우가 많다.
 * 즉, 행렬의 항목 중 아주 많은 부분이 0값일 수 있다.
 * 구글의 예에서는 각 행에서 0이 아닌 항목의 개수가 평균적으로 작은 상수값에 지나지 않는다.
 * 즉, 거의 모든 웹페이지가 겨우 몇 개의 외부 참조 링크를 가지고 있다.
 * 따라서 행렬을 `희소 벡터(Sparse Vector)` 데이터 타입의 배열로 표현할 수 있다.
 * 구현할 SparseVector 데이터 타입은 해시 테이블의 클라이언트가 된다.

```java
package chap35;

import chap13.Queue;

import java.util.Arrays;

public class SparseVector {

    public int dimension;
    private HashSTintKeysdoubleValues hashTable;

    public SparseVector(int dimension) {
        this(dimension, 997);
    }

    public SparseVector(int dimension, int initialSize) {
        hashTable = new HashSTintKeysdoubleValues(initialSize);
        this.dimension = dimension;
    }

    public int size() {
        return hashTable.size();
    }

    public double get(int key) {
        if (!hashTable.contains(key)) return 0;
        else return hashTable.get(key);
    }

    public void put(int key, double value) {
        if (value == 0) {
            hashTable.delete(key);
            return;
        }
        hashTable.put(key, value);
    }

    public void delete(int key) {
        hashTable.delete(key);
    }

    public SparseVector plus(SparseVector sparseVector) {
        if (dimension != sparseVector.dimension) throw new IllegalArgumentException("Sparse vector dimensions must be the same.");
        SparseVector result = new SparseVector(dimension);
        for (int key : hashTable.keys()) {
            result.put(key, get(key));
        }
        for (int key : sparseVector.hashTable.keys()) {
            double sum = get(key) + sparseVector.get(key);
            if (sum != 0) {
                result.put(key, sum);
            } else {
                result.delete(key);
            }
        }
        return result;
    }

    public double dot(SparseVector sparseVector) {
        if (dimension != sparseVector.dimension) throw new IllegalArgumentException("Sparse vector dimensions must be the same.");
        double sum = 0;
        if (size() <= sparseVector.size()) {
            for (int key : hashTable.keys()) {
                if (sparseVector.hashTable.contains(key)) sum += get(key) * sparseVector.get(key);
            }
        } else {
            for (int key : sparseVector.hashTable.keys()) {
                if (hashTable.contains(key)) {
                    sum += get(key) * sparseVector.get(key);
                }
            }
        }
        return sum;
    }

    public double dot(double[] that) {
        double sum = 0.0;
        for (int key: hashTable.keys()) {
            sum += this.get(key) * that[key];
        }
        return sum;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int key : hashTable.keys()) {
            stringBuilder.append("(").append(key).append(", ").append(get(key)).append(") ");
        }
        return stringBuilder.toString();
    }

    // Optimized hash map with primitive keys and values, not requiring autoboxing and unboxing
    private class HashSTintKeysdoubleValues {

        private int keysSize;
        private int size;
        private int[] keys;
        private double[] values;

        private final static int EMPTY_KEY = Integer.MIN_VALUE;

        private final int[] PRIMES = {
                1, 1, 3, 7, 13, 31, 61, 127, 251, 509, 1021, 2039, 4093, 8191, 16381,
                32749, 65521, 131071, 262139, 524287, 1048573, 2097143, 4194301,
                8388593, 16777213, 33554393, 67108859, 134217689, 268435399,
                536870909, 1073741789, 2147483647
        };

        private int lgM;

        private HashSTintKeysdoubleValues(int size) {
            this.size = size;
            keys = new int[size];
            values = new double[size];
            for (int i = 0; i < size; i++) {
                keys[i] = EMPTY_KEY;
            }
            lgM = (int) (Math.log(size) / Math.log(2));
        }

        public int size() {
            return keysSize;
        }

        public boolean isEmpty() {
            return keysSize == 0;
        }

        private int hash(int key) {
            int hash = Integer.valueOf(key).hashCode() & 0x7fffffff;
            if (lgM < 26) hash = hash % PRIMES[lgM + 5];
            return hash % size;
        }

        private double getLoadFactor() {
            return keysSize / (double) size;
        }

        private void resize(int newSize) {
            HashSTintKeysdoubleValues tempHashTable = new HashSTintKeysdoubleValues(newSize);
            for (int i = 0; i < size; i++) {
                if (keys[i] != EMPTY_KEY) {
                    tempHashTable.put(keys[i], values[i]);
                }
            }
            keys = tempHashTable.keys;
            values = tempHashTable.values;
            size = tempHashTable.size;
        }

        public boolean contains(int key) {
            if (key == EMPTY_KEY) throw new IllegalArgumentException("Invalid Key");
            for (int tableIndex = hash(key); keys[tableIndex] != EMPTY_KEY; tableIndex = (tableIndex + 1) % size) {
                if (keys[tableIndex] == key) return true;
            }
            return false;
        }

        public double get(int key) {
            if (key == EMPTY_KEY) throw new IllegalArgumentException("Invalid Key");
            for (int tableIndex = hash(key); keys[tableIndex] != EMPTY_KEY; tableIndex = (tableIndex + 1) % size) {
                if (keys[tableIndex] == key) {
                    return values[tableIndex];
                }
            }
            return EMPTY_KEY;
        }

        public void put(int key, double value) {
            if (key == EMPTY_KEY) throw new IllegalArgumentException("Invalid Key");
            if (keysSize >= size / (double) 2) {
                resize(size * 2);
                lgM++;
            }
            int tableIndex;
            for (tableIndex = hash(key); keys[tableIndex] != EMPTY_KEY; tableIndex = (tableIndex + 1) % size) {
                if (keys[tableIndex] == key) {
                    values[tableIndex] = value;
                    return;
                }
            }
            keys[tableIndex] = key;
            values[tableIndex] = value;
            keysSize++;
        }

        public void delete(int key) {
            if (key == EMPTY_KEY) throw new IllegalArgumentException("Invalid Key");
            if (!contains(key)) return;
            int tableIndex = hash(key);
            while (keys[tableIndex] != key) {
                tableIndex = (tableIndex + 1) % size;
            }
            keys[tableIndex] = EMPTY_KEY;
            values[tableIndex] = EMPTY_KEY;
            keysSize--;

            tableIndex = (tableIndex + 1) % size;
            while (keys[tableIndex] != EMPTY_KEY) {
                int keyToRedo = keys[tableIndex];
                double valueToRedo = values[tableIndex];
                keys[tableIndex] = EMPTY_KEY;
                values[tableIndex] = EMPTY_KEY;
                keysSize--;

                put(keyToRedo, valueToRedo);
                tableIndex = (tableIndex + 1) % size;
            }

            if (keysSize > 1 && keysSize <= size / (double) 8) {
                resize(size / 2);
                lgM--;
            }
        }

        public int[] keys() {
            Queue<Integer> keySet = new Queue<>();
            for (int key : keys) {
                if (key != EMPTY_KEY) keySet.enqueue(key);
            }
            int[] keys = new int[keySet.size()];
            for (int i = 0; i < keys.length; i++) {
                keys[i] = keySet.dequeue();
            }
            Arrays.sort(keys);
            return keys;
        }

    }
}
```
 * i행 j열 항목에 접근할 때 a[i][j] 대신 a[i].put(j, val) 또는 a[i].get(j)를 이용해 값을 쓰거나 읽는다.
 * 이 클래스를 이용하면 배열을 사용할 때보다 행렬과 벡터의 곱 연산을 더 단순하게 수행할 수 있다.
 * 또한, 실행 소요 시간이 N + '0이 아닌 항목의 개수'에 비례하게 된다.
 * 행렬의 크기가 작거나 희소 특성이 없다면 오히려 오버헤드 비용의 비용이 매우 커지게 된다.
 * 하지만 크기가 매우 큰 희소 행렬에서는 완전히 다른 효과가 있다.

 * 구글의 검색 서비스는 기본적으로 그래프 처리 응용이다. 
 * 이 응용은 대규모 희소 행렬을 생성하여 활용한다.
 * 행렬만 주어진다면 페이지 랭크 계산은 행렬/벡터 곱의 반복 작업에 지나지 않는다.
 * 결과 벡터를 다시 입력 벡터로 바꾸어가며 결과가 수렴할 때가지 반복한다.
 * 따라서 이러한 응용에 SparseVector 클래스를 적용하면 엄청난 시간을 절감할 수 있다.