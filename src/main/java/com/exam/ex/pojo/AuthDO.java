package com.exam.ex.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 权限表
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-01
 */
@TableName("ex_auth")
@Data
public class AuthDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限id
     */
    @TableId(value = "auth_id", type = IdType.INPUT)
    private String authId;

    /**
     * 权限名，用于展示
     */
    private String authName;

    /**
     * 权限代码，用于后台写代码用
     */
    private String authCode;

    /**
     * 父级权限
     */
    private String authFather;

    /**
     * 排序字段
     */
    private Integer authIndex;

    /**
     * 乐观锁
     */
    @Version
    private Integer authVersion;

    /**
     * 1正常0删除
     */
    @TableLogic
    private Integer authDelete;

    @TableField(exist = false)
    private List<AuthDO> list;

    @Override
    public String toString() {
        return "AuthDO{" +
                "authId=" + authId +
                ", authName=" + authName +
                ", authCode=" + authCode +
                ", authFather=" + authFather +
                ", authIndex=" + authIndex +
                ", authVersion=" + authVersion +
                ", authDelete=" + authDelete +
                "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthDO authDO = (AuthDO) o;
        return authId.equals(authDO.authId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authId);
    }
}
