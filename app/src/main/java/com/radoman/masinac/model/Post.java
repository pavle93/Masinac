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
import java.util.List;


public class Post{

    private static final String FIELD_TEME = "teme";


    @SerializedName(FIELD_TEME)
    private List<Teme> mTemes;


    public Post(){

    }

    public void setTemes(List<Teme> temes) {
        mTemes = temes;
    }

    public List<Teme> getTemes() {
        return mTemes;
    }


}