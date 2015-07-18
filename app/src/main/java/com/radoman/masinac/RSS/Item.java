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


public class Item{

    private static final String FIELD_XMLNS = "xmlns";
    private static final String FIELD_ID = "id";
    private static final String FIELD_XMLLANG = "xml:lang";
    private static final String FIELD_LINK = "link";
    private static final String FIELD_TITLE = "title";
    private static final String FIELD_UPDATED = "updated";
    private static final String FIELD_DESCRIPTION = "description";
    private static final String FIELD_AUTHOR = "author";
    private static final String FIELD_ENTRY = "entry";


    @SerializedName(FIELD_XMLNS)
    private String mXmln;
    @SerializedName(FIELD_ID)
    private String mId;
    @SerializedName(FIELD_XMLLANG)
    private String mXmllang;
    @SerializedName(FIELD_LINK)
    private List<Link> mLinks;
    @SerializedName(FIELD_TITLE)
    private String mTitle;
    @SerializedName(FIELD_UPDATED)
    private String mUpdated;
    @SerializedName(FIELD_DESCRIPTION)
    private String mDescription;
    @SerializedName(FIELD_AUTHOR)
    private Author mAuthor;
    @SerializedName(FIELD_ENTRY)
    private List<Entry> mEntries;


    public Item(){

    }

    public void setXmln(String xmln) {
        mXmln = xmln;
    }

    public String getXmln() {
        return mXmln;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getId() {
        return mId;
    }

    public void setXmllang(String xmllang) {
        mXmllang = xmllang;
    }

    public String getXmllang() {
        return mXmllang;
    }

    public void setLinks(List<Link> links) {
        mLinks = links;
    }

    public List<Link> getLinks() {
        return mLinks;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setUpdated(String updated) {
        mUpdated = updated;
    }

    public String getUpdated() {
        return mUpdated;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setAuthor(Author author) {
        mAuthor = author;
    }

    public Author getAuthor() {
        return mAuthor;
    }

    public void setEntries(List<Entry> entries) {
        mEntries = entries;
    }

    public List<Entry> getEntries() {
        return mEntries;
    }

    @Override
    public boolean equals(Object obj){
        if(obj instanceof Item){
            return ((Item) obj).getId() == mId;
        }
        return false;
    }

    @Override
    public int hashCode(){
        return mId.hashCode();
    }


}