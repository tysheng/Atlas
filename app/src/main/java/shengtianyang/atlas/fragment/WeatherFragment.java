package shengtianyang.atlas.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import shengtianyang.atlas.R;
import shengtianyang.atlas.app.AtlasApp;
import shengtianyang.atlas.utils.Constant;

/**
 * Created by shengtianyang on 16/1/31.
 */
public class WeatherFragment extends Fragment {


    @Bind(R.id.tv_aqi)
    TextView tvAqi;
    @Bind(R.id.tc_qlty)
    TextView tcQlty;
    @Bind(R.id.tv_city)
    TextView tvCity;
    @Bind(R.id.tv_brf)
    TextView tvBrf;
    @Bind(R.id.tv_txt)
    TextView tvTxt;
    @Bind(R.id.tv_tmp)
    TextView tvTmp;
    @Bind(R.id.tv_cond)
    TextView tvCond;

    public WeatherFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.bind(this, view);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constant.HF_WEATHER_API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.optJSONArray("HeWeather data service 3.0");
                    JSONObject object1 = array.optJSONObject(0);
                    JSONObject object2 = object1.optJSONObject("aqi");
                    JSONObject basic = object1.optJSONObject("basic");
                    JSONObject now = object1.optJSONObject("now");
                    JSONObject suggestion = object1.optJSONObject("suggestion");
                    JSONObject comf = suggestion.optJSONObject("comf");
                    JSONArray hourly_forecast = object1.optJSONArray("hourly_forecast");
                    JSONObject hourforecast = hourly_forecast.getJSONObject(0);
                    JSONObject object3 = object2.optJSONObject("city");
                    String aqi = object3.optString("aqi");
                    String qlty = object3.optString("qlty");
                    String brf = comf.optString("brf");
                    String txt = comf.optString("txt");
                    String tmp = hourforecast.optString("tmp");
                    String city_name = basic.optString("city");
                    String cond = now.optJSONObject("cond").optString("txt");
                    tvAqi.setText(aqi);
                    tvBrf.setText(brf);
                    tvCity.setText(city_name);
                    tvTmp.setText(tmp+"°");
                    tvTxt.setText(txt);
                    tcQlty.setText(qlty);
                    tvCond.setText(cond);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> header = new HashMap<String, String>();
                header.put("apikey", Constant.HF_WEATHER_APIKEY);
                return header;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("city", "shaoxing");
                return params;
            }
        };
        stringRequest.setTag("WeatherFragment");
        AtlasApp.getRequestQueue().add(stringRequest);


        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
