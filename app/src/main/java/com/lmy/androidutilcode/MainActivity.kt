package com.lmy.androidutilcode

import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.lmy.androidutilcode.view.ContactsSyncView

class MainActivity : AppCompatActivity() {
    private lateinit var csv: ContactsSyncView
    private lateinit var rotateAnimation: RotateAnimation
    private val function = {
        csv.setSyncState(1)
        val animation: Animation =
            RotateAnimation(0f, 360f, csv.width / 2.toFloat(), csv.height / 2.toFloat())
        animation.duration = 1000;
        animation.repeatCount = Animation.INFINITE
        animation.repeatMode = Animation.RESTART
        // animation.fillAfter = true;//设置为true，动画转化结束后被应用
        animation.interpolator = LinearInterpolator()
        csv.startAnimation(animation)//开始动画
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // PermissionUtils.permission(android.Manifest.permission.READ_EXTERNAL_STORAGE,
        //     android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
        //     android.Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)
        //     .callback { isAllGranted, granted, deniedForever, denied ->
        //         val appInfos = ApkInfoUtil.getAppsInfo()
        //         for (appInfo in appInfos) {
        //             // Log.e("lmy", appInfo.toString())
        //             // if (!appInfo.isInAppStore){
        //             //
        //             // }
        //
        //             if (appInfo.name.equals("抖音")) {
        //                 appInfo.packagePath?.let { ApkInfoUtil.backupApp(it, "MyTikTok") }
        //             }
        //         }
        //     }
        //     .request()

        csv = findViewById(R.id.csv)

        //开始同步
        findViewById<TextView>(R.id.tv_start).setOnClickListener {
            csv.setSyncState(ContactsSyncView.STATE_SYNCING_FIRST)
        }

        //结束同步
        findViewById<TextView>(R.id.tv_end).setOnClickListener {
            csv.setSyncState(ContactsSyncView.STATE_SYNC_COMPLETE)
        }
    }
}