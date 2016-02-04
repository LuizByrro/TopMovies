package com.luizbyrro.topmovies.Adapters;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.luizbyrro.topmovies.R;
import com.luizbyrro.topmovies.json.Trailer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Luiz Byrro on 03/02/2016.
 */


public class ListaTrailerAdapter extends RecyclerView.Adapter<ListaTrailerAdapter.ViewHolder> {


    List<Trailer> mItems;
    Context context;

    private String baseYoutubeURL = "https://www.youtube.com/watch?v=";

    public ListaTrailerAdapter(Context context) {
        super();
        this.context = context;

        mItems = new ArrayList<Trailer>();
    }

    public ListaTrailerAdapter(Context context, Trailer[] trailers) {
        super();
        this.context = context;

        mItems = new ArrayList<Trailer>();
        for(int i=0; i<trailers.length;i++){
            mItems.add(trailers[i]);
        }


    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layouttrailer, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        final Trailer trailer = mItems.get(i);

        viewHolder.trailer.setText("Trailer "+String.valueOf(i+1));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(trailer.getSite().equals("YouTube")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Open YouTube?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {



                            if(isYouTubeAppInstalled("com.google.android.youtube")){
                                /*PackageManager pm = context.getPackageManager();
                                Intent launchIntent = pm.getLaunchIntentForPackage("com.google.android.youtube");
                                launchIntent.setData(Uri.parse(baseYoutubeURL + trailer.getKey()+";feature=youtube_gdata"));
                                context.startActivity(launchIntent);*/

                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + trailer.getKey()));
                                context.startActivity(intent);
                            }else{
                                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(baseYoutubeURL + trailer.getKey())));
                            }
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView trailer;
        public CardView carviewtrailer;


        public ViewHolder(View itemView) {
            super(itemView);
            trailer = (TextView) itemView.findViewById(R.id.id_trailer);
            carviewtrailer = (CardView) itemView.findViewById(R.id.card_view_trailer);
        }
    }


    public void removeAt(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mItems.size());
    }

    public void add(Trailer trailer) {
        int position = mItems.size();
        mItems.add(position, trailer);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mItems.size());
    }

    public void clear(){
        mItems.clear();
        notifyDataSetChanged();
    }


    protected boolean isYouTubeAppInstalled(String packageName) {
        Intent mIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        if (mIntent != null) {
            return true;
        }
        else {
            return false;
        }
    }


}



