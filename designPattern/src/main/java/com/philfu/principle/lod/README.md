## LOD: Law of Demeter, 迪米特法则
又名: 最少知识原则(Least Knowledge Priciple, LKP)
4层含义:
- 只和朋友交流。Only talk to your immediate friends。出现在成员变量、方法的输入输出参数中的类成为成员朋友类, 而出现在**方法体内部的类不属于朋友类**.
类与类的关系是建立在类间的,而不是方法里,因此一个方法尽量不引入一个类中不存在的对象,当然,JDK提供的类除外
- 朋友间也是有距离的。朋友类之间也不能无话不说,无所不知。
- 是自己的就是自己的。一个方法放在本类中可以,放在其他类里也没错,怎么衡量? 如果一个方法放在本类中,既不增加类间关系,又不对本类产生负面影响,就放在本类中。
- 谨慎使用 Serializable。序列化对象,需要客户端和服务器端保持一致,否则会出现问题。不过这是项目管理的问题

最佳实践:
> 类间解耦,弱耦合,类的复用率才可以提高。但会导致大量的中转类,导致复杂性提高,难以维护。使用迪米特法则要反复衡量,做到既结构清晰,又高内聚低耦合

如果一个类需要跳转两次以上才能访问到另一个类,就需要进行重构