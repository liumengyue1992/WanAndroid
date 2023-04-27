package com.lmy.module_home.bean

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
    val id: Int,
    val link: String,
    var niceDate: String,
    val shareUser: String,
    val title: String,
    val superChapterId: Int, // superChapterId其实不是一级分类id，因为要拼接跳转url，内容实际都挂在二级分类下，所以该id实际上是一级分类的第一个子类目的id，拼接后故可正常跳转。
    val superChapterName: String, // 一级分类的名称
    var collect: Boolean,
)