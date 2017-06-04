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
