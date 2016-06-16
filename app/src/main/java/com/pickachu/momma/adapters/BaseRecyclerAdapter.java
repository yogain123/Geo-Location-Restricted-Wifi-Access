package com.pickachu.momma.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pickachu.momma.MommaApp;
import com.pickachu.momma.Network.NetworkManager;
import com.pickachu.momma.database.DbSingleton;
import com.pickachu.momma.database.SqliteHelper;

/**
 * Created by vaibhavsinghal on 3/11/15.
 */
public class BaseRecyclerAdapter<M> extends RecyclerView.Adapter implements /*Response.Listener<JSONObject>, Response.ErrorListener,*/ View.OnClickListener {

    protected Context context;
    private NetworkManager networkManager;
    private static Boolean isClickAllowed = true;

    public BaseRecyclerAdapter(Context context) {
        this.context = context;
        this.networkManager = NetworkManager.newInstance(context, MommaApp.BASE_URL);
    }

    /**
     * Called when RecyclerView needs a new {@link RecyclerView.ViewHolder} of the given type to represent
     * an item.
     * <p/>
     * This new RecyclerView.ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p/>
     * The new RecyclerView.ViewHolder will be used to display items of the adapter using
     * {@link #onBindViewHolder(RecyclerView.ViewHolder, int)}. Since it will be re-used to display different
     * items in the data set, it is a good idea to cache references to sub views of the View to
     * avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new RecyclerView.ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(RecyclerView.ViewHolder, int)
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method
     * should update the contents of the {@link RecyclerView.ViewHolder#itemView} to reflect the item at
     * the given position.
     * <p/>
     * Note that unlike {@link ListView}, RecyclerView will not call this
     * method again if the position of the item changes in the data set unless the item itself
     * is invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside this
     * method and should not keep a copy of it. If you need the position of an item later on
     * (e.g. in a click listener), use {@link RecyclerView.ViewHolder#getPosition()} which will have the
     * updated position.
     *
     * @param holder   The RecyclerView.ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        return;
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onClick(View view) {
        flipOnClickListener(view, this);
        return;
    }

    protected static void flipOnClickListener(final View view, final RecyclerView.Adapter recyclerAdapter) {
        /*view.setOnClickListener(null);
        isClickAllowed = false;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setOnClickListener((View.OnClickListener) recyclerAdapter);
                isClickAllowed = true;
            }
        }, 1000);*/
    }

    protected int getViewPosition(View view) {
        return (int) view.getTag(view.getId());
    }

    protected void setViewPosition(View view, int position) {
        view.setTag(view.getId(), position);
    }

    public NetworkManager getNetworkManager() {
        return networkManager;
    }

    public SqliteHelper getSqliteHelper() {
        return DbSingleton.getInstance();
    }
}

