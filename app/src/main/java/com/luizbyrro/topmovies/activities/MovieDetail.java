package com.luizbyrro.topmovies.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.luizbyrro.topmovies.Adapters.ListaFilmesAdapter;
import com.luizbyrro.topmovies.Adapters.ListaTrailerAdapter;
import com.luizbyrro.topmovies.R;
import com.luizbyrro.topmovies.custom.CustomRecyclerView;
import com.luizbyrro.topmovies.custom.MyLinearLayoutManager;
import com.luizbyrro.topmovies.json.FilmeInfo;
import com.luizbyrro.topmovies.json.RetornoConfiguration;
import com.luizbyrro.topmovies.json.RetornoMovieInfo;
import com.luizbyrro.topmovies.json.RetornoTMDB;
import com.luizbyrro.topmovies.json.RetornoTrailer;
import com.luizbyrro.topmovies.json.Trailer;
import com.luizbyrro.topmovies.retrofit.MoviesService;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Luiz Byrro on 03/02/2016.
 */

public class MovieDetail extends AppCompatActivity {

    public static final String MOVIE_DETAIL_KEY = "MOVIE_DETAIL_KEY";
    public static final String CONFIGURATION_KEY = "CONFIGURATION_KEY";



    private RetornoConfiguration configuration;
    private FilmeInfo info;

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;
    ImageView imagemFilme, progress;

    TextView Nome, Duracao, Sinopse, Rating, Ano, Trailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        try {
            Bundle bundle = getIntent().getExtras();
            configuration = (RetornoConfiguration) bundle.getSerializable(CONFIGURATION_KEY);
            info = (FilmeInfo) bundle.getSerializable(MOVIE_DETAIL_KEY);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao acessar lista!", Toast.LENGTH_LONG).show();
            onBackPressed();
        }

        Nome = (TextView) findViewById(R.id.movie_name_detail);
        Duracao = (TextView) findViewById(R.id.duracao_detail);
        Sinopse = (TextView) findViewById(R.id.info_detail);
        Rating = (TextView) findViewById(R.id.rating_detail);
        Ano = (TextView) findViewById(R.id.ano_detail);
        imagemFilme=(ImageView) findViewById(R.id.poster_detail);
        progress=(ImageView) findViewById(R.id.progress_detail);
        Trailer=(TextView) findViewById(R.id.trailer_movie_detail);
        Trailer.setVisibility(View.GONE);

        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_detail);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
/*        Nome.setText(info.getTitle());
        Duracao.setText(info.getOriginal_language());
        Sinopse.setText(info.getOverview());
        Rating.setText(info.getVote_average()+"/10");
        Ano.setText(info.getRelease_date().split("-")[0]);*/
        imagemFilme=(ImageView) findViewById(R.id.poster_detail);

        progress.setVisibility(View.VISIBLE);
        Picasso.with(this).load(configuration.getImages().getBase_url()+configuration.getImages().getPoster_sizes()[2] + info.getPoster_path())
                .fit()
                .error(R.drawable.ic_error)
                .into(imagemFilme, new Callback() {
                    @Override
                    public void onSuccess() {
                        progress.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progress.setVisibility(View.GONE);
                    }
                });


        findViewById(R.id.scroll_movie_detail).setFocusable(true);

        findViewById(R.id.layout_movie_detail).requestFocus();

        getMovieInfoById();
        getMovieTrailerById();
    }


    public void getMovieInfoById(){
        final ProgressDialog load = new ProgressDialog(this);
        load.setMessage("Loading movie info...");
        load.show();
        MoviesService.getMovieById(info.getId(), new retrofit.Callback<RetornoMovieInfo>() {
            @Override
            public void success(RetornoMovieInfo retornoMovieInfo, Response response) {
                load.dismiss();
                if (retornoMovieInfo != null) {
                    if (retornoMovieInfo.getTitle() != null)
                        Nome.setText(retornoMovieInfo.getTitle());
                    if (retornoMovieInfo.getRuntime() != null)
                        Duracao.setText(retornoMovieInfo.getRuntime() + "min");
                    if (retornoMovieInfo.getOverview() != null)
                        Sinopse.setText(retornoMovieInfo.getOverview());
                    if (retornoMovieInfo.getVote_average() != null)
                        Rating.setText(retornoMovieInfo.getVote_average() + "/10");
                    if (retornoMovieInfo.getRelease_date() != null)
                        Ano.setText(retornoMovieInfo.getRelease_date().split("-")[0]);
                }

                Log.d("retornoMovieInfo", retornoMovieInfo.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                load.dismiss();
                Log.d("erro", error.toString());
                Toast.makeText(MovieDetail.this, "Connection Error: Try Again!", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });
    }


    public void getMovieTrailerById(){
        final ProgressDialog load = new ProgressDialog(this);
        load.setMessage("Loading movie trailers...");
        load.show();
        MoviesService.getTrailerById(info.getId(), new retrofit.Callback<RetornoTrailer>() {
            @Override
            public void success(RetornoTrailer retornoTrailer, Response response) {
                load.dismiss();
                if (retornoTrailer != null) {
                    if (retornoTrailer.getResults() != null) {
                        if (retornoTrailer.getResults().length == 0) {
                            Trailer.setVisibility(View.GONE);
                        } else {
                            Trailer.setVisibility(View.VISIBLE);
                            final LinearLayoutManager layoutManager = new MyLinearLayoutManager(MovieDetail.this, LinearLayoutManager.VERTICAL, false);
                            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_detail);
                            mAdapter = new ListaTrailerAdapter(MovieDetail.this, retornoTrailer.getResults());
                            recyclerView.setLayoutManager(layoutManager);
                            recyclerView.setAdapter(mAdapter);
                        }
                    }
                }

                Log.d("retornoMovieInfo", retornoTrailer.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                load.dismiss();
                Log.d("erro", error.toString());
                Toast.makeText(MovieDetail.this, "Connection Error: Try Again!", Toast.LENGTH_LONG).show();
                onBackPressed();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the left-facing triangle icon on the main android toolbar.
                // if this doesn't work as desired, another possibility is to call `finish()` here.
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
