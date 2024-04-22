package com.pointedInquery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pointedInquery.entity.Favoritedirs;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FavoritedirsMapper extends BaseMapper<Favoritedirs> {
    @Select("SELECT * from favoritedirs where phone=#{phone}")
    List<Favoritedirs> selectExpertList(String phone);

    @Select("SELECT count(*) from favoritedirs where phone=#{phone} AND expert_id=#{expert_id}")
    int selectFavoriteDirNum(String phone, String expert_id);

    @Delete("DELETE FROM favoritedirs WHERE phone = #{phone} AND expert_id = #{expert_id}")
    int deleteFavoriteDirByPhoneAndExpertId(String phone, String expert_id);

    @Insert("INSERT INTO favoritedirs (phone, expert_id, collect_time) VALUES (#{phone}, #{expert_id}, #{time})")
    int insertFavoriteDir(String phone, String expert_id, String time);
}
