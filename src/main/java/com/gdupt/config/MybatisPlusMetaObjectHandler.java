package com.gdupt.config;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.gdupt.annotation.DefaultValue;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author XHP
 * @date 2022/11/20
 * 字段自动填充 配置
 */
@Component
public class MybatisPlusMetaObjectHandler implements MetaObjectHandler {

    /**
     * 自定义插入时自动填充规则
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        setDefaultValue("isDelete", 0, metaObject, FieldFill.INSERT);
        setDefaultValue("deleted", 0, metaObject, FieldFill.INSERT);
        setDefaultValue("createTime", new Timestamp(System.currentTimeMillis()), metaObject, FieldFill.INSERT);
        setDefaultValue("createDate", new Timestamp(System.currentTimeMillis()), metaObject, FieldFill.INSERT);
        setDefaultValue("uploadTime", new Timestamp(System.currentTimeMillis()), metaObject, FieldFill.INSERT);

        setDefaultValue(metaObject, FieldFill.INSERT);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setDefaultValue("updateTime", new Timestamp(System.currentTimeMillis()), metaObject, FieldFill.UPDATE);
        setDefaultValue("lastUpdateTime", new Timestamp(System.currentTimeMillis()), metaObject, FieldFill.UPDATE);
        setDefaultValue("modifyDate", new Timestamp(System.currentTimeMillis()), metaObject, FieldFill.UPDATE);
        setDefaultValue("uploadTime", new Timestamp(System.currentTimeMillis()), metaObject, FieldFill.UPDATE);

        setDefaultValue(metaObject, FieldFill.UPDATE);
    }

    /**
     * 新增插入时，实体对象字段为空时，才填充默认值
     * 更新时，只要配置了@DefaultValue 都默认填充
     *
     * @param fieldName  字段名
     * @param fieldVal   填充值
     * @param metaObject metaObject
     * @param fieldFill  fieldFill
     */
    private void setDefaultValue(String fieldName, Object fieldVal, MetaObject metaObject, FieldFill fieldFill) {
        if (FieldFill.INSERT.equals(fieldFill) && Objects.isNull(this.getFieldValByName(fieldName, metaObject))) {
            //插入填充(属性值为空时)
            this.setInsertFieldValByName(fieldName, fieldVal, metaObject);
        } else if (FieldFill.UPDATE.equals(fieldFill)) {
            //更新填充
            this.setUpdateFieldValByName(fieldName, fieldVal, metaObject);
        }
    }

    /**
     * 获取注解@DefaultValue上配置的默认值
     *
     * @param metaObject metaObject
     * @param fieldFill  fieldFill
     */
    private void setDefaultValue(MetaObject metaObject, FieldFill fieldFill) {
        Field[] fields = new Field[]{};
        //判断是否是entity
        if (metaObject.getOriginalObject().getClass().isAnnotationPresent(TableName.class)) {
            fields = metaObject.getOriginalObject().getClass().getDeclaredFields();
        } else if (metaObject.hasGetter(Constants.ENTITY)) {
            // update操作时，entity 通过“et”获取
            Object entity = metaObject.getValue(Constants.ENTITY);
            if (entity != null && entity.getClass().isAnnotationPresent(TableName.class)) {
                fields = entity.getClass().getDeclaredFields();
            }
        }
        for (Field field : fields) {
            String fieldName = field.getName();
            Object fieldVal = null;
            DefaultValue defaultValue = field.getAnnotation(DefaultValue.class);
            String type = field.getGenericType().getTypeName();
            if (defaultValue != null) {
                switch (type) {
                    case "java.lang.Byte":
                        fieldVal = defaultValue.byteValue();
                        break;
                    case "java.lang.Short":
                        fieldVal = defaultValue.shortValue();
                        break;
                    case "java.lang.Integer":
                        fieldVal = defaultValue.intValue();
                        break;
                    case "java.lang.Long":
                        fieldVal = defaultValue.longValue();
                        break;
                    case "java.lang.Float":
                        fieldVal = defaultValue.floatValue();
                        break;
                    case "java.lang.Double":
                        fieldVal = defaultValue.doubleValue();
                        break;
                    case "java.lang.Character":
                        fieldVal = defaultValue.charValue();
                        break;
                    case "java.lang.Boolean":
                        fieldVal = defaultValue.booleanValue();
                        break;
                    case "java.lang.String":
                        fieldVal = defaultValue.stringValue();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + type);
                }
                setDefaultValue(fieldName, fieldVal, metaObject, fieldFill);
            }
        }
    }
}

