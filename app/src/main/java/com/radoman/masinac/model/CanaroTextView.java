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

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;



/**
 * Created by Dmytro Denysenko on 5/6/15.
 */
public class CanaroTextView extends TextView {
    public CanaroTextView(Context context) {
        this(context, null);
        try{
            Typeface type = Typeface.createFromAsset(context.getResources().getAssets(), "fonts/Roboto-Bold.ttf");
            setTypeface(type);
            isInEditMode();
        }catch (Exception e)
        {

        }
    }

    public CanaroTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        try{
            Typeface type = Typeface.createFromAsset(context.getResources().getAssets(), "fonts/Roboto-Bold.ttf");
            setTypeface(type);
            isInEditMode();
        }catch (Exception e)
        {

        }
    }

    public CanaroTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try{
            Typeface type = Typeface.createFromAsset(context.getResources().getAssets(), "fonts/Roboto-Bold.ttf");
            setTypeface(type);
            isInEditMode();
        }catch (Exception e)
        {

        }




    }

}