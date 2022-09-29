package com.exflyer.oddi.user.api.mustad.dao;

import com.exflyer.oddi.user.api.mustad.dto.MemeberAdvStateResult;
import org.springframework.stereotype.Repository;


@Repository
public interface MustadMapper {

    MemeberAdvStateResult findMustadInfo(String mustadId, String today);

    MemeberAdvStateResult findMemberAdvStateResign(String userId, String today);
}
