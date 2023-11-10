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
     * 获取角色列表通过用户id
     *
     * @param userId 用户id
     * @return java.util.List<com.pandama.top.user.entity.SysRole>
     * @author 王强
     */
    List<SysRole> getRoleListByUserId(@Param("userId") Long userId);

}
