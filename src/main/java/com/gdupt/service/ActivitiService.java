package com.gdupt.service;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gdupt.entity.Dept;
import com.gdupt.entity.Holiday;
import com.gdupt.entity.User;


import com.gdupt.entity.UserRole;
import com.gdupt.enums.RoleEnum;
import com.gdupt.mapper.DeptMapper;
import com.gdupt.mapper.UserMapper;
import com.gdupt.mapper.UserRoleMapper;
import com.gdupt.util.ApiResultUtils;
import com.gdupt.util.ApiResults;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;


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
        Integer deptId = user.getDeptId();
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getDeptId,deptId);
        List<User> userList = userMapper.selectList(userLambdaQueryWrapper);
        List<Integer> userIdList = userList.stream().map(User::getUserId).collect(Collectors.toList());
        List<Integer> mangerIdList = getRoleIdList(RoleEnum.MANAGER.getRoleId(),userIdList);


        List<User> allUserList = userMapper.selectList(null);
        List<Integer> allUserIdList = allUserList.stream().map(User::getUserId).collect(Collectors.toList());
        List<Integer> adminIdList = getRoleIdList(RoleEnum.ADMIN.getRoleId(),allUserIdList);



        Map<String, Object> holidayMap = new HashMap<>(2);
        holidayMap.put("holiday",holiday);
        holidayMap.put("name",user.getUserId().toString());
        holidayMap.put("manger", mangerIdList);
        holidayMap.put("admin",adminIdList);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_DEFINE_KEY,holidayMap);
        Task task = taskService.createTaskQuery()
                        .processInstanceId(processInstance.getId())
                        .taskAssignee(user.getUsername()).singleResult();
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

    /**
     * 获取角色 ID 列表
     * @param roleId
     * @param idList
     * @return
     */
    public List<Integer> getRoleIdList(Integer roleId,List<Integer>idList){
        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(UserRole::getRoleId, roleId)
                .in(UserRole::getUserId,idList);
        List<UserRole> roleList = userRoleMapper.selectList(userRoleLambdaQueryWrapper);
        List<Integer> roleIdList = roleList.stream().map(UserRole::getUserId).collect(Collectors.toList());
        return roleIdList;
    }


}
