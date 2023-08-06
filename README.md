# Awakening-Insects

> Awakening-Insects单体应用开发框架
>
> 使用Spring Boot3+Mybatis Flex作为主要业务开发
>
> 已集成Spring Security6权限管理，sql文件夹内提供init.sql脚本快速创建简单RBAC模型



## 项目结构说明

| 功能                    | 所在位置                                            |
| ----------------------- | --------------------------------------------------- |
| 请求日志拦截            | com.lansu.awakening.aop.Log4aiAspect                |
| Spring Security相关配置 | com.lansu.awakening.auth                            |
| Mybatis-Flex配置        | com.lansu.awakening.config.MyBatisFlexConfiguration |
| 统一请求返回            | com.lansu.awakening.config.MyBatisFlexConfiguration |
| 统一异常处理            | com.lansu.awakening.exception                       |