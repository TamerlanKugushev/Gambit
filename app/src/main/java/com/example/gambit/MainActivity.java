package com.example.gambit;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager=new LinearLayoutManager( this );
    private RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        setActionBarTitle ();

        APIService apiService=APIClient.CreateService ( APIService.class );
        Call<List<Product>> call=apiService.getProductsList( APIConstants.PAGE );
        call.enqueue ( new Callback<List<Product>>() {
            @Override
            public void onResponse( Call<List<Product>> call, Response<List<Product>> response ) {
                if (response.isSuccessful ()) {
                    List<Product> products=response.body ();
                    recyclerView=findViewById ( R.id.recyclerView );
                    recyclerView.setHasFixedSize ( true );
                    recyclerAdapter=new RecyclerAdapter ( products );
                    recyclerView.setLayoutManager ( layoutManager );
                    recyclerView.setAdapter ( recyclerAdapter );
                }
            }

            @Override
            public void onFailure( Call<List<Product>> call, Throwable t ) {

            }
        } );
    }

    private void setActionBarTitle() {
        getSupportActionBar ().setDisplayOptions ( ActionBar.DISPLAY_SHOW_CUSTOM );
        getSupportActionBar ().setCustomView ( R.layout.action_bar_layout );
    }
}