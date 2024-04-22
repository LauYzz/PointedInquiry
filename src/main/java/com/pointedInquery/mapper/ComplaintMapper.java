package com.pointedInquery.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pointedInquery.entity.Complaint;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ComplaintMapper extends BaseMapper<Complaint> {

    @Insert("INSERT INTO complaint (order_id,user_id,be_user_id,state,contents,time) values (#{orderId},#{userId},#{beUserId},#{state},#{contents},#{time})")
    int addComplaint(Complaint complaint);
}
