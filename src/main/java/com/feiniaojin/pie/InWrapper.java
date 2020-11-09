package com.feiniaojin.pie;

/**
 * 入参包装
 *
 * @author: <a href=mailto:qinyujie3@jd.com>qinyujie3</a>
 */
public class InWrapper<IN> {
    private IN in;

    public InWrapper() {
    }

    public InWrapper(IN in) {
        this.in = in;
    }

    public IN getIn() {
        return in;
    }

    public void setIn(IN in) {
        this.in = in;
    }
}
