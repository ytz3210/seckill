package cn.yang.interceptor;


import cn.yang.common.utils.UUID;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.*;
import java.util.regex.Matcher;

/**
 * mybatis拦截器，自动注入创建人、创建时间、修改人、修改时间
 * @Author scott
 * @Date  2019-01-19
 *
 */
@Slf4j
@Component
@Intercepts({ @Signature(type = Executor.class, method = "query",
        args = { MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class }),
              @Signature(type = Executor.class,method = "update",
               args={MappedStatement.class, Object.class})})
public class MybatisInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if(invocation.getArgs().length > 1){
            parameter = invocation.getArgs()[1];
            log.error("parameter:{}",parameter);
        }
        String sqlId = mappedStatement.getId();
        log.error("sqlId:{}",sqlId);
        //注解中method的值
        String methodName = invocation.getMethod().getName();
        //sql类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if ("update".equals(methodName)) {
            Object object = invocation.getArgs()[1];
            Long currentDate = System.currentTimeMillis();
            //对有要求的字段填值
            if (SqlCommandType.INSERT.equals(sqlCommandType)) {
                Field fieldId = object.getClass().getSuperclass().getDeclaredField("id");
                fieldId.setAccessible(true);
                String id = UUID.getUUID();
                fieldId.set(object, id);
                log.error("插入操作前设置id:{}", id);
                Field fieldCreateTime = object.getClass().getSuperclass().getDeclaredField("createTime");
                fieldCreateTime.setAccessible(true);
                fieldCreateTime.set(object, currentDate);
                log.error("插入操作时设置create_time:{}", currentDate);
                Field fieldIsDeleted = object.getClass().getSuperclass().getDeclaredField("deleted");
                fieldIsDeleted.setAccessible(true);
                fieldIsDeleted.set(object,false);
                log.error("插入操作时设置is_deleted:{}", false);
                Field fieldUpdateTime = object.getClass().getSuperclass().getDeclaredField("updateTime");
                fieldUpdateTime.setAccessible(true);
                fieldUpdateTime.set(object,currentDate);
                log.error("插入操作时设置update_time:{}", currentDate);
                Field fieldVersion = object.getClass().getSuperclass().getDeclaredField("version");
                fieldVersion.setAccessible(true);
                fieldVersion.set(object,1);
                log.error("插入操作时设置version:{}", 1);
            } else if (SqlCommandType.UPDATE.equals(sqlCommandType)) {
                Field fieldModifyTime = object.getClass().getSuperclass().getDeclaredField("updateTime");
                fieldModifyTime.setAccessible(true);
                fieldModifyTime.set(object, currentDate);
                log.info("更新操作时设置update_time:{}", currentDate);
            }
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            Configuration configuration = mappedStatement.getConfiguration();
            String sql = getSql(configuration, boundSql, sqlId); // 获取到最终的sql语句
            log.error("sql语句:{}",sql);
        }
        return invocation.proceed();
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub
    }
    // 封装了一下sql语句，使得结果返回完整xml路径下的sql语句节点id + sql语句
    private static String getSql(Configuration configuration, BoundSql boundSql, String sqlId)
    {
        String sql = showSql(configuration, boundSql);
        StringBuilder str = new StringBuilder(100);
        str.append(sqlId);
        str.append(":");
        str.append(sql);
        return str.toString();
    }

    // 如果参数是String，则添加单引号， 如果是日期，则转换为时间格式器并加单引号； 对参数是null和不是null的情况作了处理
    private static String getParameterValue(Object obj)
    {
        String value = null;
        if (obj instanceof String)
        {
            value = "'" + obj.toString() + "'";
        }
        else if (obj instanceof Date)
        {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT,
                    DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(new Date()) + "'";
        }
        else
        {
            if (obj != null)
            {
                value = obj.toString();
            }
            else
            {
                value = "";
            }

        }
        return value;
    }

    // 进行？的替换
    private static String showSql(Configuration configuration, BoundSql boundSql)
    {
        // 获取参数
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        // sql语句中多个空格都用一个空格代替
        String sql = boundSql.getSql().replaceAll("[\\s]+", " ");
        if (CollectionUtils.isNotEmpty(parameterMappings) && parameterObject != null)
        {
            // 获取类型处理器注册器，类型处理器的功能是进行java类型和数据库类型的转换
            TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
            // 如果根据parameterObject.getClass(）可以找到对应的类型，则替换
            if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass()))
            {
                sql = sql.replaceFirst("\\?",
                        Matcher.quoteReplacement(getParameterValue(parameterObject)));

            }
            else
            {
                // MetaObject主要是封装了originalObject对象，提供了get和set的方法用于获取和设置originalObject的属性值,主要支持对JavaBean、Collection、Map三种类型对象的操作
                MetaObject metaObject = configuration.newMetaObject(parameterObject);
                for (ParameterMapping parameterMapping : parameterMappings)
                {
                    String propertyName = parameterMapping.getProperty();
                    if (metaObject.hasGetter(propertyName))
                    {
                        Object obj = metaObject.getValue(propertyName);
                        sql = sql.replaceFirst("\\?",
                                Matcher.quoteReplacement(getParameterValue(obj)));
                    }
                    else if (boundSql.hasAdditionalParameter(propertyName))
                    {
                        // 该分支是动态sql
                        Object obj = boundSql.getAdditionalParameter(propertyName);
                        sql = sql.replaceFirst("\\?",
                                Matcher.quoteReplacement(getParameterValue(obj)));
                    }
                    else
                    {
                        // 打印出缺失，提醒该参数缺失并防止错位
                        sql = sql.replaceFirst("\\?", "缺失");
                    }
                }
            }
        }
        return sql;
    }

}
