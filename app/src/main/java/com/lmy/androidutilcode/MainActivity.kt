package com.lmy.androidutilcode

import com.lmy.androidutilcode.base.BaseVMActivity
import com.lmy.androidutilcode.databinding.ActivityMainBinding
import com.lmy.androidutilcode.view.ContactsSyncView

class MainActivity : BaseVMActivity<ActivityMainBinding, MainViewModel>() {
    
    override fun initVariableId(): Int = BR.vm
    
    override fun ActivityMainBinding.initView() {
        binding.tvStart.setOnClickListener {
            binding.csv.setSyncState(ContactsSyncView.STATE_SYNCING_FIRST)
        }
        binding.tvEnd.setOnClickListener {
            binding.csv.setSyncState(ContactsSyncView.STATE_SYNC_COMPLETE)
        }
    }
    
    override fun ActivityMainBinding.initData() {
        viewModel.getData()
    }
}

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
