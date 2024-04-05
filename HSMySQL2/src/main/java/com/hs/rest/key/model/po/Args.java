package com.hs.rest.key.model.po;

import lombok.Data;

// 資料表	：args
// 使用方式 ?key=abc123 給特殊權限的人使用,每次使用必須更新
// id 一率都是 7
// id, name, strArg1, memo
@Data
public class Args {
    private final Integer id = 7; // 固定值(表示:登入首頁權限密鑰)
    private String name;
    private String strArg1; // 放 key 內容
    private String memo;
}
