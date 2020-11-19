package com.feiniaojin.pie;

/**
 * 入参包装工厂
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
 */
public interface InWrapperFactory<IN> {
    InWrapper<IN> newInstance(IN in);
}
