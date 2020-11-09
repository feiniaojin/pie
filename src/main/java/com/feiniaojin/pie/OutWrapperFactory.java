package com.feiniaojin.pie;

/**
 * 出参包装工厂
 *
 * @author: <a href=mailto:qinyujie3@jd.com>qinyujie3</a>
 */
public interface OutWrapperFactory<OUT> {
    OutWrapper<OUT> newInstance(OUT out);
}
