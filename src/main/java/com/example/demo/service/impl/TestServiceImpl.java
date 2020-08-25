package com.example.demo.service.impl;

import com.example.demo.service.TestService;
import com.example.demo.util.Sm4Util;
import com.example.demo.sm4.Sm4UtilOther;
import com.example.demo.util.StringUtil;
import org.springframework.stereotype.Service;

/**
 * @author mulj
 * @date 2020/8/7 17:03
 * @Email:mlj@citycloud.com.cn
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    public String strChange(String str){
        String t= StringUtil.strTo16(str);
        return t;
    }
    @Override
    public String jiamiStr(){
//        String key="6137636338643261";
//        String str="{\"groupName\":\"test\",\"templateGroups\":[{\"templateId\":\"1001\",\"sort\":\"110\"}],\"inputDeptIds\":[\"58\",\"59\"],\"auditDeptId\":\"23\",\"items\":[{\"name\":\"test6\",\"unit\":\"t\",\"sort\":\"21\"},{\"name\":\"test7\",\"unit\":\"t\",\"sort\":\"22\"}]}";
//        String returnStr="";
//        try {
//            returnStr=Sm4Util.encryptEcb(key,str);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnStr;
        String cipherText="";
        try {
           // String plainText = "{\"groupName\":\"test\",\"templateGroups\":[{\"templateId\":\"1001\",\"sort\":\"110\"}],\"inputDeptIds\":[\"58\",\"59\"],\"auditDeptId\":\"23\",\"items\":[{\"name\":\"test6\",\"unit\":\"t\",\"sort\":\"21\"},{\"name\":\"test7\",\"unit\":\"t\",\"sort\":\"22\"}]}";
            String plainText=null;
            Sm4UtilOther sm4 = new Sm4UtilOther();
            sm4.secretKey = "3e7bdd04-10a8-4598-8506-121b1c91a8e4".substring(0,16);
            plainText.getBytes("UTF-8");
             cipherText = sm4.encryptData_ECB(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }
    @Override
    public  String jiemiStr(){
//        String key="6137636338643261";
//        String str="392ea43f9952f0d44238cd4960994bae8873baa0dfbbd84fb9f01875fd7df866337bffbad6a276378f93eb9d6429a98306018dd1ae0f76d1facee3bbc9055ed859919abf83ed1eb3a7b48993ac7b0313e3bbbe8099e3123a9da9cecfe3542227ba53fa5f70015003f213bd667b92162e7226a2cccfd964dc969d986472f9c8be90105f5a84d239a8ca4638f268c964896baab713d78571e08bf851eea9a9eaa61a07b8edb10daee6e48658927b70a4aa0d6bd650ed40b8ae0583a6b37ebb1ed6a13aa88911d9809adfa9e9a29e0a941d13b260af629f5c2b5872ab99113f1021";
//        String returnStr="";
//        try {
//            returnStr=Sm4Util.decryptEcb(key,str);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return returnStr;
        String cipherText="";
        try {
           // String plainText = "OPXQyBIIeE8gdczwYXD9OIlzPwIIop3fCc9ITfuAW4XG59ZWLziCA7vpiAr2APt5uuZCHLF3nEJ28s8lEiMzwBr5VHC9/yumWVL1cyQjj2N/phACzw58kqbk46qwjnZqMHTsUK27dg4UZ7S0yJP0cweURN4VThGizV55eYsnkudYS7KB3JJ55oIbREOBvI0cDy4GSWHsShKO/uvi/R6csoPwLgmbyFkoLv6OFu0Wwc1OxnHx/WNgK4LGOH+v7GsN2YcrGtrVEX4gLTbBqbtx59P1/rdgq1pECyBaFvVtJWo=";
            String plainText="";
            Sm4UtilOther sm4 = new Sm4UtilOther();
            sm4.secretKey = "3e7bdd04-10a8-4598-8506-121b1c91a8e4".substring(0,16);
            plainText.getBytes("UTF-8");
            cipherText = sm4.decryptData_ECB(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherText;
    }
    @Override
    public boolean verfi(){
        String key="61376363386432612d386634642d3464";
        String json="{\"groupName\":\"test\",\"templateGroups\":[{\"templateId\":\"1001\",\"sort\":\"110\"}],\"inputDeptIds\":[\"58\",\"59\"],\"auditDeptId\":\"23\",\"items\":[{\"name\":\"test6\",\"unit\":\"t\",\"sort\":\"21\"},{\"name\":\"test7\",\"unit\":\"t\",\"sort\":\"22\"}]}";
        String str="392ea43f9952f0d44238cd4960994bae8873baa0dfbbd84fb9f01875fd7df866337bffbad6a276378f93eb9d6429a98306018dd1ae0f76d1facee3bbc9055ed859919abf83ed1eb3a7b48993ac7b0313e3bbbe8099e3123a9da9cecfe3542227ba53fa5f70015003f213bd667b92162e7226a2cccfd964dc969d986472f9c8be90105f5a84d239a8ca4638f268c964896baab713d78571e08bf851eea9a9eaa61a07b8edb10daee6e48658927b70a4aa0d6bd650ed40b8ae0583a6b37ebb1ed6a13aa88911d9809adfa9e9a29e0a941d13b260af629f5c2b5872ab99113f1021";
        boolean flag=false;
        try {
            flag=Sm4Util.verifyEcb(key,str,json);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }
}
