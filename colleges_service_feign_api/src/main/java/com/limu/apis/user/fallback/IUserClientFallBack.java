//package com.limu.apis.user.fallback;
//
//import com.limu.apis.user.IUserClient;
//import com.limu.model.common.dtos.ResponseResult;
//import com.limu.model.common.enums.AppHttpCodeEnum;
//import org.springframework.stereotype.Component;
//
//@Component
//public class IUserClientFallBack implements IUserClient {
//
//
//    @Override
//    public ResponseResult<?> getUserById(Integer id) {
//        return ResponseResult.errorResult(AppHttpCodeEnum.SERVER_ERROR,"获取数据失败");
//    }
//}
