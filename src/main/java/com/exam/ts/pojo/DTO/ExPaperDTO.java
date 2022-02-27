package com.exam.ts.pojo.DTO;

import com.exam.ex.dto.GaConfigDTO;
import lombok.Data;

import java.util.List;

/**
 * 考试智能组卷
 * @author lth
 * @version 1.0.0
 * @date
 */

@Data
public class ExPaperDTO {

    private List<String> stuId;

    /**
     * 试卷id
     */
    private String paperId;

    /**
     * 考试id（在线考试组卷时使用）
     */
    private String examId;

    /**
     * 用户选择多个配置
     */
    private List<GaConfigDTO> configList;

}
