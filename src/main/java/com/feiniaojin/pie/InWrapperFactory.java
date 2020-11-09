package com.feiniaojin.pie;

/**
 * 入参包装工厂
 *
 * @author: <a href=mailto:qinyujie3@jd.com>qinyujie3</a>
 */
public interface InWrapperFactory<IN> {
    InWrapper<IN> newInstance(IN in);
}
