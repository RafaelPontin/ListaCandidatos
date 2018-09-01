package br.com.fib.candidatos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import API.ApiClient;
import API.ApiServices;
import bean.Candidatos;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CandidatoActivity extends AppCompatActivity {

    private Long idCandidato;
    private ProgressDialog progress = null;
    private ApiServices apiServices;
    private Candidatos candidato;
    private ImageView imageViewFotoCandidato;
    private Button buttonBtVotar;
    private TextView totalVotos, txtNome, txtPartido, txtDetalhes, txtSite, txtPropostas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidato);

        imageViewFotoCandidato = (ImageView) findViewById(R.id.activity_fotocandidato);
        buttonBtVotar = (Button) findViewById(R.id.btvotar);
        totalVotos = (TextView) findViewById(R.id.txtTotalVotos);
        txtNome = (TextView) findViewById(R.id.txtNome);
        txtPartido = (TextView) findViewById(R.id.txtPartido);
        txtDetalhes = (TextView) findViewById(R.id.txtDetalhes);
        txtSite = (TextView) findViewById(R.id.txtSite);
        txtPropostas = (TextView) findViewById(R.id.txtPropostas);

        buttonBtVotar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress = ProgressDialog.show(CandidatoActivity.this,
                        "Aguarde ...", "Recebendo informações da web", true, true);
                apiServices = ApiClient.getClient().create(ApiServices.class);
                Call<Candidatos> call = apiServices.getVotar("application/json", idCandidato);
                call.enqueue(new Callback<Candidatos>() {
                    @Override
                    public void onResponse(Call<Candidatos> call, Response<Candidatos> response)
                    {
                        if (response.isSuccessful()) {
                            Toast.makeText(CandidatoActivity.this, "Voto para o candidato:  " + candidato.getNome(),
                                    Toast.LENGTH_SHORT).show();

                        }
                        progress.dismiss();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Candidatos> call, Throwable t) {
                        progress.dismiss();
                        t.printStackTrace();
                    }
                });


            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        idCandidato = intent.getLongExtra("id", Constants.CANDIDATO_PADRAO);
        if(intent != null){
            getCandidato(idCandidato);
        }

    }


    private void getCandidato(Long id)
    {
        progress = ProgressDialog.show(CandidatoActivity.this,
                "Aguarde ...", "Recebendo informações da web", true, true);
        apiServices = ApiClient.getClient().create(ApiServices.class);
        Call<Candidatos> call = apiServices.getCandidato("application/json", id);
        call.enqueue(new Callback<Candidatos>() {
            @Override
            public void onResponse(Call<Candidatos> call, Response<Candidatos> response)
            {
               //tela
                if (response.isSuccessful()) {
                    candidato = response.body();
                    if (candidato != null)
                    {
                        Ion.with(imageViewFotoCandidato)
                                .centerCrop()
                                .placeholder(R.drawable.place_holder)
                                .error(R.drawable.error)
                                .load(Constants.PATH_URL + "/" + candidato.getFoto());
                        totalVotos.setText("Votos: " + String.valueOf(candidato.getTotalVotos()));
                        txtDetalhes.setText("Detalhes: " + candidato.getDetalhes());
                        txtNome.setText("Nome: " + candidato.getNome());
                        txtPartido.setText("Partido: " + candidato.getPartido());
                        txtSite.setText("Site: " + candidato.getSite());
                        txtPropostas.setText("Propostas: " + candidato.getPropostas());
                    }
                    progress.dismiss();
                }
                else
                {
                    progress.dismiss();
                }
            }


            @Override
            public void onFailure(Call<Candidatos> call, Throwable t) {
                progress.dismiss();
                t.printStackTrace();
            }

          }
        );
    }
}

