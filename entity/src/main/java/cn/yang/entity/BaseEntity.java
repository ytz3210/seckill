package cn.yang.entity;

import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {
    protected String id;
    protected boolean deleted;
    protected long version;
    protected long createTime;
    protected long updateTime;
}
