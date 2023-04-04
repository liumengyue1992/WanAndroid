package com.lmy.wanandroid.ui.ashmem

/**
 * @description：
 * @author：mengyue.liu
 * @time： 2022/6/27 16:39
 */
// 我们在使用Binder在进程间传递数据的时候，有时候会抛出TransactionTooLargeException这个异常，
// 这个异常的产生是因为Binder驱动对内存的限制引起的。也就是说，我们不能通过Binder传递太大的数据。官方文档里有说明，最大通常限制为1M-8K。
//
// 但是请大家思考一个问题，在Android系统中，APP端View视图的数据是如何传递SurfaceFlinger服务的呢？
// View绘制的数据最终是按照一帧一帧显示到屏幕的，而每一帧都会占用一定的存储空间，在APP端执行draw的时候，
// 数据很明显是要绘制到APP的进程空间，但是视图窗口要经过SurfaceFlinger图层混排才会生成最终的帧，
// 而SurfaceFlinger又运行在另一个独立的服务进程，那么View视图的数据是如何在两个进程间传递的呢，
// 普通的Binder通信肯定不行，因为Binder不太适合这种数据量较大的通信，那么View数据的通信采用的是什么IPC手段呢？
// 答案就是匿名共享内存（Anonymous Shared Memory）-Ashmem

