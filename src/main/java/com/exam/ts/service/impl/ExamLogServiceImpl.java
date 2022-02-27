package com.exam.ts.service.impl;
import java.math.BigDecimal;
import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.exam.core.constant.CoreConstant;
import com.exam.core.constant.PaperEnum;
import com.exam.core.constant.ResultEnum;
import com.exam.core.constant.SelectEnum;
import com.exam.core.exception.ExamException;
import com.exam.core.pojo.Page;
import com.exam.core.utils.IdWorker;
import com.exam.core.utils.ShiroUtils;
import com.exam.ex.mapper.PaperMapper;
import com.exam.ex.pojo.BankDO;
import com.exam.ex.pojo.PaperDO;
import com.exam.ex.pojo.TeacherDO;
import com.exam.ts.mapper.ExamMapper;
import com.exam.ts.mapper.RoomMapper;
import com.exam.ts.mapper.StudentPaperMapper;
import com.exam.ts.pojo.ExamDO;
import com.exam.ts.pojo.ExamLogDO;
import com.exam.ts.mapper.ExamLogMapper;
import com.exam.ts.pojo.PO.LogPO;
import com.exam.ts.pojo.RoomDO;
import com.exam.ts.pojo.StudentPaperDO;
import com.exam.ts.service.ExamLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 考试日志表 服务实现类
 * </p>
 *
 * @author 杨德石
 * @since 2019-05-24
 */
@Service
public class ExamLogServiceImpl extends ServiceImpl<ExamLogMapper, ExamLogDO> implements ExamLogService {

    @Autowired
    private ExamLogMapper examLogMapper;
    @Autowired
    private ExamMapper examMapper;
    @Autowired
    private RoomMapper roomMapper;
    @Autowired
    private StudentPaperMapper studentPaperMapper;
    @Autowired
    private PaperMapper paperMapper;
    @Autowired
    private IdWorker idWorker;

    @Override
    public void addExamLog(String examId) throws ExamException {
        // 先查看是否都统计完成
        QueryWrapper<StudentPaperDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("paper_exam",examId);
        // 1 正常
        queryWrapper.eq("paper_delete",1);
        queryWrapper.ne("paper_flag", PaperEnum.FINISH.getCode());
        Integer count = studentPaperMapper.selectCount(queryWrapper);
        if(count == 0){
            throw new ExamException(ResultEnum.NO_FINISH);
        }

        ExamLogDO examLogDO = new ExamLogDO();
        ExamDO examDO = examMapper.selectById(examId);
        RoomDO roomDO = roomMapper.selectById(examDO.getExamRoom());
        PaperDO paperDO = paperMapper.selectById(examDO.getExamPaper());
        LogPO logPO = studentPaperMapper.countOfPaper(examDO.getExamId());
        examLogDO.setLogId(idWorker + "");
        examLogDO.setLogDate(examDO.getExamDate());
        examLogDO.setLogTime(examDO.getExamTime());
        examLogDO.setLogRoom(roomDO.getRoomName());
        examLogDO.setLogStudentNum(logPO.getStuNum());
        examLogDO.setLogPaperTitle(paperDO.getPaperTitle());
        examLogDO.setLogPass(new BigDecimal(logPO.getStuNum()));
        examLogDO.setLogCreateBy(ShiroUtils.getLoginTeacher().getTeacherId());
        examLogDO.setLogVersion(0);
        examLogDO.setLogDelete(1);

        examLogMapper.insert(examLogDO);

    }

    @Override
    public Page<ExamLogDO> getListByPage(Page<ExamLogDO> page) {
        // 处理参数
        page.filterParams();
        // 处理角色
        TeacherDO loginTeacher = ShiroUtils.getLoginTeacher();
        if(!SelectEnum.SELECT_ALL.getCode().equals(loginTeacher.getTeacherOrg())) {
            // 不是查询所有，就只查询自己学院的
            page.getParams().put("orgCollege", loginTeacher.getTeacherCollege());
        }
        // 设置每页显示条数
        if (page.getCurrentCount() == null) {
            page.setCurrentCount(CoreConstant.CURRENT_COUNT);
        }
        // 计算索引
        Integer index = (page.getCurrentPage() - 1) * page.getCurrentCount();
        page.setIndex(index);
        // 查询每页数据
        List<ExamLogDO> list = examLogMapper.getListByPage(page);
        page.setList(list);
        Integer totalCount = examLogMapper.getCountByPage(page);
        page.setTotalCount(totalCount);
        // 计算总页数
        page.setTotalPage((int) Math.ceil((page.getTotalCount() * 1.0) / page.getCurrentCount()));
        return page;
    }
}
