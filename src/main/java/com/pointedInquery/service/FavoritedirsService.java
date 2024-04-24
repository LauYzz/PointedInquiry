package com.pointedInquery.service;

import com.pointedInquery.dto.ExpertDetailedDto;
import com.pointedInquery.entity.Favoritedirs;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 */
public interface FavoritedirsService extends IService<Favoritedirs> {
    List<ExpertDetailedDto> GetDirsByUserid(String phone);

    boolean DeleteDirsByUserid(String phone, String expert_id);

    boolean CreateDirsByUserid(String phone, String expert_id);

}
