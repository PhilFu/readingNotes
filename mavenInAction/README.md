# 《Maven 实战》
POM: Project Object Model,项目对象模型。Maven可以使项目对象模型最大程度地与实际代码相独立,称之为解耦,或者正交性。
很大成都上避免了Java代码和POM代码的相互影响。

## 坐标
- groupId: 定义当前Maven项目隶属的实际项目。首先,Maven项目和实际项目不一定是一对一关系,实际项目往往会被划分成很多模块;其次,groupId不应该对应项目隶属的组织或公司,一个公司下会有很多实际项目;
最后,groupId的标示方式与Java包名的表示方式类似,通常与域名反向一一对应。
- artifactId: 定义实际项目中的一个Maven项目（模块）,推荐做法是使用实际项目名称作为artifactId前缀。如spring-core, spring-beans
- version: 定义Maven项目当前所处的版本。
- packaging: 定义Maven项目的打包方式。首先,打包方式通常与所生成构建的文件扩展名对应;其次,打包方式会影响到构建的生命周期,jar和war会使用不同的命令;
最后,不定一packaging时,默认是jar。
- classifier: 用来帮助定义构建输出的一些附属构建。附属构建与主构建对应,项目可能会通过一些插件生成附属构建,如Java文档或源代码,这样附属构建也有了自己的坐标。
不能直接定义项目的classifier,因为附属构建不是项目直接默认生成的,而是由附加的插件帮助生成。

项目构建命名规则:  artifactId-version[-classfier].packaging
如: nexus-indexer-2.0.0.jar, 附属构建: nexus-indexer-2.0.0-javadoc.jar

## 依赖
```
<project>
  ...
  <dependencies>
    <dependency>
      <groupId>...</groupId>        <!-- 坐标 -->
      <artifactId>...</artifactId>  <!-- 坐标 -->
      <version>...</version>        <!-- 坐标 -->
      <type>...</type>      <!-- 依赖类型,默认为jar,对应于项目坐标的packaging -->
      <scope>...</scope>    <!-- 依赖的范围 -->
      <optional>...</optional>  <!-- 依赖是否可选 -->
      <exclusions>          <!-- 用来排除传递性依赖 -->
        <exclusion>
        ...
        </exclusion>
        ...
      </exclusions>
    </dependency>
    ...
  </dependencies>
  ...
</project>
```

### 依赖范围
Maven在编译项目主代码的时候需要使用一套classpath;编译和执行测试的时候会使用另外一套classpath;实际运行Maven项目的时候,又会使用一套classpath。
依赖范围就是用来控制依赖与这三种classpath的关系。
- compile: 编译。**默认值**,对于编译、测试、运行三种classpath都有效,都需要使用该依赖。
- test: 测试。只对测试classpath有效,只有在编译测试代码、运行测试的时候才需要。
- provided: 已提供。对于编译和测试classpath有效,但在运行时无效,如servlet-api。运行项目时,由于容器已经提供,不需要Maven重复引入。
- runtime: 运行时。对于测试和运行classpath有效,但在编译主代码时无效。如JDBC驱动,项目主代码的编译只需要JDK提供的JDBC接口,只有在执行测试和运行项目时才需要实现上述接口的具体JDBC驱动
- system: 系统。与三种classpath的关系,和provided一致,但使用system依赖时,必须通过systemPath显式指定依赖文件的路径。此类依赖不是通过maven仓库解析的,经常与本机系统绑定,造成构建不可移植
- import: 导入。不会对三种classpath产生实际影响。

|依赖范围|对于编译classpath有效|对于测试classpath有效|对于运行时classpath有效|例子|
|---|---|---|---|---|
|compile|Y|Y|Y|spring-core|
|test|-|Y|-|JUnit|
|provided|Y|Y|-|servlet-api|
|runtime|-|Y|Y|JDBC驱动实现|
|system|Y|Y|-|本地的,Maven仓库之外的类库文件|

### 依赖传递
假设A依赖于B, B依赖于C, 则A对于B是第一直接依赖,B对于C是第二直接依赖,A对于C是传递性依赖。

||compile|test|provided|runtime|
|---|---|---|---|---|
|compile|compile|-|-|runtime|
|test|test|-|-|test|
|provided|provided|-|provided|provided|
|runtime|runtime|-|-|runtime|

举例来说: account-email有greenmail的直接依赖,这是第一依赖,其范围是test; greenmail又有一个mail的直接依赖,这是第二依赖,其范围是compile;
mail 是 account-email 的传递性依赖。对照上表可知: 第一依赖是test,第二依赖范围是compile时,传递性依赖的范围是test。
> - 当第二直接依赖范围是compile时,传递性依赖的范围与第一直接依赖的范围一致;
> - 当第二直接依赖范围是test时,依赖不会被传递
> - 当第二直接依赖范围是provided时,只传递第一直接依赖范围也为provided的依赖,且传递性依赖范围同样为provided
> - 当第二直接依赖范围是runtime时,传递性依赖的范围与第一直接依赖的范围一致,但compile例外,此时传递性依赖的范围为compile

### 依赖调解
假设A有这样的依赖关系: A -> B -> C -> X(1.0), A -> D -> X(2.0), X是A的传递依赖,但有两个版本。
Maven依赖调解(Dependency Mediation)的原则:
> - 路径最近者优先。 X(1.0) 比 X(2.0)的路径长, 因此 X(2.0) 会被解析使用
> - 第一声明优先。 在依赖路径长度相同的前提下, 在 POM 中依赖声明的顺序决定了睡会被先解析使用,顺序最靠前的优胜。

### 可选依赖
假设有这样的依赖关系: A 依赖于 B, B 依赖于 X 和 Y。 B 对于 X 和 Y 的依赖都是可选依赖。
根据依赖传递的定义,如果这三个依赖范围都是 compile, 那么 X,Y 就是 A 的 compile 范围传递依赖。但如果 X 和 Y 是可选依赖,依赖不会被传递, X, Y 不会对 A 有任何影响
> 为什么需要可选依赖? 项目 B 可能实现了两个特性, 其中一个特性依赖于 X, 另一个特性依赖于 Y, 而且这两个特性是互斥的,用户不可能同时使用两个特性。
比如 B 是一个持久层隔离工具包,支持多种数据库,MySql、Oracle等,在构建这个工具包时,需要这两种数据库的驱动程序,但是在使用这个工具包的时候,只依赖一种数据库。
当项目 A 依赖于 B 的时候,如果 A 实际使用 MySql 数据库,那么在 A 的POM中要显示声明对MySql的依赖。

> 理想情况下,**不要使用可选依赖**。使用可选依赖的原因是项目实现了多个特性,但这违反了单一职责原则,更好的做法是为MySql和Oracle分布创建一个 Maven 项目。

### 排除依赖
依赖传递会隐式引入很多依赖,极大的简化项目依赖的管理,但可能会带来问题。如果项目有一个依赖,该依赖由于某些原因依赖了另一个类库的 SNAPSHOT 版本,
那么这个 SNAPSHOT 就会成为当前项目的传递依赖, 直接影响到当前项目。需要排除掉该 SNAPSHOT, 并且声明该类库的某个正式版本
```
<dependency>
  <groupId>com.philfu.mavenInAction</groupId>
  <artifactId>project-a</artifactId>
  <version>1.0.1</version>
  <exclusions>                  <!--使用exclusions排除依赖-->
    <exclusion>
      <groupId>com.philfu.mavenInAction</groupId>
      <artifactId>project-b</artifactId>
    </exclusion>
  </exclusions>
  <dependency>                  <!--显示声明对b的依赖-->
    <groupId>com.philfu.mavenInAction</groupId>
    <artifactId>project-b</artifactId>
    <version>1.1.0</version
  </dependency>
</dependency>
```
> 声明 exclusion 的时候,只需要 groupId 和 artifactId, 而不需要 version 元素。原因:依赖调解, 只能存在一个依赖

### 归类依赖
使用 <properties> 定义全局变量, 归类管理版本,例如 springframework

### 优化依赖
Maven 会自动解析所有项目的直接依赖和传递依赖,并根据规则正确判断每个依赖的范围,对于依赖冲突,也能进行依赖调解,确保任何一个构建只有唯一的版本在依赖中存在。
在这些工作之后,最后得到的依赖称为**已解析依赖**(Resolved Dependency)。
将当前项目 pom 声明的依赖称为顶层依赖,这些顶层依赖的依赖成为第二层依赖,依次类推,有第三层、第四层依赖。这些依赖经 Maven 解析后,会构成一棵树。
这棵依赖树能很清楚的看到所有依赖是通过哪条传递路径引入的。
```
mvn dependency:list     该命令可查看当前项目的已解析依赖, 包括依赖的范围
mvn dependency:tree     该命令可以查看当前项目的依赖树
mvn dependency:analyze  该命令可以帮助分析当前项目的依赖(需编译项目)
```
依赖分析结果中有两项很重要:
> - Used undeclared dependencies: 指项目中使用到的,但是没有显示声明的依赖。这种依赖意味着风险,这种依赖是通过直接依赖传递进来的,
当直接依赖升级时,传递依赖也会发生变化,可能导致项目出错。这种隐藏的威胁一旦出现,需要耗费大量时间来查明真相。因此,显示声明任何项目中直接用到的依赖。
> - Unused declared dependencies: 指项目中未使用的,但是显示声明的依赖。对于这一类依赖,不要简单的直接删除。
dependency:analyze 只会分析**编译**主代码和测试代码需要用到的依赖, 而**执行**测试和**运行时**需要的依赖就发现不了。