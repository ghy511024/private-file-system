package com.bj58.ctf2021.privatefilesystem.controller.entity.response;

/**
 * 简单返回结果
 *
 * @author me
 * @date 2021/4/1
 */
public class PlainResult<T> extends BaseResult {
    private static final long serialVersionUID = -2942030052302474151L;

    /**
     * 调用返回的数据
     */
    private T data;

    /**
     * @return the data
     */
    public T getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(T data) {
        this.data = data;
    }
}
