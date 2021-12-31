package com.negier.contactlistview.bean;
/**
 * ==========================================================
 *
 * 版 权： NEGIER团队 版权所有(c)
 *
 * 作 者： 段雪彬
 *
 * 版 本： 1.0
 *
 * 创建日期：2017/1/26 下午 14:07
 *
 * 描 述：
 *
 * 这个类为联系人的bean对象，用来显示在列表上的数据源，可根据需求自己定义。
 * 为了简化，这里就定义了三个必要的字段
 *
 * 修订历史：
 *
 * ==========================================================
 */
public class Contact {
    private String name;//姓名
    private String headUrl;//头像url地址
    private String firstLetter;//首字母

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }
}
