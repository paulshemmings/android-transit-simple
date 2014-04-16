package com.razor.transit.handlers;

import com.razor.transit.comms.IReplyHandler;
import com.razor.transit.comms.IRequestContent;
import com.razor.transit.comms.IRequestHandler;
import com.razor.transit.comms.providers.TransitRequestHandler;
import com.razor.transit.helpers.LogHelper;
import com.razor.transit.viewmodels.IViewModel;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.fail;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class AgencyHandlerTest {

    private IRequestHandler testRequestHandler = null;

    private final String rootUrl = "http://vast-ridge-9517.herokuapp.com/services/transit-public/";
    private final String messageUrl = "getAgencies";

    @Before
    public void setUp() throws Exception {
        this.testRequestHandler = new IRequestHandler()
        {
            @Override
            public void handleRequest(int messageType,
                                      IRequestContent content,
                                      IReplyHandler replyHandler)
            {
            }
        };
        LogHelper.turnLoggingOff(true);
    }

    @Test
    public void shouldRetrieveAgencyList() {

        AgencyHandler.OnAgenciesLoadedListener listener = new AgencyHandler.OnAgenciesLoadedListener(){

            @Override
            public void onAgenciesLoaded(List<IViewModel> agencyItems)
            {
                int count = agencyItems.size();
                LogHelper.i("AgencyHandlerTest", String.valueOf(count));
            }

            @Override
            public void onAgencyLoadFailed(String errorMessage)
            {
            }
        };

        AgencyHandler agencyHandler = new AgencyHandler();
        agencyHandler.setRequestHandler(testRequestHandler);

        agencyHandler.listAgencies(listener);
    }
}