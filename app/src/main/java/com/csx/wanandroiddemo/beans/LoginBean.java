package com.csx.wanandroiddemo.beans;

import com.csx.wanandroiddemo.dbs.UserInfoDb;

import java.util.List;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/25
 * @description:
 */
public class LoginBean {


    /**
     * data : {"chapterTops":[],"collectIds":[7938,7972,7957,7925,7654,5249],"email":"","icon":"","id":17631,"password":"","token":"","type":0,"username":"17610176618"}
     * errorCode : 0
     * errorMsg :
     */

    private UserInfoDb data;
    private int errorCode;
    private String errorMsg;

    public UserInfoDb getData() {
        return data;
    }

    public void setData(UserInfoDb data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


}
