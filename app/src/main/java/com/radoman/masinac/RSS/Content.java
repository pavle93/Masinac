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


public class Content{

    private static final String FIELD_TYPE = "type";
    private static final String FIELD_XMLBASE = "xml:base";
    private static final String FIELD_CONTENT = "content";


    @SerializedName(FIELD_TYPE)
    private String mType;
    @SerializedName(FIELD_XMLBASE)
    private String mXmlbase;
    @SerializedName(FIELD_CONTENT)
    private String mContent;


    public Content(){

    }

    public void setType(String type) {
        mType = type;
    }

    public String getType() {
        return mType;
    }

    public void setXmlbase(String xmlbase) {
        mXmlbase = xmlbase;
    }

    public String getXmlbase() {
        return mXmlbase;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getContent() {
        return mContent;
    }


}