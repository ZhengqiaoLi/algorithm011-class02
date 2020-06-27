学习笔记:
一、Queue是一个interface，特点是FIFO，入队出队都是O(1)。
提供两套接口：
   1、add(E)、remove()、element().
      add: 当队满的时候，add添加元素，会抛出IllegalStateException运行时异常；添加Null时，会抛出NullPointException运行时异常等。其中，不支持Null的原因是：当队空时，poll()返回为null，如果支持添加元素null，将不能区分这两种情况。
      remove: 当队空的时候，移除对头元素，会抛出NoSuchElementException异常。
      element：查看对头元素，当队空时，会抛出NoSuchElementException异常。
   2、offer(E)、poll()、peek()
      offer: 返回值为true/false
      poll: 返回正确值或null
      peek: 返回正确值或null
二、Queue的子类
     1、BlockingQueue
       在BlockingQueue中又提供了put、take；offer(E,timeout,timeunit)、poll(timeout, timeunit)两组阻塞接口，用于多线程模式中的生产-消费模式。如：ArrayBlockingQueue、LinkedBlockingQueue、PriorityBlockingQueue都是实现的BlockingQueue接口.
       ArrayBlockingQueue:内部维持一个capacity的数组（capacity是构造函数的必填字段），ReentranLock和两个Conditino，offer和put在队满时的表现不一样，offer(E)会返回false，put会阻塞，而offer(E, timeout,timeunit)会等待时间后，判断是否队满，仍然满会返回false；添加元素成功后，会调用notEmpty.signal()唤醒。而poll、put、poll(timeout,timeunit)变现与offer组类似，Condition使用的则是notEmpty。
       LinkedBlockingQueue:内部维持两个ReentranLock和对应的两个Condition；capacity默认为Integer.MAX_VALUE; LinkedBlockingQueue将读写分离，性能上是优于ArrayBlockingQueue的。需要注意的是：putLock.await()不阻塞时，需要再次判断是否唤醒其他putLock阻塞线程，不然容易造成死锁，以及判断是否需要唤醒takeLock。如put线程A/B都阻塞着，线程Btake两个数据后，不再读数据，会signal一个线程，假如A唤醒后，处理结束不唤醒B线程。当A和C线程不再处理任务时，B线程会一直阻塞着，无人唤醒啊。
       PriorityBlockingQueue: 无解阻塞队列; 实现接口Comparator比较器作为优先级队列的大小比较，为null采用元素自身的compareTo方法；内部采用堆来作为数据结构，而大根堆和小根堆的特点是父节点大于或小于子节点，且保持完全二叉树的特点。完全二叉树使得数组能够很好的支持存储，而大小根堆的特点又使得根节点就是最大或最小值。因此，PriorityBlockingQueue虽然是无界阻塞队列，但是内部维持的数组默认值为11，且存在扩容的场景。一般情况下，底层由数组支持的结构都是由扩容场景的，比如ArrayList默认为10，PriorityBlockingQueue为11，ArrayDeque为8等，而ArrayBlocingQueue虽然由数组支持，但不支持扩容。因为PriorityBlockingQueue为无解的，所以ReentranLock只有一个notEmpty Condition，不需要notFull，而且add、put等直接调用offer，不会返回失败。Priority扩容在在offer主流程中，因为扩容是非常耗时的，一直阻塞会影响读的性能，所以扩容时，线程会释放持有的锁，采用cas的方法尝试扩容，扩容成功后会重新获取锁，然后就是加入元素，添加元素后，根据Comparator判断大小，向上堆化。所以增加元素的复杂度为O(logn)，即堆的高度。而poll、take等则是将数组下标为0的数据返回，并且向下堆化。因此，删除元素的复杂度为O(logn)，为树的高度。
     2、Deque
        Deque双端队列，可以实现队列和栈结构。接口中提供了addFirst、addLast、offerFirst、offerLast、removeFirst、removeLast、pollFirst、pollLast、getFirst、getLast、peekFirst、peekLast等新的接口。常用的子类有：ArrayDeque和LinkedList
        ArrayDeque: 底层由数组实现，默认的capacity为8
        LinkedList: 底层由双向链表支持。