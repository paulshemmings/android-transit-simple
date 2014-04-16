package com.razor.transit.comms.handlers;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.razor.transit.comms.IHandler;
import com.razor.transit.comms.IRequest;
import com.razor.transit.comms.IResponse;
import com.razor.transit.helpers.LogHelper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

public class SimpleJsonHandler extends AbstractHandler implements IHandler {

    @Override
    protected HttpEntity buildEntity(final IRequest restRequest) throws Exception{

        String json = restRequest.getJSON();
        LogHelper.w("SimpleJsonHandler-Entity", json);

        StringEntity se = new StringEntity(json, HTTP.UTF_8);
        se.setContentType("application/json; charset=utf-8");
        return se;
    }

    @Override
    protected void storeResponse(final HttpResponse httpResponse,
                                 final IResponse response) throws Exception{
        try{
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            response.setResponseContent(sb.toString());
            response.setResponseCode((long) (sb == null ? -1 : 0));

            LogHelper.w("SimpleJsonHandler-Response", (String) response.getResponseContent());

        } catch (final Exception ex){
            response.setResponseCode((long) -1);
            response.setErrorMessage(ex.getMessage());
        }
    }


}
