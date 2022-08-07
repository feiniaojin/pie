[![vuzZ26.jpg](https://s1.ax1x.com/2022/08/07/vuzZ26.jpg)](https://imgtu.com/i/vuzZ26)
[![GitHub license](https://img.shields.io/github/license/feiniaojin/pie)](https://github.com/feiniaojin/pie/blob/main/LICENSE)
[![GitHub stars](https://img.shields.io/github/stars/feiniaojin/pie)](https://github.com/feiniaojin/pie/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/feiniaojin/pie)](https://github.com/feiniaojin/pie/network)
[![GitHub issues](https://img.shields.io/github/issues/feiniaojin/pie)](https://github.com/feiniaojin/pie/issues)
![Maven Central](https://img.shields.io/maven-central/v/com.feiniaojin.ddd.ecosystem/pie)
# 1. pie简介

责任链模式是开发过程中常用的一种设计模式，在SpringMVC、Netty等许多框架中都有实现。

我们日常的开发中要使用责任链模式，通常需要自己来实现。但自己临时实现的责任链既不通用，也很容易产生框架与业务代码耦合不清的问题，增加Code Review的成本。

Netty的代码向来以优雅著称，早年我在阅读Netty的源码时，萌生出将其责任链的实现，应用到业务开发中的想法。之后花了点时间将Netty中责任链的实现代码抽取出来，形成了本项目，也就是`pie`。

pie的核心代码均来自Netty，绝大部分的API与Netty是一致的。

pie是一个可快速上手的责任链框架，开发者只需要专注业务，开发相应的handler，即可完成业务的责任链落地。

一分钟学会、三分钟上手、五分钟应用，欢迎star。

pie源码地址：https://github.com/feiniaojin/pie.git

pie案例工程源码地址：https://github.com/feiniaojin/pie-example.git

![yf0KL8.jpg](https://s3.ax1x.com/2021/02/19/yf0KL8.jpg)

# 2. 快速入门

## 2.1 引入maven依赖

pie目前已打包发布到maven中央仓库，开发者可以直接通过maven坐标将其引入到项目中。

```xml
<dependency> 
    <groupId>com.feiniaojin.ddd.ecosystem</groupId>
    <artifactId>pie</artifactId>
    <version>1.0</version>
</dependency>
```
>目前最新的版本是1.0

## 2.2 实现出参工厂

出参也就是执行结果，一般的执行过程都要求有执行结果返回。实现OutboundFactory接口，用于产生接口默认返回值。

例如：

```java
public class OutFactoryImpl implements OutboundFactory {
    @Override
    public Object newInstance() {
        Result result = new Result();
        result.setCode(0);
        result.setMsg("ok");
        return result;
    }
}
```

## 2.3 实现handler接口完成业务逻辑

在pie案例工程( https://github.com/feiniaojin/pie-example.git )的**Example1**中，为了展示pie的使用方法，实现了一个虚拟的业务逻辑：CMS类项目修改文章标题、正文，大家不要关注修改操作放到两个handler中是否合理。

三个Hadnler功能如下：

**CheckParameterHandler**：用于参数校验。

**ArticleModifyTitleHandler**：用于修改文章的标题。

**ArticleModifyContentHandler**：用于修改文章的正文。

CheckParameterHandler的代码如下：

```java
public class CheckParameterHandler implements ChannelHandler {

    private Logger logger = LoggerFactory.getLogger(CheckParameterHandler.class);

    @Override
    public void channelProcess(ChannelHandlerContext ctx,
                               Object in,
                               Object out) throws Exception {

        logger.info("参数校验:开始执行");

        if (in instanceof ArticleTitleModifyCmd) {
            ArticleTitleModifyCmd cmd = (ArticleTitleModifyCmd) in;
            String articleId = cmd.getArticleId();
            Objects.requireNonNull(articleId, "articleId不能为空");
            String title = cmd.getTitle();
            Objects.requireNonNull(title, "title不能为空");
            String content = cmd.getContent();
            Objects.requireNonNull(content, "content不能为空");
        }
        logger.info("参数校验:校验通过,即将进入下一个Handler");
        ctx.fireChannelProcess(in, out);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause,
                                Object in,
                                Object out) throws Exception {
        logger.error("参数校验:异常处理逻辑", cause);
        Result re = (Result) out;
        re.setCode(400);
        re.setMsg("参数异常");
    }
}
```

ArticleModifyTitleHandler的代码如下：

```java
public class ArticleModifyTitleHandler implements ChannelHandler {

    private Logger logger = LoggerFactory.getLogger(ArticleModifyTitleHandler.class);

    @Override
    public void channelProcess(ChannelHandlerContext ctx,
                               Object in,
                               Object out) throws Exception {

        logger.info("修改标题:进入修改标题的Handler");

        ArticleTitleModifyCmd cmd = (ArticleTitleModifyCmd) in;

        String title = cmd.getTitle();
        //修改标题的业务逻辑
        logger.info("修改标题:title={}", title);

        logger.info("修改标题:执行完成,即将进入下一个Handler");
        ctx.fireChannelProcess(in, out);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause,
                                Object in,
                                Object out) throws Exception {
        logger.error("修改标题:异常处理逻辑");
        Result re = (Result) out;
        re.setCode(1501);
        re.setMsg("修改标题发生异常");
    }
}
```

ArticleModifyContentHandler的代码如下：
```java
public class ArticleModifyContentHandler implements ChannelHandler {

    private Logger logger = LoggerFactory.getLogger(ArticleModifyContentHandler.class);

    @Override
    public void channelProcess(ChannelHandlerContext ctx,
                               Object in,
                               Object out) throws Exception {

        logger.info("修改正文:进入修改正文的Handler");
        ArticleTitleModifyCmd cmd = (ArticleTitleModifyCmd) in;
        logger.info("修改正文,content={}", cmd.getContent());
        logger.info("修改正文:执行完成,即将进入下一个Handler");
        ctx.fireChannelProcess(in, out);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause,
                                Object in,
                                Object out) throws Exception {

        logger.error("修改标题:异常处理逻辑");

        Result re = (Result) out;
        re.setCode(1502);
        re.setMsg("修改正文发生异常");
    }
}

```

## 2.4 通过BootStrap拼装并执行 

```java
public class ArticleModifyExample1 {

    private final static Logger logger = LoggerFactory.getLogger(ArticleModifyExample1.class);

    public static void main(String[] args) {
        //构造入参
        ArticleTitleModifyCmd dto = new ArticleTitleModifyCmd();
        dto.setArticleId("articleId_001");
        dto.setTitle("articleId_001_title");
        dto.setContent("articleId_001_content");
        
        //创建引导类
        BootStrap bootStrap = new BootStrap();

		//拼装
        Result result = (Result) bootStrap
                .inboundParameter(dto)//入参
                .outboundFactory(new ResultFactory())//出参工厂
                .channel(new ArticleModifyChannel())//自定义channel
                .addChannelHandlerAtLast("checkParameter", new CheckParameterHandler())//第一个handler
                .addChannelHandlerAtLast("modifyTitle", new ArticleModifyTitleHandler())//第二个handler
                .addChannelHandlerAtLast("modifyContent", new ArticleModifyContentHandler())//第三个handler
                .process();//执行
        //result为执行结果
        logger.info("result:code={},msg={}", result.getCode(),result.getMsg());
    }
｝
```
## 2.5执行结果

以下是运行ArticleModifyExample1的main方法打出的日志，可以看到我们定义的handler被逐个执行了。

[![vKSLpq.png](https://s1.ax1x.com/2022/08/07/vKSLpq.png)](https://imgtu.com/i/vKSLpq)
