
# 3.2 이진 탐색 트리

 * 노드 마다 두 개의 링크를 사용하고 이진 탐색 트리 데이터 구조를 기반으로 구현한 효율적인 심볼 테이블을 만들어보자.
 * 이 데이터 구조는 링크를 가진 노드들로 구성된다.
 * 각 링크는 공백 객체(null 링크)와 연결되어 있을 수도 있고 다른 노드를 가리키는 참조와 연결되어 있을 수도 있다.
 * 이진 트리에서는 어떤 노드든 단 하나의 들어오는 링크(부모 링크)를 가질 수 있도록 제한한다.
 * 뿌리 노드는 들어오는 링크가 없다.
 * 나가는 링크는 정확히 두 개 존재하고 각각 왼쪽 링크, 오른쪽 링크라고 한다. 
 * 이 두 개의 링크 각각ㅇ 연결된 노드를 각각 왼쪽 자식, 오른쪽 자식 노드라고 부른다.
 * 각 링크가 가리키는 것이 하나의 노드이지만, 그 노드를 뿌리로 가지는 또 다른 이진 트리를 가리키는 것으로 볼 수도 있다.
 * 따라서 이진 트리를 `공백 링크 또는 부분 트리를 가리키는 좌/우 링크를 가진 노드`로 정의할 수 있다.
 * 이진 탐색 트리에서는 각 노드가 키와 값을 가지며 효율적인 탐색이 가능하도록 순서에 제약을 가진다.

> 이진 탐색 트리(Binary Search Tree, BST) : 각 노드가 Comparable 키와 그에 연관된 값을 가지고, 
> 그 키가 왼쪽 부분 트리의 키들 보다는 크고, 오른쪽 부분 트리의 키들보다는 작다는 제약을 만족하는 
> 이진 트리이다.

## 1. 기본 구현

 * 표현 방식
   * 연결 리스트에서 처럼 BST의 private class로 노드를 정의한다.
   * 각 노드는 키, 값, 왼쪽 링크, 오른쪽 링크, 그리고 자식 노드의 개수를 가진다.
   * BST에서 왼쪽 링크는 현재 노드의 키보다 작은 키를 가진 항목들을 가리킨다.
   * BST에서 오른쪽 링크는 현재 노드의 키보다 큰 키를 가진 항목들을 가리킨다.
   * 인스턴스 변수 N은 그 노드를 뿌리 노드로 갖는 부분 트리가 가진 노드의 개수를 담는다. (자기 자신 포함)
   * size(x) = size(x.left) + size(x.right) + 1
   * 어떤 키 집합에 대한 BST 표현 방식은 유일하지 않다. 
 * 탐색
   * 심볼 테이블에 키가 존재하면 탐색 성공으로 연관된 값을, 존재하지 않으면 탐색 실패로 null을 리턴한다.
   * BST의 재귀적인 구조로 인해 재귀적인 탐색 알고리즘을 쉽게 만들 수 있다.
     * 트리가 공백이면 탐색 실패
     * 탐색 키가 뿌리 노드의 키와 같으면 탐색 성공이다.
     * 위 두 경우가 아니라면 적합한 부분 트리로 재귀적으로 이동하며 탐색해 나간다. 이 때 탐색 키가 현재 노드의 키보다 작으면 왼쪽, 크면 오른쪽 부분 트리로 이동한다.
     * 탐색 키를 찾거나 탐색 중인 부분 트리가 공백이면 종료한다.
     * 탐색은 꼭대기에서 시작하여 재귀적으로 자식 노드 중 하나를 따라가며 실행된다.
   * BST를 한 단계씩 내려갈 때마다 탐색하는 노드를 뿌리로 하는 부분 트리의 크기가 줄어든다.
   * 이상적으로는 절반씩 줄어들며, 최소한 노드 하나씩은 탐색 범위에서 제거된다.
 * 삽입
   * 탐색 과정에서 null 링크를 만나는 동작에서 null 링크를 새로운 키를 가진 새로운 노드로 바꾸기만 하면 삽입 작업이 된다.
   * 재귀적인 put() 메서드는 재귀적인 탐색과 비슷한 논리 과정으로 삽입 작업을 완수한다.
     * 트리가 공백이면 키/값 쌍을 가진 새로운 노드를 리턴한다.
     * 탐색 키가 뿌리 노드의 키보다 작으면 왼쪽 부분 트리의 삽입 결과를 왼쪽 링크에 연결한다.
     * 탐색 키가 뿌리 노드의 키보다 크면 오른쪽 부분 트리의 삽입 결과를 오른쪽 링크에 연결한다.
 
```java
public class BinarySearchTree<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {

    protected class Node {
        protected Key key;
        protected Value value;
        protected Node left;
        protected Node right;
        protected int size;

        public Node(Key key, Value value, int size) {
            this.key = key;
            this.value = value;
            this.size = size;
        }
    }

    protected Node root;

    public int size() {
        return size(root);
    }

    protected int size(Node node) {
        if (node == null) return 0;
        return node.size;
    }

    public boolean isEmpty() {
        return size(root) == 0;
    }

    @Override
    public Value get(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to get() cannot be null");
        return get(root, key);
    }

    private Value get(Node node, Key key) {
        if (node == null) return null;
        int compare = key.compareTo(node.key);
        if (compare < 0)      return get(node.left, key);
        else if (compare > 0) return get(node.right, key);
        else                  return node.value;
    }

    @Override
    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if (value == null) {
            delete(key);
            return;
        }
        root = put(root, key, value);
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) return new Node(key, value, 1);
        int compare = key.compareTo(node.key);
        if (compare < 0) {
            node.left = put(node.left, key, value);
        } else if (compare > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }
        node.size = size(node.left) + 1 + size(node.right);
        return node;
    }

    public Key min() {
        if (root == null) throw new NoSuchElementException("Empty binary search tree");
        return min(root).key;
    }

    private Node min(Node node) {
        if (node.left == null) return node;
        return min(node.left);
    }

    public Key max() {
        if (root == null) throw new NoSuchElementException("Empty binary search tree");
        return max(root).key;
    }

    private Node max(Node node) {
        if (node.right == null) return node;
        return max(node.right);
    }

    public Key floor(Key key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        Node node = floor(root, key);
        if (node == null) return null;
        return node.key;
    }

    private Node floor(Node node, Key key) {
        if (node == null) return null;
        int compare = key.compareTo(node.key);
        if (compare == 0) {
            return node;
        } else if (compare < 0) {
            return floor(node.left, key);
        } else {
            Node rightNode = floor(node.right, key);
            if (rightNode != null) {
                return rightNode;
            } else {
                return node;
            }
        }
    }

    public Key ceiling(Key key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        Node node = ceiling(root, key);
        if (node == null) return null;
        return node.key;
    }

    private Node ceiling(Node node, Key key) {
        if (node == null) return null;
        int compare = key.compareTo(node.key);
        if (compare == 0) {
            return node;
        } else if (compare > 0) {
            return ceiling(node.right, key);
        } else {
            Node leftNode = ceiling(node.left, key);
            if (leftNode != null) {
                return leftNode;
            } else {
                return node;
            }
        }
    }

    public Key select(int index) {
        if (index < 0 || index >= size()) {
            throw new IllegalArgumentException("Index cannot be negative and must be lower than tree size");
        }
        return select(root, index).key;
    }

    private Node select(Node node, int index) {
        int leftSubtreeSize = size(node.left);
        if (leftSubtreeSize == index) {
            return node;
        } else if (leftSubtreeSize > index) {
            return select(node.left, index);
        } else {
            return select(node.right, index - leftSubtreeSize - 1);
        }
    }

    public int rank(Key key) {
        return rank(root, key);
    }

    private int rank(Node node, Key key) {
        if (node == null) return 0;
        int compare = key.compareTo(node.key);
        if (compare < 0) {
            return rank(node.left, key);
        } else if (compare > 0) {
            return size(node.left) + 1 + rank(node.right, key);
        } else {
            return size(node.left);
        }
    }

    public void deleteMin() {
        if (root == null) return;
        root = deleteMin(root);
    }

    private Node deleteMin(Node node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        node.size = size(node.left) + 1 + size(node.right);
        return node;
    }

    public void deleteMax() {
        if (root == null) return;
        root = deleteMax(root);
    }

    private Node deleteMax(Node node) {
        if (node.right == null) return node.left;
        node.right = deleteMax(node.right);
        node.size = size(node.left) + 1 + size(node.right);
        return node;
    }

    @Override
    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to contains() cannot be null");
        return get(key) != null;
    }

    @Override
    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if (isEmpty()) return;
        if (!contains(key)) return;
        root = delete(root, key);
    }

    private Node delete(Node node, Key key) {
        int compare = key.compareTo(node.key);
        if (compare < 0) {
            node.left = delete(node.left, key);
        } else if (compare > 0) {
            node.right = delete(node.right, key);
        } else {
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                Node aux = node;
                node = min(aux.right);
                node.right = deleteMin(aux.right);
                node.left = aux.left;
            }
        }
        node.size = size(node.left) + 1 + size(node.right);
        return node;
    }

    @Override
    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    private Iterable<Key> keys(Key low, Key high) {
        if (low == null) throw new IllegalArgumentException("First argument to keys() cannot be null");
        if (high == null) throw new IllegalArgumentException("Second argument to keys() cannot be null");
        Queue<Key> queue = new Queue<>();
        keys(root, queue, low, high);
        return queue;
    }

    private void keys(Node node, Queue<Key> queue, Key low, Key high) {
        if (node == null) return;
        int compareLow = low.compareTo(node.key);
        int compareHigh = high.compareTo(node.key);
        if (compareLow < 0) keys(node.left, queue, low, high);
        if (compareLow <= 0 && compareHigh >= 0) queue.enqueue(node.key);
        if (compareHigh > 0) keys(node.right, queue, low, high);
    }
    
    public int size(Key low, Key high) {
        if (low == null) throw new IllegalArgumentException("First argument to size() cannot be null");
        if (high == null) throw new IllegalArgumentException("Second argument to size() cannot be null");
        if (low.compareTo(high) > 0) return 0;
        if (contains(high)) {
            return rank(high) - rank(low) + 1;
        } else {
            return rank(high) - rank(low);
        }
    }
    
}
```

## 2. 분석
 * 이진 탐색 트리 알고리즘의 실행 시간은 트리의 모양에 종속적이다.
 * 즉, 실행 시간은 키가 삽입된 순서에 영향을 받는다.
 * 최적 조건은 완전히 균형 잡힌 트리를 이룬 경우로 N개의 노드가 있을 때 뿌리와 공백 링크 사이에 ~log(N)개의 노드가 존재한다.
 * 최악 조건은 탐색 경로에서 N개의 노드 모두를 거쳐 가야 하는 상황이다.
 * 다행히 전형적인 상황에서는 최악 조건 보다는 최적 조건인 균형 잡힌 트리에 더 가깝다.

> 명제 C : N개의 무작위 키를 가진 BST에서 탐색 성공은 평균적으로 ~2log(N)번의 비교 연산을 소요한다.
 * 탐색 성공 시 소요되는 비교 연산 횟수는 찾은 노드까지의 깊이에 1을 더한 값이다.
 * 모든 노드들에 대한 깊이를 더한 값은 트리의 `내부 경로 거리`로서 정의된 값이다.
 * C_N을 N개의 무작위 순서의 서로 다른 키를 삽입해서 만든 BST의 경로 거리라고 하자.
 * 평균적인 탐색 성공 비용은 1 + C_N / N으로 표현된다.
 * 또한 N > 1에 대해 C_0 = C_1 = 0 이므로 아래 재귀적 관계를 도출할 수 있다.
   * C_N = N - 1 + (C_0 + C_(N-1)) / N + (C_1 + C_(N-2)) / N + ... + (C_(N-1) + C_0) / N
   * N - 1 항은 뿌리에서 다른 노드들, 즉 N - 1 개의 노드들에 이르는 경로들에 추가되는 부분을 반영한다.
   * 나머지 항들은 부분 트리에서의 비용으로 N 크기에 관계없이 수식은 동일하다.
   * 위 식을 정리하면 C_N ~ 2N*log(N)과 같은 근사해가 도출된다.


> 명제 D : N개의 무작위 키로 생성된 BST에서 삽입 작업과 탐색 실패 상황은 평균적으로 ~2log(N)번의 비교 연산을 수행한다.
 * 삽입과 탐색 실패는 탐색 성공보다 한 번 더 많은 비교 연산을 소요한다.

 * 위 명제 C, D를 통해 아래와 같은 결론을 낼 수 있다.
   * BST에서의 탐색 비용은 이진 탐색보다 약 39% 더 높다.
   * BST에서의 삽입 작업 비용은 이진 탐색(~N, 선형시간)보다 효율적인 로그 시간을 따른다. 
   * 즉 탐색 비용에는 약간의 오버헤드가 발생하지만 삽입 작업 비용이 크게 감소한다.
 * BinarySearchSymbolTable과의 FrequencyCounter(8, tale.txt) 비교 실험
   * BinarySearchSymbolTable의 경우 평균 비용이 484회 (배열 접근 또는 비교 연산)
   * BinarySearchTree의 경우 평균 비용이 13회로 크게 줄어듦.

> 명제 E : BST의 모든 작업은 최악 조건에서 트리의 높이에 비례하는 시간을 소요한다.
 * BST의 순서 기반 작업들은 얼마나 효율적일까?
 * 명제 E에 따라 트리의 높이는 모든 BST 작업의 최악 조건 비용을 결정한다.
 * 트리의 높이는 평균 내부 경로 거리보다 클 것으로 기대할 수 있다. 
 * 1979년 롭슨에 의해 무작위 키로 생성된 BST의 평균 높이가 로그라는 사실이 증명되었다.
 * 나중에 데브로이에 의해 N이 커질수록 2.99log(N)에 접근한다는 것이 밝혀졌다.
 * 즉, 심볼 테이블의 모든 작업은 로그 시간 성능을 가지도록 구현하는 것이 가능하다.
 * 무작위 키로 생성되지 않은 키에서는 어떻게 될까?
   * BST에서의 최악 조건은 무작위가 아닌 키를 순서대로 또는 역순으로 삽입할 때 발생한다.
   * 이러한 이유로 더 나은 알고리즘과 더 나은 데이터 구조 개발이 이루어졌다.
   * 이는 다음 절의 내용인 균형 BST에서 다루기로 한다.

| 데이터구조 | 최악 조건 search | 최악 조건 insert | 보통 search hit | 보통 insert | 순차 작업 효율적 지원 여부 |
| --- | --- | --- | --- | --- | --- |
| 비순차 연결 리스트 - 순차탐색 | N | N | N/2 | N | no |
| 순차 배열 - 이진탐색 | logN | 2N | logN | N | yes |
| 이진 탐색 트리 | N | N | 1.39logN | 1.39logN | yes |