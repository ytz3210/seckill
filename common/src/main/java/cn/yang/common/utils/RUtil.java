package cn.yang.common.utils;


import cn.yang.common.source.REnum;
import cn.yang.common.source.ResTO;

/**
 * @Description: 对象模型工具类
 */
@SuppressWarnings("Duplicates")
public class RUtil {

    /**
     * @Description 不带数据的成功返回
     **/
    public static ResTO success() {
        return success(null);
    }

    /**
     * @param object
     * @Description 带数据的成功返回
     **/
    public static ResTO success(Object object) {
        ResTO resTO = new ResTO();
        resTO.setCode(REnum.SUCCESS.getCode());
        resTO.setMessage(null);
        resTO.setData(object);
        return resTO;
    }

    /**
     * @param object
     * @Description 分页的成功返回
     **/
//    public static ResTO successByPage(Object object) {
//        ResTO resTO = new ResTO();
//        resTO.setCode(REnum.SUCCESS.getCode());
//        resTO.setMessage(null);
//
//        Page p = (Page) object;
//        PageTO pageTO = new PageTO();
//        pageTO.setData(p.getContent());
//        pageTO.setPageNo(p.getNumber());
//        pageTO.setPageSize(p.getSize());
//        pageTO.setTotalCount(p.getTotalElements());
//        pageTO.setTotalPage(p.getTotalPages());
//        resTO.setData(pageTO);
//        return resTO;
//    }

    /**
     * @param rEnum
     * @Description 错误提示
     **/
    public static ResTO error(REnum rEnum) {
        ResTO resTO = new ResTO();
        resTO.setCode(rEnum.getCode());
        resTO.setMessage(rEnum.getMessage());
        resTO.setData(null);
        return resTO;
    }

    /**
     * @return Result
     * @Description: 错误类型
     * @Param type:错误类型
     **/
    public static ResTO errorByType(String type) {

        REnum rEnum = iterationFindByName(type);
        ResTO resTO = new ResTO();
        if (rEnum == null) {
            rEnum = REnum.valueOf("OTHER_EXCEPTION");
        }
        resTO.setCode(rEnum.getCode());
        resTO.setMessage(rEnum.getMessage());
        return resTO;
    }

    /**
     * @return Result
     * @Description: 错误类型
     * @Param type:错误类型
     * @Author ZhangJun
     * @Date 2019年7月16日 14:19
     **/
    public static ResTO errorByCode(Integer code) {

        REnum rEnum = iterationFindByCode(code);
        ResTO resTO = new ResTO();
        if (rEnum == null) {
            rEnum = REnum.valueOf("OTHER_EXCEPTION");
        }
        resTO.setCode(rEnum.getCode());
        resTO.setMessage(rEnum.getMessage());
        return resTO;
    }

    /**
     * @return
     * @Description: 根据名称迭代枚举类型
     * @Param
     **/
    public static REnum iterationFindByName(String name) {
        for (REnum rEnum : REnum.values()) {
            if (name.equals(rEnum.name())) {
                return rEnum;
            }
        }
        return null;
    }

    /**
     * @return
     * @Description: 根据名称迭代枚举类型
     * @Param
     **/
    public static REnum iterationFindByCode(Integer code) {
        for (REnum rEnum : REnum.values()) {
            if (rEnum.getCode().equals(code)) {
                return rEnum;
            }
        }
        return null;
    }

}
