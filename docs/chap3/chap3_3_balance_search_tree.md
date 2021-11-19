
# 3.3 균형 탐색 트리

 * BST는 다양한 응용 상황에서 만족스럽게 활용할 수 있지만, 최악 조건 성능은 문제가 있다.
 * 키가 입력되는 순서에 관계없이 로그 시간 성능이 보증되는 이진 탐색 트리를 알아보자.
 * 이상적으로는 이진 트리를 완전 균형 상태로 유지하면 N개 노드가 있는 트리의 높이가 ~ln(N)이 된다.
 * 하지만 동적으로 삽입이 일어나는 상황에서도 완전 균형 상태를 유지하려면 너무 많은 추가비용이 소모된다.
 * 따라서, 완전 균형보다는 다소 완화된 요건으로 삽입과 탐색 모두 로그 성능을 보증할 수 있는 균형 트리 데이터 구조를 알아보자.
 * 단, 범위 기반 탐색은 예외.

## 1. 2-3 탐색 트리
 * 탐색 트리의 균형 상태를 보증하려면 유연성 있게 노드 하나가 여러 개의 키를 가질 수 있도록 해야 한다.
 * 앞서 보았던 BST를 2-노드(키 하나에 두 개의 링크)라고 부른다.
 * 3-노드 BST(두 개의 키를 가지고 3개의 링크를 가지는 BST)를 만들어보자.
 * 2-노드와 3-노드 둘 다 키에 의해 나뉘어지는 각 구간별로 링크를 가진다.

> 정의 : `2-3 탐색 트리`는 공백이거나 다음 중 하나이다. 
>- 2-노드: 하나의 키(와 그와 연관된 값) 그리고 두 개의 링크를 가진 노드. 
> 왼쪽 링크는 더 작은 키들을 담고 있는 2-3 탐색 트리를, 오른쪽 링크는 더 큰 키들을 담고 있는 2-3 탐색 트리를 가리킨다.
>- 3-노드 : 두 개의 키(와 각각 연관된 두 개의 값) 그리고 세 개의 링크를 가진다. 왼쪽 링크는 더 작은 키들을 담고 있는 
> 2-3 탐색 트리를, 가운데 링크는 두 개의 키 사이에 있는 키들을 담고 있는 2-3 탐색 트리를, 오른쪽 링크는 더 큰 키들을 
> 담고 있는 2-3 탐색 트리를 가리킨다.
> 
> 공백 트리는 null 링크로 표현한다.

 * 완전하게 균형 잡힌 2-3 탐색 트리를 `2-3 트리`라고 부르기로 하자.
 * 2-3 트리를 자유롭게 조작할 수 있다고 탐색 트리로서 어떻게 이용할 수 있는지 알아보자.
 * 탐색
   * BST 탐색 알고리즘을 일반화한다.
   * 트리에 키가 존재하는지 알기 위해 뿌리의 키들과 비교한다. 만약 그 중 하나와 동일하면 탐색 성공, 그렇지 않으면 키의 값에 따라 목적하는 키가 들어 있을 수 있는 적합한 구간의 부분 트리를 탐색한다.
   * 만약 부분 트리로의 링크가 null이면 탐색 실패이다. 그렇지 않다면 그 부분 트리를 탐색한다.
 * 2-노드로의 삽입
   * 2-3 트리에 새로운 키를 삽입할 때는 탐색 실패 후에 바닥에 새로운 노드를 만들어 연결할 수 있다.
   * 하지만 그렇게 하면 트리의 완전 균형 상태가 망가지게 된다.
   * 2-노드를 3-노드로 바꾸어 새로운 키를 추가하면 균형 상태가 망가지지 않는다.
   * 탐색이 종료된 3-노드라면 추가적인 작업이 필요하다.
 * 한 개의 3-노드로 구성된 트리로의 삽입
   * 3-노드 하나는 두 개의 키를 저장하고 있으며, 새로운 키가 들어갈 자리가 없다.
   * 삽입을 위해 임시로 4-노드(3개의 키와 4개의 링크)로 만들어 키를 추가한다.
   * 그 다음 4-노드를 3개의 2-노드로 분리하여 3개의 2-노드를 가진 트리로 변환한다.
   * 이 과정에서 트리의 높이가 1 증가하게 된다.
 * 2-노드를 부모로 가진 3-노드로의 삽입
   * 우선 3-노드를 임시로 4-노드로 만들어 중간 키를 담을 공간을 만든다.
   * 중간 키를 담을 새로운 노드를 만드는 대신 중간 키를 부모 노드로 옮긴다. (부모 노드는 2-노드에서 3-노드로 바뀐다.)
   * 변환 후에도 트리의 정렬 특성이 유지되며, 균형 또한 유지된다.
 * 3-노드를 부모로 가진 3-노드로의 삽입
   * 우선 3-노드를 임시로 4-노드로 만들어 중간 키를 담을 공간을 만든다.
   * 중간 키를 담을 새로운 노드를 만드는 대신 중간 키를 부모 노드로 옮긴다.
   * 부모 노드는 3-노드에서 임시로 4-노드로 바꾼다.
   * 이 과정을 부모가 2-노드일 때 까지 계속 위로 거슬러 올라간다. 
   * 만난다면 2-노드가 3-노드로 변환되고 상황 종료.
   * 하지만 뿌리 노드까지 3-노드라면 아래 작업을 추가적으로 수행해야 한다.
 * 뿌리 노드의 분할
   * 위 작업에서 뿌리 노드까지 이미 3-노드라면, 뿌리 노드를 임시로 4-노드로 만든다.
   * 임시 4-노드인 뿌리 노드를 3개의 2-노드로 만들고 트리 높이를 1 증가시킨다.
   * 변환이 뿌리 노드에서 일어나기 때문에 여전히 완전 균형 상태가 유지된다.
 * 특징
   * 지역 변환
     * 임시 4-노드를 분할하는 작업은 2-3 트리의 6가지 변환 중 한 가지이다.
     * 4-노드는 뿌리일 수도 있고, 2-노드의 왼쪽 또는 오른쪽 자식 노드일 수도 있고, 3-노드의 왼쪽, 중간, 오른쪽 자식 노드일 수도 있다.
     * 2-3 트리에서의 삽입 알고리즘은 이들 변환이 모두 순수하게 지역적이라는 특성에 근간을 두고 있다.
     * 즉, 지정된 노드와 링크들 외에는 검사되거나 변경되어야 할 노드가 없다.
     * 각 변환마다 변경되는 링크의 개수는 작은 상수값 이하로 제한된다.
     * 특히 트리의 바닥이 아니더라도 트리의 어느 부분이든 특정 패턴이 있기만 하다면 이러한 변환이 효과적으로 수행될 수 있다.
     * 각각의 변환은 임시 4-노드의 키 하나를 부모로 전달하면서 트리의 다른 부분은 건드리지 않고 관련된 링크들만 적절하게 조정한다.
   * 전역 속성
     * 지역 변환들은 트리의 전역적인 정렬 속성과 완전 균형 속성을 깨뜨리지 않고 유지한다.
     * 위에서 설명한 모든 변환은 이러한 속성을 유지한다.
     * 단, 뿌리 노드가 3개의 2-노드로 바뀔 경우 예외적으로 null 링크로의 거리가 일괄적으로 1 증가한다.
   * 표준 BST와 달리 2-3 트리는 위에서 아래로 자라지 않고 아래에서 위로 자란다.
   * BST에서 10개의 키를 오름차순으로 삽입하면 최악 조건으로서 높이 9인 트리가 생성되지만 2-3 트리에서는 높이 2인 트리가 생성된다.
   * BST에서는 평균 성능에 관심이 있지만 2-3 트리에서는 최악 조건 성능에 더 관심이 있다.
   * 심볼 테이블에서는 클라이언트가 어떤 순서로 키를 삽입할지 통제할 수 없기 때문에 최악 조건을 분석하는 것은 성능을 보증할 한 가지 방법이 된다.

> 명제 F : 2-3 트리에서는 N개의 키로 탐색과 삽입 작업을 할 때 방문하는 노드의 수가 최대 log(N)개임이 보증된다.
 * N개의 노드를 가진 2-3 트리의 높이는 [log_3(N)] = [log(N)/log(3)]과 [log(N)] 사이에 있다.
 * [log_3(N)] : 트리의 모든 노드가 3-노드인 경우
 * [log(N)] : 트리의 모든 노드가 2-노드인 경우
 * 작업 중 방문하는 각각의 노드에서 소요되는 시간은 상숫값으로 제한된다.
 * 탐색/삽입 두 작업 모두 트리를 내려가거나 올라오는 경로 하나만을 사용하므로 전체 소요 비용이 로그임이 보증된다.
 * 10억 개의 키를 가진 2-3 트리의 높이는 19~30 정도이며, 이는 10억 개의 노드를 가진 트리라 하더라도 최대 30 노드만 검색하면 탐색/삽입 작업을 완료할 수 있을을 뜻한다.

## 2. 레드-블랙 BST
 * 기본 아이디어는 표준 BST(2-노드만으로 이루어진 트리)를 기반으로 하되 3-노드를 표현할 수 있도록 추가 정보를 첨가해 넣는 것이다.
 * 이를 위해 링크를 두 종류로 나누어 생각한다.
   * 레드 링크 : 2개의 2-노드를 묶어서 3-노드를 표현한다.
   * 블랙 링크 : 2-3 트리들을 묶는다. 왼쪽으로 기울어진(큰 키에서 작은 키로 연결된) 하나의 레드 링크로 두 개의 2-노드를 연결하여 3-노드로 삼는다.
 * 이러한 방식은 표준 BST의 get() 메서드를 코드 수정 없이 그대로 재활용 할 수 있다는 장점이 있다.
 * 어떤 2-3 트리가 주어지든 각 노드를 이러한 방식으로 변환하여 대응되는 BST를 쉽게 유도해 낼 수 있다.
 * 이런 방식으로 2-3 트리를 표현하는 BST를 레드-블랙 BST라고 부른다.
 * 레드-블랙 BST를 레드 링크, 블랙 링크를 가지면서 아래 세 가지 조건을 만족하는 BST로 정의할 수도 있다.
   * 레드 링크는 왼쪽으로 기운다.
   * 어떤 노드도 두 개의 레드 링크에 연결되지 않는다.
   * 트리는 완전 블랙 균형 상태이다 : 뿌리에서 null 링크로의 모든 경로들이 동일한 개수의 블랙 링크를 거쳐 간다. 이 숫자를 트리의 블랙 높이라 부른다.
 * 1:1 대응
   * 위와 같이 정의된 레드-블랙 BST는 2-3 트리와 1:1 대응이다.
     * 레드-블랙 트리에서 레드 링크를 가로로 표현하면 2-3 트리가 된다.
   * 즉, 레드-블랙 트리는 BST이기도 하며 2-3 트리이기도 하다.
   * 따라서 2-3 트리의 삽입 알고리즘을 BST와 1:1 대응을 유지하도록 구현하면 두 정의에 따른 트리의 장점만을 취할 수 있다.
 * 색상 표현
   * 각 노드는 단 하나의 들어오는 링크(부모 링크)를 가진다고 하자.
   * 들어오는 링크의 성격을 노드 안에서 색상으로 표현한다.
   * 이를 위해 boolean 타입 인스턴스 변수 color를 Node 데이터 타입에 추가한다. true이면 레드, false면 블랙 링크이다.
   * 관례로 null 링크는 블랙으로 표현하기로 한다.
   * 가독성을 위해 상수 RED와 BLACK을 정의하여 이용한다.
   * 노드가 가진 부모로부터의 링크가 레드인지 블랙인지 확인하기 위해 private 메서드 isRed()를 둔다.
   * 노드의 색을 이야기할 때는 그 노드를 가리키는(부모로부터 들어오는) 링크의 색을 의미하는 것으로 한다.
 * 로테이션
   * 앞으로 살펴볼 구현에서는 작업 와중에 오른쪽으로 기운 레드 링크가 생기거나 같은 행에 두 개의 레드 링크가 발생할 수도 있다.
   * 하지만 작업이 완료되기 전에 항상 올바르게 교정된다.
   * 이 교정 작업은 `로테이션`이라는 작업을 주의 깊게 적용함으로써 이루어진다.
   * `로테이션`은 레드 링크의 방향을 바꾸는 작업이다.
   * 오른쪽으로 기운 레드 링크를 왼쪽으로 옮기는 작업을 `왼쪽 로테이션`이라고 하자.
   * 이와 반대되는(왼쪽과 오른쪽이 바뀐) 작업을 `오른쪽 로테이션`이라 하자. 
 * 로테이션 후 부모 링크의 재설정
   * 로테이션은 그 결과로 링크를 리턴한다. 
   * 이를 이용하여 부모(또는 뿌리)의 링크를 적절하게 재설정한다.
   * 리턴받은 링크가 왼쪽일 수도, 오른쪽일 수도 있지만 부모의 링크를 재설정하는 데에는 문제없다.
   * 재설정된 링크는 레드일 수도, 블랙일 수도 있다.
   * 이 때문에 트리의 같은 행에 두 개의 레드 링크가 만들어질 수도 있다. 
   * 이 경우도 자기 자신을 로테이션하여 상황을 바로 잡을 수 있다.
 * 로테이션은 정렬과 완전 블랙 균형을 보존한다. 나머지 두 속성(연속된 레드 링크는 존재하지 않음, 오른쪽으로 기운 레드 링크는 존재하지 않음)은 어떻게 보존할까?
 * 단일 2-노드로의 삽입
   * 키 하나를 저장하고 있는 레드-블랙 BST는 2-노드 하나만 존재한다.
   * 여기에 두 번째 키를 삽입할 때 로테이션이 필요할 수 있다.
   * 만약 새로운 키가 이미 존재하는 키보다 작다면 새로운 키로 새로운 노드(레드 노드)를 만드는 것으로 작업이 끝나고 3-노드 하나인 레드-블랙 BST가 생긴다.
   * 하지만 새로운 키가 기존 키보다 크다면 새로운 노드 추가로 인해 오른쪽으로 기울어진 레드 링크가 생겨버린다. 이 경우 뿌리 노드를 rotateLeft 해야 한다.
 * 바닥에 있는 2-노드로의 삽입
   * 일반 BST에서와 동일한 방법으로 레드-블랙 BST에 키를 삽입한다.
   * 하지만 항상 레드 링크로 부모와 연결한다.
   * 만약 새로운 노드가 왼쪽 링크에 연결된다면 부모 노드는 3-노드가 된다.
   * 만약 오른쪽 링크에 연결된다면 잘못된 방향으로 연결되었으므로 왼쪽 로테이션을 수행하여 작업을 마무리한다.
 * 두 개의 키로 구성된(3-노드) 트리로의 삽입
   * 새로운 키가 기존의 두 키보다 작거나, 사이거나, 크거나의 세 가지 경우가 있을 수 있다.
   * 각각 경우 모두 두 개의 레드 링크로 새로운 노드를 생성하게 된다.
     * 새로운 키가 기존의 두 키보다 큰 경우
       * 기존 3-노드의 가장 오른쪽에 연결한다.
       * 중간 키가 뿌리가 되고 그보다 작은 키와 큰 키가 각각 레드 링크와 연결되어 트리의 균형이 이루어진다.
       * 두 레드 링크의 색을 블랙으로 바꾸면 높이가 1인 완전 블랙 균형 트리가 된다.
     * 새로운 키가 기존 두 키보다 작은 경우
       * 새로운 키는 왼쪽 링크로 가게 된다.
       * 같은 행에 왼쪽으로 기울어진 두 개의 레드 링크가 존재하게 된다.
       * 이 때 가장 위쪽 링크를 오른쪽으로 회전하면 중간 키가 뿌리가 되고 앞의 경우와 같아진다.
       * 두 레드 링크의 색을 블랙으로 바꾸면 높이가 1인 완전 블랙 균형 트리가 된다.
     * 새로운 키가 두 키 사이인 경우
       * 새로운 키는 왼쪽 링크의 오른쪽 링크에 존재하는 상황이 된다.
       * 이번에도 같은 행에 두 개의 레드 링크가 존재하게 된다.
       * 아래쪽 링크를 왼쪽으로 로테이션하면 바로 앞의 경우와 같아지게 된다.
       * 가장 위쪽 링크를 오른쪽으로 회전하고, 두 레드 링크의 색을 블랙으로 바꾸면 높이가 1인 완전 블랙 균형 트리가 된다.
   * 위 내용을 요약하면, 뿌리의 두 자식 링크의 색을 반전하고 0~2번 로테이션하여 목적하는 결과를 얻을 수 있다.
 * 색 반전
   * 어떤 노드의 두 자식 노드의 색을 반전시킬 때에는 flipColors() 메서드를 이용한다.
   * 자식 노드들의 색을 레드에서 블랙으로 반전하면서 부모 노드의 색도 블랙에서 레드로 반전한다.
   * 이러한 색 반전 작업은 로테이션과 마찬가지로 트리의 완전 균형 상태를 유지하는 지역 변환이라는 매우 중요한 속성을 가진다.
 * 뿌리를 블랙으로 유지하기
   * 위에서 살펴본 경우 (단일 3-노드로의 삽입) 색 반전 작업이 뿌리 노드를 레드 노드로 만든다. 
   * 엄밀하게 따지자면 어떤 노드가 레드라는 것은 그 노드가 부모 노드와 함께 3-노드의 구성요소 중 하나임을 의미한다.
   * 하지만 뿌리는 부모가 없으므로 삽입 작업 후에 뿌리 노드의 색을 블랙으로 돌려 놓는다.
   * 뿌리 노드의 색이 반전될 때마다 트리의 블랙 높이가 1씩 증가한다.
 * 바닥에 있는 3-노드로의 삽입
   * 앞서 설명한 세 가지 상황이 동일하게 나타난다.
   * 색 반전은 중간 노드를 레드 노드로 만들며, 이것은 부모를 따라 올라가며 동일한 상황이 발생한다는 의미이다.
   * 이 부분은 트리를 거슬로 올라가면서 교정이 진행된다.
 * 트리를 거슬러 레드 링크 올려보내기
   * 2-3 트리의 삽입 알고리즘은 3-노드를 분할하여 중간 노드를 2-노드나 뿌리를 만날 때까지 계속해서 부모로 올려보낸다.
   * 즉, 필요한 로테이션 작업을 완료할 때마다 중간 노드를 레드 노드로 만드는 색 반전을 한다.
   * 그 노드의 부모 노드 입장에서는 새로운 노드의 연결로 인한 레드 링크의 처리 상황과 동일하다.
   * 다시 말해 레드 링크를 중간 노드로 올려보낸다.
   * 이러한 작업은 2-3 트리로의 삽입을 구현하기 위한 레드-블랙 트리 상의 동작들이다.
   * 즉, 3-노드로 삽입하기 위해 임시 4-노드를 만들고, 그 노드를 분할하고, 중간 키로 연결되는 레드 링크를 그 부모로 올려보낸다.
   * 이러한 절차를 2-노드 또는 뿌리 노드에 도달할 때까지 동일하게 계속한다.
 * 정리하면, 새로운 키를 삽입할 때는 왼쪽 로테이션, 오른쪽 로테이션, 그리고 색 반전 이 3가지 작업을 주의 깊게 적용함으로써 2-3 트리와 레드-블랙 트리의 1:1 관계를 유지할 수 있다.
 * 삽입 작업은 삽입 위치에서 필요한 노드를 트리 위로 올려보내면서 다음의 작업들을 차례로 적용하여 완수된다.
   * 만약 오른쪽 자식 노드가 레드이고 왼쪽 자식 노드가 블랙이면 왼쪽으로 로테이션한다.
     * 부모가 2-노드일 때 3-노드가 왼쪽으로 기울게 해야 하는 경우
     * 새로운 레드 링크가 3-노드의 중간 링키일 때 가장 아래 링크를 왼쪽으로 기울게 해야 하는 경우
   * 만약 왼쪽 자식 노드와 그 노드의 왼쪽 자식 노드가 모두 레드이면 오른쪽으로 로테이션한다.
   * 만약 양쪽 자식 노두 모두 레드이면 색 반전한다.
 * 구현
   * 트리를 거슬러 올라가면서 균형을 잡아 나가기 때문에 표준적인 재귀 구현으로 쉽게 만들 수 있다.
   * 바로 위의 세 가지 작업은 각각 트리의 두 노드의 색을 검사하는 if문 하나로 처리된다.
   * 노드의 색을 3회 또는 5회 비교하는 비용으로(추가로 한두 번의 로테이션 또는 색 반전 작업이 있을 수 있음) 거의 완전 균형 상태인 BST를 얻을 수 있다.
 * 삭제
 
## 3. 삭제
 * 삭제의 구현도 간단하지 않다. 
 * 삽입에서 처럼 일련의 지역 변환을 정의하여 노드를 삭제하면서도 완전 균형 상태를 유지하게 해야 하기 때문이다.
 * 다만, 삽입보다 좀 더 복잡한게, 탐색 경로를 내려갈 때도 변환을 해야 하기 때문이다.
 * 노드가 삭제될 수 있도록 임시 4-노드를 생성할 때는 내려가는 탐색 경로를, 남아 있는 4-노드를 분할할 때는 삽입의 경우와 동일하게 올라가는 탐색 경로를 사용한다.
 * 우선 트리의 위아래 양쪽으로 변환을 전개하는 알고리즘을 생각해보자.
   * 4-노드가 임시가 아닌 보통 노드로서 계속 존재하는 2-3-4 트리를 대상으로 한다.
   * 삽입 알고리즘은 트리를 내려가면서 현재 노드가 4-노드가 아니라는 불변 조건이 만족되도록 변환을 수행한다.
   * 즉, 바닥에 새로운 키를 추가할 공간이 존재하도록 한다.
   * 그리고 다시 트리를 올라가면서 혹시 생성되었을 수 있는 4-노드들의 균형을 바로잡는다.
   * 트리를 내려가면서 수행하는 변환은 2-3 트리의 4-노드를 분할할 때 사용했던 변환과 완전히 동일하다.
     * 만약 뿌리가 4-노드라면 3개의 2-노드로 분할하고 트리의 높이를 1 증가시킨다.
     * 트리를 내려갈 때, 만약 2-노드를 부모로 가진 4-노드를 만나면 4-노드를 두 개의 2-노드로 분할하고 중간 키를 부모로 올려보내 부모를 3-노드로 만든다.
     * 트리를 내려갈 때, 만약 3-노드를 부모로 가진 4-노드를 만나면 4-노드를 두 개의 2-노드로 분할하고 중간 키를 부모로 올려보내 부모를 4-노드로 만든다.
   * 불변 조건 때문에 4-노드를 부모로 가지는 4-노드를 만나는 상황은 고려할 필요가 없다.
   * 불변 조건이 지켜지는 한 바닥에는 2-노드 또는 3-노드만 존재한다.
   * 따라서 새로운 키를 추가할 공간이 항상 존재한다.
   * 이 알고리즘을 레드-블랙 트리에서 고현하기 위해서는
     * 4-노드를 세 개의 2-노드로 구성된 균형 잡힌 부분 트리로 표현한다. 이 때 왼쪽, 오른쪽 자식 노드 모두 부모에 레드 링크로 연결한다.
     * 트리를 내려갈 때 4-노드를 분할하며 색 반전한다.
     * 트리를 올라가면서 삽입 때와 마찬가지로 로테이션을 이용해 4-노드의 균형을 잡는다.
   * 놀랍게도 put() 메서드에서 colorFlip() 호출을 재귀 호출 이전으로 옮기기만 하면 탑-다운 2-3-4 트리를 구현할 수 있다.
   * 해당 알고리즘은 현재 노드에서 한 개 또는 두 개 링크 범위 안에서만 접근하기 때문에 복수의 프로세스가 같은 트리에 접근해야 하는 경우 유리하다.
 * 다음으로 2-3 트리에서 최소 항목을 삭제하는 작업을 생각해보자.
   * 기본적인 아이디어는 트리의 바닥에 있는 3-노드에서는 쉽게 키를 삭제할 수 있지만, 2-노드에서는 어렵다는데에서 출발한다.
   * 2-노드에서 키를 삭제하면 키가 없는 노드가 남게 된다. null 링크로 만들면 완전 균형 상태가 망가지게 된다.
   * 따라서 아래와 같은 접근 방식이 필요하다.
     * 삽입 위치를 찾는 작업이 2-노드에서 끝나지 않는다는 것을 보증하기 위해, 트리를 내려가면서 적합한 변환을 적용하여 마지막에 2-노드를 만나지 않는다는 것을 불변조건으로 한다.
     * 즉, 3-노드 또는 임시 4-노드를 현재 노드가 되게 한다.
   * 삭제 작업을 진행할 때 아래 세 가지 중 한가지 경우를 만나게 된다.
     * 현재 노드의의 왼쪽 자식 노드가 2-노드가 아니라면 아무것도 할 것이 없다.
     * 만약 왼쪽 자식 노드가 2-노드이고 바로 옆의 형제 노드가 2-노드가 아니라면 그 형제 노드의 가장 작은 키를 부모로 옮기고, 다시 부모의 가장 작은 키를 자식 노드로 옮긴다.
       * 즉, 여유 있는 형제 노드에서 키를 빌려와 2-노드를 3-노드로 만든다.
     * 만약 왼쪽 자식 노드와 바로 옆의 형제 노드 둘 다 2-노드이면 부모의 가장 작은 키와 함께 모두 합쳐서 4-노드로 만들고 부모를 한 단계 작은 노드로 만든다.
   * 왼쪽 링크에서 바닥까지 이러한 절차를 따르면 가장 작은 키를 3-노드 또는 4-노드로 흡수하게 된다.
   * 따라서 3-노드를 2-노드로, 4-노드를 3-노드로 줄이면서 쉽게 삭제할 수 있다.
   * 그 다음 트리를 올라가면서 사용되지 않은 임시 4-노드를 분할한다.
 * delete()의 구현은 앞의 최소 항목 삭제 과정에서 사용한 방법을 동일하게 현재 노드가 2-노드가 보증하는 방식을 사용한다.
 * 만약 탐색 키가 바닥에 있다면 그냥 삭제하면 된다.
 * 만약 키가 바닥에 없다면 보통의 BST 처럼 근후행(successor) 노드와 교환해야 한다. 그 다음 현재 노드가 2-노드가 아니게 되었으므로 뿌리 노드가 2-노드가 아닌 부분 트리에서 최소 항목을 삭제하는 문제로 단순화된다.
 * 삭제 이후에는 탐색 경로를 거슬러 올라가면서 남아있는 4-노드를 분할한다.

## 4. 코드

```java
public class RedBlackBST<Key extends Comparable<Key>, Value> implements SymbolTable<Key, Value> {

    protected final static boolean RED = true;
    protected final static boolean BLACK = false;

    protected class Node {
        public Key key;
        public Value value;
        public Node left, right;

        boolean color;
        int size;

        Node(Key key, Value value, int size, boolean color) {
            this.key = key;
            this.value = value;
            this.size = size;
            this.color = color;
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

    protected boolean isRed(Node node) {
        if (node == null) return false;
        return node.color == RED;
    }

    protected Node rotateLeft(Node node) {
        if (node == null || node.right == null) return node;

        Node newRoot = node.right;
        node.right = newRoot.left;
        newRoot.left = node;

        newRoot.color = node.color;
        node.color = RED;

        newRoot.size = node.size;
        node.size = size(node.left) + 1 + size(node.right);

        return newRoot;
    }

    protected Node rotateRight(Node node) {
        if (node == null || node.left == null) return node;

        Node newRoot = node.left;
        node.left = newRoot.right;
        newRoot.right = node;

        newRoot.color = node.color;
        node.color = RED;

        newRoot.size = node.size;
        node.size = size(node.left) + 1 + size(node.right);

        return newRoot;
    }

    protected void flipColors(Node node) {
        if (node == null || node.left == null || node.right == null) return;
        if (
                (isRed(node) && !isRed(node.left) && !isRed(node.right))
                || (!isRed(node) && isRed(node.left) && isRed(node.right))
        ) {
            node.color = !node.color;
            node.left.color = !node.left.color;
            node.right.color = !node.right.color;
        }
    }

    public void put(Key key, Value value) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if (value == null) {
            delete(key);
            return;
        }
        root = put(root, key, value);
        root.color = BLACK;
    }

    private Node put(Node node, Key key, Value value) {
        if (node == null) return new Node(key, value, 1, RED);
        int compare = key.compareTo(node.key);

        if (compare < 0) {
            node.left = put(node.left, key, value);
        } else if (compare > 0) {
            node.right = put(node.right, key, value);
        } else {
            node.value = value;
        }

        if (isRed(node.right) && !isRed(node.left)) {
            node = rotateLeft(node);
        }
        if (isRed(node.left) && isRed(node.left.left)) {
            node = rotateRight(node);
        }
        if (isRed(node.left) && isRed(node.right)) {
            flipColors(node);
        }

        node.size = size(node.left) + 1 + size(node.right);
        return node;
    }

    public Value get(Key key) {
        if (key == null) return null;
        return get(root, key);
    }

    private Value get(Node node, Key key) {
        if (node == null) return null;
        int compare = key.compareTo(node.key);
        if (compare < 0) {
            return get(node.left, key);
        } else if (compare > 0) {
            return get(node.right, key);
        } else {
            return node.value;
        }
    }

    public boolean contains(Key key) {
        if (key == null) throw new IllegalArgumentException("Argument to contains() cannot be null");
        return get(key) != null;
    }

    public Key min() {
        if (root == null) throw new NoSuchElementException("Empty Red-Black BST");
        return min(root).key;
    }

    protected Node min(Node node) {
        if (node.left == null) return node;
        return min(node.left);
    }

    public Key max() {
        if (root == null) throw new NoSuchElementException("Empty Red-Black BST");
        return max(root).key;
    }

    protected Node max(Node node) {
        if (node.right == null) return node;
        return max(node.right);
    }

    public Key floor(Key key) {
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
        if (index >= size()) throw new IllegalArgumentException("Index is higher than tree size");
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
        if (isEmpty()) return;
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMin(root);
        if (!isEmpty()) root.color = BLACK;
    }

    protected Node deleteMin(Node node) {
        if (node.left == null) return null;
        if (!isRed(node.left) && !isRed(node.left.left)) node = moveRedLeft(node);
        node.left = deleteMin(node.left);
        return balance(node);
    }

    public void deleteMax() {
        if (isEmpty()) return;
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = deleteMax(root);
        if (!isEmpty()) root.color = BLACK;
    }

    protected Node deleteMax(Node node) {
        if (isRed(node.left)) node = rotateRight(node);
        if (node.right == null) return null;
        if (!isRed(node.right) && !isRed(node.right.left)) node = moveRedRight(node);
        node.right = deleteMax(node.right);
        return balance(node);
    }

    public void delete(Key key) {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        if (isEmpty() || !contains(key)) return;
        if (!isRed(root.left) && !isRed(root.right)) root.color = RED;
        root = delete(root, key);
        if (!isEmpty()) root.color = BLACK;
    }

    private Node delete(Node node, Key key) {
        if (node == null) return null;
        if (key.compareTo(node.key) < 0) {
            if (!isRed(node.left) && node.left != null && !isRed(node.left.left)) node = moveRedLeft(node);
            node.left = delete(node.left, key);
        } else {
            if (isRed(node.left)) node = rotateRight(node);
            if (key.compareTo(node.key) == 0 && node.right == null) return null;
            if (!isRed(node.right) && node.right != null && !isRed(node.right.left)) node = moveRedRight(node);
            if (key.compareTo(node.key) == 0) {
                Node aux = min(node.right);
                node.key = aux.key;
                node.value = aux.value;
                node.right = deleteMin(node.right);
            } else {
                node.right = delete(node.right, key);
            }
        }
        return balance(node);
    }

    protected Node moveRedLeft(Node node) {
        // Assuming that node is red and both node.left and node.left.left are black,
        // make node.left or one of its children red
        flipColors(node);
        if (node.right != null && isRed(node.right.left)) {
            node.right = rotateRight(node.right);
            node = rotateLeft(node);
            flipColors(node);
        }
        return node;
    }

    protected Node moveRedRight(Node node) {
        // Assuming that node is red and both node.right and node.right.left are black,
        // make node.right or one of its children red
        flipColors(node);
        if (node.left != null && isRed(node.left.left)) {
            node = rotateRight(node);
            flipColors(node);
        }
        return node;
    }

    protected Node balance(Node node) {
        if (node == null) return null;
        if (isRed(node.right)) node = rotateLeft(node);
        if (isRed(node.left) && isRed(node.left.left)) node = rotateRight(node);
        if (isRed(node.left) && isRed(node.right)) flipColors(node);
        node.size = size(node.left) + 1 + size(node.right);
        return node;
    }

    public Iterable<Key> keys() {
        return keys(min(), max());
    }

    public Iterable<Key> keys(Key low, Key high) {
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

## 5. 레드-블랙 BST의 속성
 * 2-3 트리와의 연계성을 찾는 일로 귀결된다.
 * 최종 결과는 레드-블랙 BST에서의 모든 심볼 테이블 작업들은 트리 크기의 로그에 비례하는 성능을 가진다는 것이다. 
 * 단, 범위 탐색은 결과 키들 개수에 비례하는 추가 비용이 있다.

> 명제 G : N개의 노드를 가진 레드-블랙 BST의 높이는 2log(N)보다 크지 않다.
 * 2-3 트리에서의 최악 조건은 가장 왼쪽 경로만 3-노드로 되어 있고 나머지는 모두 2-노드인 경우이다.
 * 이 경우, 뿌리에서 왼쪽 링크를 얻는 경로가 2-노드만 관계될 때의 길이 ~ log(N) 보다 두 배 더 길다.

> 명제 H : N개의 노드를 가진 레드-블랙 BST에서 뿌리로부터 노드까지의 평균 경로 거리는 ~ 1.00log(N)이다.
 * 전형적인 BST와 비교해 볼 때 레드-블랙 BST는 균형이 꽤 잘 잡혀 있다.
 * FrequencyCounter 결과를 통해 경로 길이(탐색 비용)가 기초적인 BST에 비해 40% 정도 더 짧다는 것을 확인할 수 있다.

 * 레드-블랙 BST의 get()은 노드의 색을 상관하지 않기 때문에 균형 유지 메커니즘으로 인한 오버헤드가 없다.
 * 오히려 트리가 균형 잡혀 있기 때문에 탐색 속도가 기초적인 BST보다 더 빠르다.
 * 따라서 상대적으로 적은 비용으로 탐색 성능이 최적에 가까워진다.
 * 이 구현은 삽입과 탐색 둘 다 로그 성능을 보증하는 첫 번째 구현이다.
 * 또한 이 구현은 짧은 내부 루프를 가진다.
 * 자바의 TreeSet, TreeMap이 레드-블랙 트리를 구현하고 있다.

| 데이터구조 | 최악 조건 search | 최악 조건 insert | 보통 search hit | 보통 insert | 순차 작업 효율적 지원 여부 |
| --- | --- | --- | --- | --- | --- |
| 비순차 연결 리스트 - 순차탐색 | N | N | N/2 | N | no |
| 순차 배열 - 이진탐색 | logN | 2N | logN | N | yes |
| 이진 트리 탐색 | N | N | 1.39logN | 1.39logN | yes |
| 2-3 트리 탐색 | 2logN | 2logN | 1.00logN | 1.00logN | yes |

## 6. 순차 심볼 테이블 API
 * 레드-블랙 BST의 가장 큰 장점 중 하나는 put() 메서드에만 복잡한 코드가 들어간다는 점이다.
 * 표준 BST에서 구현한 min/max, select, rank, floor, ceiling, 그리고 범위 검색을 전혀 수정 없이 그대로 사용할 수 있다.
 * 명제 G와 명제 E를 통해 API 상 모든 동작들이 로그 성능을 가진다는 것이 보증된다.

> 명제 I : 레드-블랙 BST에서 다음의 작업들은 최악 조건에서 로그 시간 성능을 보인다. 
>  => 탐색, 삽입, 최솟값 찾기, 최댓값 찾기, floor, ceiling, rank, select, 최솟값 삭제, 최댓값 삭제, 삭제, 범위 헤아리기

## 7. AVL 트리
 * BST의 한 종류로 모든 노드들이 그 형제 노드와의 높이 차이가 1이하라는 특성을 가진다.
 * 짝수 높이 노드에서 홀수 높이 노드로 이동하면서 레드 링크로 색을 입히면 2-3-4 트리가 된다.
 * Balance Factor(BF) = 높이 차이를 통해 균형을 유지한다.
 * BF가 -1 ~ 1 범위를 벗어난다면 그 트리의 균형은 깨진 것이며, BF가 2보다 크거나 -2보다 작은 노드 기준으로 로테이션이 수행된다.
 * 레드-블랙 트리보다 더욱 엄격한 균형을 이루고 있기 때문에 더 빠른 조회를 제공
 * 레드-블랙 트리보다 더욱 엄격한 균형을 이루고 있기 때문에 삽입과 제거 작업에 있어서 오버헤드 발생
 * 각 노드에 대해 BF를 저장하므로 노드 당 integer에 해당하는 추가 메모리 필요 (레드-블랙 트리는 1비트로 충분)
 * AVL은 보통 더 빠른 검색이 필요한 데이터베이스 등에서 사용