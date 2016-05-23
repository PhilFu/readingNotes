# 《写给大忙人看的Java SE 8》

-------------------------------------

##  第一章  lambda表达式

* 1. Arrays.sort方法中的比较器代码的线程与调用sort的线程是同一个吗？
* 2. 使用java.io.File类的listFiles(FileFilter)和isDirectory方法，编写一个返回指定目录下所有子目录的方法。使用lambda表达式来代替FileFilter对象，再将它改写为一个方法引用。
* 3. 使用java.io.File类的list(FilenameFilter)方法，编写一个返回指定目录下、具有指定扩展名的所有文件。使用lambda表达式（而不是FilenameFilter）来实现。它会补货闭合作用域中的哪些变量？