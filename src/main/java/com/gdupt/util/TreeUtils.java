package com.gdupt.util;


import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNodeConfig;
import cn.hutool.core.lang.tree.TreeUtil;
import com.gdupt.entity.Right;

import java.util.List;

/**
 * @author xuhuaping
 * @date 2022/9/14
 * 树形数据工具类
 */
public class TreeUtils {

    /**
     * 构建权限树
     * @param rights
     * @return
     */
    public static List<Tree<String>> buildTree(List<Right>rights){
        TreeNodeConfig treeNodeConfig = new TreeNodeConfig();
        treeNodeConfig.setIdKey("rightId");
        treeNodeConfig.setParentIdKey("parentId");
        treeNodeConfig.setNameKey("rightName");
        treeNodeConfig.setWeightKey("order");
        treeNodeConfig.setChildrenKey("children");

        List<Tree<String>> treeNodes = TreeUtil.build(rights, "0", treeNodeConfig,
                (treeNode, tree) -> {
                    tree.setId(treeNode.getRightId().toString());
                    tree.setName(treeNode.getRightName());
                    tree.setWeight(treeNode.getOrder().toString());
                    tree.setParentId(treeNode.getParentId().toString());

                    tree.putExtra("routerName",treeNode.getRouterName());
                    tree.putExtra("path",treeNode.getPath());
                    tree.putExtra("component",treeNode.getComponent());
                    tree.putExtra("rightCode",treeNode.getRightCode());
                    tree.putExtra("icon",treeNode.getIcon()==null?"":treeNode.getIcon());
                    tree.putExtra("title",treeNode.getTitle()==null?"":treeNode.getTitle());

                });
        return treeNodes;
    }


}
