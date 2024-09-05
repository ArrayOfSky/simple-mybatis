# arrayofsky-mybatis-simple

#### 介绍
简易Mybatis实现demo

#### 软件架构
软件架构说明

**目前阶段整体的流程为**：
1. 通过SqlSessionFactoryBuilder指定资源加载路径来开始启动并获取SqlSessionFactory
	1. 会先加载**XmlConfiguration**
		1. XmlConfiguration 会先**初始化Configuration**。
			1. Configuration先初始化 **MapperRegistry**
			2. 和一个Map 存放 **MappedStatement** 映射语句
			3. 以及类型别名注册机 **TypeAliasRegistry**
				1. 因为本项目暂时先实现了Druid数据源和JDBC事务管理、Pool连接和UnPool连接
				2. 而在后续xml配置文件中会配置指定druid和jdbc，为了保证能根据文本获取到class，这里要初始化添加。
		2. XmlConfiguration 然后开始解析XML
			1. **先解析环境信息**
				1. 解析xml，获取配置的 **事务管理器** **TransactionFactory**，再根据type从typeAliasRegistry中获取对应的类，来实例化对象。
				2. 解析xml，获取配置的是 **数据源** **DataSourceFactory**，再根据type从typeAliasRegistry中获取对应的类，实例化对象。
					1. 
				3. 解析xml，获取数据库的连接信息，配置到数据源对象中（注意这里并没有立即连接）**DataSource**
				4. 将上面解析到的信息全部放入环境对象 **Environment** 中。
			2. **再解析mapper信息**
				1. 先解析xml配置的mappers，获取每个mapper的文件地址
				2. 循环解析每一个mapper
					1. 先解析命名空间
					2. 然后需要对select、update、delete、insert进行分别处理，目前仅处理了select
					3. 获取id、parameterType、resultType、sql等xml中配置的信息
					4. 获取sql中的每一个#{}形式的块，替换为？，并将参数加入到paramMap
					5. 根据name创建 **SqlCommandType** ，识别select、update、insert、delete
					6. 根据paramMap、id、parameterType、resultType、sql等信息 创建 **BoundSql**
					7. 最后构造出 **MappedStatement**，然后存入Configuration中的map
				3. 将识别到需要被代理的 接口 mapper 注册到MapperRegistry中
					1. 首先MapperRegistry会先判断 该类 是否为一个接口
					2. 然后判断是否已经加载过该类
					3. 创建 **MapperProxyFactory** 放入map中，需要时再创建mapper对象。
	2. 此时遍完成了初始信息的加载，将加载好的Configuration对象用于创建 **SqlSessionFactory** 用于创建SqlSession。
2. 调用SqlSessionFactory获取SqlSession
	1. 调用了openSession方法
	2. 先创建事务处理器 **Transaction**
		1. 从configuration对象中获取环境对象environment
		2. 从环境对象environment中获取事务管理器TransactionFactory
		3. 使用TransactionFactory创建事务
	3. 再创建执行器
		1. 使用configuration创建执行器 **Excutor**
	4. 最后通过configuration和执行器创建 **SqlSession**
3. 通过SqlSession来获取需要代理的mapper对象
	1. 因为MapperRegistry在configuration中，所以调用configuration的getMapper方法
	2. configuration调用mapperRegistry
	3. mapperRegistry中，先判断传入的class是否在 sql解析 时解析过。
	4. 如果解析过，则创建对应的代理对象 **MapperProxy** 返回
4. 使用代理对象执行方法
	1. 先判断 执行的方法是否属于 Object的方法，因为不需要对Object下的方法进行代理增强。
	2. 先判断该接口的方法是否已经解析为 **MapperMethod** 过。
		1. 这是一层缓存，因为同一个类下的方法的内容是不会变的，所以解析过一次的方法就不需要再进行一次解析
		2. 如果未解析过，初始化 MapperMethod 进行解析。
			1. 先创建 **SqlCommand** 对象
				1. 根据 接口名+方法名 获取到 configuration 中，在xml解析过程中获取到的 MappedStatement
			2. 此时便完成了 接口方法 与 xml中解析的sql 对应映射关系
	3. 调用MapperMethod的excute方法
		1. 根据sql类型来执行不同的SqlSession操作。（目前仅实现了select）
		2. 进入SqlSession的 selectOne 逻辑
			1. 先根据传入的 接口+方法名 获取到 MappedStatement
			2. 调用excutor的query方法
				1. 获取 语句处理器 **StatementHandler**
					1. 由configuration直接创造
					2. 其中内聚了 结果处理器 **ResultSetHandler**
				2. 从 transaction 获取 **Connection**
					1. 调用datasource获取connection
					2. 根据datasource的不同走不同的逻辑
						1. PooledDataSource
							1. 先从空闲队列 idleConnections 判断有无空闲连接
								1. 有就直接返回
							2. 如果无空闲连接，则检查当前活跃连接数
								1. 如果当前活跃连接数小于最大可支持连接数，则创建新的连接
								2. 如果当前活跃连接数大学最大可支持连接数，则进行等待
							3. 创建新连接的过程：
								1. 先获取一个unpooledconnection，因为pooledconnection其实就是unpooledconnection进行了额外包装
								2. unpooledconnection是通过DriverManager来创建的。
				3. 获取 PreparedStatement 进行后续参数操作
				4. 语句处理器注入查询参数
					1. 这里目前还是写死来处理的
				5. 语句处理器执行查询语句
					1. 调用 PreparedStatement 执行 excute 函数
					2. 调用 结果处理器 ResultSetHandler 处理 PreparedStatement 的执行结果，并解析为对象返回。

#### 安装教程

1.  xxxx
2.  xxxx
3.  xxxx

#### 使用说明

1.  xxxx
2.  xxxx
3.  xxxx

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
2.  Gitee 官方博客 [blog.gitee.com](https://blog.gitee.com)
3.  你可以 [https://gitee.com/explore](https://gitee.com/explore) 这个地址来了解 Gitee 上的优秀开源项目
4.  [GVP](https://gitee.com/gvp) 全称是 Gitee 最有价值开源项目，是综合评定出的优秀开源项目
5.  Gitee 官方提供的使用手册 [https://gitee.com/help](https://gitee.com/help)
6.  Gitee 封面人物是一档用来展示 Gitee 会员风采的栏目 [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
