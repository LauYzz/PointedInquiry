package com.pointedInquery.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pointedInquery.entity.User;

/**
 * <p>
 * 服务类
 * </p>
 */
public interface UserService extends IService<User> {
    int changePasswd(String newPasswd, String userId);

    boolean IsExpert(String userId);

    boolean beExpert(String userId);

    int checkCollectDir(String userId, String expertId);
}
