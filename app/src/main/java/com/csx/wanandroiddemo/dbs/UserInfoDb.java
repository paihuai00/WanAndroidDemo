package com.csx.wanandroiddemo.dbs;


import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.List;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/25
 * @description:
 */

@Table(database = AppDatabase.class)
public class UserInfoDb extends BaseModel {
    /**
     * chapterTops : []
     * collectIds : [7938,7972,7957,7925,7654]
     * email :
     * icon :
     * id : 17631
     * password :
     * token :
     * type : 0
     * username : 17610176618
     */
    @Column
    public String email;
    @Column
    public String icon;
    @Column
    @PrimaryKey
    public int id;
    @Column
    public String password;
    @Column
    public String token;
    @Column
    public int type;
    @Column
    public String username;
    //    @Column
    public List<Integer> chapterTops;
    //    @Column
    public List<Integer> collectIds;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Integer> getChapterTops() {
        return chapterTops;
    }

    public void setChapterTops(List<Integer> chapterTops) {
        this.chapterTops = chapterTops;
    }

    public List<Integer> getCollectIds() {
        return collectIds;
    }

    public void setCollectIds(List<Integer> collectIds) {
        this.collectIds = collectIds;
    }
}
