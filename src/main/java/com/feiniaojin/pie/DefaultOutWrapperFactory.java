package com.feiniaojin.pie;

/**
 * 出参包装器工厂
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
 */
public class DefaultOutWrapperFactory<OUT> implements OutWrapperFactory<OUT> {

    @Override
    public OutWrapper<OUT> newInstance(OUT out) {
        return new OutWrapper(out);
    }
}
