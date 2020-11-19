package com.feiniaojin.pie;

/**
 * 出参包装工厂
 *
 * @author: <a href=mailto:943868899@qq.com>Yujie</a>
 */
public interface OutWrapperFactory<OUT> {
    OutWrapper<OUT> newInstance(OUT out);
}
