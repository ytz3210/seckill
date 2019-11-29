package cn.yang.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@Data
public abstract class BaseEntity implements Serializable {
    @Column(name = "id",type = MySqlTypeConstant.VARCHAR,isKey = true,isUnique = true,length = 50)
    private String id;

    @Column(name = "is_deleted",type = MySqlTypeConstant.INT,length = 1)
    private boolean deleted;

    @Column(name = "version",type = MySqlTypeConstant.DOUBLE)
    private long version;

    @Column(name = "create_time",type = MySqlTypeConstant.DATETIME)
    private long createTime;

    @Column(name = "update_time",type = MySqlTypeConstant.DATETIME)
    private long updateTime;
}
