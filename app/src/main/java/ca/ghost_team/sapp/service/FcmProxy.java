package ca.ghost_team.sapp.service;

import ca.ghost_team.sapp.service.dto.FcmBackDTO;
import ca.ghost_team.sapp.service.dto.FcmMessage;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface FcmProxy {
    @Headers({"Content-Type: application/json",
            "Authorization: Key=AAAAlIUSmJg:APA91bHw6UBuWvoc9taeJAJ2tvSoEr7lpckvyD14Yv8cdzrRsX47cQzghsUHfDwKwL_I0IhyITi6z62ErzuGTEhUwdM-6N3Jh2bM0rfKMl8JAhzfE6M04vxfDzipykecWu5M9mD_D4fi"})
    @POST("send")
    Call<FcmBackDTO> sendMessage(@Body FcmMessage msg);
}
