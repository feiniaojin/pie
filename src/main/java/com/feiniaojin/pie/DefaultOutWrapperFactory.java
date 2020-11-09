package com.feiniaojin.pie;

/**
 * 出参包装器工厂
 *
 * @author: <a href=mailto:qinyujie3@jd.com>qinyujie3</a>
 */
public class DefaultOutWrapperFactory<OUT> implements OutWrapperFactory<OUT> {

    @Override
    public OutWrapper<OUT> newInstance(OUT out) {
        return new OutWrapper(out);
    }
}
