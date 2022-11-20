package com.gdupt.service;

import cn.hutool.json.JSONObject;
import com.gdupt.entity.Holiday;
import com.gdupt.entity.User;


import com.gdupt.util.ApiResultUtils;
import com.gdupt.util.ApiResults;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xuhuaping
 * @date 2022/11/7
 */
@Service
public class ActivitiService {

    private static final String PROCESS_DEFINE_KEY = "holiday";

    @Autowired
    RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;


    /**
     * 提交申请表
     * @param data
     * @param user
     * @return
     */
    public ApiResults submitApplication(JSONObject data,User user) {
        Holiday holiday = data.toBean(Holiday.class);
        startProcess(holiday,user);
        return ApiResultUtils.getSuccess("");
    }



    /**
     * 完成任务
     * @param taskId
     */
    public void completeTask(String taskId){
        taskService.complete(taskId);
    }


    /**
     * 查询申请
     * @param data
     * @return
     */
    public ApiResults queryApplications(JSONObject data) {
        return null;
    }

    /**
     * 取消申请
     * @param key
     * @return
     */
    public ApiResults cancelApplication(String key) {
        return null;
    }

    /**
     * 查看申请进度
     * @param key
     * @return
     */
    public ApiResults checkApplicationProgress(String key) {
        return null;
    }


    /**
     * 开始任务
     * @param holiday
     * @param user
     */
    public void startProcess(Holiday holiday, User user){
        Map<String, Object> holidayMap = new HashMap<>(2);
        holidayMap.put("holiday",holiday);
        holidayMap.put("name",user.getUserId().toString());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINE_KEY,holidayMap);
        Task task =
                taskService.createTaskQuery().processInstanceId(processInstance.getId()).taskAssignee(user.getUsername()).singleResult();
        if (task != null){
            taskService.complete(task.getId());
        }
    }

    /**
     * 认领任务
     */
    public void claimTask(User user){
        List<Task> groupTaskList = findGroupTaskList(user);
        Task task = groupTaskList.stream().findFirst().orElse(null);
        if(task!=null){
            //拾取任务
            taskService.claim(task.getId(), user.getUserId().toString());
            //完成任务
            completeTask(task.getId());
        }
    }

    /**
     * 查询组任务
     * 根据候选人查询组任务
     */
    public List<Task> findGroupTaskList(User user) {
        //查询组任务
        List<Task> taskList = taskService.createTaskQuery()
                .processDefinitionKey(PROCESS_DEFINE_KEY)
                .taskCandidateUser(user.getUsername())
                .list();
        return taskList;
    }



}
