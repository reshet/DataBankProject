package com.mresearch.databank.shared;

public interface ICatalogizationConceptBindable<ContentsType extends ICatalogizable> {
	public void refreshContents();
	public void attachCBinder(ConceptBinder<ContentsType> binder);
	public boolean isBinderAttached();
}
