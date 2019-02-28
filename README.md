# xw-mail

#### 介绍
xw-mail 是一个定时发送邮件的后台系统，该系统可以配置 发件人、收件人、密送人、发送时间等邮件信息。亮点是可以灵活配置邮件内容，邮件内容也可灵活扩展。

#### 软件架构
软件架构说明：项目依据bootdo做了二次开发，去除掉一些功能模块。

主要用到下面技术
1. springboot
2. mybatis
3. shiro
4. thymeleaf
5. bootstrap
6. Quartz

#### 使用说明

* 安装运行

    * 克隆项目 ` git clone https://gitee.com/xzbd/xw-mail.git `
    * 创建数据库 `xw-mail` （mysql），并导入项目中 ` xw-mail.sql ` 中数据
    * 修改配置文件（根据自己的需要可忽略）
    * 找到 ` XwMailApplication.java `，启动项目。访问 ` localhost:8099 `

* 项目简介

    *  项目场景：向众多不确定的群体定期推送邮件（文字，数据）。邮件内容主要以动
    态数据为主，且邮件是固定周期分发，邮件数量庞大。

    * 解决方案： 以定时任务，自动查询数据、组织信息、将数据以Excel为载体发送。
    并记录发送邮件记录。

    * 项目图片
        * 配置sheet
            * 自动生成：可以根据数据库表自动生成配置内容：其中sheet名称对应表
        注释；sheet表头对应表中字段注释，当注释为空时实用表字段对应，
        列明对应表中字段名；生成的SQL 如： `select * from user `
            * 手动配置
                * 完全自己手动配置，SQL 可能比较复杂，比如需要关联多表，字段查询结果需要转换 ……
                * SQL 编写时提供了一个 string 型时间参数，使用时可以
                以 `$DATE$` 或 `$date$` 的形式代替，如：
                `select * from tb where DATA(create_time) = $DATE$ `，今天（2019-01-21），
                则执行的 sql 为 `select * from tb where DATA(create_time) = '2019-01-20'` 。
                即 '$DATE' 或 `$date$` 会被当前日期的前一天替换。该参数非必选。

                ![](docs/img/1548326156.jpg)

            * 测试配置结果(调整前与调整后)

                ![](docs/img/1548326156(1).jpg)

                ![](docs/img/1548326156(2).jpg)
    * Excel 配置

        ![](docs/img/1548382123(1).jpg)

    * 邮件任务配置

        ![](docs/img/1548326156(3).jpg)

    * 收件人配置

        ![](docs/img/1548396829(1).jpg)

    * 发件人配置

        ![](docs/img/1548396682(1).jpg)

    * 个性化主页配置

        ![](docs/img/1548322792(1).jpg)


#### 欢迎参与贡献

1. 提出宝贵建议与指导
2. Fork 本仓库
3. 点 ☆
4. 点评

