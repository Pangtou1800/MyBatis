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
