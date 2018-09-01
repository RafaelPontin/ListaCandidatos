package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

import java.util.List;

import bean.Candidatos;
import br.com.fib.candidatos.Constants;
import br.com.fib.candidatos.R;

public class ListaAdapter extends BaseAdapter {

    private Context context;
    private List<Candidatos> lista;
    private ViewHolder holder;

    public ListaAdapter(Context context, List<Candidatos> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Candidatos getItem(int position) {

        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {

        return lista.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Candidatos candidatos = lista.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.linha, null);
            holder = new ViewHolder();
            holder.nomePartido = (TextView) convertView.findViewById(R.id.nome_partido);
            holder.fotosCandidatos = (ImageView) convertView.findViewById(R.id.fotocandidato);
            holder.totalVotos = (TextView) convertView.findViewById(R.id.totalVotos);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nomePartido.setText(candidatos.getNome() + " - " + candidatos.getPartido());
        holder.totalVotos.setText(String.valueOf(candidatos.getTotalVotos()) + " VOTOS (" + candidatos.getVotosPercentuais() + "%)");

        Ion.with(holder.fotosCandidatos)
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .error(R.drawable.error)
                .load(Constants.PATH_URL + "/" + candidatos.getFoto());

        return convertView;
    }

    static class ViewHolder {
        TextView nomePartido;
        TextView totalVotos;
        ImageView fotosCandidatos;
    }


}
