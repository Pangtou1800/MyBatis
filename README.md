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