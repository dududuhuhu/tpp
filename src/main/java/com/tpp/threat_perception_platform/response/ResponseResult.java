package com.tpp.threat_perception_platform.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * 通用响应结果封装类（适配 layui 表格分页格式）
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T> {

    /**
     * 状态码：0 = 成功，其他为失败
     */
    private Integer code = 0;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 总记录数（用于分页）
     */
    private Long count;

    /**
     * 数据内容
     */
    private T data;

    // 常用构造方法们

    public ResponseResult() {
        this.code = 0;
        this.msg = "";
    }

    public ResponseResult(Long count, T data) {
        this.code = 0;
        this.msg = "";
        this.count = count;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
}
