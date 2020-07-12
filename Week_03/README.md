本周研究了一下java的先行发生原则，虽然跟数据结构关联不大，但是，也算是本周的感悟吧……
“先行发生”原则是Java内存模型中定义的两项操作之间的偏序关系，而且先行发生并不是时间上的先后顺序。主要是内存可见性问题。内存可见性问题就是多线程间的共享资源的可见性，因此“先行发生”原则主要描述的就是多线程间的原则。

1、程序次序规则(Program Order Rule)：在一个线程内，按照控制流顺序，书写在前面的操作先行发生于书写在后面的操作。

     在一个线程内，程序内代码的执行顺序是可能被重排序，导致描写代码的执行顺序提前，但是最终呈现在外的结果是不变的。而相互有依赖关系的两行代码则不会重排序执行，否则体现在外的结果就不一致了。程序次序规则，我认为针对的场景就是单线程中代码重排序的问题。如：

1 int a = 3;
2 int b = 2;
3 int c = a;
第3行依赖第1行的结果，因此，第3行代码执行时是不可能在第1行前执行的，但是第2行是没有依赖关系的，是可以重排在第1行前或第3行执行的，最终对外展现的结果仍然是a=3，b=2，c=3。



2、管程锁定规则(Monitor Lock Rule)：一个unlock操作先行发生于后面对同一个锁的lock操作。

      lock、unlock是jvm提供的原子指令，体现在汇编就是monitorenter和monitroexit指令。synchronized底层就是这两组指令支持。unlock会将工作内存的数据写入主内存，同时会引起别的处理器或者别的内核无效化其缓存，保证其他线程访问共享资源时从主存中读取。synchronized具备互斥性，被synchronized修饰的静态方法、代码块、方法在多线程执行的场景下是串行执行的，保证共享资源的安全性。

int a = 3;
public synchronized increase() {
    a++;
}
public synchronized decrease() {
    a--;
}
线程A执行increase，线程B执行decrease。当线程执行a++会存在多个指令支持，read、load、use等，这些指令虽然是原子的，但是read和load间不是原子的，如果此时cpu切换线程，就有意思了。因为synchronized加锁，此时线程B仍然被阻塞，当cpu重新切换回线程A时，会继续执行该操作，保证a++的完成，a最终结果为14。如果a=14不重新刷回主存，线程B从主村中取到a的值为13，执行a--后，a就会为12，这就反人类了。而因为管程锁定规则支持，最终a的结果才是13。这儿反应出有意思的就是：高级语言是对底层操作的抽象，往往一句简单的操作在底层可能需要多个指令完成，所以代码执行的原子性不敢站在高级语言的层面来分析，否则就凉透了。synchronized是提供给高级语言层面的原子性操作，同时保证了数据的可见性。



3、volatile变量规则(Volatile Variable Rule)：对一个volatile变量的写操作先行发生于后面对这个变量的读操作。

      volatile具有两个特点：保证共享资源的可见性、避免代码的重排序。比如在程序次序规则中volatile int c时，参数b是不会排到int c = a后面执行的，当volatile写数据后会主动将数据写回主存并会引起别的处理器或者别的内核无效化其缓存。虽然volatile修饰的参数，其他线程读取时是直接从主存中读取，但是，当读取后，某个线程在写该数值时，最终的值仍然不是一样的，因此jvm中的指令原子性是read、load、assign、use、store、write等的原子性，而这些原子性操作间是不具备原子性的。

volatile int a = 3;
public increase() {
    a++;
}
public decrease() {
    a--;
}
跟上述例子不同的是，此时a用volatile修改，去掉synchronized。volatile具备可见性和避免重排序。线程A、B分别取执行increase、decrease时，你觉得值是多少。我想说值可能是2、3、4；volatile底层是需要read、load、assign、use、store、write等操作支持的。它们每个都是原子性操作，但是read和load中间是可以插入其他操作的。因此，在线程A将值从主存读入内如时，线程B仍然可以读到a=3，各自操作各自的数值。一个返回4，一个返回2，依次刷回主存时，就会出现2和4两种值。当然3就是串行执行的结果了。volatile保证的是可见性和避免重排序，而不具备互斥性，导致多个线程写volatile的值时就会出现五花八门的结果。而这条规则针对的就是内存可见性问题，并不保证原子性。volatile适用于一个线程写，多个线程读的场景，面对这种场景是比较有效的，但能保证数据的正确性，可能是旧值，并不会保证数据的强一致性，除非采用synchronized。



线程间启动、中断、终止三个规则

4、线程启动规则(Thread Start Rule)：Thread对象的start()方法先行发生于此线程的每一个动作

static int a = 2;
public void test {
    Thread thread = new Thread(new Runnable() {
       @Override
       public void run() {
          while(a == 3) {
            a = 4;
          }
       }
    });
    a = 3;
    thread.start(); 
    thread.join();
    System.out.print(a);
}
      如果test所在的线程中a=3的值不对thread线程可见，thread会一直处于while中，但是，实际情况是最终输出的结果是4。

5、线程终止规则(Thread Termination Rule)：线程中所有操作都先行发生于此检测的每一个动作。(Thread.join()、Thread.isAlive()等都需要知道线程是否已经终止)

     让我们复用线程启动规则例子，如果a=4赋值后，不对test所在线程可见，你觉得这段代码能执行完吗？线程终止后，thread.join()能检测到线程的状态时，当前线程才能继续往下执行，最终输出4。

6、线程中断规则(Thread Interruption Rule)：对线程interrupt()方法的调用先行发生于被中断线程代码检测到中断事件的发生，可以通过Thread::interrupted()方法检测是否有中断发生

 略微该下线程启动规则的例子，如下所示：

static int a = 2;
public void test {
    Thread thread = new Thread(new Runnable() {
       @Override
       public void run() {
          while(Thread.currentThread().isInterrupted()) {
            a = 4;
          }
       }
    });
    thread.start(); 
    thread.interrupt();
    System.out.print("current thread interrupt status:"+ thread.isInterrupted());
    System.out.print(a);
}

输出：
current thread interrupt status：true
4
线程的启动、终止、中断三个规则结束后，想问下：这三大规则的底层支持的依赖于什么呢？

贴一段Thread源码的逻辑，可以品味下

private static long threadSeqNumber; //线程名
private volatile int threadStatus = 0; //线程状态
public synchronized void start() {
        if (threadStatus != 0)
            throw new IllegalThreadStateException();
        group.add(this);
        boolean started = false;
        try {
            start0();
            started = true;
        } finally {
            try {
                if (!started) {
                    group.threadStartFailed(this);
                }
            } catch (Throwable ignore) {
            }
        }
    }
public void interrupt() {
        if (this != Thread.currentThread())
            checkAccess();
         synchronized (blockerLock) {
            Interruptible b = blocker;
            if (b != null) {
                interrupt0();           // Just to set the interrupt flag
                b.interrupt(this);
                return;
            }
        }
        interrupt0();
    }
public final synchronized void join(long millis)
    throws InterruptedException {
        long base = System.currentTimeMillis();
        long now = 0;
        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }
        if (millis == 0) {
            while (isAlive()) {
                wait(0);
            }
        } else {
            while (isAlive()) {
                long delay = millis - now;
                if (delay <= 0) {
                    break;
                }
                wait(delay);
                now = System.currentTimeMillis() - base;
            }
        }
    }


对象的初始化的回收的先后顺序

7、对象终结规则(Finalizer Rule)：一个对象的初始化(构造函数执行结束)先行发生于它的finalize()方法的开始

支持上述7大先行规则的传递性规则

8、传递性(Transitivity)：如果操作A先行发生于操作B，操作B先行发生于操作C，那就可以得出操作A先行发生于操作C的结论。



很多时候，基于并发编程结束后，是否满足安全性，其实就是基于上述八大原则分析的。