package API;

import java.util.List;

import bean.Candidatos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface ApiServices {

    @GET("/candidato/{id}")
    Call<Candidatos> getCandidato(@Header("Content-Type") String content_type, @Path("id") Long id);

    @GET("/candidato/list")
    Call<List<Candidatos>> findAllCandidato(@Header("Content-Type") String content_type);

    @GET("/candidato/{id}/vota")
    Call<Candidatos> getVotar(@Header("Content-Type") String content_type, @Path("id") Long id);

}
