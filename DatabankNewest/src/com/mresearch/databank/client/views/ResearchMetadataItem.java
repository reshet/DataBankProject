package com.mresearch.databank.client.views;

import com.google.gwt.user.client.ui.TreeItem;
import com.mresearch.databank.shared.SocioResearchDTO_Light;

public class ResearchMetadataItem extends ConceptContentsItem{
	public ResearchMetadataItem(SocioResearchDTO_Light dto)
	{
		super(dto);
		setText("Метаданные");
	}
}
