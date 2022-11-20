package com.gdupt.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.gdupt.entity.Right;
import com.gdupt.entity.User;
import com.gdupt.entity.UserRole;
import com.gdupt.mapper.RightMapper;
import com.gdupt.mapper.UserRoleMapper;
import com.gdupt.util.ApiResultUtils;
import com.gdupt.util.ApiResults;
import com.gdupt.util.TreeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xuhuaping
 * @date 2022/9/20
 */
@Service
public class RightService {


    @Autowired
    private RightMapper rightMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 查询用户权限
     * @param currentUser
     * @return
     */
    public ApiResults getAllRights(User currentUser) {
        Integer userId = currentUser.getUserId();
        LambdaQueryWrapper<UserRole> userRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userRoleLambdaQueryWrapper.eq(UserRole::getUserId,userId);
        List<UserRole> userRoles = userRoleMapper.selectList(userRoleLambdaQueryWrapper);
        List<Integer> roleIds = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        List<Right> rights = rightMapper.selectBatchIds(roleIds);
        List<Tree<String>> trees = TreeUtils.buildTree(rights);
        return ApiResultUtils.getSuccess("",trees);
    }
}
