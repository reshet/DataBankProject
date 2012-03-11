package com.mresearch.databank.client.views;

//public interface ICatalogizable<Type> {
//	public Type getType();
//}
public interface IFiltersVisitor{
	public void registerFilter(IFilterProvider provider);
}