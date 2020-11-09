package com.feiniaojin.pie;

/**
 * 出参包装
 *
 * @author: <a href=mailto:qinyujie3@jd.com>qinyujie3</a>
 */
public class OutWrapper<OUT> {

    private OUT out;

    public OutWrapper() {
    }

    public OutWrapper(OUT out) {
        this.out = out;
    }

    public OUT getOut() {
        return out;
    }

    public void setOut(OUT out) {
        this.out = out;
    }
}
