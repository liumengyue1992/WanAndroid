package com.lmy.uitl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;


/**
 * 刷新的辅助类
 */
public class SmartRefreshUtils {
    private final RefreshLayout mRefreshLayout;
    private RefreshListener mRefreshListener = null;
    private LoadMoreListener mLoadMoreListener = null;

    public static SmartRefreshUtils with(RefreshLayout layout) {
        return new SmartRefreshUtils(layout);
    }

    private SmartRefreshUtils(RefreshLayout layout) {
        mRefreshLayout = layout;
        mRefreshLayout.setEnableAutoLoadMore(false);
        mRefreshLayout.setEnableOverScrollBounce(true);
    }

    public SmartRefreshUtils pureScrollMode() {
        mRefreshLayout.setEnableRefresh(false);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnablePureScrollMode(true);
        mRefreshLayout.setEnableNestedScroll(true);
        mRefreshLayout.setEnableOverScrollDrag(true);
        return this;
    }

    public SmartRefreshUtils setRefreshListener(@Nullable RefreshListener refreshListener) {
        this.mRefreshListener = refreshListener;
        if (refreshListener == null) {
            mRefreshLayout.setEnableRefresh(false);
        } else {
            mRefreshLayout.setEnablePureScrollMode(false);
            mRefreshLayout.setEnableRefresh(true);
            mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    refreshLayout.finishRefresh((int) 500, false, false);
                    mRefreshListener.onRefresh();
                }
            });
        }
        return this;
    }

    public SmartRefreshUtils setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.mLoadMoreListener = loadMoreListener;
        if (loadMoreListener == null) {
            mRefreshLayout.setEnableLoadMore(false);
        } else {
            mRefreshLayout.setEnablePureScrollMode(false);
            mRefreshLayout.setEnableLoadMore(true);
            mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    refreshLayout.finishLoadMore((int) 500);
                    mLoadMoreListener.onLoadMore();
                }
            });
        }
        return this;
    }

    public void autoRefresh() {
        mRefreshLayout.autoRefresh();
    }

    public void autoLoadMore() {
        mRefreshLayout.autoLoadMore();
    }

    public void success() {
        mRefreshLayout.finishRefresh(true);
        mRefreshLayout.finishLoadMore(true);
    }

    public void fail() {
        mRefreshLayout.finishRefresh(false);
        mRefreshLayout.finishLoadMore(false);
    }

    public interface RefreshListener {
        void onRefresh();
    }

    public interface LoadMoreListener {
        void onLoadMore();
    }
}
