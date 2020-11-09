package com.feiniaojin.pie;

/**
 * 入参包装
 *
 * @author: <a href=mailto:qinyujie3@jd.com>qinyujie3</a>
 */
public class DefaultInWrapperFactory<IN> implements InWrapperFactory<IN> {

    @Override
    public InWrapper<IN> newInstance(IN in) {
        return new InWrapper(in);
    }
}
