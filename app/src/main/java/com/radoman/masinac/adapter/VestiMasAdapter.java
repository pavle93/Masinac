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

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.radoman.masinac.R;
import com.radoman.masinac.model.CustomLinkMovementMethod;
import com.radoman.masinac.model.Predmeti;

import java.util.ArrayList;
import java.util.HashMap;

import me.grantland.widget.AutofitTextView;

public class VestiMasAdapter extends RecyclerView.Adapter<VestiMasAdapter.ContactViewHolder> {

    ArrayList<HashMap<String, String>> temeList;
    Predmeti p;
    public VestiMasAdapter(ArrayList<HashMap<String, String>> temeList) {
        this.temeList = temeList;
        p = new Predmeti();
    }


    @Override
    public int getItemCount() {
        return temeList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {

            String date = temeList.get(i).get("date");
            date = date.substring(5,date.length()-2);

            contactViewHolder.naslov.setText(temeList.get(i).get("naslov"));
            contactViewHolder.poslednjeObavestenje.setText(date);
            contactViewHolder.autor.setText(Html.fromHtml(temeList.get(i).get("content")));


        contactViewHolder.autor.setMovementMethod(CustomLinkMovementMethod.getInstance());
        contactViewHolder.autor.setLinksClickable(true);

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_view_clean, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView poslednjeObavestenje;
        protected TextView autor;

        protected AutofitTextView naslov;
        protected TextView poslednjeObavestenjeTema;

        public ContactViewHolder(View v) {
            super(v);
            poslednjeObavestenje =  (TextView) v.findViewById(R.id.txtNaslov);
            autor = (TextView)  v.findViewById(R.id.txtAutor);

            naslov = (AutofitTextView) v.findViewById(R.id.tvTitle);
            poslednjeObavestenjeTema = (TextView) v.findViewById(R.id.txtTema);
        }
    }


}


