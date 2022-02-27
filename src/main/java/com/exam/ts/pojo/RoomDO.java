package com.exam.ts.pojo;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 教室/考场表
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@TableName("te_room")
@Data
public class RoomDO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "room_id", type = IdType.INPUT)
    private String roomId;

    /**
     * 教室名
     */
    private String roomName;

    /**
     * 几栋
     */
    private String roomBuilding;

    /**
     * 状态，1空闲，2占用
     */
    private Integer roomState;

    /**
     * 备注
     */
    private String roomComment;

    /**
     * 乐观锁
     */
    @Version
    private Integer roomVersion;

    /**
     * 0删除1正常
     */
    @TableLogic
    private Integer roomDelete;

    @Override
    public String toString() {
        return "RoomDO{" +
        "roomId=" + roomId +
        ", roomName=" + roomName +
        ", roomBuilding=" + roomBuilding +
        ", roomState=" + roomState +
        ", roomComment=" + roomComment +
        ", roomVersion=" + roomVersion +
        ", roomDelete=" + roomDelete +
        "}";
    }
}
