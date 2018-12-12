package cn.rf.loan.server.util;

/**
 *
 *
 * @author yuzaizhou
 * @version 2018-12-03
 */
public enum HttpCode {
    /** 200请求成功 */
    OK(200),

    /**
     *  1-200 公共code
     *
     **/

     /**信息获取失败**/
    GETINFO_FAIL(1),
    /**参数错误**/
    ERROR_PARAM(2),
    /**服务器出错**/
    INTERNAL_SERVER_ERROR(3),
    /**保存失败**/
    SAVE_FAIL(5),
    /**删除失败**/
    DELE_FAIL(6),
    /**JSON解析失败**/
    JSON_PARSE_FAIL(7),
    /**测试异常***/
    TEST_ERROR(8),
    ;

    private final Integer value;

    private HttpCode(Integer value) {
        this.value = value;
    }

    /**
     * Return the integer value of this status code.
     */
    public Integer value() {
        return this.value;
    }

    public String msg() {
        return null;
    }

    public String toString() {
        return this.value.toString();
    }
}

