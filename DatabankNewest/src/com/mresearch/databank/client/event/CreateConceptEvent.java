package com.mresearch.databank.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.mresearch.databank.shared.CatalogConceptDTO;

public class CreateConceptEvent extends GwtEvent<CreateConceptEventHandler> {
	public static Type<CreateConceptEventHandler> TYPE = new Type<CreateConceptEventHandler>();
	private CatalogConceptDTO dto;
	public CreateConceptEvent(CatalogConceptDTO dto)
	{
		this.setDto(dto);
	}
	@Override
	public Type<CreateConceptEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(CreateConceptEventHandler handler) {
		handler.onCreateConcept(this);
	}
	public CatalogConceptDTO getDto() {
		return dto;
	}
	public void setDto(CatalogConceptDTO dto) {
		this.dto = dto;
	}

}
