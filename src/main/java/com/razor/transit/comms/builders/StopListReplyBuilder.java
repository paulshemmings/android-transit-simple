package com.razor.transit.comms.builders;

import com.razor.transit.comms.IReply;
import com.razor.transit.comms.IReplyBuilder;
import com.razor.transit.comms.IResponse;
import com.razor.transit.comms.replies.ModelReply;
import com.razor.transit.helpers.JsonHelper;
import com.razor.transit.helpers.LogHelper;
import com.razor.transit.models.StopCollectionDTO;

public class StopListReplyBuilder implements IReplyBuilder
{

    @Override
    public IReply buildReply(final IResponse response)
    {
        JsonHelper<StopCollectionDTO> helper
                = new JsonHelper<StopCollectionDTO>(StopCollectionDTO.class);

        StopCollectionDTO stopCollectionDTO
                = helper.fromJson((String) response.getResponseContent());

        ModelReply<StopCollectionDTO> modelReply
                = new ModelReply<StopCollectionDTO>(response.getMessageType());

        modelReply.addItem(stopCollectionDTO);
        modelReply.setResponseCode(0);
        modelReply.setErrorMessage("");

        LogHelper.i("StopListReplyBuilder",
                    "Built ModelReply containing %d routes",
                    stopCollectionDTO.getStops().size());

        return modelReply;
    }
}
