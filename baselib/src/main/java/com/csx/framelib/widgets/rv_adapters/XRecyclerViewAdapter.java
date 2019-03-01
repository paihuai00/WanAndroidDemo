package com.csx.framelib.widgets.rv_adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.csx.framelib.R;
import com.csx.framelib.utils.XDensityUtils;

import java.util.List;


public abstract class XRecyclerViewAdapter<T> extends BaseRecyclerViewAdapter<T, XViewHolder> {
    // 用来存放底部和头部View的集合  比Map要高效一些
    private SparseArray<View> mHeaderViews = new SparseArray<>();
    private SparseArray<View> mFooterViews = new SparseArray<>();
    private static int TYPE_HEADER = 0x100;
    private static int TYPE_FOOTER = 0x200;
    private static final int TYPE_LOAD_FAILED = 0x1;
    private static final int TYPE_NO_MORE = 0x2;
    private static final int TYPE_LOAD_MORE = 0x3;
    private static final int TYPE_NO_VIEW = 0x4;
    private RecyclerView mRecyclerView;
    private Context mContext;
    private View mLoadMoreFailedView;
    private int mLoadItemType = TYPE_NO_VIEW;
    private OnLoadMoreListener onLoadMoreListener;
    private LayoutInflater inflater;
    private int layoutId;
    private boolean isLoadError = false;//标记是否加载出错
    private boolean isHaveStatesView = false;//是否有加载更多
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemChildClickListener onItemChildClickListener;
    private OnItemChildLongClickListener onItemChildLongClickListener;
    private OnRetryClickListener onRetryClickListener;
    private OnEmptyClickListener onEmptyClickListener;
    private String mLoadMoreText, mNoMoreText;
    private View mEmptyView;
    private View mErrorView;

    public XRecyclerViewAdapter(@NonNull RecyclerView mRecyclerView, List<T> dataLists) {
        this(mRecyclerView, dataLists, -1);
    }

    public XRecyclerViewAdapter(@NonNull RecyclerView mRecyclerView, List<T> dataLists, @LayoutRes int layoutId) {
        this.mRecyclerView = mRecyclerView;
        this.dataLists = dataLists;
        this.layoutId = layoutId;
        this.mContext = mRecyclerView.getContext();
        this.inflater = LayoutInflater.from(mContext);
        mEmptyView = inflater.inflate(R.layout.xloading_empty_view, null);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mEmptyView.findViewById(R.id.ll).getLayoutParams();
        params.topMargin = XDensityUtils.getScreenHeight(mContext) / 4;
        mEmptyView.findViewById(R.id.ll).setLayoutParams(params);
        mEmptyView.findViewById(R.id.ll_empty).setLayoutParams(new LinearLayout.LayoutParams(XDensityUtils.getScreenWidth(mContext),
                ViewGroup.LayoutParams.WRAP_CONTENT));
        mErrorView = inflater.inflate(R.layout.xloading_error_view, null);
        LinearLayout.LayoutParams params1 = (LinearLayout.LayoutParams) mErrorView.findViewById(R.id.ll).getLayoutParams();
        params1.topMargin = XDensityUtils.getScreenHeight(mContext) / 4;
        mErrorView.findViewById(R.id.ll).setLayoutParams(params1);
        mErrorView.findViewById(R.id.ll_error).setLayoutParams(new LinearLayout.LayoutParams(XDensityUtils.getScreenWidth(mContext),
                ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public int getItemViewType(int position) {
        if (isHeaderPosition(position)) {
            return mHeaderViews.keyAt(position);
        }
        if (isLoadPosition(position)) {
            return mLoadItemType;
        }
        if (isFooterPosition(position)) {
            position = position - getHeaderCount() - getDataCount();
            return mFooterViews.keyAt(position);
        }
        position = position - getHeaderCount();
        return getItemLayoutResId(getItem(position), position);
    }

    @Override
    public XViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeaderViewType(viewType)) {
            return new XViewHolder(mHeaderViews.get(viewType));
        }

        if (isFooterViewType(viewType)) {
            return new XViewHolder(mFooterViews.get(viewType));
        }
        if (viewType == TYPE_NO_MORE) {
            View mNoMoreView = inflater.inflate(R.layout.adapter_no_more, mRecyclerView, false);
            if (!TextUtils.isEmpty(mNoMoreText)) {
                TextView tvLM = mNoMoreView.findViewById(R.id.adapter_no_more_text);
                tvLM.setText(mNoMoreText);
            }
            return new XViewHolder(mNoMoreView);
        }
        if (viewType == TYPE_LOAD_MORE) {
            View mLoadMoreView = inflater.inflate(R.layout.adapter_loading, mRecyclerView, false);
            if (!TextUtils.isEmpty(mLoadMoreText)) {
                TextView tvLM = mLoadMoreView.findViewById(R.id.adapter_loading_text);
                tvLM.setText(mLoadMoreText);
            }
            return new XViewHolder(mLoadMoreView);
        }
        if (viewType == TYPE_LOAD_FAILED) {
            mLoadMoreFailedView = inflater.inflate(R.layout.adapter_loading_failed, mRecyclerView, false);
            return new XViewHolder(mLoadMoreFailedView);
        }
        return new XViewHolder(inflater.inflate(viewType, parent, false));
    }

    @Override
    public void onBindViewHolder(final XViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_LOAD_FAILED) {
            mLoadMoreFailedView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onRetry();
                        isLoadMore(true);
                    }
                }
            });
            return;
        }
        if (holder.getItemViewType() == TYPE_LOAD_MORE) {
            if (onLoadMoreListener != null && isHaveStatesView) {
                if (!isLoadError) {
                    onLoadMoreListener.onLoadMore();
                }
            }
            return;
        }
        if (isFooterPosition(position) || isHeaderPosition(position)) return;

        final int finalPosition = position - getHeaderCount();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(holder.itemView, finalPosition);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    return onItemLongClickListener.onItemLongClick(holder.itemView, finalPosition);
                }
                return false;
            }
        });

        holder.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(XViewHolder holder, View view, int position) {
                if (onItemChildClickListener != null) {
                    onItemChildClickListener.onItemChildClick(holder, view, position - getHeaderCount());
                }
            }
        });

        holder.setOnItemChildLongClickListener(new OnItemChildLongClickListener() {
            @Override
            public void onItemChildLongClick(XViewHolder holder, View view, int position) {
                if (onItemChildLongClickListener != null) {
                    onItemChildLongClickListener.onItemChildLongClick(holder, view, position);
                }
            }
        });

        bindData(holder, getItem(finalPosition), finalPosition);
    }

    protected abstract void bindData(XViewHolder holder, T data, int position);

    public int getItemLayoutResId(T data, int position) {
        return layoutId;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {   // 布局是GridLayoutManager所管理
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) manager;
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果是Header、Footer的对象则占据spanCount的位置，否则就只占用1个位置
                    return (isHeaderPosition(position) || isFooterPosition(position)) ? gridLayoutManager.getSpanCount() : 1;
                }
            });
        }
    }


    @Override
    public void onViewAttachedToWindow(XViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        int position = holder.getLayoutPosition();
        if (isFooterPosition(position) || isHeaderPosition(position)) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
                p.setFullSpan(true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return getDataCount() + getHeaderCount() + getFooterCount() + (isHaveStatesView ? 1 : 0);
    }

    public int getDataCount() {
        return dataLists.size();
    }

    public int getHeaderCount() {
        return mHeaderViews.size();
    }

    public int getFooterCount() {
        return mFooterViews.size();
    }

    public void isLoadMore(boolean isHaveStatesView) {
        mLoadItemType = TYPE_LOAD_MORE;
        isLoadError = false;
        this.isHaveStatesView = isHaveStatesView;
        notifyItemChanged(getItemCount());
    }

    public void showLoadError() {
        mLoadItemType = TYPE_LOAD_FAILED;
        isLoadError = true;
        isHaveStatesView = true;
        notifyItemChanged(getItemCount());
    }

    public void showLoadComplete() {
        mLoadItemType = TYPE_NO_MORE;
        isLoadError = false;
        isHaveStatesView = true;
        removeFooterView(mEmptyView);
        removeFooterView(mErrorView);
        notifyItemChanged(getItemCount());
    }

    public void gone() {
        isLoadError = false;
        isHaveStatesView = false;
        notifyItemChanged(getItemCount());
    }

    @Override
    public void setDataLists(List<T> datas) {
        mLoadItemType = TYPE_LOAD_MORE;
        super.setDataLists(datas);
    }

    private boolean isFooterViewType(int viewType) {
        int position = mFooterViews.indexOfKey(viewType);
        return position >= 0;
    }

    private boolean isHeaderViewType(int viewType) {
        int position = mHeaderViews.indexOfKey(viewType);
        return position >= 0;
    }

    private boolean isFooterPosition(int position) {
        return position >= (getHeaderCount() + getDataCount());
    }

    private boolean isLoadPosition(int position) {
        return position == getItemCount() - 1 && isHaveStatesView;
    }

    private boolean isHeaderPosition(int position) {
        return position < getHeaderCount();
    }

    public void addHeaderView(View view) {
        int position = mHeaderViews.indexOfValue(view);
        if (position < 0) {
            mHeaderViews.put(TYPE_HEADER++, view);
        }
        notifyDataSetChanged();
    }

    public void addFooterView(View view) {
        int position = mFooterViews.indexOfValue(view);
        if (position < 0) {
            mFooterViews.put(TYPE_FOOTER++, view);
        }
        notifyDataSetChanged();
    }

    public void removeHeaderView(View view) {
        int index = mHeaderViews.indexOfValue(view);
        if (index < 0) return;
        mHeaderViews.removeAt(index);
        notifyDataSetChanged();
    }

    public void removeFooterView(View view) {
        int index = mFooterViews.indexOfValue(view);
        if (index < 0) return;
        mFooterViews.removeAt(index);

        notifyDataSetChanged();
    }

    public XRecyclerViewAdapter<T> setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
        return this;
    }

    public XRecyclerViewAdapter<T> setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
        return this;
    }

    public XRecyclerViewAdapter<T> setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
        return this;
    }

    public XRecyclerViewAdapter<T> setOnItemChildClickListener(OnItemChildClickListener onItemChildClickListener) {
        this.onItemChildClickListener = onItemChildClickListener;
        return this;
    }

    public XRecyclerViewAdapter<T> setOnItemChildLongClickListener(OnItemChildLongClickListener onItemChildLongClickListener) {
        this.onItemChildLongClickListener = onItemChildLongClickListener;
        return this;
    }

    public XRecyclerViewAdapter<T> setOnRetryClickListener(OnRetryClickListener onRetryClickListener) {
        this.onRetryClickListener = onRetryClickListener;
        return this;
    }

    public XRecyclerViewAdapter<T> setOnEmptyClickListener(OnEmptyClickListener onEmptyClickListener) {
        this.onEmptyClickListener = onEmptyClickListener;
        return this;
    }

    public interface OnLoadMoreListener {
        void onRetry();

        void onLoadMore();
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public interface OnItemLongClickListener {
        boolean onItemLongClick(View v, int position);
    }

    public void setLoadMoreText(String loadMoreText) {
        this.mLoadMoreText = loadMoreText;
    }

    public void setNoMoreText(String noMoreText) {
        this.mNoMoreText = noMoreText;
    }

    public interface OnItemChildClickListener {
        void onItemChildClick(XViewHolder holder, View view, int position);
    }

    public interface OnItemChildLongClickListener {
        void onItemChildLongClick(XViewHolder holder, View view, int position);
    }

    public void showContent() {
        removeFooterView(mEmptyView);
        removeFooterView(mErrorView);
    }

    public void showEmpty() {
        showContent();
        (mEmptyView.findViewById(R.id.ll_empty)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEmptyClickListener != null)
                    onEmptyClickListener.onEmptyClick();
            }
        });
        addFooterView(mEmptyView);
    }

    public void showEmpty(String msg) {
        showContent();
        ((TextView) mEmptyView.findViewById(R.id.tv_no_data)).setText(msg);
        (mEmptyView.findViewById(R.id.ll_empty)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEmptyClickListener != null)
                    onEmptyClickListener.onEmptyClick();
            }
        });
        addFooterView(mEmptyView);
    }

    public void showEmpty(String msg, int pic) {
        showContent();
        ((TextView) mEmptyView.findViewById(R.id.tv_no_data)).setText(msg);
        ((ImageView) mEmptyView.findViewById(R.id.iv_no_data)).setBackgroundResource(pic);
        (mEmptyView.findViewById(R.id.ll_empty)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEmptyClickListener != null)
                    onEmptyClickListener.onEmptyClick();
            }
        });
        addFooterView(mEmptyView);
    }

    public void showEmptyWithoutImg(String msg) {
        showContent();
        ((TextView) mEmptyView.findViewById(R.id.tv_no_data)).setText(msg);
        ((ImageView) mEmptyView.findViewById(R.id.iv_no_data)).setVisibility(View.GONE);
        (mEmptyView.findViewById(R.id.ll_empty)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onEmptyClickListener != null)
                    onEmptyClickListener.onEmptyClick();
            }
        });
        addFooterView(mEmptyView);

    }

    public void showError() {
        showContent();
        mErrorView.findViewById(R.id.xloading_retry).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onRetryClickListener != null) {
                    onRetryClickListener.onRetryClick();
                }
            }
        });
        addFooterView(mErrorView);
    }

    public interface OnRetryClickListener {
        void onRetryClick();
    }

    public interface OnEmptyClickListener {
        void onEmptyClick();
    }


    public int interfaceSuccess(List<T> list, int pageNum, boolean hasMore) {
        showContent();
        if (pageNum == 1) {
            clear();
            if (list == null || list.size() == 0) {
                showEmpty();
                isLoadMore(false);
                return pageNum;
            } else {
                addAll(list);
                isLoadMore(hasMore);
                if (!hasMore) {
                    showLoadComplete();
                    return pageNum;
                } else {
                    return pageNum + 1;
                }
            }
        } else {
            addAll(list);
            isLoadMore(hasMore);
            if (!hasMore) {
                showLoadComplete();
                return pageNum;
            } else {
                return pageNum + 1;
            }
        }
    }
}
