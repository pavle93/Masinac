package com.radoman.masinac.adapter;

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
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.radoman.masinac.R;

import java.util.ArrayList;
import java.util.HashMap;

public class TemeAdapter extends RecyclerView.Adapter<TemeAdapter.ContactViewHolder> {

    ArrayList<HashMap<String, String>> temeList;
    int forum_id;
    Context context;

    public TemeAdapter(ArrayList<HashMap<String, String>> temeList, Context context) {
        this.temeList = temeList;
        this.forum_id = forum_id;
        this.context = context;
    }

    public TemeAdapter() {

    }


    @Override
    public int getItemCount() {

        return temeList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {


        Typeface font = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");

        contactViewHolder.vTitle.setText(temeList.get(i).get("naslov"));
        contactViewHolder.vAutor.setTypeface(font);
        if(Boolean.parseBoolean(temeList.get(i).get("attachment"))==true)
            contactViewHolder.vAutor.setText(context.getString(R.string.fa_paperclip)+" "+ "Autor: "+temeList.get(i).get("autor")+"\n"+"Datum: "+temeList.get(i).get("datum"));
        else
            contactViewHolder.vAutor.setText("Autor: " + temeList.get(i).get("autor") + "\n" + "Datum: " + temeList.get(i).get("datum"));
        contactViewHolder.vTitle.setTag(temeList.get(i).get("thread_id"));


    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.teme_card_view, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView vTitle;
        protected TextView vAutor;


        public ContactViewHolder(View v) {
            super(v);
            vTitle =  (TextView) v.findViewById(R.id.title);
            vAutor = (TextView)  v.findViewById(R.id.txtTemeAutor);

        }
    }
}