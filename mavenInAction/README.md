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
当直接依赖升级时,传递依赖也会发生变化,可能导致项目出错。这种隐藏的威胁一旦出现,需要耗费大量时间来查明真相。因此,显示声明任何项目中**直接**用到的依赖。
> - Unused declared dependencies: 指项目中未使用的,但是显示声明的依赖。对于这一类依赖,不要简单的直接删除。
dependency:analyze 只会分析**编译**主代码和测试代码需要用到的依赖, 而**执行**测试和**运行时**需要的依赖就发现不了。

## 仓库
在 Maven 中,任何一个依赖、插件或者项目的输出都称为构件。坐标和依赖是任何一个构件在 Maven 中的逻辑表示方式, 物理表示方式是文件。
Maven 通过**仓库**来统一管理这些文件, 路径与坐标的对应关系为: groupId/artifactId/version/artifactId-version.packaging
```
Maven 处理仓库布局的源码
private static final char PATH_SEPARATOR = '/';
private static final char GROUP_SEPARATOR = '.';
private static final char ARTIFACT_SEPARATOR = '-';

public String pathOf(Artifact artifact) {   // 根据构件信息生成其在仓库中的路径
    ArtifactHandler artifactHandler = artifact.getArtifactHandler();
    StringBuilder path = new StringBuilder(128);

    path.append(formatDirectory(artifact.getGroupId())).append(PATH_SEPARATOR);
    path.append(artifact.getArtifactId()).append(PATH_SEPARATOR);   // 可以看出 artifactId 只有一个字段,没有'.'
    path.append(artifact.getBaseVersion()).append(PATH_SEPARATOR);  // baseVersion 是为 SNAPSHOT 版本服务的 1.0-SNAPSHOT, baseVersion是1.0
    path.append(artifact.getArtifactId()).append(ARTIFACT_SEPARATOR).append(artifact.getVersion());

    if (artifact.hasClassifier()) {
        path.append(ARTIFACT_SEPARATOR).append(artifact.getClassifier());
    }

    // extension是从 artifactHandler 获取的, artifactHandler是由项目的 packaging 决定的, 因此 packaging 决定了构件的扩展名
    if (artifactHandler.getExtension() != null && artifactHandler.getExtension().length > 0) {
        path.append(GROUP_SEPARATOR).append(artifactHandler.getExtension());
    }

    return path.toString();
}

private String formatAsDirectory(String directory) {
    return directory.replace(GROUP_SEPARATOR, PATH_SEPARATOR);
}
```

### 仓库的分类
Maven 仓库分为本地仓库和远程仓库(中央仓库,私服,其他公共库)。当 Maven 根据坐标寻找构件的时候,会先查找本地仓库,如果存在则直接使用;如果不存在,
或者需要查看是否有更新的版本, Maven 会去远程仓库查找,发现需要的构件后,下载到本地仓库再使用。如果本地仓库和远程仓库都没有, Maven 会报错。

#### 本地仓库
Maven 项目中没有 lib/ 这样用来存放依赖文件的目录, 执行编译或测试时, 总是基于坐标使用本地仓库中的依赖文件。
默认每个用户在自己的目录下有个 .m2/repository/ 的仓库目录, 如果想要自定义本地仓库目录, 可以编辑 .m2/setting.xml 文件
默认是不存在 .m2/setting.xml 的, 需要从 Maven 的安装目录复制 $M2_HOME/con/setting.xml 然后在进行编辑, 不推荐修改全局 setting.xml 文件
```
<setting>
    <localRepository>D:\java\repository</localRepository>
</setting>
```
将本地项目的构件安装到 Maven 仓库中, 执行 **mvn clean install** 命令, install 插件的 install 目标是将项目的构件输出文件安装到本地仓库。

#### 远程仓库
每个用户只有一个本地仓库,但可以配置访问很多远程仓库

##### 中央仓库
中央仓库[http://repo1.maven.org/maven2](http://repo1.maven.org/maven2)是一个默认的远程仓库, Maven 按照文件中自带了中央仓库的配置。
位于: $M2_HOME/lib/maven-model-builder-3.0.jar 中, 目录 org/apache/maven/model/pom-4.0.0.xml

##### 私服
私服代理广域网上的远程仓库,供局域网内的 Maven 用户使用。需要下载构件时,从私服请求;如果私服上不存在,则从外部远程仓库下载,缓存在私服上。
即使可以直连Internet,也需要建立私服,原因:
> - 节省外网带宽。对外的重复构件下载被消除,降低外网带宽的压力
> - 加速 Maven 构建。Maven 的一些机制(如快照更新检查等),要求 Maven 在执行构建的时候不停的检查远程仓库数据,如果配置很多外部远程仓库,构建速度会大大降低
> - 部署第三方构件。某些构件无法从任何一个外部远程仓库获得,如组织内部生成的私有构件、Oracle的JDBC驱动由于版权因素不能发布到公共仓库中
> - 提高稳定性,增强控制。如果使用远程仓库,当Internet不稳定,Maven构建也会不稳定,甚至无法构建。一些私服软件(如Nexus)还提供了权限管理等高级控制功能
> - 降低中央仓库的负荷。

#### 远程仓库配置
```
<project>
  <repositories>    <!--可以配置多个远程仓库-->
    <repository>
      <id>jboss</id>    <!--id必须唯一,中央仓库id为central,可以使用该id覆盖中央仓库的配置-->
      <name>JBoss Repository</name>
      <url>http://repository.jboss.com/maven2</url> <!--url基于http协议,可以直接在浏览器中打开浏览构件-->
      <releases>
        <enabled>true</enabled>     <!--下载发布版的构件-->
        <updatePolicy>daily</updatePolicy>  <!--每天从远程仓库检查更新, never-从不;always-每次构建;interval:X-每隔X分钟-->
        <checksumPolicy>ignore</checksumPolicy> <!--下载构件时,maven会验证校验和文件,失败时策略:warn-输出警官;fail:构建失败;ignore:忽略-->
      </releases>
      <snapshots>
        <enabled>false</enabled>    <!--不下载快照版的构件-->
      </snapshots>
      <layout>default</layout>      <!--仓库布局是maven2及maven3的默认布局-->
    </repository>
  </repositories>
</project>
```

##### 远程仓库的认证
仓库信息可以配置在 POM 文件中,但认证信息必须配置在 setting.xml 中,因为 POM 会被提交到仓库中供所有人访问,setting.xml 则存放在本机
```
<settings>
  <servers>
    <server>
      <id>my-proj</id>      <!--id必须与 POM 中需要认证的 repository 元素的 id 完全一致-->
      <username>repo-user</username>
      <password>repo-pwd</password>
    </server>
  </servers>
</settings>
```

##### 部署到远程仓库
执行 **mvn clean deploy**, Maven 会将构建输出部署到对应的远程仓库
```
<project>
  <distributionManagement>
    <repository>        <!--发布构件的仓库-->
      <id>proj-release</id>
      <name>Proj Release Repository</name>
      <url>http://192.168.1.100/content/repositories/proj-releases</url>
    </repository>
    <snapshotRepository>    <!--快照版本的仓库-->
      <id>proj-snapshots</id>
      <name>Proj Snapshot Repository</name>
      <url>http://192.168.1.100/content/repositories/proj-snapshots</url>
    </snapshotRepository>
  </distributionManagement>
</project>
```

### 快照版本
假设小张开发模块A的2.1版本,小李开发模块B,B的功能依赖于A。如何协同开发?
- 小李checkout模块A的源码进行构建, 效率太低
- 重复部署模块A的2.1版本。虽然小张能保证仓库中的构件是最新的,但是同样的版本和同样的坐标意味着同样的构件, 小李每次构件时,需要清除本地仓库才能更新
- 不停的更新版本 2.1.1, 2.1.2, 2.1.3。小张和小李都需要频繁更改版本号,其次大量版本其实仅包含了微小的差异,是对版本号的滥用。

Maven 快照就是解决上述问题。小张只需要将模块A的版本设定为2.1-SNAPSHOT, 发布到私服中,发布时Maven会自动为构件打上时间戳。
如 2.1-20161214.221414-13表示2016年12月14日22点14分14秒的第13次快照
小李配置对模块2.1-SNAPSHOT的依赖, 当他构建模块B时, Maven会自动从仓库中检查模块A的2.1-SNAPSHOT的最新构件,有更新时会下载
Maven默认每天检查一次(由updatePolicy控制), 也可以使用命令 -U 强制更新, 如 mvn clean install -U
当项目经过完善的测试后需要发布版本时,将快照版本更改为发布版本, 将2.1-SNAPSHOT改为2.1, 表示该版本已经稳定。

### 从仓库解析依赖机制
1. 当依赖范围是 system 的时候, Maven 直接从本地文件系统解析构件。
2. 根据依赖坐标计算仓库路径后, 尝试从本地仓库寻找构件, 如果发现相应的构件, 则解析成功
3. 在本地仓库不存在相应构件的情况下, 如果依赖的版本是显式的发布构件版本, 如1.2、 2.1-beta-1等, 则遍历所有远程仓库, 发现后下载并使用。
4. 如果依赖的版本是 RELEASE 或者 LATEST, 则基于更新策略读取所有远程仓库的元数据 groupId/artifactId/maven-metadata.xml, 将其与本地仓库的对应元数据合并,
计算出 RELEASE 或者 LATEST 真实的值, 然后基于这个真实的值检查本地和远程仓库, 如步骤2,3.
5. 如果依赖的版本是 SNAPSHOT, 则基于更新策略读取所有远程仓库的元数据 groupId/artifactId/version/maven-metadata.xml, 将其与本地仓库的对ing元数据合并,
得到最新快照版本的值, 然后基于该值检查本地仓库, 或者从远程仓库下载
6. 如果最后解析得到的构件版本是时间戳的快照, 如 1.4.1-20091104.121450-121, 则复制其时间戳格式的文件至非时间戳格式, 如 SNAPSHOT, 并使用该非时间戳格式的构件。
```
<metadata>
  <groupId>org.sonatype.nexus</groupId>
  <artifactId>nexus</artifactId>
  <versioning>
    <latest>1.4.2-SNAPSHOT</latest>
    <release>1.4.0</release>
    <versions>
      <version>1.3.5</version>
      <version>1.3.6</version>
      <version>1.4.0-SNAPSHOT</version>
      <version>1.4.0</version>
      <version>1.4.0.1-SNAPSHOT</version>
      <version>1.4.2-SNAPSHOT</version>
    </versions>
    <lastUpdated>20161214221557</lastUpdated>
  </versioning>
</metadata>
```
maven-metadata.xml 列出了仓库中存在的该构件的所有可用版本, 同时 latest 元素指向这些版本中的最新版本, release 元素指向了这些版本中最新的发布版本。
Maven 通过合并多个远程仓库及本地仓库的元数据, 就能计算出基于所有仓库的 latest 和 release 分别是什么, 然后再解析出具体的构件。
不推荐在依赖中使用 LATEST 和 RELEASE, 因为 Maven 随时可能解析到不同的构件, 并且不会明确高速用户这样的变化, 当这种变化导致构建失败时, 很难发现问题。
```
<metadata>
  <groupId>org.sonatype.nexus</groupId>
  <artifactId>nexus</artifactId>
  <version>1.4.2-SNAPSHOT</version>
  <versioning>
    <snapshot>
      <timestamp>20161214.221414</timestamp>  <!--快照版本的时间戳-->
      <buildNumber>13</buildNumber>        <!--快照版本的构建号-->
    <snapshot>
    <lastUpdated>20161214221558</lastUpdated>
  </versioning>
</metadata>
```

### 镜像
如果仓库 X 可以提供仓库 Y 存储的所有内容, 那么可以认为 X 是 Y 的一个镜像。由于地理位置因素, 镜像往往比中央仓库更快的服务。
镜像完全屏蔽了被镜像仓库, 当镜像不稳定或者停止服务时, Maven 无法访问镜像仓库, 则会无法下载构件
```
<settings>
  <mirrors>
    <mirror>
      <id>maven.net.cn</id>
      <name> one of the central mirrors in China</name>
      <url>http://maven.net.cn/content/groups/public</url>
      <mirrorOf>central</mirrorOf>  <!--表明是中央仓库的镜像, 任何对中央仓库的请求都会转至该镜像-->
      <mirrorOf>*</mirror>  <!--对任何远程仓库的请求都会被转移至该仓库, 配置私服时常用-->
      <mirrorOf>external:*</mirror>  <!--匹配所有远程仓库, 使用 localhost 的除外, 使用 file:// 协议的除外-->
      <mirrorOf>repo1,repo2</mirror>  <!--匹配 repo1 和 repo2, 使用逗号分割多个远程仓库-->
      <mirrorOf>*,!repo1</mirror> <!--匹配所有远程仓库, repo1 除外, 使用!将仓库从匹配中排除-->
    </mirror>
  </mirrors>
</settings>
```

## 生命周期和插件
Maven 日常使用中, 命令行的输入往往就对应了生命周期, 如 mvn package 就表示执行默认生命周期阶段 package.
Maven 的生命周期是抽象的, 其实际行为是由插件完成, 如 package 阶段的任务可能就会由 maven-jar-plugin 完成。

### 生命周期
Maven 总结了一套完善的、易扩展的声明周期, 包括: 项目的清理、初始化、编译、测试、打包、集成测试、验证、部署、站点生成。几乎所有项目的构建, 都能映射到这样一个生命周期上。
Maven 的生命周期是抽象的,意味着生命周期本身不做任何实际的工作, 都交由插件来完成（模板方法模式),而且 Maven 为大多数构建步骤编写并绑定了默认插件。

#### Maven 的三套生命周期
Maven 拥有三套相互独立的生命周期, 分别是: clean, default, site.每个生命周期都包含一些阶段(phase),这些阶段是有顺序的,并且后面的阶段依赖于前面的阶段

##### clean: 清理项目
- pre-clean: 执行一些清理前需要完成的工作
- clean: 清理上一次构建生成的文件
- post-clean: 执行一些清理后需要完成的工作

##### default: 构建项目
- validate
- initialize
- generate-sources
- process-sources: 处理项目主资源目录。对 src/main/resources 目录的内容进行变量替换等工作后, 复制到项目输出的主 classpath 目录中。
- generate-resources
- process-resources
- compile: 编译项目的主源码。 编译 src/main/java 目录下的 Java 文件至项目输出的主 classpath 目录中
- process-classes
- generate-test-sources
- process-test-sources: 处理项目测试资源文件。 对 src/test/resources 目录的内容进行变量替换等工作, 复制到项目输出的测试 classpath 目录中
- generate-test-resources
- process-test-resources
- test-compile 编译项目的测试代码。 编译 src/test/java 目录下的 Java 文件至项目输出的测试 classpath 目录中
- process-test-classes
- test: 使用单元测试框架运行测试, 测试代码不会被打包或部署。
- prepare-package
- package: 接受编译好的代码, 打包成可发布的格式, 如 jar
- pre-integration-test
- integration-test
- post-integration-test
- verify
- install: 将包安装到 Maven 本地仓库, 供本地其他 Maven 项目使用
- deploy: 将最终包复制到远程仓库, 供其他开发人员和 Maven 项目使用

##### site: 建立和发布项目站点
- pre-site: 执行一些在生成项目站点之前需要完成的工作
- site: 生成项目站点文件
- post-site: 执行一些在生成项目站点之后需要完成的工作
- site-deploy: 将生成的项目站点发布到服务器上

##### 命令行与生命周期
- mvn clean: 调用 clean 生命周期的 clean 阶段, 实际执行的阶段为 clean 生命周期的 pre-clean 和 clean 阶段
- mvn test: 调用 default 生命周期的 test 阶段, 实际执行的阶段为 default 生命周期的 validate 直到 test 所有阶段。(因此会编译)
- mvn clean install: 调用 clean 生命周期的 clean 阶段和 default 生命周期的 install 阶段, 实际执行 clean 生命周期的 pre-clean, clean 阶段,
以及 default 生命周期的从 validate 到 install 的所有阶段。该命令结合了两个生命周期
- mvn clean deploy site-deploy: 调用 clean 生命周期的 clean 阶段、default 生命周期的 deploy 阶段, 以及 site 生命周期的 site-deploy 阶段。
实际执行的阶段为 clean 生命周期的 pre-clean, clean 阶段, default 生命周期的所有阶段, 以及 site 生命周期的所有阶段。

### 插件
插件目标(Plugin Goal): Maven定义了抽象的生命周期,具体任务交由插件完成。但每个任务编写一个插件并不可取,因为任务背后有很多可复用的代码。
因此插件往往能完成多个功能, 每个功能就是一个插件目标。 maven-dependency-plugin 有十多个目标,每个目标对应一个功能
> - dependency:analyze 分析项目依赖, 帮助找到潜在的无用依赖
> - dependency:tree 列出项目依赖树, 帮助分析依赖来源
> - dependency:list 列出项目所有已解析的依赖

冒号前面是插件前缀, 冒号后面是该插件的目标。 类似的 compiler:compile, surefire:test

#### 插件绑定
Maven 生命周期的阶段与插件的目标项目绑定, 以完成某个具体的构建任务。
如项目编译, 对应了 default 生命周期的 compile 阶段, maven-compiler-plugin 插件的 compile 目标能完成该任务, 将他们绑定, 就能实现项目编译。

##### 内置绑定
为了让用户不用任何配置就能构建 Maven 项目, Maven 为主要的生命周期绑定了很多插件目标, 用户通过命令行调用声明周期阶段时, 对应的插件目标就会执行响应的任务。

clean 生命周期
|生命周期阶段|插件目标|
|---|---|
|pre-clean||
|clean|maven-clean-plugin:clean|
|post-clean||

site 声明周期
|生命周期阶段|插件目标|
|---|---|
|pre-site||
|site|maven-site-plugin:site|
|post-site||
|site-deploy|maven-site-plugin:deploy|

deploy 生命周期, 项目打包类型会影响构件的具体过程, deault 声明周期的阶段与插件目标的绑定关系由项目的打包类型(packaging)决定。jar包如下:
|生命周期阶段|插件目标|执行任务|
|---|---|---|
|process-resources|maven-resource-plugin:resources|复制主资源文件至主输出目录|
|compile|maven-compiler-plugin:compile|编译主代码至主输出目录|
|process-test-resources|maven-resources-plugin:testResources|复制测试资源文件至测试输出目录|
|test-compile|maven-compiler-plugin:testCompile|编译测试代码至测试输出目录|
|test|maven-surefire-plugin:test|执行测试用例|
|package|maven-jar-plugin:jar|创建项目jar包|
|install|maven-install-plugin:install|将项目输出构件安装到本地仓库|
|deploy|maven-deploy-plugin:deploy|将项目输出构件部署到远程仓库|

##### 自定义绑定
```
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-source-plugin</artifactId>
      <version>2.1.1</version>
      <executions>
        <execution>
          <id>attach-sources</id>
          <phase>verify</phase>     <!--执行verify生命周期阶段时, maven-source-plugin:jar-no-fork会得以执行-->
          <goals>
            <goal>jar-no-fork</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```
删除上面的 <phase>verify</phase> 插件目标仍能执行,原因: 很多插件的目标在编写时已经定义了默认绑定阶段。查看插件详细信息,了解默认绑定阶段
```
mvn help:describe-Dplugin=org.apache.maven.plugins:maven-source-plugin:2.1.1-Ddetail
```
多个目标被绑定到统一阶段, 这些插件声明的先后顺序决定了目标的执行顺序

#### 插件配置
Maven 插件的目标有一些可配置的参数

##### 命令行插件配置
可以在 Maven 命令中使用 -D 参数,并伴随一个 参数键=参数值 的形式,来配置插件目标的参数
```
mvn install -Dmaven.test.skip=true  ## -D 是 Java 自带的, 功能是通过命令行设置一个Java系统属性, Maven简单的重用了该参数
```

##### POM 中插件全局配置
全局的配置,所有基于该插件目标的任务,都会使用这些配置
```
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-compiler-plugin</artifactId>
      <version>2.1</version>
      <configuration>         <!--不管是绑定到 compile 阶段的 maven-compiler-plugin:compile 任务-->
        <source>1.5</source>  <!--还是绑定到 test-compiler 阶段的 maven-compiler-plugin:testCompiler 任务-->
        <target>1.5</target>  <!--都能使用该配置,基于 1.5 版本进行编译-->
      </configuration>
    </plugin>
  </plugins>
</build>
```

##### POM中插件任务配置
除了为插件配置全局的参数, 还可以为某个插件任务配置特定的参数
```
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-antrun-plugin</artifactId>
      <version>1.3</version>
      <executions>
        <execution>
          <id>ant-validate</id>
          <phase>validate</phase>
          <goals>
            <goal>run</goal>
          </goals>
          <configuration>       <!--configuration位于executions下,而非plugin下。表明这是特定任务的配置-->
            <tasks>
              <echo>I'm bound to validate phase.</echo>
            </tasks>
          </configuration>
        </execution>
        <execution>
          <id>ant-verify</id>
          <phase>verify</phase>
          <goals>
            <goal>run</goal>
          </goals>
          <configuration>
            <tasks>
              <echo>I'm bound to verify phase.</echo>
            </tasks>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

#### 获取插件信息
```
mvn help:describe -Dplugin=org.apache.maven.plugins:maven-compiler-plugin:2.1
mvn help:describe -Dplugin=org.apache.maven.plugins:maven-compiler-plugin   ## 获取最新版本的描述
mvn help:describe -Dplugin=compiler     ## 使用 Goal Prefix 代替坐标
mvn help:describe -Dplugin=compiler -Dgoal=compile      ## 仅描述指定的目标
mvn help:describe -Dplugin=compiler -Ddetail    ## 详细信息
```
Goal Prefix的作用:方便在命令行直接运行插件

#### 从命令行调用插件
```
mvn -h     # 输出 usage: mvn [options] [<goal(s)>] [<phase(s)>]
```
该命令告诉了mvn命令的基本用法, options 表示可用的选项, goals 指插件目标, phases 指生命周期阶段。
可以通过 mvn 命令激活生命周期阶段,从而执行绑定在生命周期阶段上的插件目标。Maven 还支持直接在命令行调用插件目标,因为有些任务不适合绑定在生命周期上。
例如 maven-help-plugin:describe, 不需要在构建项目的时候去描述插件信息; maven-dependency-plugin:tree,不需要构建时显示依赖树
因此这些命令需要通过如下方式使用
```
mvn help:describe -Dplugin=compiler     ## help 是 maven-help-plugin 的目标前缀
mvn dependency:tree     ## dependency 是 maven-dependency-plugin 的前缀
```

#### 插件解析机制
Maven 不需要用户提供完整的插件坐标信息,就可以解析到正确的插件, 这是一把双刃剑, 简化了插件的使用和配置, 但很难定位到出问题的插件

##### 插件仓库
和依赖一样,插件同样基于坐标存储在 Maven 仓库中, Maven会区别对待依赖的远程仓库和插件的远程仓库。
前面配置的远程仓库只对依赖有效。当 Maven 需要的依赖在本地仓库找不到时,会去配置的远程仓库找;当插件在本地仓库不存在时,不会去这些远程仓库找
不同于 repositories 及其 repository 子元素, 插件的远程仓库使用 pluginRepositories 和 pluginRepository 配置
```
maven 内置的插件远程仓库
<pluginRepositories>
  <pluginRepository>
    <id>central</id>
    <name>Maven Plugin Repository</name>
    <url>http://repo1.maven.org/maven2</url>
    <layout>default</layout>
    <snapshots>
      <enabled>false</enabled>
    </snapshots>
    <release>
      <updatePolicy>never</updatePolicy>
    </release>
  </pluginRepository>
</pluginRepositories>
```

##### 插件的默认groupId
如果插件是 Maven 的官方插件（groupId 为 org.apache.maven.plugin）,可以省略 groupId 配置
```
<build>
  <plugins>
    <plugin>
      <artifactId>maven-compiler-plugin</artifactId>
      <version>2.1</version>
      <configuration>
        <source>1.5</source>
        <target>1.5</target>
      </configuration>
    </plugin>
  </plugins>
</build>
```

##### 解析插件版本
用户没有提供插件版本的情况下, maven 会自动解析插件版本。Maven 在超级POM中会所有核心插件设定了版本,即使不加任何配置,核心插件的版本就确定了。
如果用户使用某个插件没有设定版本,同时该插件又不是核心插件, Maven 会检查所有仓库中的可用版本, 然后做出选择,将元数据归并,计算出latest和release值。
Maven2 中会使用 latest, maven3中会使用 release。

##### 解析插件前缀
```
<metadata>
  <plugins>
    <plugin>
      <name>Maven Clean Plugin</name>
      <prefix>clean</prefix>
      <artifactId>maven-clean-plugin</artifactId>
    </plugin>
    <plugin>
      <name>Maven Compiler Plugin</name>
      <prefix>compiler</prefix>
      <artifactId>maven-compiler-plugin</artifactId>
    </plugin>
    <plugin>
      <name>Maven Dependency Plugin</name>
      <prefix>dependency</prefix>
      <artifactId>maven-dependency-plugin</artifactId>
    </plugin>
  </plugins>
</metadata>
```