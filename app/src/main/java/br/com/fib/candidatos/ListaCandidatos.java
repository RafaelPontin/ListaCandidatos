package br.com.fib.candidatos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

import API.ApiClient;
import API.ApiServices;
import adapter.ListaAdapter;
import bean.Candidatos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ListaCandidatos extends AppCompatActivity {

    private ApiServices apiServices;
    private ProgressDialog progress = null;
    private ListView lista;
    private ListaAdapter adapter;
    private List<Candidatos> candidatos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_candidatos);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(myToolbar);

        lista = findViewById(R.id.lista);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int posicao, long id) {
                //tela do voto
                Intent intent = new Intent(ListaCandidatos.this, CandidatoActivity.class);
                intent.putExtra("id", candidatos.get(posicao).getId());
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getCandidatos();
    }

    private void getCandidatos()
    {
        progress = ProgressDialog.show(ListaCandidatos.this,
                "Aguarde ...", "Recebendo informações da web", true, true);

        //cria obj do para comunicar com o web service
        apiServices = ApiClient.getClient().create(ApiServices.class);

        Call<List<Candidatos>> call = apiServices.findAllCandidato ("application/json");

        call.enqueue(new Callback<List<Candidatos>>() {
            @Override
            public void onResponse(Call<List<Candidatos>> call, Response<List<Candidatos>> response) {
                if (response.isSuccessful()) {
                    candidatos = response.body();
                    updateList();
                    progress.dismiss();
                } else {
                    progress.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<Candidatos>> call, Throwable t) {
                //colocar msg de erro
                t.printStackTrace();
            }
        });
    }

    private void updateList() {
        adapter = new ListaAdapter(getApplicationContext(), candidatos);
        lista.setAdapter(adapter);
    }


}
