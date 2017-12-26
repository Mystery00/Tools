/*
 * Created by Mystery0 on 17-12-26 下午11:29.
 * Copyright (c) 2017. All Rights reserved.
 *
 * Last modified 17-10-12 下午12:39
 */

package com.mystery0.toolsdemo;

/**
 * Created by myste.
 */

public class Hitokoto {
    private String content;
    private String source;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Hitokoto{" +
                "content='" + content + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
