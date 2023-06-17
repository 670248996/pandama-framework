package com.pandama.top.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pandama.top.user.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @description: 角色信息Mapper接口
 * @author: 白剑民
 * @dateTime: 2022/10/21 15:55
 */
@Repository
public interface RoleMapper extends BaseMapper<SysRole> {

    /**
     * @param userId 用户id
     * @description: 根据应用户id获取其所有角色列表
     * @author: 白剑民
     * @date: 2022-10-31 13:27:32
     * @return: java.util.List<com.gientech.iot.user.entity.Role>
     * @version: 1.0
     */
    List<SysRole> getRoleListByUserId(@Param("userId") Long userId);

}