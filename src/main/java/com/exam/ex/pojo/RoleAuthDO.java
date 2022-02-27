package com.exam.ex.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 角色-权限表
 * </p>
 *
 * @author 杨德石
 * @since 2019-04-01
 */
@TableName("ex_role_auth")
@Data
public class RoleAuthDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "ra_id", type = IdType.INPUT)
    private String raId;

    /**
     * 角色id
     */
    private String raRole;

    /**
     * 权限id
     */
    private String raAuth;

    @Override
    public String toString() {
        return "RoleAuthDO{" +
        "raId=" + raId +
        ", raRole=" + raRole +
        ", raAuth=" + raAuth +
        "}";
    }
}
