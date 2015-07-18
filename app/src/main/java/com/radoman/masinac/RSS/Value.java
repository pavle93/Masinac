package com.radoman.masinac.RSS;
/**
    This file is part of Mašinac.

    Mašinac is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Foobar is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Foobar.  If not, see <http://www.gnu.org/licenses/>.
**/
import com.google.gson.annotations.SerializedName;
import java.util.List;


public class Value{

    private static final String FIELD_CALLBACK = "callback";
    private static final String FIELD_GENERATOR = "generator";
    private static final String FIELD_ITEMS = "items";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_PUB_DATE = "pubDate";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_LINK = "link";


    @SerializedName(FIELD_CALLBACK)
    private String mCallback;
    @SerializedName(FIELD_GENERATOR)
    private String mGenerator;
    @SerializedName(FIELD_ITEMS)
    private List<Item> mItems;
    @SerializedName(FIELD_TITLE)
    private String mTitle;
    @SerializedName(FIELD_PUB_DATE)
    private String mPubDate;
    @SerializedName(FIELD_DESCRIPTION)
    private String mDescription;
    @SerializedName(FIELD_LINK)
    private String mLink;


    public Value(){

    }

    public void setCallback(String callback) {
        mCallback = callback;
    }

    public String getCallback() {
        return mCallback;
    }

    public void setGenerator(String generator) {
        mGenerator = generator;
    }

    public String getGenerator() {
        return mGenerator;
    }

    public void setItems(List<Item> items) {
        mItems = items;
    }

    public List<Item> getItems() {
        return mItems;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setPubDate(String pubDate) {
        mPubDate = pubDate;
    }

    public String getPubDate() {
        return mPubDate;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getLink() {
        return mLink;
    }


}