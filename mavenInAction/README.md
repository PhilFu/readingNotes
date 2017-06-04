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