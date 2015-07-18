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
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.radoman.masinac.R;
import com.radoman.masinac.model.Predmeti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import me.grantland.widget.AutofitTextView;

public class PredmetiAdapter extends RecyclerView.Adapter<PredmetiAdapter.ContactViewHolder> {

    ArrayList<HashMap<String, String>> temeList;
    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    ArrayList<HashMap<String, String>> visibleObjects;
    String[] naslovi = null;
    String[] naslovi_visible;
    int[] ids_visible;
    Context c;
    int forum_id;

    public PredmetiAdapter(ArrayList<HashMap<String, String>> temeList,int forum_id,Context c) {
        this.temeList = temeList;
        this.forum_id = forum_id;
        this.c = c;
        this.temeList = temeList;
        this.visibleObjects = temeList;
        prefs = PreferenceManager.getDefaultSharedPreferences(c);
        editor = prefs.edit();
    }

    public PredmetiAdapter() {

    }


    @Override
    public int getItemCount() {

        return visibleObjects.size();
    }

    @Override
    public void onBindViewHolder(final ContactViewHolder contactViewHolder, int i) {
        final int j = i;

        Typeface type = Typeface.createFromAsset(c.getResources().getAssets(), "fontawesome-webfont.ttf");
        contactViewHolder.faNot.setTypeface(type);
        contactViewHolder.notification.setOnCheckedChangeListener(null);


        contactViewHolder.notification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                boolean state = false;
                int forumId = Integer.parseInt(visibleObjects.get(j).get("forum_id"));

                state  = prefs.getBoolean(String.valueOf(forumId),false);

                   editor.putBoolean(String.valueOf(forumId), isChecked);
                editor.apply();

                Log.v("CHECKLISTENRE",visibleObjects.get(j).get("forum_id")+"    "+prefs.getBoolean(String.valueOf(forumId),false));
            }
        });


          Predmeti p = new Predmeti();
          String post_link = visibleObjects.get(i).get("poslednji_post_link");
          contactViewHolder.vTema.setVisibility(View.GONE);

        contactViewHolder.vTitle.setText(visibleObjects.get(i).get("naslov"));
        contactViewHolder.vTitle.setTag(visibleObjects.get(i).get("forum_id"));
        if(!visibleObjects.get(i).get("poslednji_post").contains("[")) {

            contactViewHolder.vName.setText("Poslednje obaveštenje: " + visibleObjects.get(i).get("poslednji_post"));
            contactViewHolder.vSurname.setText("Autor: " + visibleObjects.get(i).get("autor"));
        }
        else {
            contactViewHolder.vName.setVisibility(View.GONE);
            contactViewHolder.vSurname.setVisibility(View.GONE);
        }

        contactViewHolder.notification.setChecked(prefs.getBoolean(contactViewHolder.vTitle.getTag().toString(),false));

    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.card_view, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {



        protected TextView vName;
        protected TextView vSurname;
        protected TextView vEmail;
        protected AutofitTextView vTitle;
        protected AutofitTextView faNot;
        protected TextView vTema;
        protected CheckBox notification;
        public ContactViewHolder(View v) {
            super(v);

            vName =  (TextView) v.findViewById(R.id.txtNaslov);
            vSurname = (TextView)  v.findViewById(R.id.txtAutor);
            notification = (CheckBox)v.findViewById(R.id.cbNot);
            vTitle = (AutofitTextView) v.findViewById(R.id.tvTitle);
            faNot = (AutofitTextView) v.findViewById(R.id.faNot);
            vTema = (TextView) v.findViewById(R.id.txtTema);


        }
    }

    public void flushFilter() {
        visibleObjects=new  ArrayList<>();
        visibleObjects.addAll(temeList);


        notifyDataSetChanged();
    }

    public void setEmptyFilter() {
        ArrayList<HashMap<String, String>> visibleObjects;
        visibleObjects = new ArrayList<>();

        for(int i =0;i<this.visibleObjects.size();i++)
        {
            if(!this.visibleObjects.get(i).get("poslednji_post").isEmpty()) {

                visibleObjects.add(this.visibleObjects.get(i));
            }
        }
        this.visibleObjects = visibleObjects;


        notifyDataSetChanged();
    }

    public void setFilter(String queryText)
    {
        visibleObjects=null;

        visibleObjects = new ArrayList<>();


        for(int i =0;i<temeList.size();i++)
        {

            if(transliterate(temeList.get(i).get("naslov").toUpperCase(new Locale("sr"))).contains(queryText.toUpperCase())) {

                visibleObjects.add(temeList.get(i));


            }


        }


        notifyDataSetChanged();
    }



    public static String transliterate(String message){
        String[] abcCyr =   {
                " ",
                "А",
                "Б",
                "В",
                "Г",
                "Д",
                "Ђ",
                "Е",
                "Ж",
                "З",
                "И",
                "Ј",
                "К",
                "Л",
                "Љ",
                "М",
                "Н",
                "Њ",
                "О",
                "П",
                "Р",
                "С",
                "Т",
                "Ћ",
                "У",
                "Ф",
                "Х",
                "Ц",
                "Ч",
                "Џ",
                "Ш",
        }; String[] abcLat = {
                " ",
                "A",
                "B",
                "V",
                "G",
                "D",
                "Dj",
                "E",
                "Z",
                "Z",
                "I",
                "J",
                "K",
                "L",
                "LJ",
                "M",
                "N",
                "NJ",
                "O",
                "P",
                "R",
                "S",
                "T",
                "C",
                "U",
                "F",
                "H",
                "C",
                "C",
                "DZ",
                "S",
        }; StringBuilder builder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            for(int x = 0; x < abcCyr.length; x++ )
                if (message.charAt(i) == abcCyr[x].charAt(0)) {
                    builder.append(abcLat[x]);
                }

        }
        return builder.toString();

    }
}