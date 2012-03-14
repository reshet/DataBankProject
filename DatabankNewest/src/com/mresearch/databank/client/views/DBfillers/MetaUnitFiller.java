package com.mresearch.databank.client.views.DBfillers;

import com.mresearch.databank.shared.JSON_Representation;
import com.mresearch.databank.shared.MetaUnitDTO;

public interface MetaUnitFiller {
	String getUniqueName();
	String getFilledValue();
	JSON_Representation getJSON();
	MetaUnitDTO getDTO();
}
