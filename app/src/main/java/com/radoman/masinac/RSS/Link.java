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


public class Link{

    private static final String FIELD_TYPE = "type";
    private static final String FIELD_REL = "rel";
    private static final String FIELD_HREF = "href";


    @SerializedName(FIELD_TYPE)
    private String mType;
    @SerializedName(FIELD_REL)
    private String mRel;
    @SerializedName(FIELD_HREF)
    private String mHref;


    public Link(){

    }

    public void setType(String type) {
        mType = type;
    }

    public String getType() {
        return mType;
    }

    public void setRel(String rel) {
        mRel = rel;
    }

    public String getRel() {
        return mRel;
    }

    public void setHref(String href) {
        mHref = href;
    }

    public String getHref() {
        return mHref;
    }


}