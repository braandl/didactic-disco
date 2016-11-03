package groovey.didactic.disco.org.didacticdisco.network;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface DiscoService {

    @POST("/lineRequest")
    Call<DrawResponse> postLine(@Body LineRequest lineRequest);

}
