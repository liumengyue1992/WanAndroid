package com.lmy.module_common.bean

data class Article(
    val datas: List<ArticleDetail>,
    val over: Boolean,
    val curPage: Int,
    val pageCount: Int,
    val size: Int,
    val total: Int,
)

data class ArticleDetail(
    val author: String,
    val fresh: Boolean,
    val tags: List<ArticleTag>,
    val id: Int,
    val link: String,
    var niceDate: String,
    val shareUser: String, //author 与 shareUser 网站上的文章可能是某位作者author的，也可能是某位分享人shareUser分享的。 如果是分享人分享的，author 为 null。
    val title: String,
    val superChapterId: Int, // superChapterId其实不是一级分类id，因为要拼接跳转url，内容实际都挂在二级分类下，所以该id实际上是一级分类的第一个子类目的id，拼接后故可正常跳转。
    val superChapterName: String, // 一级分类的名称
    val chapterName: String,
    val collect: Boolean,
    val type: Int
)

data class ArticleTag(val name: String, val url: String)

//{
//    "adminAdd": false,
//    "apkLink": "",
//    "audit": 1,
//    "author": "",
//    "canEdit": false,
//    "chapterId": 502,
//    "chapterName": "自助",
//    "collect": false,
//    "courseId": 13,
//    "desc": "",
//    "descMd": "",
//    "envelopePic": "",
//    "fresh": true,
//    "id": 26298,
//    "isAdminAdd": false,
//    "link": "https://juejin.cn/post/7226549820202647610",
//    "niceDate": "3小时前",
//    "niceShareDate": "3小时前",
//    "publishTime": 1682567425000,
//    "realSuperChapterId": 493,
//    "route": false,
//    "selfVisible": 0,
//    "shareDate": 1682567425000,
//    "shareUser": "345丶",
//    "superChapterId": 494,
//    "superChapterName": "广场Tab",
//    "tags": [],
//    "title": "Android | View.post 到底是在什么时候执行的？",
//    "type": 0,
//    "userId": 70343,
//    "visible": 1,
//    "zan": 0
//}