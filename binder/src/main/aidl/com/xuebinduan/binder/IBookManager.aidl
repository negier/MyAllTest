package com.xuebinduan.binder;

import com.xuebinduan.binder.Book;
import com.xuebinduan.binder.IOnNewBookArrivedListener;

/*
 * AIDL支持的数据类型：
 * 基本数据类型：int、long、char、boolean、double等；
 * String和CharSequence；
 * List:只支持ArrayList,里面每个元素都必须能够被AIDL支持；
 * Map:只支持HashMap，里面的每个元素都必须被AIDL支持，包括key和value；
 * Parcelable：所有实现了Parcelable接口的对象；（自定义的Parcelable对象和AIDL对象必须要显示的import进来，不管它们是否和当前的AIDL文件位于同一个包内）
 * AIDL：所有的AIDL接口本身也可以在AIDL文件中使用。
 *
 * 另记：对象是不能跨进程传输的，对象的跨进程传输本质上都是反序列化过程，这就是为什么AIDL中的自定义对象都必须要实现Parcelable接口的原因。
 */

interface IBookManager{
    List<Book> getBookList();
    void addBook(in Book book);
    void registerListener(IOnNewBookArrivedListener listener);
    void unregisterListener(IOnNewBookArrivedListener listener);
}