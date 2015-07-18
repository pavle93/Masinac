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
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.radoman.masinac.R;
import com.radoman.masinac.model.Teme;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ContactViewHolder> {

    List<Teme> postovi;
    Context context;

    public PostAdapter(List<Teme> postovi, Context context) {
        this.postovi = postovi;
        this.context = context;
    }

    public PostAdapter() {

    }


    @Override
    public int getItemCount() {

        return postovi.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int i) {


        Typeface font = Typeface.createFromAsset(context.getAssets(), "fontawesome-webfont.ttf");
        System.out.println(postovi.get(i).getNaslov());
        contactViewHolder.postNaslov.setText(postovi.get(i).getNaslov());
        contactViewHolder.postContent.setText(Html.fromHtml(postovi.get(i).getContent()));



        String ss = "";
        String prefix = "http://nastava.mas.bg.ac.rs/nastava/";
        for(int j = 0;j<postovi.get(i).getAttachments().size();j++)
        {

            String link =  postovi.get(i).getAttachments().get(j).getLink();
            String name = postovi.get(i).getAttachments().get(j).getNaziv();
            link = link.substring(1);
            link = prefix + link;

            ss = ss + "<a href=\""+link+"\">"+name+"</a><br>";


        }

        contactViewHolder.attachments.setText(Html.fromHtml(ss));
        contactViewHolder.attachments.setMovementMethod(LinkMovementMethod.getInstance());




    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.post_card_view, viewGroup, false);

        return new ContactViewHolder(itemView);
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {

        protected TextView postNaslov;
        protected TextView postContent;
        protected TextView attachments;


        public ContactViewHolder(View v) {
            super(v);
            postNaslov =  (TextView) v.findViewById(R.id.title);
            postContent = (TextView)  v.findViewById(R.id.postBody);
            attachments = (TextView)  v.findViewById(R.id.postAttachments);

        }
    }
}