# MyBatis

## 第一节 MyBatis简介

    MyBatis负责和数据库进行交互，是一个持久化层框架（SQL映射框架）

    1. 原始的JDBC -> QueryRunner -> JdbcTemplate ... 数据库操作工具

        工具：一些功能的简单封装
        框架：某个领域的整体解决方案 - DB：缓存、异常处理、部分字段映射问题...

    2. Hibernate持久化框架（ORM框架）

        ORM - Object Relation Mapping 对象关系映射

        完全实现 0 SQL，和数据的交互是黑箱的

        缺点：
            1.无法手动编写SQL，而总会有一些需要定制的场景；
              Hibernate里要想定制则需要使用HQL语法

            2.Hibernate是一个全映射框架，难以实现部分字段映射

    3. MyBatis

        将SQL独立到外部配置文件，而前后的执行和封装依然自动完成

          - 容易维护
          - 容易优化
          - 速度快

        ※MyBatis底层就是对原生JDBC的一个简单封装

## 第二节 HelloWorld

    2.1 导包

        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.5</version>
        </dependency>

        ※建议导入log4j，然后再配置一个log4j.xml或log4j.properties

    2.2 基础环境

        -建表
        -bean
        -Dao接口

    2.3 写配置

        1.全局配置文件 - mybatis-config.xml

            -数据库连接信息

        2.SQL映射文件 - EmployeeDao.xml

            -写好每一个方法如何向数据库发送SQL

        3.将SQL映射文件注册到全局配置文件中

    2.4 测试

        1.根据全局配置文件创建SqlSessionFactory

        2.获取SqlSession对象

        3.向SqlSession对象获取Dao的实例

        4.执行Dao的方法即可

    2.5　细节

        1.配置文件
            增删改不用写返回值类型，MyBatis会自动判断
            如果是数字则返回影响行数，如果是boolean，则返回 行数!=0

        2.获取的Dao是MyBatis自动创建的动态代理对象

        3.SqlSessionFactory和SqlSession对象

            ·Factory只创建一次即可
            ·SqlSession相当于Connection，和数据库的每一次会话都应该创建一个新的SqlSession

## 第三节 全局配置文件

    3.1 作用

        指导MyBatis正确运行的全局设置

    3.2 内容一览

        configuration
            -properties 属性
            -settings 设置 ☆
            -typeAlias 类型命名
            -typeHandlers 类型处理器
            -objectFactory 对象工厂
            -plugins 插件
            -environments 环境
                -environment 环境变量
                    -transactionManager 事务管理器
                    -dataSource 数据源
            -databaseIdProvider 数据库厂商标识
            -mappers 映射器 ☆

    3.3 properties

        类似于Spring中的<context:property-placeholder>，引用外部配置文件

        -resource : 从类路径下引用资源
        -url ：从磁盘或网络路径引用资源

    3.4 settings ☆

        极其重要的配置内容，可以改变MyBatis的各种行为

        演示：
            mapUnderscoreToCamelCase - 将下划线和驼峰规则自动匹配

            <settings>
                <setting name="mapUnderscoreToCamelCase" value="true"/>
            </settings>

    3.5 typeAlias 

        1.为类型起别名

        <typeAliases>
            <typeAlias type="pt.joja.bean.Employee" alias="Employee"/>
        </typeAliases>

        在SQL映射文件中就不用写全类名了 ※不写别名默认类名

        2.批量起别名

        <typeAliases>
            <package name="pt.joja.bean"/>
        </typeAliases>

        3.别名就是类名

        加@Alias注解也可以 @Alias("emp")

        ※推荐：不要使用别名

        4.MyBatis已经为常用Java类起好了别名

    3.5 typeHandlers

        MyBatis向Statement中设置参数或是从ResultSet中取值时，都是用各种typeHandler来进行转换

        可以
            ·实现org.apache.ibatis.type.TypeHandler接口
            ·继承org.apache.ibatis.type.BaseTypeHandler类
        来自定义类型处理器

        然后加入即可
        <handlers>
            <handler>..

        扩展：日期类型的处理

            Java8 完全实现了JSR310时间日期规范，关于这些新增日期类型的处理器，
            MyBatis在3.4版本后也会自动添加

    3.6 objectFactory

        将查询结果封装为对象 ※没人用

    3.7 plugins

        插件师MyBatis的一个强大功能

        插件通过动态代理机制，可以介入四大对象的任何一个方法的执行

        ·Executor
        ·ParameterHandler
        ·ResultSetHandler
        ·StatementHandler

    3.8 environments

        <environments default="env1">
            <environment id="env1">
                <transactionManager type="">
                <dataSource type="">
            <environment id="env2">
                <transactionManager type="">
                <dataSource type="">

        ※整合之后，数据源和事务管理都交由Spring来管理

    3.9 databaseIdProvider

        用来处理数据库移植

        name - 数据库厂商的标识 MySQL,Oracle,SQL Server...
        value - 别名

        SQL映射文件默认是不区分数据库厂商的，配置上述元素之后，
        为SQL映射文件的元素添加databaseId属性即可
        ·精确匹配 > 默认

        ※一般是没有数据库移植的需要的
    
    3.10 mappers ☆

        注册SQL映射文件

        <mappers>
            <mapper>
                -resource：类路径下寻找SQL映射文件
                -url：磁盘或者网络路径寻找SQL映射文件
                -class：
                    1.Dao接口全类路径 - xml放在Dao接口相同目录下，且文件名一致
                    2.SQL文直接作为注解值表在Dao接口的方法上
                        @Select("select * from t_employee where id = #{id}")
                        public Employee getEmpById(Integer id);
            <package>
                -name：dao包名，但是所有配置文件都要放到Dao接口同路径下

## 第四节 SQL映射文件

    4.1 作用

        相当于Dao接口的实现描述

    4.2 内容一览

        mapper
            -cache 缓存相关
            -cache-ref 缓存相关
            -delete update insert select
            -parameterMap 参数表，原本用作复杂参数映射，现在被其他特性取代
            -resultMap 结果映射，自定义结果集的封装规则
            -sql 抽取可重用SQL

    4.3 增删改

        insert/update/delete
            -id 命名空间中唯一标识符：方法名 ※可见MyBatis不支持方法重载
            -parameterType 会自动根据TypeHandler推断，一般省略
            -flushCache 缓存相关
            -timeout 事务超时，整合后由Spring来控制
            -statementType
                ·STATEMENT 原生Statement
                ·PREPARED 原生PreparedStatement ※默认
                ·CALLABLE 调用存储过程
            -useGeneratedKeys 使用数据库的自增Id
            -keyProperty 唯一标记一个属性
            -keyColumn 指定主键列，仅对特定数据库生效，一般不管
            -databaseId 指定数据库别名

            <insert id="insertEmployee" useGeneratedKeys="true" keyProperty="id">

            即可在调用insertEmployee后，把数据库生成的自增id存入对象中

            在不支持主键自增的数据库如Oracle中，可以使用"子查询"
            <insert>
                <selectKey order="BEFORE" keyProperty="id" resultType="int">
                    select max(id) + 1 from t_employee
                </selectKey>
                ...

    4.4 查询

        1.传参到底能传哪些类型？
            ·单个参数
                基本类型：
                    #{随便}
                POJO：
                    #{属性名}
            ·多个参数
                取值#{arg0}, #{arg1}或#{param1}, #{param2}

                因为多个参数时MyBatis会把参数封装进一个Map中，key默认就是上面的

                可以在Dao方法里为参数打注解@Param("id")来规定键值　→ 推荐

                多个参数是POJO或Map时，到达该参数之后再 . 取值即可

        2.取值时可以设置一些规则 - #{key/属性名}

            id=#{id,jdbcType=INTEGER}

            javaType, jdbcType, mode, numericScale...

            一般都不用指定

            举例：插入null时，MySQL可以支持，但Oracle不会分析null的类型，需要明确指定

        3.两种取值方式 - #{属性名} vs ${属性名}

            where id = ${id} -> SQL: where id = 1
            where id = #{id} -> SQL: where id = ?

            #{} : 参数为预编译形式，值后来写入  -> 推荐，可以避免SQL注入
            ${} : 不是预编译形式，直接SQL拼串

            但是，SQL只有参数位置可以预编译，
            要动态改变表名、字段名等就可以使用${}

            select log_data from log_${date}

        4.查询集合

            > List
                Dao接口方法返回值为List即可，SQL映射文件无需做出任何修改

            > Map
                <select id="getEmpByIdToMap" resultType="map">
                键是列名

            > Map<key, POJO>
                <select id="getAllEmpsToMap" resultType="pt.joja.bean.Employee">
                    ！type一定是被封装的类型，写了map之后会得到Map<key, Map<,>>

                @MapKey("id")  <- key的字段 ※就是这个标记提示MyBatis将多个对象封装为Map
                public Map<String, Employee> getAllEmpsToMap();

        5.列名和bean属性不对应时

            > 开启驼峰命名法

            > 起别名
                select id, c_name as name, c_age as age, c_gender as gender

            > 自定义结果集
                <mapper>
                    <resultMap>
                        -id: 唯一标识
                        -type: POJO类名
                        <id>
                            -column: 指定主键列
                            -property：指定POJO的主键列对应属性
                        <result>
                            -column: 指定列
                            -property：指定POJO的对应属性
                        ...
                    <select resultMap="mappingId">
                        ...

        6.级联查询时

            > 级联属性 1:1

                <resultMap id="keyMapper1" type="pt.joja.bean.Key">
                    <id column="key_id" property="id"/>
                    <result column="key_name" property="keyName"/>
                    <result column="lock_id" property="lock.id"/>
                    <result column="lock_name" property="lock.lockName"/>
                </resultMap>

            > 1:1 <association> ※MyBatis推荐

                <resultMap id="keyMapper2" type="pt.joja.bean.Key">
                    <id column="key_id" property="id"/>
                    <result column="key_name" property="keyName"/>
                    <association property="lock" javaType="pt.joja.bean.Lock">
                        <id column="lock_id" property="id"/>
                        <result column="lock_name" property="lockName"/>
                    </association>
                </resultMap>

            > 1:n <collection>

                <resultMap id="lockMapper" type="pt.joja.bean.Lock">
                    <id property="id" column="lock_id"/>
                    <result property="lockName" column="lock_name"/>
                    <collection property="keys" ofType="pt.joja.bean.Key">
                        <id property="id" column="key_id"/>
                        <result property="keyName" column="key_name"/>
                    </collection>
                </resultMap>

        7.分步查询

            目标：
                1.Key key = keyDao.getKeyById(id)
                2.Lock lock = lockDao.getLockById(id)

            <resultMap id="keyMapper3" type="pt.joja.bean.Key">
                <id column="key_id" property="id"/>
                <result column="key_name" property="keyName"/>
                <association property="lock" column="lock_id" select="pt.joja.dao.LockDao.getLockByIdSimple"/>
            </resultMap>

            多个参数时 column={key1=col1,key2=col2}

        8. 按需加载

            在分步查询的基础上开启全局按需加载策略即可

            <setting name="lazyLoadingEnabled" value="true"/>
            <setting name="aggressiveLazyLoading" value="false"/>

            <mapper>
                -fetchType="eager" / "lazy"可以单独制定

            ※ 但还是推荐使用连接查询

## 第五节 动态SQL

    MyBatis非常强大的功能，简化SQL动态拼串操作

    5.1 if

        <if test=""> => OGNL表达式，基本上什么都支持

    5.2 where

        根据内容是否为空决定拼不拼where

        <where>
            <if test="id!=null">
                id > #{id}
            </if>
            <if test="teacherName!=null &amp;&amp; !teacherName.equals(&quot;&quot;)">
                and teacher_name like #{teacherName}
            </if>
            <if test="birthday!=null">
                and birthday &lt; #{birthday}
            </if>
        </where>

        where标签自动去除前方and

    5.3 trim 强大

        <trim>
            -prefix : 不为空时加前缀
            -prefixOverrides : 前缀为指定值时去除
            -suffix ：不为空时加后缀
            -suffixOverrides ：后缀为指定值时去除

        <trim prefix="where" prefixOverrides="and" suffix="" suffixOverrides="">

    5.4 foreach

        <if test="idList!=null and idList.size()>0">
            id in
            <foreach collection="idList" item="idItem" index="" open="(" close=")" separator=",">
                #{idItem}
            </foreach>
        </if>

        ※集合参数在Dao中配合@Param使用
        ※index Map时就是key

    5.5 choose - when - otherwise

        分支选择

    5.6 用在update中

        <set>
            <if test="teacherName!=null and !teacherName.equals('')">
                teacher_name = #{teacherName}
            </if>
            <if test="className!=null and !className.equals('')">
                ,class_name = #{className},
            </if>
            <if test="address!=null and !address.equals('')">
                address = #{address},
            </if>
            <if test="birthday!=null">
                birthday = #{birthday}
            </if>
        </set>

        会去前后逗号

    5.7 OGNL语言 - 对象图导航语言

        例：
            Person
                -lastName
                -email
                -Address
                    -city
                    -province
                    -Street
                        -adminName
                        -info
                        -peopleAmount
                -getName()

        属性：person.lastName
        方法：person.getName()
        静态属性：@Person@Amount
        静态方法：@Person@nextId()
        构造：new Person('admin') - 不区分' "
        运算符：+ - * / %
        逻辑： in, not in, > >= < <= == !=   => xml中的话要转义

        集合还有伪属性

        List, Set, Map : size, isEmpty
        List, Set      : iterator
        Map            : keys, values
        Iterator       : next, hasNext

        在MyBatis中，参数以外还有两个默认变量可以用来判断：
            
            _parameter - 传入的参数整体
                1.单个参数：就是那个参数
                2.多个参数：封装后的Map

            _datebaseId - 数据库标识字符

    5.8 bind 变量预加工

        <bind name="_name" value="'%'+name+'%'"/>

    5.9 include 通用sql语句

        <sql id="selectTeacher">select * from t_teacher</sql>
        <select id="getTeacherById" resultMap="teacherMap">
            <include refid="selectTeacher"/>
            where id = #{id}
        </select>

## 第六节 缓存机制

    暂时存储一些数据，加快系统的查询速度

    MyBatis缓存机制：
        内置了一个Map，可以保存一些查询结果

        ·一级缓存 - 线程级别的缓存，也称为本地缓存、SqlSession级别缓存
        ·二级缓存 - 全局范围的缓存，除当前线程外也可使用，当前SqlSession以外其他也可以使用

    6.1 一级缓存

        ·默认一直存在
        ·只要之前查询过的数据，MyBatis就会把它保存在一个缓存中，再做相同查询时直接取出结果

        key: hashCode + SqlId + Sql文 + 参数

    6.2 一级缓存失效的几种情况

        1.只有当前SqlSession期间查询到的数才会保存在它的一级缓存中，其他SqlSession无法使用

        2.查询条件变更之后会再次发行SQL

        3.两次查询之间执行任意一次增删改操作，会清空当前SqlSession的缓存
            ※其他SqlSession即使Commit也不影响当前缓存

        4.调用clearCache()方法清理缓存

    6.2 二级缓存

        全局作用域缓存，默认选项会随着版本发生变化，需要手动配置

        MyBatis提供二级缓存的接口以及实现，缓存实现要求POJO实现Serializable接口

        ※二级缓存在SqlSession关闭或提交之后才会生效

        1.步骤
            ·全局配置文件开启二级缓存
                <setting name="cacheEnabled" value="true"/>
            ·指定缓存对象的Dao - 向SQL映射文件添加一个空cache标签
                <mapper>
                    <cache/>
            ·查询结果对象实现java.io.Serializable接口

        2.说明
            又称为namespace级别的缓存 - Dao可以自己选择是否开启二级缓存

            <cache>
                -eviction：缓存回收策略
                    ·FIFO
                    ·LRU
                    ·SOFT
                    ·WEAK
                -flushInterval：刷新间隔，单位毫秒
                -size：引用数目，最大缓存对象数
                -readOnly：
                    true表示缓存对象是只读的，直接返回命中对象的引用
                    false表示缓存中对象可读写，会返回缓存对象的拷贝（通过序列化）

        3.优先级

            二级缓存 > 一级缓存

            多个一级缓存中有相同查询内容时，后关闭的一级缓存会覆盖二级缓存的内容

        4.缓存有关设置

            <setting>
                cacheEnable

            <mapper>
                <cache>

            <select>
                useCache -> 对一级缓存没有影响

            <CRUD
                flushCache 同时清空一二级缓存，增删改默认true，查询默认false

            sqlSession.clearCache() - 清空以及缓存

    6.3 整合第三方缓存

        用第三方二级缓存替代MyBatis的默认二级缓存

        步骤：
            实装Cache接口，在方法中连接到专业的缓存库

        

        Client -> CachingExecutor <=> [decorate Executor] <=> database
                        ||                  ||
                        ||              Local Cache
                        ||
            ----Configuration -------------------------------
                    Mapper namspace1    Mapper namspace2
                      [Cache]               [Cache]
                        ||                      ||
                        ||                      ||
                Third Party Cache
                    MemCached, OSCache, EHCache...

        练习：
            整合EHCache

            1.导包
                ehcache - 核心
                mybatis-ehcache - MyBatis提供的Cache接口实现

            2.ehcache配置文件
                encache.xml

            3.指定使用Cache实现类
                <mapper>
                    <cache type="Cache实现类">

    6.4 命名空间公用缓存

        <mapper>
            <cache-ref namespace="">

## 第七节 SSM整合

    7.1 导包

        1.Spring

            [IOC核心]
                core
                beans
                context
                expression
                
            [AOP核心]
                aop
                aspects

            [AOP增强]
                cglib
                aopalliance
                aspectj

            [jdbc核心]
                jdbc
                orm
                tx

            [依赖]
                commons-logging    

        2.SpringMVC

            [Web核心]
                web
                webmvc

            [上传下载]
                commons-fileupload
                commons-io
            
            [jsp标准标签库]
                jstl
                standard

            [Hibernate校验]
                hibernate-validator
                hibernate-validator-annotation

            [Hibernate校验依赖]
                classmate
                jboss-logging
                validation-api

            [json]
                jackson-core
                jackson-databind
                jackson-annotation
            
            [日志]
                log4j        

        3. MyBatis

            [MyBatis]
                mybatis

            [jdbc driver]
                xxx

            [ehcache]
                ehcache

    7.2 写配置

        1.Spring

        2.SpringMVC

        3.MyBatis

            ·导入整合包 - 将Dao的实现加入容器中
                mybatis-spring

            <!-- 2.配置MyBatis操作数据库 -->
            <!-- 2.1 sqlSessionFactory加入容器 -->
            <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
                <property name="configLocation" value="classpath:mybatis/mybatis-config.xml"/>
                <property name="dataSource" ref="dataSource"/>
                <property name="mapperLocations" value="classpath:mybatis/mapper/*.xml"/>
            </bean>
            <!-- 2.2 Dao实例加入容器-->
            <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
                <property name="basePackage" value="pt.joja.dao"/>
            </bean>

        4.其他小框架

    7.3 测试
