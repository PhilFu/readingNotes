# 《写给大忙人看的Java SE 8》

-------------------------------------

##  第一章  lambda表达式

* 1. Arrays.sort方法中的比较器代码的线程与调用sort的线程是同一个吗？
* 2. 使用java.io.File类的listFiles(FileFilter)和isDirectory方法，编写一个返回指定目录下所有子目录的方法。使用lambda表达式来代替FileFilter对象，再将它改写为一个方法引用。
* 3. 使用java.io.File类的list(FilenameFilter)方法，编写一个返回指定目录下、具有指定扩展名的所有文件。使用lambda表达式（而不是FilenameFilter）来实现。它会补货闭合作用域中的哪些变量？
* 4. 对于一个指定的File对象数组，首先按照路径的目录排序，然后对每组目录中的元素再按照路径名排序。使用lambda表达式（而不是Comparator）来实现。
* 6. 你是否讨厌在Runnable实现中处理检查异常？编写一个补货所有异常的uncheck方法，再将它改造为不需要检查异常的方法。例如：
```
 new Thread(uncheck)
     () -> { System.out.println("Zzz"); Thread.sleep(1000); })).start();
       //看，不需要catch (InterruptedException) !
```
> 提示：定义一个RunnableEx接口，其run方法可以抛出任何异常。然后实现public static Runnable uncheck(RunnableEx runner)。在uncheck函数中使用一个lambda表达式。

> 为什么你不能直接使用Callable<Void>来代替RunnableEx？

* 7. 编写一个静态方法andThen，它接受两个Runnable实例作为参数，并返回一个分别运行这两个实例的Runnable对象。在main方法中，向andThen方法传递两个lambda表达式，并返回运行的实例。
* 8. 当一个lambda表达式捕获了如下增强for循环中的值时，会发生什么？
```
 String[] names = {"Peter", "Paul", "Mary"};
 List<Runnable> runners = new ArrayList<>();
 for (String name : names)
     runners.add( () -> System.out.println(name));
```
> 这样做是否合法？每个lambda表达式都捕获了一个不同的值，还是它们都获得了最终的值？如果使用传统的for循环，例如for(int i = 0; i < names.length; i++)，又会发生什么？

* 9. 编写一个继承Collection接口的子接口Collection2， 并添加一个默认方法：void forEachIf(Consumer<T> action, Predicate<T> filter)，用来将action应用到所有filter返回true的元素上。你能够如何使用它？