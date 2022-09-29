package com.exflyer.oddi.user.api.user.auth.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminAuthDao {

  boolean isAvailableAct(@Param("id") String id
    , @Param("menuCode") String menuCode
    , @Param("createAction") Boolean createAction
    , @Param("readAction") Boolean readAction
    , @Param("updateAction") Boolean updateAction
    , @Param("deleteAction") Boolean deleteAction);
}
