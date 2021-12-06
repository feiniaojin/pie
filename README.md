# pie

## 1. pie简介

责任链模式是很常用的一种设计模式，包括javax.servlet.Filter、SpringMVC、Netty都有实现。

pie是一个可快速上手的责任链框架，其抽象了handler链的拼装维护，使用者只需要专注业务，开发相应的handler即可。

pie的核心代码抽取自netty，90%的api与netty是一致的。

![](https://s3.ax1x.com/2021/02/19/yf0KL8.jpg)

## 2. 快速入门

## 2.1 导入maven依赖包

```xml
<dependency> 
    <groupId>com.feiniaojin</groupId>
    <artifactId>pie</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## 2.2 实现出参工厂

实现OutboundFactory接口，用于产生接口返回值，例如：

```java
public class OutFactoryImpl implements OutboundFactory {
    @Override
    public Object newInstance() {
        return new Result();
    }
}
```

## 2.3 实现handler接口

FirstHandler：

```java
public class FirstHandler implements ChannelHandler {

    @Override
    public void channelProcess(ChannelHandlerContext ctx,
                               Object in,
                               Object out) throws Exception {
        System.out.println("first:11111");
        ((UserDto) in).setName("first:change");
        Result re = (Result) out;
        re.setCode(100);
        re.setMsg("success");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause,
                                Object in,
                                Object out) throws Exception {
        System.out.println("+++++");
        Result re = (Result) out;
        re.setCode(404);
        re.setMsg("异常");
        ctx.fireExceptionCaught(cause, in, out);
    }
}
```

SecondHandler：

```java
public class SecondHandler implements ChannelHandler {
    @Override
    public void channelProcess(ChannelHandlerContext ctx,
                               Object in,
                               Object out) throws Exception {
        System.out.println(((UserDto) in).getName());
        System.out.println("second:22222");
        Result re = (Result) out;
        re.setCode(200);
        re.setMsg("success");
        ctx.fireChannelProcess(in, out);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause,
                                Object in,
                                Object out) throws Exception {
        Result re = (Result) out;
        System.out.println(re.getCode());
        System.out.println(re.getMsg());
        re.setCode(500);
        re.setMsg("500异常");
    }
}
```

## 2.4 拼装并执行 

```java
BootStrap bootStrap = new BootStrap();
Result result = (Result) bootStrap
        .inboundParameter(dto)//请求入参
        .outboundFactory(new OutFactoryImpl())//出参工厂
        .channel(new UserCheckChannel())//自定义channel
        .addChannelHandlerAtLast("first", new FirstHandler())//第一个handler
        .addChannelHandlerAtLast("second", new SecondHandler())//第二个handler
        .process();//执行
//result为执行结果
System.out.println(result.getCode());
System.out.println(result.getMsg());
```

具体代码见PieTest类。