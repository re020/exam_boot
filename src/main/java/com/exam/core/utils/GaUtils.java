package com.exam.core.utils;

import com.exam.ex.dto.GaConfigDTO;
import com.exam.ex.ga.GaService;
import com.exam.ex.pojo.PaperConfigDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 遗传算法工具类
 *
 * @Author: 杨德石
 * @Date: 2019/5/3 0003 下午 9:16
 * @Version 1.0
 */
@Component
public class GaUtils {

    private static IdWorker idWorker;
    private static GaService gaService;

    public static void setConfigId(PaperConfigDO paperConfigDO) {
        paperConfigDO.setConfigId(idWorker.nextId() + "");
    }

    public static List getGaList(GaConfigDTO configDTO) {
        return gaService.getGaList(configDTO);
    }

    /**
     * 获取变异列表
     * @param question
     * @param configDTO
     * @return
     */
    public static List getMutateList(Object question, GaConfigDTO configDTO) {
        return gaService.getMutateList(question, configDTO);
    }

    @Autowired
    public void setIdWorker(IdWorker idWorker) {
        GaUtils.idWorker = idWorker;
    }

    @Autowired
    public void setGaService(GaService gaService) {
        GaUtils.gaService = gaService;
    }
}
