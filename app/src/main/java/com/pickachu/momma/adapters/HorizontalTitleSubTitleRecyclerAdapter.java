package com.pickachu.momma.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pickachu.momma.Interfaces.HorizontalTitleSubtitleInterface;
import com.pickachu.momma.R;
import com.pickachu.momma.dom.TitleSubtitleData;
import com.pickachu.momma.widgets.MommaRecyclerView;

import java.util.List;

import static com.pickachu.momma.utils.UIUtils.getColorRedId;

public class HorizontalTitleSubTitleRecyclerAdapter extends BaseRecyclerAdapter<HorizontalTitleSubTitleRecyclerAdapter.HorizontalTitleSubtitleViewHolder> {

    private static final String TAG = HorizontalTitleSubTitleRecyclerAdapter.class.getSimpleName();

    private List<TitleSubtitleData> titleSubtitleDataList;
    private ViewGroup viewGroup;
    private static Context mContext;
    private HorizontalTitleSubtitleInterface classToCallOnClick;
    private int clickedPosition;

    public HorizontalTitleSubTitleRecyclerAdapter(Context context, List<TitleSubtitleData> titleSubtitleDataList, HorizontalTitleSubtitleInterface classToCallOnClick) {
        super(context);

        mContext = context;
        this.titleSubtitleDataList = titleSubtitleDataList;
        this.classToCallOnClick = classToCallOnClick;
        this.clickedPosition = 0;
    }

    public HorizontalTitleSubTitleRecyclerAdapter(Context context, List<TitleSubtitleData> titleSubtitleDataList, HorizontalTitleSubtitleInterface classToCallOnClick, int preSelectedPosition) {
        super(context);

        mContext = context;
        this.titleSubtitleDataList = titleSubtitleDataList;
        this.classToCallOnClick = classToCallOnClick;
        this.clickedPosition = preSelectedPosition;
    }

    @Override
    public HorizontalTitleSubtitleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_title_subtitle, viewGroup, false);

        return new HorizontalTitleSubtitleViewHolder(mContext, itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {

        final HorizontalTitleSubtitleViewHolder holder = (HorizontalTitleSubtitleViewHolder) viewHolder;
        final TitleSubtitleData titleSubtitleData = titleSubtitleDataList.get(position);

        setupLayout(holder, position);
    }

    @Override
    public int getItemCount() {
        return titleSubtitleDataList.size();
    }

    public void scrollToSetPosition(MommaRecyclerView horizontalTitleSubtitleRecyclerView) {
        int scrollToPosition = clickedPosition;

        /*if(clickedPosition > titleSubtitleDataList.size()/2) {
            scrollToPosition = Math.min(clickedPosition + 1, titleSubtitleDataList.size() - 1);
        } else {
            scrollToPosition = Math.max(clickedPosition - 1, 0);
        }*/
        horizontalTitleSubtitleRecyclerView.smoothScrollToPosition(scrollToPosition);
    }

    public static class HorizontalTitleSubtitleViewHolder extends BaseViewHolder {

        public View itemViewParent;

        public TextView txtTitle;
        public TextView txtSubtitle;
        public View underlineLayout;

        public HorizontalTitleSubtitleViewHolder(final Context context, final View convertView) {
            super(convertView);

            itemViewParent = convertView.findViewById(R.id.itemViewParent);

            txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            txtSubtitle = (TextView) convertView.findViewById(R.id.txtSubtitle);
            underlineLayout = convertView.findViewById(R.id.underline_layout);
        }
    }

    private void setupLayout(final HorizontalTitleSubtitleViewHolder holder, final int position) {

        final TitleSubtitleData titleSubtitleData = titleSubtitleDataList.get(position);
        String title = titleSubtitleData.getTitle().toUpperCase();
        String subtitle = titleSubtitleData.getSubtitle().toUpperCase();

        holder.txtTitle.setText(Html.fromHtml(title));
        holder.txtSubtitle.setText(Html.fromHtml(subtitle));
        holder.underlineLayout.setBackgroundColor(titleSubtitleData.getUnderlineLayoutColorId());

        holder.itemViewParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (classToCallOnClick != null && clickedPosition != position) {
                    classToCallOnClick.performOnHeaderClickAction(titleSubtitleData.getObjectToPassOnClick());
                    clickedPosition = position;
                    notifyDataSetChanged();
                }
            }
        });

        changeItemColor(holder, position);
    }

    private void changeItemColor(HorizontalTitleSubtitleViewHolder holder, int position) {
        if (position != clickedPosition) {
            holder.itemViewParent.setBackgroundColor(getColorRedId(context, R.color.horizontal_slider_background));
            ((TextView) holder.txtTitle).setTextColor(getColorRedId(context, R.color.horizontal_slider_title));
            ((TextView) holder.txtSubtitle).setTextColor(getColorRedId(context, R.color.horizontal_slider_subtitle));
        } else {
            holder.itemViewParent.setBackgroundColor(getColorRedId(context, R.color.horizontal_slider_background));
            ((TextView) holder.txtTitle).setTextColor(getColorRedId(context, R.color.horizontal_selected_slider_title));
            ((TextView) holder.txtSubtitle).setTextColor(getColorRedId(context, R.color.horizontal_selected_slider_subtitle));
        }
    }
}