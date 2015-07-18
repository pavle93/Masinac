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


public class Entry{

    private static final String FIELD_ID = "id";
    private static final String FIELD_PUBLISHED = "published";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_UPDATED = "updated";
    private static final String FIELD_LINK = "link";
    private static final String FIELD_CONTENT = "content";
    private static final String FIELD_AUTHOR = "author";


    @SerializedName(FIELD_ID)
    private String mId;
    @SerializedName(FIELD_PUBLISHED)
    private String mPublished;
    @SerializedName(FIELD_TITLE)
    private Title mTitle;
    @SerializedName(FIELD_UPDATED)
    private String mUpdated;
    @SerializedName(FIELD_LINK)
    private Link mLink;
    @SerializedName(FIELD_CONTENT)
    private Content mContent;
    @SerializedName(FIELD_AUTHOR)
    private Author mAuthor;


    public Entry(){

    }

    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public void setPublished(String published) {
        mPublished = published;
    }

    public String getPublished() {
        return mPublished;
    }

    public void setTitle(Title title) {
        mTitle = title;
    }

    public Title getTitle() {
        return mTitle;
    }

    public void setUpdated(String updated) {
        mUpdated = updated;
    }

    public String getUpdated() {
        return mUpdated;
    }

    public void setLink(Link link) {
        mLink = link;
    }

    public Link getLink() {
        return mLink;
    }

    public void setContent(Content content) {
        mContent = content;
    }

    public Content getContent() {
        return mContent;
    }

    public void setAuthor(Author author) {
        mAuthor = author;
    }

    public Author getAuthor() {
        return mAuthor;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Entry){
            return ((Entry) obj).getId() == mId;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return mId.hashCode();
    }


}