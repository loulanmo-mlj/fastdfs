package com.example.demo.sm4;

/**
 * @author mulj
 * @date 2020/8/7 17:57
 * @Email:mlj@citycloud.com.cn
 */
public class SM4_Context {
    public int mode;

    public int[] sk;

    public boolean isPadding;

    public SM4_Context()
    {
        this.mode = 1;
        this.isPadding = true;
        this.sk = new int[32];
    }
}
