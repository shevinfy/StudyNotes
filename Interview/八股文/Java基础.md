shevin°:
八股文：https://docs.qq.com/doc/p/5caa3cf233146cd625e57cd9938eedf99e5e8dcd

##             1.     **面向过程与面向对象的区别**

面向过程：面向过程的**程序性能**比面向对象高，因为**类实例化时需要消耗资源**，所以面向对象开销更大。

面向对象：面向对象的程序更加容易**维护，复用和扩展**。因为面向对象具有**封装，继承和多态**等特性，所以可以设计出**低耦合**的系统，使系统更加灵活，容易维护。

当性能是最重要的因素时，如单片机，嵌入式，Linux系统一般采用面向过程开发。当开发大型软件系统时，一般采用面向对象开发，更加易于维护。

##             2.     **Java语言的特点**

1.面向对象（拥有**继承，封装和多态**三大特性）

2.平台无关性（编译后形成字节码文件由JVM执行，**支持跨平台**）

3.编译（ 将源代码==**一次性**==转换成目标代码的过程  ）与解释( 将源代码逐条转换成目标代码同时逐条运行的过程。  )并存（Java程序首先由编译器进行编译，形成的中间代码由JVM解释执行）

4.可靠安全（有异常处理机制和垃圾回收机制，去除指针特性）

5.提供了很多**内置类库**（如对多线程的支持，对网络通信的支持）

6.提供了对web应用程序的开发支持。

##             3.     **什么是JVM？**

JVM是**运行Java字节码的虚拟机**。JVM有针对不同系统的特定实现，目的是使用**相同**的字节码，JVM运行都会给出**相同的结果。**

##             4.     **什么是字节码？采用字节码有什么好处？**

**JVM可以理解运行的代码**叫做字节码，也就是扩展名为.class的文件。Java通过字节码方式，在一定程度上解决了传统解释型语言**执行效率低**的问题，同时又保留了解释型语言**可移植**的特点。

##             5.     **Java从源代码到运行的三步是什么？**

首先java源代码被JDK中的javac编译形成.class文件，然后由JVM解释执行，形成机器可以执行的**二进制机器码**，最后在硬件机器上执行二进制机器码。

其中在**形成二进制机器代码时**，因为直接**逐行解释执行效率较低**，所以引入了**JIT**(Just-in-time，即时编译，边运行边编译)编译器**保留热点代码**，提高**经常运行**的代码的效率。JDK9引入了一种新的**编译模式AOT(**Ahead of Time Compilation)提前编译”.**直接将字节码编译成机器码**，避免了JIT预热方面的开销，但**编译质量**不如JIT编译器（优化）。

## 6. **静态编译与动态编译的区别：**

动态编译的可执行文件需要**附带一个**的动态链接库，在执行时，需要调用其**对应动态链接库中的命令**。

所以其**优点**一方面是**缩小了执行文件本身的体积**，另一方面是**加快了编译速度**，节省了系统资源。

**缺点**一是哪怕是很简单的程序，只用到了链接库中的一两条命令，也**需要附带一个相对庞大的链接库**；二是**如果其他计算机上没有安装对应的运行库**，则**用动态编译的可执行文件就不能运行**。

静态编译 就是程序在**执行前**全部被翻译为机器码，编译器在编译可执行文件的时候，将可执行文件需要调用的对应动态链接库(.so)中的部分提取出来，链接到可执行文件中去，使可执行文件在运行的时候**不依赖于动态链接库**。所以其优缺点与动态编译的可执行文件正好互补。  

```
Java的动态链接库是一种库文件，其中包含了Java代码和本地机器代码的二进制形式，可以在Java程序中动态地加载和链接。这些库文件通常具有扩展名为“.dll”（在Windows平台上）或“.so”（在Unix / Linux平台上）。

在Java中，可以使用Java本机接口（JNI）来访问动态链接库中的本地机器代码。通过JNI，Java程序可以调用动态链接库中的函数，传递参数并获得返回值。
```



## 7. **JRE与JDK分别是什么？**

JRE是Java运行时环境，包括JVM,Java类库，Java命令和其他的一些基础构件。不能用于创建新程序。

JDK是Java开发环境，除了拥有JRE拥有的一切还有编译器(javac)和工具（如javadoc和jdb）。能够创建和编译程序。



## 8. **Open JDK与Orcle JDK有什么区别?**

Open jdk项目主要基于Sun公司捐赠的HotSpot源代码，是一个参考模型并且是完全开源的，Orcle jdk是Open jdk的一个实现，面向商用，并不是完全开源的。Orcle jdk比Open jdk提供了更好的响应性和性能。

```
性能差异可以归因于Oracle JDK和OpenJDK的不同实现方式。Oracle JDK的实现中包含了一些专有的代码和优化，例如垃圾回收器（Garbage Collector）和即时编译器（Just-In-Time Compiler），这些代码和优化能够提高Java程序的性能和响应性。而OpenJDK则是一个开源项目，它的实现相对简单，可能没有包含一些高级的优化和专有的代码。

响应性是指Java虚拟机（JVM）对用户输入的快速响应能力。

HotSpot是Java虚拟机（JVM）的一种实现
```



## 9. **Java和C++的区别**

相同点

都是面向对象语言，支持继承，封装和多态。

不同点

Java不提供指针来直接访问内存，程序内存更加安全。

Java类只支持单继承，C++类支持多继承，Java中接口支持多继承。

Java有自动内存管理机制，C++需要手动释放动态内存。

**Java字符串或字符数组不需要用额外字符'\0'来表示结束,而C++需要。（Java万物皆对象，数组对象有属性length）**

## 10. **字符型常量和字符串常量的区别?**

1.首先在形式上，字符型常量是用**单引号**引起来的一个字符，字符串常量是用**双引号**引起来的多个字符。

2.其次在含义上，字符型常量相当于一个**整型值**(ASCII值)，可以参加**表达式运算**。字符串常量代表一个**地址值**（表示该字符串在内存中存放的位置）

3.占内存大小 **字符型常量只占2个字节（char在Java中占两个字节），**字符串常量占若干个字节。

## 11. **Java八种基本数据所占空间大小**

**Java每种数据类型所占空间大小并不随着硬件的改变而改变**

**boolean true/false Boolean**

**byte 8bit Byte -128~127**

**char 16bit Character Unicode 0~Unicode 2****16****-1**

**short 16bit Short -2****15****~2****15****-1**

**int 32bit Integer -2****31****~2****31****-1**

**long 64bit Long -2****63****~2****63****-1**

**float 32bit Float IEEE754**

**double 64bit Double IEEE754**

## 12. **构造器Constructor是否可以被override(重写)？**

Constructor不能被重写，但是可以被重载，所以我们**一个类中有多个构造函数**的情况。

## 13. **重载和重写的区别**

重载发生在编译期，发生在同一个类中，方法名必须相同，参数不同，可以是参数类型不同，个数不同或者是顺序不同，方法的返回值和修饰符可以不同。实际上，重载就是同一个类中多个同名方法根据不同的传参来**执行不同的逻辑处理**。

重写发生在运行期，是子类对父类允许它访问的方法的逻辑处理过程进行的一次重新编写。方法名，参数列表，返回值类型都必须相同（返回值类型是**void和基本数据类型**，则重写时不可修改，但如果是**引用数据类型**，重写时可以==返回该引用类型的子类==），**访问权限修饰符范围大于等于父类**，但是抛出的**异常**是**父类方法异常的子类或者相等**。如果父类方法访问修饰符为**private/final/static则子类不能重写该方法**，但是被static修饰的方法能够被再次声明。最后就是，**构造器方法无法被重写**。实际上，重写就是子类对父类方法的重新改造，外部样式不能改变，内部逻辑可以改变。

## 14. **Java面向对象三大特性:封装 继承 多态**

1.封装：封装就是把一个对象的**属性私有化**，同时**向外界提供**可以访问该属性的**方法**。

2.继承：子类通过继承父类的属性和方法，可以方便的复用以前的代码。第一，子类拥有父类**所有的属性和方**法，包括私有属性和私有方法，但是无法访问。第二，子类可以**拥有自己属性和方法**，对父类进行扩展，第三，子类可以用**自己的方式实现**父类的方法。

3.多态：多态就是指，同一个方法，不同的对象调用效果可能不一样。程序中定义的引用变量发出的方法调用在编程时并不确定，而是在程序运行时才确定。**在Java中有两种形式可以实现多态实现**，一是==继承==，多个子类对父类的同一方法进行重写，二是==接口==，实现接口并覆盖接口中的同一方法。

## 15. **String,StringBuffer,StringBuilder的区别是什么？**

String对象是**不可变的**，可以理解为常量，线程安全。而StringBuiler和StringBuffer都继承了**AbstractStringBuiler，是可变的**。StringBuilder线程不安全，但是效率更高，StringBuffer对父类的方法加了==同步锁（synic）==，所以是线程安全的，但是效率比StringBuilder低。

```
1.三者的运行速度
        首先从三者的执行速度来看，String<StringBuffer<StringBuilder。
String最慢的原因：
原来的str就会被JVM的垃圾回收机制（GC）给回收掉了，所以，str实际上并没有被更改，也就是前面说的String对象一旦创建之后就不可更改了。所以，Java中对String对象进行的操作实际上是一个不断创建新的对象并且将旧的对象回收的一个过程，所以执行速度很慢。

2.三者的使用总结
String：适用于少量的字符串操作的情况；
StringBuilder：适用于单线程下在字符缓冲区进行大量操作的情况；
StringBuffer：适用多线程下在字符缓冲区进行大量操作的情况。
```



## 16. **String为什么是不可变的？**

String类中**使用final关键字修饰字符数**组保存字符串。所以String对象是不可变的。同时String类被final修饰，不会被继承和扩展。（string是用final修饰，这里要说一下，并不是存引用值的a，b中的引用不可以改变，**而是创建的对象或常量对象其本身无法改变**，当改变其中一个时，会在**常量池**（缓冲池）或**堆**中创建一个**新的对象**并将对象的引用存入a,b（原对象本身并未改变）。为了解决这个问题stringbuffer和stringbuild出现了）

## 17. **String在内存中的实例化**

第一种方式: String str1 = "aaa"; 是**在常量池中获取对象**("aaa" 属于字符串字面量，因此编译时期会在常量池中创建一个字符串对象)

第二种方式: String str2 = new String("aaa") ; 一共**会创建两个字符串对象**，一个在常量池中（前提是常量池中还没有 "aaa" 字符串对象，一个在堆中。

如果不是用**双引号声明**的String对象,可以使用 String 提供的 intern 方法**。String.intern() 是一个 Native 方法**，它的作用是： 如果运行时常量池中已经有此 String 对象内容的字符串，则返回该字符串的引用； 如果没有，则在常量池中创建与此 String 内容相同的字符串，并返回常量池中创建的字符串的引用。

```
String s1 = new String("AAA");

String s2 = s1.intern();

String s3 = "AAA";

System.out.println(s2);//AAA

System.out.println(s1 == s2);//false，因为一个是堆内存中的String对象一个是常量池中的String对象，

System.out.println(s2 == s3);//true， s2,s3指向常量池中的”AAA“
```



## 18. **"+"号拼接String的实现原理\***

首先将变量str存储局部变量表里，接着new StringBuilder()，并初始化，然后调用StringBuilder.append()方法拼接字符串，再通过StringBuilder.toString()调用new String()得到一个新的字符串变量，再将这个变量存储到局部变量表，最后return结束。所以代码过程其实为new StringBuilder().append(str).append("world").toString()。

而StringBuilder的toString()调用的是new String()

底层是通过StringBuilder的append方法先将字符串放在char[]数组中，再通过toString()调用new String()新建一个合适长度的char[]，再把旧的char[]复制到新的char[]上完成的，而一个小小的”+“拼接的过程经过了3次System.arraycopy，过程复杂，每次“+”都会new StringBuilder又要toString，若是在for循环中使用这种方式，就会有非常多的stringbuilder对象。

## 19. **自动装箱和拆箱**

装箱：将基本类型用它们对相应的引用类型包装起来。

拆箱：将包装类型转换为基本数据类型。

## 20. **int与Integer、new Integer()**

**（1）int与Integer、new Integer()进行==比较时，结果永远为true**

```
1.Integer与int比较时，Integer会有拆箱的过程
public int intValue(){
  return value;
}
```

**（2）Integer与new Integer()进行==比较时，结果永远为false**

```
Integer a=n时，如果n大于等于-128小于等于127时，会直接从IntegerCache中取，不在这个范围内，会new一个对象，所以Integer与new Integer进行 ==比较时，结果都是false。
```

**（3）Integer与Integer进行==比较时，看范围；在大于等于-128小于等于127的范围内为true，在此范围外为false。**

```
Integer与Integer比较，需要看范围，如果在-128~127(包含-128，不包含127)范围内，因为都是从IntegerCache中取值，所以相等；若不在这个范围内，则都要去new一个对象，所以结果为false。
```

因为new的对象的每一个引用地址不同，== 比较引用值是比较引用地址。

## 21. **在一个静态方法中调用一个非静态成员为什么是非法的？**

因为静态方法可以通过类直接调用，而非静态成员变量随着类的**实例化对象**出现才出现，所以在静态方法中不能访问和调用非静态成员变量。

## 22. **为什么要定义空参构造器？**

因为Java程序在实例化子类，调用子类的构造器之前，如果没有显示用super调用父类的构造器，则会默认调用父类中的空参构造器。在父类中，如果没有定义构造器，则系统默认提供空参构造器，如果定义了其他构造器，则需要显示声明空参构造器，否则将在**子类构造器调用**时发生**错误**。

## 23. **import java和javax有什么区别？**

刚开始时，java开头的包是标准API包，javax作为扩展API包来使用，后来javax包也成为了标准API的一部分，二者实际上没有区别，只是名字不同。

### **private、default、protected、public的作用范围**

private:表示私有，只有自己的类能访问。----子类能继承，但是不能调用。

default：表示没有修饰符修饰（即默认），只有同一个包的类能访问。

protect：表示可以被同一个包的类以及其它包的子类访问。

public：表示可以被该项目中的所有包的所有类访问。



## 24. **接口和抽象类的区别是什么？**

1.在Java8以前接口中只能有抽象方法，但抽象类可以有非抽象的方法。

2.接口中只能有static、final变量，而抽象类可以有其他类型的变量。

3.一个类可以实现多个接口，但是只能实现一个抽象类。接口本身也可以通过extends关键字扩展多个接口。

4.接口中方法权限修饰符只能是public，而抽象方法可以使用public，protected和default权限修饰符。（抽象方法为了被重写，不能使用private权限修饰符修饰）

5.从设计层面上，接口是对行为的抽象，是一种**行为规范**，而抽象类是对类的抽象，是一种**模板设计**。

![image-20230415170755369](Java%E5%9F%BA%E7%A1%80.assets/image-20230415170755369.png)

## 25. **接口从jdk1.7~jdk1.9发生了哪些变化？**

1.在jdk1.7及以前，接口中只能有常量变量和抽象方法。

2.jdk1.8时接口可以有默认方法和静态方法。

3.jdk1.9时接口可以有私有方法和私有静态方法

```
为了增强 Java 接口的功能和灵活性。私有方法和默认方法可以使接口更容易重构，而私有静态方法和私有静态常量可以将通用代码封装在接口中，而不会对外部类产生影响。
```



## 26. **成员变量和局部变量有什么不同？**

1.从语法形式上来看：成员变量是**属于类**的，而局部变量是在**方法**中定义的变量或者是方法参数。成员变量可以用**权限修饰符及static修饰**，而局部变量不能被权限修饰符及**static所修饰**，二者都能够被final所修饰。

2.从变量在内存中的存储方式来看：如果成员变量是用static修饰的，那么它是属于类的，存放在**方法区**中，如果没有用static修饰，那么它是属于**实例**的，存放在**堆中**。对于**局部变量**，如果是**基本数据类型**，那么它存放在**栈**中，如果是引用数据类型，那么存放的是指向**堆内存的引用**或者是指向**常量池中的地址**。

3.从变量的生命周期来看：成员变量是对象的一部分，随着**对象的创建而存在**，而局部变量随着**方法的调用而创建**，随着方法的结束而消失。

4.成员变量如果没有赋初值，则会根据数据类型**自动赋默认**值（final修饰的必须**显式赋值**），而局部变量则不会**自动赋值**。

## 27. **对象引用和对象实例有何不同？**

**对象引用指向对象实例**，对象引用存放在**栈内存**中，而对象实例存放在**堆内存**中。一个对象引用可以指向0个或1个对象实例，一个对象实例可以被多个对象引用所指向。

## 28. **构造方法有哪些特性？**

1.构造方法名字与类名相同。

2.构造方法没有返回值。

3.构造方法在**类实例化**时自动执行。

## 29. **静态方法和实例方法有什么不同？**

1.外部调用静态方法时**无需创建对象**，可以直接使用**类名.方法名**的方式调用。

2.静态方法在访问本类的成员时**只能访问**静态变量和静态方法（静态成员）。

## 30. **为什么在子类初始化时调用父类的构造器？**

为了帮助**子类做初始化操作**。

```
类继承父类后,获取到父类的属性和方法,这些属性和方法在使用前必须先初始化
```



## 31. **对象的相等与指向他们的引用相等，二者有什么不同？**

对象相等，比的是内存中存放的内容是否相等。而引用相等，比较的是他们所指向的内存地址是否相等。

## 32. **==与equals的区别**

== 的作用是判断两个对象的**地址**是否相等，也就是判断两个对象是不是**同一个对象**。如果是基本数据类型，== 比较的是**值**，如果是引用数据类型，== 比较的是**内存地址**。

equals():equals()有两种情况

情况1：该类没有重写equals()方法，等价于**使用==比较这两个对象**。

情况2：该类重写了equals()方法，一般我们比较两个对象内容是否相等。例如String类的equals()方法就重写过，**比较内容是否相等**。

## 33. **hashcode与equals的关系（为什么重写equals时需要重写hashcode方法？）**

hashCode()的函数作用就是获取**哈希码**，也称为散列码。它实际上是返回一个**int整数**，这个哈希码的作用是确定**该对象在哈希表中的索引位置**。

Java中规定了equals方法与hashcode的关系

1.如果两个对象使用**equals方法判断相等**，则两**个对象相等**。

2.如果两个对象相等，那么它们的**hashcode一定相等**。

3.如果两个对象的hashcode相等，它们**不一定相等**。

因此在重写equals方法时必须重写hashcode方法，如果没有重写hashcode方法，就会出现两个对象使用equals方法判断相等，但是hashcode不相等的情况。

在Hashmap中判断key是否相等时，为了提高判断效率，使用的判断条件是k1的hashcode等于k2的hashcode与上小括号k1==k2或者是k1.equals(k2),如果重写了equals没有重写hashcode方法就会导致第一个判断条件是false，第二个判断条件是true。两个相等的对象但是key却不相等，会导致基于散列的集合(HashMap,HashSet)无法正常工作。



```
知乎知识扩展链接 https://zhuanlan.zhihu.com/p/347342971
```



## 34. **Java中为什么只有值传递？**

Java语言总是按照**值传递来调用方法**，方法得到的是**所有参数值的一个拷贝**，不能修改传递给它的参数变量的内容。如果是基本数据类型作为参数，方法会得到该基本数据类型的一个拷贝。如果是引用数据类型作为参数，方法会得到该**引用地址的一个拷贝**，引用指向同一个引用对象，因此可以对该对象进行修改。

## 35. **程序，进程和线程和协程的基本概念，它们三者之间的关系是什么？**

程序就是含有指令和数据的文件，是静态的代码。

进程是程序的一次执行过程，是系统执行程序的基本单位。

线程与进程相似，是比进程更小的调度单位。一个进程在执行过程中会产生多个线程，线程切换时开销更小。但是同类的多个线程会共享同一块内存空间和系统资源而进程之间则是相互独立的。

**进程切换与线程切换的一个最主要区别**就在于进程切换涉及到**虚拟地址空间的切换**而线程切换一般不会。因为每个进程都有自己的虚拟地址空间，而线程是共享它所在进程的虚拟地址空间的，因此同一个进程中的线程进行线程切换时不涉及虚拟地址空间的转换。 **所以上下文切换的代价不同，进程要保留上下文，线程只需要保存寄存器和栈的信息**

（1）运行(running)态：进程占有处理器正在运行。

（2）就绪(ready)态：进程具备运行条件，等待系统分配处理器以便运行。

（3）等待(wait)态：又称为阻塞(blocked)态或睡眠(sleep)态，指进程不具备运行条件，正在等待某个事件的完成。

### **什么时候用多线程什么时候用多进程？**

计算量：需要大量计算的优先使用多线程 因为需要消耗大量CPU资源且切换频繁，所以多线程好一 点 。

相关性：任务间相关性比较强的用多线程，相关性比较弱的用多进程。因为线程之间的数据共享和同 步比较简单。

可以认为协程是线程里不同的函数，这些函数之间可以相互**快速**切换 ， 协程在切换出去的时候需要保存当前的运行状态，比如 CPU 寄存器、栈信息等等 。协程调度则是在用户空间进行的，是开发人员通过调用系统底层的执行上下文相关api来完成的，有些语言，比如nodejs、go在语言层面支持了协程，而有些语言，比如C，需要使用第三方库才可以拥有协程的能力。

由于线程是操作系统的最小执行单元，因此也可以得出，协程是基于线程实现的，协程的创建、切换、销毁都是在某个线程中来进行的。

使用协程是因为线程的切换成本比较高，而协程在这方面很有优势。

\1. 协程可以自动让出 CPU 时间片。注意，不是当前线程让出 CPU 时间片，而是线程内的某个协程让出时间片供**同线程内**其他协程运行

\2. 协程可以恢复 CPU 上下文。当另一个协程继续执行时，其需要恢复 CPU 上下文环境

\3. 协程有个管理者，管理者可以选择一个协程来运行，其他协程要么阻塞，要么ready，或者died

\4. 运行中的协程将占有**当前线程**的所有计算资源

\5. 协程天生有**栈属性**，而且是 lock free

## 36. **进程运行占有的系统资源，线程运行所占用的系统资源**

### **1、进程中占有的资源**

（1）地址空间

（2）全局变量

（3）打开的文件

（4）子进程

（5）信号量

（6）账户信息

### **2、线程占有的资源**

（1）栈

（2）寄存器（ 暂时存放参与运算的数据和运算结果 ）

（3）状态

（4）程序计数器

### **3、线程共享的内容**

（1）代码段code segment

（2）数据段data section

（3）进程打开的文件描述符

（4）信号的处理器

（5）进程的当前目录和

（6）进程用户ID和进程组ID

https://blog.csdn.net/weixin_44717095/article/details/105708847

## 37. **final关键字如何使用？**

final关键字可以用来修饰变量，方法，和类。

final修饰变量时，如果是基本数据类型，则其值一旦初始化后就不能更改。如果是引用数据类型，则其在初始化后不能再指向另一个对象。

final修饰方法后，**方法不能被重写**，防止继承类修改他的含义，类中所有private方法都**隐式地指定为final**。

final修饰类时，表示这个**类不能被继承**。final类中所有**成员方法**都会被指定为**final方法。**

## 38. **介绍一下Java中的异常**

在Java中，所有的异常都有一个共同祖先，java.lang包下的Throwable类。Throwable有两个重要的子类Error(错误)和Exception(异常)。

Error(错误)是程序无法处理的错误，表示故障发生于虚拟机自身或者发生在虚拟机试图执行应用时，如Virtual MachineError(Java虚拟机运行错误)，NoClassDefFoundError(类定义错误)，错误发生时，**JVM一般会选择终止线程**。

Exception(异常)是**程序本身可以处理的异常**。Exception有一个重要的子类RuntimeException异常，RuntimeException异常由**Java虚拟机抛出**，包括NullPointerException(空指针异常)，ArithmeticException(除0异常)和ArrayIndexOutOfBoundsException(下标越界异常)等。

## 39. **try,catch,finally块有什么作用？**

try块：用于捕获异常，后面可以跟零个或多个catch块，如果没有catch块，则必须有一个finally块。

catch块：用于处理try捕获到的异常。

finally块：无论是否捕获或处理异常，finally块中的语句都会被执行。当在try块或者catch块中遇到return语句时，finally块中的语句将在方法返回前被执行。

**注意：不要在 finally 语句块中使用 return!** 当 try 语句和 finally 语句中都有 return 语句时，try 语句块中的 return 语句不会被执行。

### [**如何使用 try-with-resources 代替try-catch-finally？**](https://snailclimb.gitee.io/javaguide/#/docs/java/basis/java-basic-questions-03?id=如何使用-try-with-resources-代替try-catch-finally？)

## 40. **在哪些情况下finally块不会被执行？**

1.在finally语句块**第一行发生了异常**。（在其他行，finally块还是会得到执行）

2.在前面的代码中用了**System.exit(int)退出程序**。

3.程序**所在的线程死亡**。

4.**关闭了cpu**。

## 41. [**什么是序列化?什么是反序列化?**](https://snailclimb.gitee.io/javaguide/#/docs/java/basis/java-basic-questions-03?id=什么是序列化什么是反序列化)

如果我们需要持久化 Java 对象比如将 Java 对象保存在文件中，或者在网络传输 Java 对象，这些场景都需要用到序列化。

简单来说：

● **序列化**： 将数据结构或对象转换成**二进制字节流**的过程

● **反序列化**：将在序列化过程中所生成的二进制字节流转换成**数据结构或者对象的过程**

对于 Java 这种面向对象编程语言来说，我们序列化的都是对象（Object）也就是实例化后的类(Class)，但是在 C++这种半面向对象的语言中，struct(结构体)定义的是数据结构类型，而 class 对应的是对象类型。

## 42. **Java序列化时有些字段不想序列化怎么办？**

不想序列化的变量，可以使用**transient关键字**修饰。transient关键字会阻止**对象的变量序列化**。当对象被反序列化时，**transient修饰的变量不会被持久化和恢复。transient只能修饰变量，不能修饰类和方法。**

## 43. **Java中的IO流分为几种？**

1.按照流的流向划分，可以分为输入流和输出流。

2.按照操作单元划分，可以分为字节流和字符流。

3.按照流的角色划分，可以分为节点流和处理流。

Java中的IO流都是从4个抽象类基类中派生出来的。

InputStream/Reader:是所有输入流的父类，InputStream是字节输入流，Reader是字符输入流。

OutputStream/Writer:是所有输出流的父类，前者是字节输出流，后者是字符输出流。

## 44. **既然有了字节流，为什么还要有字符流？（既然信息最小存储单元是字节，那为什么还要划分为字节流和字符流？）**

如果没有字符流，字符是由Java虚拟机将字节转换得到的，这个转换过程会非常耗时，并且如果我们不知道编码类型就会很容易出现乱码问题。所以，Java IO流干脆提供了一个直接操作字符的接口，方便我们平时对字符进行流操作。因此，我们在处理音频，图片，视频文件时适合使用字节流，处理文本文件时 适合使用字符流。

## 45. **Socket通讯基本流程**

Socket编程模型

服务器端:

1)创建Socket对象。设置协议、传输方式等(连接Socket)

2)绑定IP与端口(设置要监听的IP与端口) 。Bind()

3)开启监听。Listen()

4)开始接受客户端连接。Accept)://阻塞线程,同时也需要循环不断接受用户连接。

5)接受了客户端的连接,生成一个新的Socket对象(通信Socket)

6)接受(Receive)和发送(Send)消息//需要循环不断接收用户的消息

7)Shutdown()禁用发送与接收功能。

8)Close()关闭释放资源

客户端:

1)创建Socket对象。设置协议、传输方式等(连接Socket)

2)Connect()连接服务器(IP与端口)

3)向服务器发送、接受消息

4)Shutdown()禁用发送与接收功能。

5)Close():关闭释放资源

## 46. **BIO,NIO,AIO有什么区别？**

在BIO中，等待客户端发数据这个过程是阻塞的，这样就造成了一个线程只能处理一个请求的情况，而机器能支持的最大线程数是有限的，这就是为什么BIO不能支持高并发的原因。

而NIO中，当一个Socket建立好之后，Thread并不会阻塞去接受这个Socket，而是将这个请求交给Selector，Selector会不断的去遍历所有的Socket，一旦有一个Socket建立完成，他会通知Thread，然后Thread处理完数据再返回给客户端——**这个过程是不阻塞的**，这样就能让一个Thread处理更多的请求了。

链接：https://www.jianshu.com/p/b9f3f6a16911





BIO(Blocking I/O)BIO是一种同步阻塞IO模式，数据的读取和写入必须阻塞在一个线程内等待其完成。优点是，在活动连接数不是特别高的情况下（小于1000）该模型可以让一个连接专注于自己的IO，编程模型简单。缺点是面对十万及百万级的连接时，该模型不能应对高并发量。

NIO(Non-blocking/New IO)NIO是一种同步非阻塞IO模式,java1.4引入的java.nio包提供了NIO框架。NIO提供了Channel,Selector,Buffer等抽象，支持面向缓冲的，基于通道的IO操作方法。NIO除了提供了传统同步阻塞的支持外，还提供了同步非阻塞模式，性能和可靠性更高，但是比较复杂。对于低负载低并发的应用程序可以使用同步阻塞IO来提高开发速度和维护效率，对于高负载高并发的应用程序，应该使用NIO的非阻塞模式来开发。

AIO(Asynchronous eɪˈsɪŋkrənəs IO)AIO是异步非阻塞IO模式，是Java7中引入的NIO改进版NIO2。AIO是基于事件和回调机制实现的，也就是应用操作之后会直接返回，不会堵塞在那里，当后台处理完成后，操作系统会通知相应线程进行后续操作。

## 47. **Static关键字如何使用？**

static主要有四种使用场景

1.static修饰成员变量和成员方法：被static修饰的成员属于类，被类中所有对象所共享，并且可以通过类名调用，静态变量存放在方法区中。

2.static修饰静态代码块：静态代码块随着类的加载而执行，并且只执行一次。

3.static修饰静态内部类，非静态内部类与静态内部类最大的区别是：非静态内部类在编译完成后会隐含地保存一个指向创建它的外部类的一个引用。但是静态内部类却没有，这意味着，第一，静态内部类的创建不需要依赖外部类的创建。第二，静态内部类不能使用任何外部类的非静态成员变量和方法。

4.static静态导包（jdk1.5新特性）：import static两个关键字连用可以导入某个类中指定的静态资源。

## 48. **super和this关键字如何使用?**

super代表父类引用，this代表当前对象的引用

super可以用于：

1.访问父类的**成员变量和方法**

2.访问父类的构造器

有两点需要注意

一是子类构造器中始终有一个默认隐式的super调用，所以一定是先调用父类的构造器，后执行子类的构造器。

二是使用super调用构造器必须是构造方法中的**第一个语句**，一个子类不能**多次调用super构造器。**

this可以用于

1.访问当前类的成员变量和方法

2.访问当前类的构造器

有三点需要注意

一是使用this调用构造器是不能形成循环。

二是使用this调用构造器也必须是构造方法的第一个语句。

三是super和this两种构造器调用不能同时使用。

## 49. **浅拷贝和深拷贝有什么区别？**

浅拷贝：对基本数据类型进行值传递，对引用数据类型进行引用传递搬的拷贝。

深拷贝：对基本数据类型进行值传递，对引用数据类型，创建一个新的对象并复制其内容。