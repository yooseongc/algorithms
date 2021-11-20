
# 3.4 해시 테이블

 * 만약 키가 정수 값이라면 배열을 이용해서 비순차 심볼 테이블을 만들 수 있다.
 * 즉, 키를 배열의 인덱스로 사용하여 키 i와 i번째 배열 항목을 연관시켜 키 값으로 즉시 항목 데이터에 접근할 수 있게 한다.
 * 이 절에서 다루는 해싱(hashing)은 이를 작은 정수가 아닌 더 복잡한 데이터 타입을 키로 사용할 수 있도록 확장한 것이다.
 * 키/값 쌍을 배열을 이용해 참조하기 위해서 키를 산술 연산을 이용해 배열 인덱스로 변환한다.
 * 해싱을 이용하는 탐색 알고리즘은 두 부분으로 나누어진다.
   * 첫 번째는 탐색 키를 배열 인덱스로 변환하는 함수인 `해시 함수`이다.
   * 이상적으로는 서로 다른 키는 서로 다른 인덱스에 매핑되겠지만, 그렇지 않은 경우도 존재한다.
   * 두 번째는 충돌을 해소하는 단계(collision-resolution)이다. 
   * 충돌 해소를 위한 방법으로 `개별 체이닝(separate chaining)`, `선형 탐지(linear probing)`의 두 가지 접근 방법이 있다.
 * 해싱은 서로 대립되는 시간 특성과 공간 특성 사이에서 균형점을 추구하는 전통적인 사례 중 하나이다.
   * 메모리를 무한히 사용할 수 있다면 모든 키에 대한 배열을 만들면 어떤 탐색이든 단 한 번의 메모리 접근으로 완료된다.
   * 메모리에 심한 제한이 있다면 비순차 배열에서 순차 탐색을 함으로써 메모리는 최소한으로 사용하되 시간을 더 많이 사용하게 된다.
   * 해싱 알고리즘에서는 코드를 재작성하는 것이 아니라 관련 파라미터를 조정함으로써 시간과 공간 사이의 균형점을 선택할 수 있다.
   * 파라미터 값을 결정하기 위해 전통적인 확률 이론을 사용한다.
 * 해싱을 사용하면 심볼 테이블을 이용한 탐색과 삽입 작업이 전형적인 응용 환경에서 상수 시간(분할 상쇄(amortized) 시간 기준)에 완료되기 때문에 심볼 테이블의 구현으로 많이 사용된다.

## 1. 해시 함수
 * 해시 함수 : 키를 배열의 인덱스 값으로 변환하는 함수
 * M개의 키/값 쌍을 가지는 배열을 이용한다면 임의의 키를 그 배열의 인덱스 범위 M보다 작은 값으로 변환할 수 있는 해시 함수가 필요하다.
 * 즉, 정수 범위 [0, M-1]를 변환 범위로 하면서, 계산 하기도 쉽고 키를 배열에 균일하게 분포시켜주는 해시 함수를 찾아야 한다.
 * 균일하게 분포시킨다는 것은 인덱스 값으로 0 ~ M-1 사이의 정수가 부여될 확률이 모든 키에 대해 동등해야 한다는 것이다.
 * 전형 적인 예제들
   * 양의 정수
     * 정수 해싱에 가장 흔하게 사용되는 방법은 모듈러 해싱(modular hashing)이다. 
     * 즉, 양의 정숫값인 키 k를 배열 크기 M으로 나눈 나머지를 배열 인덱스로 사용한다.
     * 이 때 배열 크기 M은 소수(prime number)를 선택하여 키의 모든 비트들이 변환에 기여하여 균일 분포를 노린다.
   * 부동소수점 숫자
     * 만약 키가 0부터 1 사이의 실수라면 M을 곱해서 가장 가까운 정수로 바꾸어 0에서 M-1 사이의 인덱스로 바꿀 수 있다.
     * 다만 이 방법은 키의 최하위 비트는 거의 아무런 역할을 하지 못한다.
     * 자바에서는 이를 해결하기 위해 부동소수점 숫자의 이진수 표현에 모듈러 해싱을 적용한다.
   * 문자열
     * 모듈러 해싱은 문자열처럼 길이가 긴 키에도 적용할 수 있다.
     * ```
       int hash = 0;
       for (int i = 0; i < s.length(); i++) {
           hash = (R * hash + s.charAt(i)) % M;
       }
       ```
     * 위 코드는 String 타입 변수 s에 대한 해시를 계산한다.
     * charAt() 메서드는 i번째 문자의 char값(자바에서는 16비트 양의 정수; UTF-16)을 리턴한다.
     * 만약 R이 모든 문자값보다 크다면 위 코드의 연산 결과는 String s를 R진수인 N자릿수의 정수로 보고 M으로 나눈 값을 구하는 것과 같다. 
     * 고전적인 호너법(Horner's method)을 이용하면 N번의 곱셈, 덧셈, 나머지 연산으로 계산으로 계산을 완료할 수 있다.
     * 만약 R값이 충분히 작아서 오버플로우가 발생하지만 않는다면 계산 결과는 의도대로 0에서 M-1 사이의 정수가 된다.
     * R로 31과 같은 작은 소수를 사용한다면 문자의 모든 비트가 골고루 영향을 미치게 할 수 있다.
   * 복합 키
     * 만약 여러 개의 정수 필드로 이루어진 데이터 타입이 키로 사용된다면 String을 처리했던 것과 마찬가지 방법으로 모든 값을 이어 붙여서 처리할 수 있다.
     * 예를 들어 3개의 정수 필드를 가진 Date 타입은 ```int hash = (((day * R + month) % M) * R + year) % M```과 같이 계산할 수 있다.
     * 만약 R이 충분히 작아서 오버플로우가 발생하지 않는다면 의도대로 0에서 M-1 사이의 정수가 계산된다.
     * 이 수식에서 R 값을 요령껏 선택하면 (예를 들어 31) 내부의 % M 연산을 생략할 수도 있다. (예를 들어 month는 12 이하이므로 나머지 연산이 필요 없다.)
   * 자바의 해시 함수 관례
     * 자바에서는 모든 데이터 타입들이 32비트 정수 해시 값을 리턴하는 hashCode()라는 메서드를 상속받게 함으로써 해시 사용을 편리하게 해준다.
     * hashCode()는 반드시 해당 데이터 타입에서 객체의 동일성과 일관성을 유지하도록 구현되어야 한다.
     * 즉, a.equals(b)가 참이라면 a.hashCode() == b.hashCode()가 성립해야 한다.
     * 반대로 두 같은 데이터 타입 객체의 해시코드 값이 다르다면 두 객체는 동일하지 않아야 한다.
     * 다만, 두 같은 데이터 타입 객체의 해시코드 값이 같다고 해서 두 객체가 반드시 동일해야 하는 것은 아니다.
     * 두 객체의 동일성은 hashCode()가 아니라 equals()를 이용하여 검사되어야 한다.
     * 위와 같은 관례는 심볼 테이블에서 hashCode() 메서드를 이용할 수 있기 위한 기본적인 요건이다.
     * 이러한 요건은 커스텀 데이터 타입을 해시 테이블에 이용하기 위해 hashCode()와 equals() 두 메서드 모두 오버라이딩 해야 함을 의미한다.
     * 디폴트 구현은 키 객체의 메모리 주솟값을 리턴하는 것이기 때문에 목적하는 응용환경에 딱 맞을 가능성은 높지 않다.
     * 자바는 흔히 사용되는 많은 타입들에서 디폴트 구현 대신 그 타입에 맞는 방식으로 hashCode()를 오버라이딩 하고 있다.
 * hashCode()값을 배열 인덱스로 변환하기
   * 최종 목적은 32비트 정수(해시값)가 아니라 배열의 인덱스이다.
   * 따라서 0에서 M-1 사이의 정수를 만들기 위해 hashCode() 결과에 모듈러 해싱을 적용하여 배열 인덱스로 변환한다.
   * ```private int hash(Key x) { return (x.hashCode() & 0x7fffffff) % M; }```
   * 위 코드는 부호 비트를 마스킹하여 제거하고(32비트 숫자를 31비트 Unsigned Integer로 변환) M으로 나눈 나머지를 구한다.
   * 보통 해시 테이블의 크기 M은 소수(prime number)를 선택하여 해시 코드의 모든 비트들이 영향을 미치도록 한다.

```java
public class Transaction {
    private final String who;
    private final Date when;
    private final double amount;
    
    public int hashCode() {
        int hash = 17;
        hash = 31 * hash + who.hashCode();
        hash = 31 * hash + when.hashCode();
        hash = 31 * hash + ((Double) amount).hashCode();
        return hash;
    }
}
```
 * double과 같은 primitive type 인스턴스 변수는 해당 래퍼 클래스를 이용해야 hashCode() 메서드를 이용할 수 있다.
 * 각 변수의 해시 코드에 곱해지는 값(위 예제의 31)에 어떤 값을 쓰는지는 그렇게 중요하지 않다.
 * 만약 해시 코드 계산에 시간이 오래 걸린다면 각 키 별로 해시 값을 캐싱해 두는 것이 바람직하다.
 * 즉, 키 데이터 타입에 인스턴스 변수 hash를 두고 각 키 객체마다 hashCode()의 값을 저장하게 한다.
 * hashCode()가 처음 불릴 때는 해시 코드를 모두 계산할 수 밖에 없지만 다음 번 호출부터는 계산 없이 이미 구해진 해시 값을 바로 사용할 수 있다.
 * 자바의 String 객체 hashCode() 메서드는 캐싱을 사용하여 구현하고 있다.

 * 좋은 해시 함수라면, 해당 데이터 타입에 대해 다음의 세 가지 주요 특성을 만족해야 한다.
   * 일관성이 있어야 한다. 즉, 같은 키라면 같은 해시값을 가져야 한다.
   * 효율적으로 계산될 수 있어야 한다.
   * 해시 값이 가능한 키 값 범위에 균일하게 분포해야 한다.
 * 자바에서 제공되는 해시 기능들은 전문가들의 노력으로 위 요구 조건을 모두 만족시키고 있다.
 * 하지만, 성능이 중요한 응용 환경이라면 해시 함수의 품질이 흔히 나타날 수 있는 "고전적인 성능 상 버그"이기 때문에 함수의 품질에 주의를 기울여야 한다.
 * 즉, 모든 것이 올바르게 동작하지만 기대했던 것보다 훨씬 더 느리다면, 키의 모든 비트가 균일하게 해시 값에 기여하지 못하고 편중되는 것이 원인일 수 있다.
 * 해싱을 사용할 때는 이러한 특성이 충족된다는 것을 기본으로 가저한다. 

> 가정 J (균일 해싱 가정) : 우리가 사용하는 해시 함수는 키의 해시 값을 서로 독립적으로 정수 0과 M-1 사이에 균일하게 분포시킨다.
 * 이 가정은 두 가지 이유에서 해싱에 대한 유용한 사고방법이다.
   * 해시 함수를 설계함에 있어 좋은 가이드가 된다. (충돌이 많이 일어날 수도 있는 임의적인 해시 함수 선택을 막아준다.)
   * 해시 알고리즘의 성능 가설을 만드는 것을 도와준다. (해시 함수가 가정 J를 만족하지 않더라도 예측된 성능을 달성하는지 실험적으로 검증할 수 있다.)

## 2. 충돌 처리(collision resolution)
 * 해시 함수가 서로 다른 두 개 이상의 키를 같은 해시 값으로 변환해 버렸을 때 인덱스 하나에 여러 개의 키를 삽입해야 하는 난감한 상황을 해소하는 것
 * 직접적이고 일반적인 처리 방법은 크기 M인 해시 배열 인덱스의 항목 각각에 연결 리스트를 두어 해시 값이 같은 복수의 항목들을 저장하는 것.
 * M > N인 경우, 즉 해시 테이블의 크기가 저장할 키/값 쌍 개수보다 큰 경우 해시 값 충돌시 이미 존재하는 빈 공간에 의존하여 충돌을 해소하는 방법도 있다.
 * 전자를 `개별 체이닝 해싱`, 후자를 `개방형 주소 지정 해싱`이라고 한다.

### 2.1 개별 체이닝 해싱
 * 크기 M인 해시 배열 인덱스의 항목 각각에 연결 리스트를 두어 해시 값이 같은 복수의 항목들을 저장하여 충돌을 처리하는 알고리즘.
 * 기본적인 아이디어는 개별 연결 리스트의 길이가 작아서 두 단계의 탐색(해시 탐색과 리스트 탐색)이 이루어지더라도 충분히 빨리 끝날 수 있도록 M의 크기를 크게 잡는 것.
 * 기초적인 연결 리스트를 이용하여 앞서 본 SequentialSearchSymbolTable을 개별-체이닝을 사용하도록 확장 구현할 수 있다.
 * 더 단순한 방법으로는 비효율적이긴 하지만 M개 각각의 인덱스에 대해서 그 인덱스에 저장되는 키만을 위한 심볼 테이블을 만드는 것이다.
 * 이후 살펴볼 SeparateChainingHashSymbolTable은 내부적으로 SequentialSearchSymbolTable 객체의 배열을 생성하여 관리한다.
 * SeparateChainingHashSymbolTable의 get(), put() 메서드는 SequentialSearchSymbolTable[] 배열의 항목 중에서 키를 가지고 있을 만한 항목을 선택하여 get() 또는 put() 메서드를 맡기는 방식으로 구현된다.
 * M개의 리스트와 N개의 키를 가지고 있기 때문에 키가 각 리스트에 어떻게 분포되어 있든지 관계없이 리스트의 평균 길이는 항상 N/M이 된다.
 * 전형적인 조건에서 가정 J의 결과를 검증할 수 있기 때문에 빠른 탐색과 삽입에 대한 근거로 삼을 수 있다.

```java
public class SeparateChainingHashSymbolTable<Key, Value> implements SymbolTable<Key, Value> {

    class SequentialSearchSymbolTable<Key, Value> implements SymbolTable<Key, Value> {

        private class Node {
            Key key;
            Value value;
            Node next;

            public Node(Key key, Value value, Node next) {
                this.key = key;
                this.value = value;
                this.next = next;
            }
        }

        private Node first;
        protected int size;

        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean contains(Key key) {
            return get(key) != null;
        }

        public Value get(Key key) {
            for (Node node = first; node != null; node = node.next) {
                if (key.equals(node.key)) return node.value;
            }
            return null;
        }

        public void put(Key key, Value value) {
            for (Node node = first; node != null; node = node.next) {
                if (key.equals(node.key)) {
                    node.value = value;
                    return;
                }
            }
            first = new Node(key, value, first);
            size++;
        }

        public void delete(Key key) {
            if (first.key.equals(key)) {
                first = first.next;
                size--;
                return;
            }
            for (Node node = first; node != null; node = node.next) {
                if (node.next != null && node.next.key.equals(key)) {
                    node.next = node.next.next;
                    size--;
                    return;
                }
            }
        }

        public Iterable<Key> keys() {
            Queue<Key> keys = new Queue<>();
            for (Node node = first; node != null; node = node.next) {
                keys.enqueue(node.key);
            }
            return keys;
        }

    }

    protected int averageListSize;
    protected int size;
    protected int keysSize;
    SequentialSearchSymbolTable<Key, Value>[] symbolTable;

    private final static int DEFAULT_HASH_TABLE_SIZE = 997;
    private final static int DEFAULT_AVERAGE_LIST_SIZE = 5;

    // The largest prime <= 2^i for i = 1 to 31
    // Used to distribute keys uniformly in the hash table after resizes
    // PRIMES[n] = 2^k - Ak
    // where k is the power of 2 and Ak is the value to subtract to reach the previous prime number
    protected final static int[] PRIMES = {
            1, 1, 3, 7, 13, 31, 61, 127, 251, 509, 1021, 2039, 4093, 8191, 16381,
            32749, 65521, 131071, 262139, 524287, 1048573, 2097143, 4194301,
            8388593, 16777213, 33554393, 67108859, 134217689, 268435399,
            536870909, 1073741789, 2147483647
    };

    // The log of the hash table size
    // Used in combination with PRIMES[] to distribute keys uniformly in the hash function after resizes
    protected int lgM;

    public SeparateChainingHashSymbolTable() {
        this(DEFAULT_HASH_TABLE_SIZE, DEFAULT_AVERAGE_LIST_SIZE);
    }

    public SeparateChainingHashSymbolTable(int initialSize, int averageListSize) {
        this.size = initialSize;
        this.averageListSize = averageListSize;
        symbolTable = new SequentialSearchSymbolTable[size];
        for (int i = 0; i < size; i++) {
            symbolTable[i] = new SequentialSearchSymbolTable<>();
        }
        lgM = (int) (Math.log(size) / Math.log(2));
    }

    public int size() {
        return keysSize;
    }

    public boolean isEmpty() {
        return keysSize == 0;
    }

    protected int hash(Key key) {
        int hash = key.hashCode() & 0x7fffffff;
        if (lgM < 26) {
            hash = hash % PRIMES[lgM + 5];
        }
        return hash % size;
    }

    protected double getLoadFactor() {
        return ((double) keysSize / (double) size);
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to contains() cannot be null");
        return get(key) != null;
    }

    public void resize(int newSize) {
        SeparateChainingHashSymbolTable<Key, Value> separateChainingHashSymbolTableTemp
                = new SeparateChainingHashSymbolTable<>(newSize, averageListSize);
        for (Key key : keys()) {
            separateChainingHashSymbolTableTemp.put(key, get(key));
        }
        symbolTable = separateChainingHashSymbolTableTemp.symbolTable;
        size = separateChainingHashSymbolTableTemp.size;
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to get() cannot be null");
        return symbolTable[hash(key)].get(key);
    }

    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if (value == null) {
            delete(key);
            return;
        }
        int hashIndex = hash(key);
        int currentSize = symbolTable[hashIndex].size;
        symbolTable[hashIndex].put(key, value);
        if (currentSize < symbolTable[hashIndex].size) {
            keysSize++;
        }
        if (getLoadFactor() > averageListSize) {
            resize(size * 2);
            lgM++;
        }
    }
    
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to delete() cannot be null");
        if (isEmpty() || !contains(key)) return;
        symbolTable[hash(key)].delete(key);
        keysSize--;
        if (size > 1 && getLoadFactor() <= averageListSize / (double) 4) {
            resize(size / 2);
            lgM--;
        }
    }
    
    public Iterable<Key> keys() {
        Queue<Key> keys = new Queue<>();
        for (SequentialSearchSymbolTable<Key, Value> sequentialSearchSymbolTable : symbolTable) {
            for (Key key : sequentialSearchSymbolTable.keys()) {
                keys.enqueue(key);
            }
        }
        if (!keys.isEmpty() && keys.peek() instanceof Comparable) {
            Key[] keysToBeSorted = (Key[]) new Comparable[keys.size()];
            for (int i = 0; i < keysToBeSorted.length; i++) {
                keysToBeSorted[i] = keys.dequeue();
            }
            Arrays.sort(keysToBeSorted);
            for (Key key : keysToBeSorted) {
                keys.enqueue(key);
            }
        }
        return keys;
    }

}
```

> 명제 K : M개의 리스트와 N개의 키가 사용되는 개별-체이닝 해시 테이블에 대해서, 각 리스트의 길이가 N/M에서 
> 작은 상숫값만큼만 차이가 날 확률은(가정 J 하에서) 1에 극히 가깝다.
 * 가정 J는 고전적인 확률 이론을 활용한다.
 * 어떤 주어진 리스트가 정확히 k개의 키를 가질 확률은 이항분포를 따른다.
   * N개의 키 중에서 k개의 키를 선택한다고 하자.
   * k개의 키가 주어진 리스트에 해시되어 속할 확률은 각각 1/M이고, 나머지 N-k개의 키가 주어진 리스트에 해시되지 않을 확률은 각각 1 - (1/M)이다.
   * a = N/M 이라고 할 때, 확률을 수식으로 정리하면 [N combination k] * (a / N)^k * (1 - a / N)^(N - k) 가 된다.
   * 만약 a가 작다면 전통적인 푸아송 분포를 따르게 된다.
   * 예를 들어 리스트의 평균 길이가 10이라면 키가 20개 이상 들어 있는 리스트에 새로운 키가 해싱될 확률은 약 0.0084에 지나지 않는다.
   * 물론 개별 리스트의 길이가 평균 길이에 집중된다는 것이 모든 리스트의 길이가 짧아진다는 것을 보증하지는 않는다.
   * 만약 a가 상수라면 가장 긴 리스트의 길이는 log(N)/log(log(N))에 비례하며 늘어난다.
 * 위 분석은 가정 J에 전적으로 의존하기 때문에, 해시 함수가 균일하지도 않고 독립적이지도 않다면 탐색 비용과 삽입 비용이 N에 비례하게 되어 순차 탐색보다 더 나을게 없어져 버린다.

> 속성 L : 개별-체이닝 해시 테이블에서 M개의 리스트와 N개의 키가 사용될 때, 탐색 실패와 삽입 시 소요되는 비교 연산(동일성 테스트)의 
> 횟수는 ~N/M을 따른다.
 * 많은 프로그래머들에 의해 개별-체이닝이 명제 K에서 예측한대로 좋은 성능을 보인다는 것이 확인되었다.
 * 심지어 가정 J를 만족하지 않는 것이 분명한 상황에서도 마찬가지였다.
 * 단, 키의 모든 비트가 해시 값 생성에 기여하지 않은 해시 함수가 사용되어 부족한 성능을 보인 경우들도 많이 알려져 있다.
 * 그런 경우들만 제외하고 보면, M개의 배열을 사용하는 개별-체이닝은 심볼 테이블의 탐색/삽입 작업에 M배 비욜의 성능 향상을 가져온다고 말할 수 있다.

 * 테이블 크기
   * 개별-체이닝을 구현할 때에는 테이블의 크기 M을 너무 크지도 작지도 않게 적절한 크기로 선택하는 것이 중요하다.
   * M이 너무 크면 비어 있는 체인이 많아져 배열에 사용된 연속된 메모리를 낭비하게 되고, M이 너무 작으면 체인이 길어져 탐색 성능이 떨어진다.
   * 개별-체이닝의 내재적 장점 중 하나는 특정 M값을 선택하더라도 그 결과가 탄력적이라는 점이다.
   * 기대보다 좀 더 많은 키가 삽입되면 더 큰 테이블 크기를 선택했을 경우보다 탐색 시간이 조금 더 느려질 뿐이다.
   * 반대로 더 적은 키가 삽입되면 약간의 공간이 낭비된다는 정도의 문제만 있다.
   * 공간 절약이 그렇게 중요하지 않다면 M값을 여유있게 선택하여 탐색 시간이 상수 시간이 되도록 할 수 있다.
   * 공간 절약이 중요해서 어쩔 수 없이 작은 M값을 선택하더라도 탐색 속도가 M배 만큼 빨라지는 효과는 누릴 수 있다.
 * 삭제
   * 단순히 키를 가진 SequentialSearchSymbolTable을 찾은 다음 그 테이블의 delete()를 호출하는 것으로 쉽게 구현할 수 있다.
 * 순차 작업
   * 해싱은 모든 키들이 균일하게 흩어져서 분포할 것이라는 가정에 전적으로 의존하고 있다.
   * 따라서 키들 간의 순서는 해싱 와중에 사라져버린다.
   * 만약 최대/최소 항목 또는 특정 범위에 속한 항목들을 빠르게 찾을 수 있어야 하거나 순차 심볼 테이블 API 중 하나를 구현해야 한다면 해싱은 적합한 도구가 아니다.
   * 해싱에서는 순차 작업들이 선형 성능을 보일 수 밖에 없다.

### 2.2 선형-탐지(linear-probing)를 이용한 해싱
 * 해시 테이블의 크기가 저장할 키/값 쌍의 개수보다 큰 경우, 충돌이 발생할 때 빈 공간에 의존하여 충돌을 해소하는 방법을 "개방형 주소 지정(open-addressing) 해싱"이라고 한다.
 * 개방형 주소 지정 해싱의 가장 단순한 형태는 `선형-탐지`이다.
 * 충돌이 발생할 때마다, 단순이 테이블의 다음 항목 위치(다음 인덱스)를 검사하면서 빈 공간을 찾는다.
 * 선형-탐지 방법은 다음과 같은 세 가지 동작 특성을 가진다.
   * 탐색 키와 탐지 중인 키가 동일한 경우 : 탐색 성공
   * 빈 공간(인덱스 위치에 null키 존재) 탐지 : 탐색 실패
   * 탐색 키와 탐지 중인 키가 서로 다름 : 다음 인덱스 시도
 * 키를 테이블 인덱스로 해싱하고 탐색 키와 매칭되는 키가 그 위치에 있는지 검사하는 작업을을 같은 키가 발견되거나 빈 공간을 찾을 때까지 반복한다.
 * 만약 인덱스를 증가시켜 배열의 마지막에 이르렀다면 배열의 처음으로 돌아간다.
 * 이렇게 테이블에 항목이 이미 있는지 없는지 검사하는 탐색 키를 "탐침(probe)"이라 부른다.
 * 개방형 주소 지정 해싱의 핵심 아이디어는 메모리 공간을 연결 리스트의 링크 참조 변수들에 허비하는 대신 해시 테이블의 키 저장 공간을 늘이는데 쓰자는 것이다.
 * 이 때 키 탐지 위치의 끝 (저장된 키 나열의 끝)을 표시하는데 null 값을 사용한다.

```java
public class LinearProbingHashSymbolTable<Key, Value> implements SymbolTable<Key, Value> {

    protected int keysSize;
    protected int size;
    protected Key[] keys;
    protected Value[] values;

    // The largest prime <= 2^i for i = 1 to 31
    // Used to distribute keys uniformly in the hash table after resizes
    // PRIMES[n] = 2^k - Ak where k is the power of 2 and Ak is the value to subtract to reach the previous prime number
    private final static int[] PRIMES = {
            1, 1, 3, 7, 13, 31, 61, 127, 251, 509, 1021, 2039, 4093, 8191, 16381,
            32749, 65521, 131071, 262139, 524287, 1048573, 2097143, 4194301,
            8388593, 16777213, 33554393, 67108859, 134217689, 268435399,
            536870909, 1073741789, 2147483647
    };

    // The log of the hash table size
    // Used in combination with PRIMES[] to distribute keys uniformly in the hash function after resizes
    protected int lgM;

    public LinearProbingHashSymbolTable(int size) {
        this.size = size;
        keys = (Key[]) new Object[size];
        values = (Value[]) new Object[size];
        lgM = (int) (Math.log(size) / Math.log(2));
    }

    public int size() {
        return keysSize;
    }

    public boolean isEmpty() {
        return keysSize == 0;
    }

    protected int hash(Key key) {
        int hash = key.hashCode() & 0x7fffffff;
        if (lgM < 26) {
            hash = hash % PRIMES[lgM + 5];
        }
        return hash % size;
    }

    protected double getLoadFactor() {
        return keysSize / (double) size;
    }

    private void resize(int newSize) {
        LinearProbingHashSymbolTable<Key, Value> tempHashTable = new LinearProbingHashSymbolTable<>(newSize);
        for (int i = 0; i < size; i++) {
            if (keys[i] != null) tempHashTable.put(keys[i], values[i]);
        }
        keys = tempHashTable.keys;
        values = tempHashTable.values;
        size = tempHashTable.size;
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to contains() cannot be null");
        return get(key) != null;
    }

    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to get() cannot be null");
        for (int tableIndex = hash(key); keys[tableIndex] != null; tableIndex = (tableIndex + 1) % size) {
            if (keys[tableIndex].equals(key)) return values[tableIndex];
        }
        return null;
    }

    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if (value == null) {
            delete(key);
            return;
        }
        if (keysSize >= size / (double) 2) {
            resize(size * 2);
            lgM++;
        }
        int tableIndex;
        for (tableIndex = hash(key); keys[tableIndex] != null; tableIndex = (tableIndex + 1) % size) {
            if (keys[tableIndex].equals(key)) {
                values[tableIndex] = value;
                return;
            }
        }
        keys[tableIndex] = key;
        values[tableIndex] = value;
        keysSize++;
    }

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to delete() cannot be null");
        if (!contains(key)) return;
        int tableIndex = hash(key);
        while (!keys[tableIndex].equals(key)) {
            tableIndex = (tableIndex + 1) % size;
        }
        keys[tableIndex] = null;
        values[tableIndex] = null;
        keysSize--;

        tableIndex = (tableIndex + 1) % size;
        while (keys[tableIndex] != null) {
            Key keyToRedo = keys[tableIndex];
            Value valueToRedo = values[tableIndex];
            keys[tableIndex] = null;
            values[tableIndex] = null;
            keysSize--;
            put(keyToRedo, valueToRedo);
            tableIndex = (tableIndex + 1) % size;
        }

        if (keysSize > 1 && keysSize <= size / (double) 8) {
            resize(size / 2);
            lgM--;
        }
    }

    public Iterable<Key> keys() {
        Queue<Key> keySet = new Queue<>();
        for (Key key : keys) {
            if (key != null) keySet.enqueue(key);
        }
        if (!keySet.isEmpty() && keySet.peek() instanceof Comparable) {
            Key[] keysToBeSorted = (Key[]) new Comparable[keySet.size()];
            for (int i = 0; i < keysToBeSorted.length; i++) {
                keysToBeSorted[i] = keySet.dequeue();
            }
            Arrays.sort(keysToBeSorted);
            for (Key key : keysToBeSorted) {
                keySet.enqueue(key);
            }
        }

        return keySet;
    }

}
```
 * 삭제
   * 단순하게 해당 키 위치를 null로 세팅하는 것은 적합한 방법이 아니다.
   * 저장된 키들의 마지막 위치를 null로 표기한다는 원칙이 깨지면서 키를 탐색할 때 null로 지워진 뒷부분에 저장된 항목들이 무시된다.
   * 따라서 삭제된 키 오른쪽에 저장된 모든 키들을 다시 테이블에 삽입해야 한다.
 * 성능
   * 개별 체이닝과 마찬가지로 a = N/M 비율에 의존한다. 
   * 하지만 해석은 다르게 한다.
     * a를 해시 테이블의 부하 비율(load factor)으로 본다.
     * 개별 체이닝에서는 a가 리스트 당 평균 키 개수이고 1보다 큰 경우가 많다.
     * 선형 탐지에서는 a가 테이블 공간을 키가 차지하고 있는 비율이고 1보다 클 수 없다.
   * 선형 탐지 해시테이블의 부하 비율이 1에 근접하게 내버려 둘 수 없다.
   * 왜냐하면 빈 공간이 없는 테이블에서는 탐색 실패가 발생하면 무한루프에 빠져버리기 때문이다.
   * 좋은 성능을 위해서라도 가변 크기 배열을 이용하여 부하 비율이 1/8 ~ 1/2 사이에서 관리되도록 해야 한다.
 * 무리 짓기(Clustering)
   * 선형-탐지의 평균 비용은 키들이 어떤 식으로 뭉쳐져서 테이블 공간을 차지하고 있느냐에 종속적이다.
   * 키가 삽입되는 와중에 연속되게 모여 있는 키 집합을 `키 무리(cluster)`라고 부른다.
   * 탐지 횟수를 줄이기 위해 짧은 키 무리는 좋은 성능을 위한 요건이 된다.
   * 하지만 테이블이 채워질수록 이 요건은 달성하기 어려워진다.
 * 선형 탐지의 분석
   * 아래 명제 M 참고
   * a 값을 1/2 근처로 유지한다면 탐지 횟수를 1.5 ~ 2.5 사이로 붙잡아 둘 수 있다.
   * 그러기 위해 가변 크기 배열을 활용할 수 있다.
 * 분할 상쇄(amortized) 분석
   * 가변 크기 배열을 이론적으로 좀 더 엄밀하게 보자면 분할 상쇄 관점에서의 성능 경계를 규명해야 한다.
   * 즉, 많은 수의 탐지가 유발되는 시점(테이블의 크기를 2배로 늘리는 삽입이 임박한 시점)을 알 수 있기 때문에 그 영향이 전체적으로 상쇄되는 정도를 분석해야 한다.
   * 아래 명제 N 참고
 
   
> 명제 M : 크기 M, 키 개수 N = a * M * k인 선형 탐지 해시 테이블에서 평균 탐지 횟수는 가정 J 하에서 
> 탐색 성공, 탐색 실패 각각에 대해 아래와 같다.  
> ~1/2*(1 + 1/(1 - a))와 ~1/2*(1 + 1/(1 - a)^2)  
> 특히 a가 1/2에 가깝다면 탐색 성공시의 평균 탐지 횟수는 약 3/2가 되고, 탐색 실패 시의 평균 탐지 횟수는 약 5/2가 된다. 
> a가 1에 가까워지면 정확도가 조금 떨어지지만 그런 경우는 무시할 수 있다. 
> 왜냐하면 선형 탐지를 위한 테이블 공간을 할당할 때 a값을 1/2 보다 작은 값이 되게 선택하면 되기 때문이다.

> 명제 N : 가변 크기 배열을 사용하는 빈 해시 테이블이 있다고 하자. 
> 가정 J에 따라 일련의 임의의 탐색, 삽입, 삭제 작업 t가 심볼 테이블에서 수행될 때 기대되는 작업 소요 시간은 
> t의 길이에 비례하고 그 메모리 사용량은 테이블에 저장된 키 개수에 상수 비율로 비례한다.

### 3. 메모리
* 메모리 사용량은 최적 성능의 해싱 알고리즘을 만들기 위해 중요한 요소이다.
* 정교한 최적화는 전문가의 영역이지만, 사용되는 참조의 개수에서 필요한 메모리 용량을 어림잡아 계산해 보는 것은 할 수 있다.
* 키/값 쌍들이 차지하는 메모리를 제외하고 보면, 개별 체이닝 해시 테이블에서는 M개의 내부의 심볼 테이블 객체와 이를 가리키는 M개의 참조 변수를 사용한다.
* 각각의 내부 심볼 테이블 객체는 객체 오버헤드 16바이트, 8바이트 참조(first), 그리고 N개의 Node 객체로 이루어진다.
* 각 Node 객체는 객체 오버해드 24바이트, 3개의 참조(key, value, next)를 가진다.
* 테이블이 1/8 ~ 1/2 사이의 공간만 차 있도록 관리하면 선형 탐지 알고리즘이 사용하는 참조 개수는 4N ~ 16N 사이가 된다.
* 따라서 메모리 사용량이 중요하다면 해싱을 사용하는 것이 바람직하지 않을 수도 있다.

| 방법 | N개 항목에 대한 공간 사용량(참조 타입) |
| --- | --- |
| 개별 체이닝 | ~48N + 32M |
| 선형 탐지 | ~32N에서 ~128N 사이 |
| BST | ~56N |

### 4. 기타
 * 단지 성능 때문에 개별 체이닝을 선형 탐지보다 우선해서 선택한다면 항상 올바른 선택이라고 단정할 수 없다.
 * 일반적인 가정 하에서, 해싱을 사용하면 심볼 테이블의 크기에 관계없이 탐색, 삽입 작업에 상수 시간이 소요된다고 기대해도 크게 틀리지 않다.
 * 하지만 아래의 이유 때문에 해싱이 만병통치약은 아니다.
   * 각 키 타입에 대한 좋은 해시 함수가 제공되어야 한다.
   * 성능 보증이 해시 함수의 품질에 의존적이다.
   * 해시 함수가 계산하기 어렵고 많은 연산을 요구할 수도 있다.
   * 순차 심볼 테이블 작업이 쉽게 지원되지 못한다.