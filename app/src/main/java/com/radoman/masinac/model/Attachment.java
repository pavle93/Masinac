package com.radoman.masinac.model;
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


public class Attachment{

    private static final String FIELD_NAZIV = "naziv";
    private static final String FIELD_LINK = "link";


    @SerializedName(FIELD_NAZIV)
    private String mNaziv;
    @SerializedName(FIELD_LINK)
    private String mLink;


    public Attachment(){

    }

    public void setNaziv(String naziv) {
        mNaziv = naziv;
    }

    public String getNaziv() {
        return mNaziv;
    }

    public void setLink(String link) {
        mLink = link;
    }

    public String getLink() {
        return mLink;
    }


}