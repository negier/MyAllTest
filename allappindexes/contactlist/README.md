# ContactListView
QQ、微信联系人列表Demo，欢迎大家使用。
<div align=center><img width="360" height="640" src="https://raw.githubusercontent.com/negier/ContactListView/master/screenshots/S70207-15005209.jpg" alt="展示图片"/></div>
# 使用指南
1. 修改数据源：Demo数据使用的数据源在values/arrays.xml，代码相关具体可在ContactsActivity页搜索"contacts" ，它是数据源String[]类型。
2. 修改布局item：ContactsRecyclerView布局item，可在layout/item_recyclerview_contacts.xml里进行修改。
3. 修改默认头像：可以在ContactsRecyclerView.java里搜索"holder.ivHead.setImageResource(R.mipmap.head)"，进行修改。
4. 修改联系人点击逻辑：代码相关具体可在ContactsActivity页搜索"OnItemClick"，在方法里修改你的逻辑就OK了。

# 作者
直接原样复制到相应的位置就可以了，详细的使用修改也在上面说了，需要这个功能，跳转到ContactsActivity，就可以了。建议阅读源码。
