package com.matrix.dao;

import com.matrix.base.interfaces.IBaseDao;
import com.matrix.pojo.dto.McWorkflowTemplateDto;
import com.matrix.pojo.entity.McWorkflowTemplate;
import com.matrix.pojo.view.McWorkflowTemplateView;

public interface IMcWorkflowTemplateMapper extends IBaseDao<Long, McWorkflowTemplate, McWorkflowTemplateDto, McWorkflowTemplateView>{
}