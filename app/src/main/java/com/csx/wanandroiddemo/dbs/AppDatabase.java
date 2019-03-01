package com.csx.wanandroiddemo.dbs;


import com.raizlabs.android.dbflow.annotation.Database;

/**
 * create by cuishuxiang
 *
 * @date : 2019/2/25
 * @description: 配置数据库相关
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    //数据库名称
    public static final String NAME = "MyAppDatabase";
    //数据库版本号
    public static final int VERSION = 1;
}
