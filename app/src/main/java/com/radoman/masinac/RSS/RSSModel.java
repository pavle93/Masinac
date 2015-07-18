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


public class RSSModel{

    private static final String FIELD_VALUE = "value";
    private static final String FIELD_COUNT = "count";


    @SerializedName(FIELD_VALUE)
    private Value mValue;
    @SerializedName(FIELD_COUNT)
    private int mCount;


    public RSSModel(){

    }

    public void setValue(Value value) {
        mValue = value;
    }

    public Value getValue() {
        return mValue;
    }

    public void setCount(int count) {
        mCount = count;
    }

    public int getCount() {
        return mCount;
    }


}