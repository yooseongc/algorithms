
# 3.1 심볼 테이블

## 1. 비순차 연결 리스트에서의 순차 탐색

 * 한 가지 생각할 수 있는 심볼 테이블 데이터 구조는 키/값 쌍을 가진 노드로 연결 리스트를 만드는 것.
 * get()은 리스트를 순회하면서 equals()를 이용해 리스트의 각 노드가 가진 키와 탐색 키를 비교
 * put()
   * equals()를 이용해 키를 검사
   * 이미 해당 키가 존재한다면 그 노드의 값만 새로 삽입
   * 존재하지 않는다면 키/값 쌍으로 새로운 노드를 만들어 리스트의 제일 앞에 추가 
 * 즉, equals()를 이용해 순차적으로 노드를 탐색함.
 * SequentialSearchST<Key, Value>

```java
package chap31;

import chap13.Queue;
import interfaces.SymbolTable;

public class SequentialSearchSymbolTable<Key, Value> implements SymbolTable<Key, Value> {

    private Node first;
    private int size;

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

    @Override
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to get() cannot be null");
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) return x.value;
        }
        return null;
    }

    @Override
    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if (value == null) {
            delete(key);
            return;
        }
        for (Node x = first; x != null; x = x.next) {
            if (key.equals(x.key)) {
                x.value = value;
                return;
            }
        }
        first = new Node(key, value, first);
        size++;
    }

    @Override
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to contains() cannot be null");
        return get(key) != null;
    }

    @Override
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to delete() cannot be null");
        if (isEmpty()) return;
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

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    @Override
    public Iterable<Key> keys() {
        Queue<Key> keys = new Queue<>();
        for (Node node = first; node != null; node = node.next) {
            keys.enqueue(node.key);
        }
        return keys;
    }

}
```

 * 탐색과 삽입이 어떤 패턴으로 호출될지 정확히 예측이 되지 않기 때문에 최악 조건 성능에 더 집중한다.
 * 찾으려는 키가 존재하는 경우를 탐색 성공(search hit), 존재하지 않는 경우를 탐색 실패(search miss)라고 부르자.

> 명제 A : N개의 키/값 쌍이 저장된 비순차 연결 리스트 기반 심볼 테이블에서,
> 새로운 키/값 쌍이 삽입되거나 탐색이 실패하는 경우 N번의 비교 연산이 소요된다. 
> 탐색이 성공하는 경우 최악 조건에서도 N번의 비교 연산이 소요된다.

> 따름 정리 : 비어 있는 연결 리스트 심볼 테이블에 N개의 서로 다른 키를 삽입할 때는 
> ~N^2/2번의 비교 연산이 소요된다.

 * 각각의 키가 탐색될 확률이 서로 동등할 경우를 무작위 탐색 성공(random search hit)이라고 하자.
 * 클라이언트의 탐색이 무작위 탐색 성공의 평균 비교 연산 횟수는 ~N/2를 따른다.
   * 위 구현의 get() 메서드는 첫 번째 키를 찾는 데 1번, 두 번째 키를 찾는데 2번, ... 
   * 평균 비교 연산 횟수는 (1 + 2 + ... + N) / N = (N + 1) / 2 ~ N/2 가 된다.
 * 이러한 분석 결과는 연결 리스트 기반 구현에서의 순차 탐색이 큰 데이터를 대상으로 할 때 대단히 느리다는 것을 보여준다.
   * FrequencyCounter 예시에서, 전체 비교 연산 횟수는 탐색 작업의 횟수와 삽입 작업의 횟수의 곱에 비례한다.
   * 최악의 경우 ~ N^3/2
   * 명령어 인수 8, tale.txt를 입력으로 14350회의 put() 작업이 발생하며, 심볼 테이블은 5131개의 키를 담을 때까지 커진다.
   * 즉, put() 작업의 1/3은 테이블의 크기를 키우고 나머지는 탐색만 수행한다.


## 2. 정렬된 배열에서의 이진 탐색 (순차 배열 기반)
 * 이번에는 두 개의 배열 한 쌍을 데이터 구조로 이용하서 순차 심볼 테이블을 구현해보자.
 * 하나는 키의 배열, 다른 하나는 값의 배열이다.
 * Comparable 타입 키를 정렬된 상태로 배열에 저장하고 get() 등이 빠르게 수행될 수 있도록 배열 인덱싱으로 접근한다.
 * 이 구현의 핵심은 주어진 키보다 작은 키가 몇 개 있는지 리턴하는 rank() 메서드에 있다.
   * get()에서는 rank()를 이용해 어느 위치에서 키를 찾아야 하는지 알아낸다.
   * put()에서는 rank()를 이용해 정확히 어디에 키를 삽입/업데이트 하는지 알아낸다.
 * 클라이언트가 배열의 크기를 신경 쓰지 않아도 되도록 필요할 때마다 배열의 크기를 자동으로 재조정하나, 큰 배열이 수반될 때 느리다.

```java
public class BinarySearchSymbolTable<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {

    private Key[] keys;
    private Value[] values;
    private int size;

    private final static int DEFAULT_INITIAL_CAPACITY = 2;

    public BinarySearchSymbolTable() {
        keys = (Key[]) new Comparable[DEFAULT_INITIAL_CAPACITY];
        values = (Value[]) new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to get() cannot be null");
        if (isEmpty()) return null;
        int rank = rank(key);
        if (rank < size && keys[rank].compareTo(key) == 0) {
            return values[rank];
        } else {
            return null;
        }
    }

    public int rank(Key key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        int low = 0;
        int high = size - 1;
        while (low <= high) {
            int middle = low + (high - low) / 2;
            int comparison = key.compareTo(keys[middle]);
            if (comparison < 0) {
                high = middle - 1;
            } else if (comparison > 0) {
                low = middle + 1;
            } else {
                return middle;
            }
        }
        return low;
    }

    @Override
    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if (value == null) {
            delete(key);
            return;
        }
        int rank = rank(key);
        if (rank < size && keys[rank].compareTo(key) == 0) {
            values[rank] = value;
            return;
        }
        if (size == keys.length) {
            resize(keys.length * 2);
        }
        for (int i = size; i > rank; i--) {
            keys[i] = keys[i - 1];
            values[i] = values[i - 1];
        }
        keys[rank] = key;
        values[rank] = value;
        size++;
    }

    @Override
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to contains() cannot be null");
        return get(key) != null;
    }

    @Override
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to delete() cannot be null");
        if (isEmpty() || !contains(key)) return;
        int rank = rank(key);
        for (int i = rank; i < size - 1; i++) {
            keys[i] = keys[i + 1];
            values[i] = values[i + 1];
        }
        keys[size - 1] = null;
        values[size - 1] = null;
        size--;

        if (size > 1 && size == keys.length / 4) {
            resize(keys.length / 2);
        }
    }

    public Key min() {
        if (isEmpty()) throw new NoSuchElementException("Empty symbol table");
        return keys[0];
    }

    public Key max() {
        if (isEmpty()) throw new NoSuchElementException("Empty symbol table");
        return keys[size - 1];
    }

    public Key select(int k) {
        if (isEmpty()) throw new NoSuchElementException("Empty symbol table");
        if (k >= size) throw new IllegalArgumentException("Index " + k + " is higher than size");
        return keys[k];
    }

    public Key ceiling(Key key) {
        int rank = rank(key);
        if (rank == size) return null;
        return keys[rank];
    }

    public Key floor(Key key) {
        if (contains(key)) return key;
        int rank = rank(key);
        if (rank == 0) return null;
        return keys[rank - 1];
    }

    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
        delete(min());
    }

    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow error");
        delete(max());
    }

    public int size(Key low, Key high) {
        if (low == null || high == null) throw new IllegalArgumentException("Key cannot be null");
        if (high.compareTo(low) < 0) {
            return 0;
        } else if (contains(high)) {
            return rank(high) - rank(low) + 1;
        } else {
            return rank(high) - rank(low);
        }
    }

    @Override
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key low, Key high) {
        if (low == null || high == null) throw new IllegalArgumentException("Key cannot be null");
        Queue<Key> queue = new Queue<>();
        for (int i = rank(low); i < rank(high); i++) {
            queue.enqueue(keys[i]);
        }
        if (contains(high)) queue.enqueue(keys[rank(high)]);
        return queue;
    }

    private void resize(int newSize) {
        Key[] tempKeys = (Key[]) new Comparable[newSize];
        Value[] tempValues = (Value[]) new Object[newSize];

        System.arraycopy(keys, 0, tempKeys, 0, size);
        System.arraycopy(values, 0, tempValues, 0, size);

        keys = tempKeys;
        values = tempValues;
    }

}
```

 * 키를 정렬된 상태로 배열에 저장하는 이유는 이진탐색을 이용한 배열 인덱싱으로 탐색에 소요되는 비교 연산 횟수를 크게 줄일 수 있기 때문이다.
 * rank()를 재귀 또는 비재귀로 구현할 수 있다. 비재귀 코드는 바로 아래에 있다.

```
public int rank(Key key, int lo, int hi) {
    if (hi < lo) return lo;
    int mid = lo + (hi - lo) / 2;
    iint cmp = key.compareTo(keys[mid]);
    if (cmp < 0) {
        return rank(key, lo, mid - 1);    
    } else if (cmp > 0) {
        return rank(key, mid + 1, hi);
    } else {
        return mid;
    }
}

```

 * 정렬된 배열에서의 이진 탐색은 아래 명제에 따라 매우 빠르다.

> 명제 B : N개의 키를 대상으로 할 때, 정렬된 배열에서의 이진 탐색은 
> 탐색 성공 유무와 관계 없이 log(N)+1회보다 더 많은 
> 비교 연산을 소요하지 않는다.

 * C(N)을 크기 N인 심볼 테이블에서 키를 찾을 때 소요해야 하는 비교 연산 횟수라고 하자.
 * C(0) = 0, C(1) = 1
 * rank()의 재귀적인 구조를 그대로 투영하여 N > 0일 때 아래 관계가 성립한다.
   * N이 2의 승수에서 1 작은 수라고 가정하자. 즉 N = 2^n - 1, n = log(N + 1)
   * C([N]) <= C([N/2]) + 1
   * 왼쪽/오른쪽 선택을 위한 한 번의 비교연산, 왼쪽/오른쪽 집합의 크기는 대략 N/2 
   * [N/2] = 2^(n-1) - 1  <- 부분 집합의 개수
   * [N] = 2^n - 1
   * C(2^n-1) <= C(2^(n-1)-1) + 1
   * 위 식을 재귀적으로 적용하면 C(2^n-1) <= C(2^(n-2)-1) + 1 + 1
   * 이 과정을 n - 2번 반복하면 C(2^n-1) <= C(2^0) + n = n + 1
   * 따라서 C([N]) = C(2^n-1) <= n + 1 < log(N + 1) + 1
   * 일반적인 N에 대한 정확한 답은 좀 더 복잡하나, 비슷하다.
   * 즉, 이진 탐색을 이용하면 로그 시간 탐색 성능이 보장된다.
 * BinarySearchSymbolTable이 로그 시간 탐색 성능을 보장해 주기는 하나 그럼에도 실제 실행 시간은 크게 줄지 않는다.
   * 비교 연산 횟수는 로그시간으로 단축
   * 그러나 무작위 순서의 키들을 정렬된 배열의 심볼 테이블로 만들 때 발생하는 배열 접근 횟수가 배열 크기 제곱에 비례

> 명제 B (계속) : 크기가 N인 정렬된 배열에 새로운 키를 삽입할 때, 최악의 경우 ~2N번의 배열 접근이 필요하다. 
> 따라서 초기 공백 상태인 테이블에 N개의 키를 삽입하는 작업은 최악의 경우 ~N^2번의 배열 접근을 소요한다.

 * FrequencyCounter의 put() 작업에 있어서 크기 8 이상인 단어에 대한 작업을 다시 생각해보자.
   * SequentialSearchSymbolTable의 경우 평균 2,246번의 비교 연산이 발생
   * BinarySearchSymbolTable의 경우 평균 484번의 비교 연산이 발생
   * 후자의 경우 크게 줄어듦.

| 데이터구조 | 최악 조건 search | 최악 조건 insert | 보통 search hit | 보통 insert | 순차 작업 효율적 지원 여부 |
| --- | --- | --- | --- | --- | --- |
| 비순차 연결 리스트 - 순차탐색 | N | N | N/2 | N | no |
| 순차 배열 - 이진탐색 | logN | 2N | logN | N | yes |

 * 탐색과 삽입 작업 둘 다 로그 시간 성능을 가지는 알고리즘을 고안하는 것도 가능하다.
   * 빠른 삽입 지원을 위해 링크 방식의 데이터 구조를 사용한다면?
     * 연결 리스트 하나만으로는 임의의 부분 배열의 중간 항목을 인덱싱으로 바로 접근할 수 없어 이진 탐색 사용이 불가능하다.
     * 중간 항목에 접근하기 위한 유일한 방법은 링크를 하나하나 따라가는 것이기 때문.
   * 즉, 이진 탐색의 효율성과 링크 구조의 유연성을 모두 가지기 위한 아래와 같은 좀 더 복잡한 데이터 구조가 필요하다.
     * 이진 탐색 트리
     * 균형 탐색 트리 (최악 성능 개선)
     * 해시 테이블
 
메