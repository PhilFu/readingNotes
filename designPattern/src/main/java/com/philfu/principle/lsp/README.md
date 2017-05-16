## LSP: Liskov Substitution Principle, 里氏替换原则
里氏替换原则定义: 所有引用基类的地方必须能够透明地使用其子类的对象（Functions that use pointers or references to base classes must be
able to use objects of derived classes without knowing it.）。

**只要父类能够出现的地方,子类就可以出现,而且替换为子类也不会产生任何错误或异常,使用者可能根本就不需要知道是父类还是子类。**

4层含义
> - 子类必须完全实现父类的方法。**在类中调用其他类时,务必要使用父类或接口。如果没有,说明类的设计已经违背了LSP原则**。如果子类不能完整地实现父类
的方法,或者父类的某些方法在子类已经发生畸变,则建议断开集成关系,使用依赖、聚合、组合等关系代替继承。
> - 子类可以有自己的个性。里氏替换原则可以正着用,但是不能反过来来用,子类出现的地方,父类未必可以胜任。**向下转型**(downcast)是不安全的
> - 覆盖或实现父类的方法时输入参数可以被放大。
