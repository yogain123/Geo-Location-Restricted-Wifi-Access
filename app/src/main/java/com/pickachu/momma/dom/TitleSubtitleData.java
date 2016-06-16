package com.pickachu.momma.dom;

/**
 * Created by vaibhavsinghal on 28/11/15.
 */
public class TitleSubtitleData {

    private String title;
    private String subtitle;
    private int titleSize;
    private int subTitleSize;
    private int underlineLayoutColorId;

    private Object objectToPassOnClick;

    public TitleSubtitleData() {
        underlineLayoutColorId = android.R.color.transparent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public int getTitleSize() {
        return titleSize;
    }

    public void setTitleSize(int titleSize) {
        this.titleSize = titleSize;
    }

    public int getSubTitleSize() {
        return subTitleSize;
    }

    public void setSubTitleSize(int subTitleSize) {
        this.subTitleSize = subTitleSize;
    }

    public int getUnderlineLayoutColorId() {
        return underlineLayoutColorId;
    }

    public void setUnderlineLayoutColorId(int underlineLayoutColorId) {
        this.underlineLayoutColorId = underlineLayoutColorId;
    }

    public Object getObjectToPassOnClick() {
        return objectToPassOnClick;
    }

    public void setObjectToPassOnClick(Object objectToPassOnClick) {
        this.objectToPassOnClick = objectToPassOnClick;
    }
}
