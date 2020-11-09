package com.feiniaojin.pie;

/**
 * 出参工厂
 *
 * @author: <a href=mailto:qinyujie3@jd.com>qinyujie3</a>
 */
public interface OutFactory<OUT> {
    OUT newInstance();
}
