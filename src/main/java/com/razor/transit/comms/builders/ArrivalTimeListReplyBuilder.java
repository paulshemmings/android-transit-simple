package com.razor.transit.comms.builders;

import com.razor.transit.comms.IReply;
import com.razor.transit.comms.IReplyBuilder;
import com.razor.transit.comms.IResponse;
import com.razor.transit.comms.replies.ModelReply;
import com.razor.transit.helpers.JsonHelper;
import com.razor.transit.models.ArrivalTimeCollectionDTO;

public class ArrivalTimeListReplyBuilder implements IReplyBuilder
{
    @Override
    public IReply buildReply(final IResponse response)
    {
        JsonHelper<ArrivalTimeCollectionDTO> helper
                = new JsonHelper<ArrivalTimeCollectionDTO>(ArrivalTimeCollectionDTO.class);

        ArrivalTimeCollectionDTO arrivalTimeCollectionDTO =
                helper.fromJson((String) response.getResponseContent());

        ModelReply<ArrivalTimeCollectionDTO> modelReply =
                new ModelReply<ArrivalTimeCollectionDTO>(response.getMessageType());

        modelReply.addItem(arrivalTimeCollectionDTO);
        modelReply.setResponseCode(0);
        modelReply.setErrorMessage("");

        return modelReply;
    }
}