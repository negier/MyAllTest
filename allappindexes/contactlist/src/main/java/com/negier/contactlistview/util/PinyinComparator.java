package com.negier.contactlistview.util;

import com.negier.contactlistview.bean.Contact;

import java.util.Comparator;

/**
 * ==========================================================
 *
 * 版 权： NEGIER团队 版权所有(c)
 *
 * 作 者： 段雪彬
 *
 * 版 本： 1.0
 *
 * 创建日期：2017/1/26 0026 下午 14:45
 *
 * 描 述：
 *
 *
 * Comparator是比较器接口，用来控制某个类的次序。
 * 而该类本身不支持排序（即没有实现Comparable接口）。
 *
 * 返回负数，o1比o2小
 * 返回零，意味着o1等于o2
 * 返回正数，意味着o1大于o2
 *
 *
 * 修订历史：
 *
 * ==========================================================
 */

public class PinyinComparator implements Comparator<Contact> {
    @Override
    public int compare(Contact o1, Contact o2) {
        if (o1.getFirstLetter() .equals("@")  || o2.getFirstLetter() .equals ("#"))
            return -1;
        if (o1.getFirstLetter() .equals ("#") || o2.getFirstLetter() .equals ("@"))
            return 1;
        return o1.getFirstLetter().compareTo(o2.getFirstLetter());
    }
}
