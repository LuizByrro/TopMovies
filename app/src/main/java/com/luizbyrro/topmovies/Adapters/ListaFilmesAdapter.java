package com.luizbyrro.topmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.luizbyrro.topmovies.activities.MovieDetail;
import com.luizbyrro.topmovies.json.Collection;
import com.luizbyrro.topmovies.json.FilmeInfo;
import com.luizbyrro.topmovies.R;
import com.luizbyrro.topmovies.json.RetornoConfiguration;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Created by Luiz Byrro on 03/02/2016.
 */



public class ListaFilmesAdapter extends RecyclerView.Adapter<ListaFilmesAdapter.ViewHolder> {


    List<FilmeInfo> mItems;
    RetornoConfiguration retornoConfiguration;
    Context context;

    private String baseURL;

    public ListaFilmesAdapter(Context context) {
        super();
        this.context = context;
        mItems = new ArrayList<FilmeInfo>();



    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layoutposter, viewGroup, false);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();
        int height = windowManager.getDefaultDisplay().getHeight();
        if(width<height) {
            v.setLayoutParams(new RecyclerView.LayoutParams((width/2), (int)((width/2)*1.5)));
        }else{
            v.setLayoutParams(new RecyclerView.LayoutParams((width/2), (int)((width/2)*1.5)));
        }

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        StaggeredGridLayoutManager.LayoutParams layoutParams = new StaggeredGridLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setFullSpan(true);

        final FilmeInfo filmeInfo = mItems.get(i);

        final ImageView progress = viewHolder.progress;
        progress.setVisibility(View.VISIBLE);
        Picasso.with(context).load(baseURL + filmeInfo.getPoster_path())
                .fit()
                .error(R.drawable.ic_error)
                .into(viewHolder.poster, new Callback() {

                    @Override
                    public void onSuccess() {
                        progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progress.setVisibility(View.GONE);
                    }
                });

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), MovieDetail.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putSerializable(MovieDetail.MOVIE_DETAIL_KEY, filmeInfo);
                bundle.putSerializable(MovieDetail.CONFIGURATION_KEY, retornoConfiguration);
                intent.putExtras(bundle);
                context.startActivity(intent);


            }
        });




        //viewHolder.enderecoLista.setText(address.getAddress());


        //viewHolder.tvNature.setText(nature.getName());
        //viewHolder.tvDesNature.setText(nature.getDes());
        // viewHolder.imgThumbnail.setImageResource(nature.getThumbnail());
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView poster;
        public ImageView progress;


        public ViewHolder(View itemView) {
            super(itemView);
            poster = (ImageView) itemView.findViewById(R.id.imagem_poster);
            progress = (ImageView) itemView.findViewById(R.id.progress);
        }
    }


    public void removeAt(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mItems.size());
    }

    public void add(FilmeInfo filmeInfo) {
        int position = mItems.size();
        mItems.add(position, filmeInfo);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mItems.size());
    }

    public void setBaseURL(String string) {
        this.baseURL = string;
    }

    public void setRetornoConfiguration(RetornoConfiguration retornoConfiguration) {
        this.retornoConfiguration = retornoConfiguration;
    }

    public void clear(){
        mItems.clear();
        notifyDataSetChanged();
    }

    public void sortByRating(){

        Collections.sort(mItems, new Comparator<FilmeInfo>() {
            @Override
            public int compare(FilmeInfo lhs, FilmeInfo rhs) {
                return rhs.getVote_average().compareTo(lhs.getVote_average());
            }
        });
        notifyDataSetChanged();
    }
    public void sortByPopularity(){

        Collections.sort(mItems, new Comparator<FilmeInfo>() {
            @Override
            public int compare(FilmeInfo lhs, FilmeInfo rhs) {
                return rhs.getPopularity().compareTo(lhs.getPopularity());
            }
        });
        notifyDataSetChanged();
    }
}



