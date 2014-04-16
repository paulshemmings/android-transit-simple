package com.razor.transit.comms.handlers;

import com.razor.transit.comms.IHandler;
import com.razor.transit.comms.IProvider;
import com.razor.transit.comms.IRequest;
import com.razor.transit.comms.IResponse;
import com.razor.transit.helpers.LogHelper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerPNames;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

public abstract class AbstractHandler implements IHandler {

    protected abstract HttpEntity buildEntity(IRequest restRequest) throws Exception;
    protected abstract void storeResponse(HttpResponse response, IResponse restResponse) throws Exception;

    public void populateResponse(final IRequest request, final IResponse response) {

        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.setParams(this.buildParameters());
        HttpResponse res;
        try {
            LogHelper.i("RestRequest-Target", request.getCommand());

            if (request.isPost()) {

                HttpPost httpost = new HttpPost(request.getCommand());
                HttpEntity httpEntity = this.buildEntity(request);
                httpost.setEntity(httpEntity);

                HttpResponse httpResponse = httpClient.execute(httpost);
                this.storeResponse(httpResponse, response);

            } else {

                HttpGet httpGet = new HttpGet(request.getCommand());
                HttpResponse httpResponse = httpClient.execute(httpGet);
                this.storeResponse(httpResponse, response);

            }

        } catch (final Exception ex){
            String message = ex.getMessage() == null ? "general failure" : ex.getMessage();
            LogHelper.e("AbstractRestHandler", message);
            response.setErrorMessage(message);
            response.setResponseCode((long) 999);
        }
    }

    /**
     * Return connection timeout duration
     * @return int
     */

    private int getConnectionTimeoutDuration(){
        return 30000;
    }

    /**
     * Return socket timeout duration
     * @return int
     */

    private int getSocketTimeoutDuration(){
        return 60000;
    }

    /**
     * build parameters for REST call
     * @return
     */

    private HttpParams buildParameters(){
        HttpParams params = new BasicHttpParams();
        params.setParameter(ConnManagerPNames.MAX_TOTAL_CONNECTIONS, 30);
        params.setParameter(ConnManagerPNames.MAX_CONNECTIONS_PER_ROUTE, new ConnPerRouteBean(30));
        //params.setParameter(ClientPNames.HANDLE_REDIRECTS, new Boolean(false));
        params.setParameter(HttpProtocolParams.USE_EXPECT_CONTINUE, false);

        HttpProtocolParams.setUseExpectContinue(params, false);
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

        HttpConnectionParams.setConnectionTimeout(params, getConnectionTimeoutDuration());
        HttpConnectionParams.setSoTimeout(params, getSocketTimeoutDuration());
        return params;
    }
}
