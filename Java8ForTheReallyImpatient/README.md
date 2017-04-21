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

## 第二章  Stream API
* 1. 编写一个第2.1节中for循环的并行版本。获取处理器的数量，创造出多个独立的线程，每个都只处理列表的一个片段，然后将它们各自的结果汇总起来。（我们不希望这些线程都更新一个计数器，为什么？）
```
获取字符串列表中长度大于12的单词的数量。
```
* 2. 请想办法验证一下,对于获得前5个最长单词的代码,一旦找到地5个最长的单词后,就不会再调用filter方法了。一个简单的方法是记录每次的方法调用。
* 3. 要统计长单词的数量,使用parallelStream与使用stream有什么区别?请具体测试一下。你可以再调用方法之前和之后调用System.nanoTimne, 并打印出它们之间的区别。如果你有速度较快的计算机,
可以试着处理一个较大的文档（例如《战争与和平》的英文原著）。
* 4. 假设你有一个数组 int[] values = {1, 4, 9, 16}. 那么Stream.of(values)的结果是什么?你如何获得一个int类型的流?
* 5. 使用 Stream.iterate 来得到一个包含随机数字的无限流--不许调用 Math.random, 只能直接实现一个线性同余生成器（LCG）。在这个生成器中,你可以从x0=seed开始, 然后根据合适的a、c和m值产生
 xn+1=(a*xn +c)%m。你应该实现一个含有参数a、c、m和seed的方法,并返回一个Stream<Long>对象。
* 6. 第2.3节中的 characterStream 方法不是很好用,它需要先填充一个数组列表,然后再转变为一个流。试着编写一行基于流的代码。一个办法是构造一个从0开始到s.length()-1的整数流,
然后使用s::charAt方法引用来映射它。
* 7. 假设你的老板让你编写一个方法 public static <T> boolean isFinite（Stream<T> stream）。 为什么这不是一个好主意?
```
Stream 是lazy的,无法判断?
```
* 8. 编写一个方法 public static <T> Stream<T> zip(Stream<T> first, Stream<T> second),依次调换流first和second中的位置,直到其中一个流结束为止。
* 9. 将一个Stream<ArrayList<T>>中的全部元素连接为一个ArrayList<T>。试着用三种不同的聚合方法实现
* 10.编写一个可以用于计算Stream<Double>平均值的聚合方法。为什么不能直接计算出总和再除以count()?
* 11.我们应该可以将流的结果并发收集到一个ArrayList中,而不是将多个ArrayList合并起来。由于对集合不相交部分的并发操作是线程安全的,所以我们假设这个ArrayList的初始大小即为流的大小。如何能够做到这一点?
* 12.如第2.13节所示,通过更新一个AtomicInteger数组来计算一个并行Stream<String>宏的所有短单词。使用原子操作方法getAndIncrement来安全地增加每个计算器的值。
* 13.重复上一个练习,这次使用collect方法、Collectors.groupingBy方法和Collectors.counting方法来过滤出短单词

## 第三章  使用lambda编程
* 1. 通过提供有条件的日志语句来加强延迟日志功能。常见调用形式类似于logIf(Level.FINEST, () -> i == 10, () -> "a[10]" = " + a[10").如果logger不会打印日志消息,
则不需要计算条件语句
* 2. 当你使用ReentrantLock时,通常需要按照如下形式使用 lock 和 unlock 方法:
     myLock.lock()
     try {
         一些操作
     } finally {
         myLock.unlock();
     }
     请提供一个可以用于如下形式调用的 withLock 方法:
     withLock(myLock, () -> { 一些操作 });
* 3. Java 1.4 通过一个 assert 关键字添加了断言功能。为什么断言没有以库的形式提供? 你能用Java 8 中的功能将它实现成一个库吗?
* 4. 你能在 Java api 中找到多个名字中带有Filter的函数式接口? 其中哪些可以在 Predicate<T>上添加值?
* 5. 下面是 ColorTransformer 的具体示例,我们希望在图片周围放置一个frame, 首先实现3.3节的transform方法的一个变体, 它的参数是一个ColorTransformer,
而不是UnaryOperator<Color>类型,然后使用一个合适的lambda表达式调用它,用10像素,灰色的frame代替边框上的像素
* 6. 完成3.4节中的方法 public static <T> Image transform(Image image, BiFunction<Color, T> f, T args);
* 7. 编写一个生成 Comparator<String> 对象的方法,这个比较器可以是正序的、逆序的,大小写敏感的、不敏感的,空格敏感、不敏感的,或者其他任意组合,你的方法应该返回一个lambda表达式
* 8. 将练习5中的方法更改为一个更通用的静态方法,它可以为一个图像添加任意宽度和颜色的frame,并返回一个ColorTransform对象
* 9. 编写一个方法 lexicographicComparator(String... args),返回根据指定顺序对字段进行比较的比较器。例如, lexicographicComparator("lastname",
"firstname")会通过反射获取两个对象lastname字段的值。如果这两个值不同,则返回两者的差距,否则继续检查firstname字段。如果所有字段都想等,则返回0
* 10. 为什么不能像下面这样调用:
      UnaryOperator op = Color::brighter;
      Image finalImage = transform(image, op.compose(Color::grayscale));
     请注意观察 UnaryOperator<T> 对象 compose 方法的返回类型。为什么它不适用于 transform 方法?如何在函数组合中使用可推断的类型和需要显式声明的类型。
* 11. 实现可以组合两个 ColorTransformer 对象的静态方法,以及可以讲一个 UnaryOperator<Color> 对象转换为 ColorTransformer
对象（忽略x和y坐标）的静态方法。然后使用这些方法给一个明亮的图片添加一个灰色的frame。
* 12. 改进第3.6节中的 LatentImage 类,使它可以同时支持 UnaryOperator<Color> 和 ColorTransformer 类型。提示:将前者适配为后者
* 13. 像模糊或者边缘检测等convolution滤镜,会根据相邻的像素来计算当前像素。要模糊一个图像,需要将每个颜色值都替换为自身及周围8个像素的平均值。对于边缘检测来说,则要每个颜色值c替换为
      4c - n - e - s - w,其中的其他颜色分别是上、下、左、右4个方向的像素。注意,由于他们需要之前阶段中已经计算出的图像（或者至少是相邻的像素）,所以这些不可能通过3.6节中的方法
       延迟计算。 请你改进延迟图像的处理过程,使其能够支持这些操作。当执行其中一个操作时,强制计算上一阶段。













