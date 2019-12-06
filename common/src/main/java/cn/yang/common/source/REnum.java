package cn.yang.common.source;

public enum REnum {
    /**
     * 操作成功
     */
    SUCCESS(0,""),

    /**
     * 访问路径不存在
     */
    ERROR_PATH(10002, "访问路径不存在！"),


    /**
     * 数据库中已存在该记录
     */
    DATA_ALREADY_EXISTS(10007, "数据库中已存在该记录！"),

    /**
     * 业务失败
     */
    OPERATION_FAILED(90000, "业务失败！");

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    REnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
