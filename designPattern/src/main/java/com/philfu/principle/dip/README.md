## DIP: Dependence Inversion Principle, 依赖倒置原则
依赖倒置原则: High level modules should not depend upon low level modules. Both should depend upon abstractions.
Abstractions should not depend upon details. Details should depend upon abstraction.
三层含义:
> - 高层模块不应该依赖低层模块,两者都应该依赖其抽象;
> - 抽象不应该依赖细节;
> - 细节应该依赖抽象;

Java中,抽象就是**接口**或**抽象类**,细节就是**实现类**, 面向接口编程。
> - 模块间的依赖通过抽象发生,实现类之间不发生直接的依赖关系,其依赖关系是通过接口或抽象类产生的;
> - 接口或抽象类不依赖于实现类;
> - 实现类依赖接口或抽象类;