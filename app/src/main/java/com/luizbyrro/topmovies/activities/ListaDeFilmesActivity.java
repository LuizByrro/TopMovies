package com.luizbyrro.topmovies.activities;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.luizbyrro.topmovies.Adapters.ListaFilmesAdapter;
import com.luizbyrro.topmovies.R;
import com.luizbyrro.topmovies.json.RetornoConfiguration;
import com.luizbyrro.topmovies.json.RetornoTMDB;
import com.luizbyrro.topmovies.retrofit.MoviesService;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by Luiz Byrro on 03/02/2016.
 */

public class ListaDeFilmesActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter mAdapter;


    private Button more;

    private int numeroDaPagina =1;

    private String tipoPesquisa = "POPULAR";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_de_filmes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mAdapter = new ListaFilmesAdapter(ListaDeFilmesActivity.this);
        mRecyclerView.setAdapter(mAdapter);





        more =(Button)findViewById(R.id.load);

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                if(numeroDaPagina<=1000) {
                    //((ListaFilmesAdapter)mAdapter).clear();
                    getTopMovies();
                    numeroDaPagina++;
                }

            }
        });
        getConfiguration();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista_de_filmes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();



        if (id == R.id.action_populares) {

            tipoPesquisa="POPULAR";
            ((ListaFilmesAdapter)mAdapter).sortByPopularity();
            mRecyclerView.getLayoutManager().scrollToPosition(0);
            return true;
        }

        if (id == R.id.action_melhorRating) {

            tipoPesquisa="RATE";
            ((ListaFilmesAdapter)mAdapter).sortByRating();
            mRecyclerView.getLayoutManager().scrollToPosition(0);
            return true;
        }

        if (id == R.id.action_clearList) {
            ((ListaFilmesAdapter)mAdapter).clear();
            numeroDaPagina=1;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void getConfiguration(){
        final ProgressDialog load = new ProgressDialog(this);
        load.setMessage("Configuring...");
        load.show();
        MoviesService.getConfiguration(new Callback<RetornoConfiguration>() {
            @Override
            public void success(RetornoConfiguration retornoConfiguration, Response response) {
                load.dismiss();
                if (retornoConfiguration != null) {
                    ((ListaFilmesAdapter) mAdapter).setBaseURL(retornoConfiguration.getImages().getBase_url() + "/" + retornoConfiguration.getImages().getPoster_sizes()[3]);
                    ((ListaFilmesAdapter) mAdapter).setRetornoConfiguration(retornoConfiguration);

                    getTopMovies();
                    numeroDaPagina++;
                }


                Log.d("retorno", retornoConfiguration.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                load.dismiss();
                Log.d("erro", error.toString());
                Toast.makeText(ListaDeFilmesActivity.this, "Connection Error: Try Again!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getTopMovies(){
        final ProgressDialog load = new ProgressDialog(this);
        load.setMessage("Loading movies...");
        load.show();
        MoviesService.getTopPopularityMovies(String.valueOf(numeroDaPagina), new Callback<RetornoTMDB>() {
            @Override
            public void success(RetornoTMDB retornoTMDB, Response response) {
                load.dismiss();
                if (retornoTMDB != null) {
                    if (retornoTMDB.getResults() != null) {
                        for (int i = 0; i < retornoTMDB.getResults().length; i++) {
                            ((ListaFilmesAdapter) mAdapter).add(retornoTMDB.getResults()[i]);
                        }
                        if(tipoPesquisa=="RATE")((ListaFilmesAdapter)mAdapter).sortByRating();
                        if(tipoPesquisa=="POPULAR")((ListaFilmesAdapter)mAdapter).sortByPopularity();
                    }
                }


                Log.d("retornoTMDB", retornoTMDB.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                load.dismiss();
                if (numeroDaPagina > 0) numeroDaPagina--;
                Log.d("erro", error.toString());
                Toast.makeText(ListaDeFilmesActivity.this, "Connection Error: Try Again!", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getTopMoviesByRating(){
        final ProgressDialog load = new ProgressDialog(this);
        load.setMessage("Loading movies...");
        load.show();
        MoviesService.getTopRatedMovies(String.valueOf(numeroDaPagina), new Callback<RetornoTMDB>() {
            @Override
            public void success(RetornoTMDB retornoTMDB, Response response) {
                load.dismiss();
                if (retornoTMDB != null) {
                    if (retornoTMDB.getResults() != null) {
                        for (int i = 0; i < retornoTMDB.getResults().length; i++) {
                            ((ListaFilmesAdapter) mAdapter).add(retornoTMDB.getResults()[i]);
                        }
                    }
                }


                Log.d("retornoTMDB", retornoTMDB.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                load.dismiss();
                if (numeroDaPagina > 0) numeroDaPagina--;
                Log.d("erro", error.toString());

                Toast.makeText(ListaDeFilmesActivity.this, "Connection Error: Try Again!", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
