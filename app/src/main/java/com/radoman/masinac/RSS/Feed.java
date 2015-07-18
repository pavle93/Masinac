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


public class Feed{

    private static final String FIELD_TYPE = "type";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_FEED_URL = "feedUrl";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_LINK = "link";
    private static final String FIELD_AUTHOR = "author";
    private static final String FIELD_ENTRIES = "entries";


    @SerializedName(FIELD_TYPE)
    private String mType;
    @SerializedName(FIELD_TITLE)
    private String mTitle;
    @SerializedName(FIELD_FEED_URL)
    private String mFeedUrl;
    @SerializedName(FIELD_DESCRIPTION)
    private String mDescription;
    @SerializedName(FIELD_LINK)
    private String mLink;
    @SerializedName(FIELD_AUTHOR)
    private String mAuthor;
    @SerializedName(FIELD_ENTRIES)
    private List<Entry> mEntries;


    public Feed(){

    }

    public void setType(String type) {
        mType = type;
    }

    public String getType() {
        return mType;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setFeedUrl(String feedUrl) {
        mFeedUrl = feedUrl;
    }

    public String getFeedUrl() {
        return mFeedUrl;
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

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setEntries(List<Entry> entries) {
        mEntries = entries;
    }

    public List<Entry> getEntries() {
        return mEntries;
    }


}