package com.razor.transit.comms.builders;

import com.razor.transit.comms.IReply;
import com.razor.transit.comms.IReplyBuilder;
import com.razor.transit.comms.IResponse;
import com.razor.transit.comms.replies.ModelReply;
import com.razor.transit.helpers.JsonHelper;
import com.razor.transit.helpers.LogHelper;
import com.razor.transit.models.RouteCollectionDTO;

public class RouteListReplyBuilder implements IReplyBuilder {

    @Override
    public IReply buildReply(final IResponse response)
    {
        JsonHelper<RouteCollectionDTO> helper
                = new JsonHelper<RouteCollectionDTO>(RouteCollectionDTO.class);

        RouteCollectionDTO routeCollectionDTO
                = helper.fromJson((String) response.getResponseContent());

        ModelReply<RouteCollectionDTO> modelReply
                = new ModelReply<RouteCollectionDTO>(response.getMessageType());

        modelReply.addItem(routeCollectionDTO);
        modelReply.setResponseCode(0);
        modelReply.setErrorMessage("");

        LogHelper.i("RouteListReplyBuilder",
                    "Built ModelReply containing %d routes",
                    routeCollectionDTO.getRoutes().size());

        return modelReply;
    }
}
