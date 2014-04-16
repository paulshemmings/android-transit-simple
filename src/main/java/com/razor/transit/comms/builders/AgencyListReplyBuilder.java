package com.razor.transit.comms.builders;

import com.razor.transit.comms.IReply;
import com.razor.transit.comms.IReplyBuilder;
import com.razor.transit.comms.IResponse;
import com.razor.transit.comms.replies.ModelReply;
import com.razor.transit.helpers.JsonHelper;
import com.razor.transit.models.AgencyCollectionDTO;

public class AgencyListReplyBuilder implements IReplyBuilder
{
    @Override
    public IReply buildReply(final IResponse response)
    {
        JsonHelper<AgencyCollectionDTO> helper
                = new JsonHelper<AgencyCollectionDTO>(AgencyCollectionDTO.class);

        AgencyCollectionDTO agencyCollectionDTO =
                helper.fromJson((String) response.getResponseContent());

        ModelReply<AgencyCollectionDTO> modelReply =
                new ModelReply<AgencyCollectionDTO>(response.getMessageType());

        modelReply.addItem(agencyCollectionDTO);
        modelReply.setResponseCode(0);
        modelReply.setErrorMessage("");

        return modelReply;
    }
}
