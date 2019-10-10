
## 1.3 BAGs, QUEUEs, and STACKs

The set of values is a collection of objects, and the operations 
revolve around adding, removing, or examining objects in the collection.   
Such data types, known as the bag, the queue, and the stack differ in 
the specification of which object is to be removed or examined next.

### APIs

APIs below reflect `generics` and `iterable` 

#### Bag

A bag is a collection where removing items is not supported. 
Its purpose is to provide clients with the ability to collect items 
and then to iterate through the collected items.

public class Bag<Item> implements Iterable\<Item>   

|         |                |                              |
|----     |----            |----                          |
|         | Bag()          | create an empty bag          |
| void    | add(Item item) | add an item                  |
| boolean | isEmpty()      | is the bag empty?            |
| int     | size()         | number of items in the bag   |    

#### FIFO queue

public class Queue<Item> implements Iterable\<Item>   

|         |                    |                                      |
|----     |----                |----                                  |
|         | Queue()            | create an empty queue                |
| void    | enqueue(Item item) | add an item                          |
| Item    | dequeue()          | remove the least recently added item |
| boolean | isEmpty()          | is the queue empty?                  |
| int     | size()             | number of items in the queue         |   

#### Pushdown (LIFO) stack

public class Stack<Item> implements Iterable\<Item>

|         |                 |                                     |
|----     |----             |----                                 |
|         | Stack()         | create an empty stack               |
| void    | push(Item item) | add an item                         |
| Item    | pop()           | remove the most recently added item |
| boolean | isEmpty()       | is the stack empty?                 |
| int     | size()          | number of items in the stack        |   

#### features

 - Generics : An essential characteristic of collection ADTs is that we should be able to use them for any type of data.
 - Autoboxing : Type parameters have to be instantiated as reference types, so Java automatically converts between a primitive type and its corresponding wrapper type. 
                This conversion enables to use generics with primitive types.
                Automatically casting a primitive type to a wrapper type is known as autoboxing, and automatically casting a wrapper type to a primitive type is known as unboxing.
 - Iterable collections : For many applications, the client's requirement is just to process each of the items in some way, or to iterate through the items in the collection. 
                          Java's foreach statement supports this paradigm.