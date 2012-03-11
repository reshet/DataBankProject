package com.mresearch.databank.client.views;

import java.awt.Checkbox;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.CatalogService;
import com.mresearch.databank.client.service.CatalogServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.client.service.UserSocioResearchServiceAsync;
import com.mresearch.databank.shared.FilterBaseDTO;
import com.mresearch.databank.shared.FilterDateDiapasonDTO;
import com.mresearch.databank.shared.FilterDiapasonDTO;
import com.mresearch.databank.shared.FilterMatchDTO;
import com.mresearch.databank.shared.FilterMultiDTO;
import com.mresearch.databank.shared.SSE_DTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO;
import com.mresearch.databank.shared.VarDTO_Light;

public class RootFilterItem extends TreeItem{
//	private VerticalPanel results_viewer;
//	private Button doFilterBtn;
//	private CheckBox doUseFilters;
//	private UserSocioResearchServiceAsync service = GWT.create(UserSocioResearchService.class);
//	private final ArrayList<IFilterProvider> allFiltersRegister = new ArrayList<IFilterProvider>();
//	public RootFilterItem(VerticalPanel display)
//	{
//		super();
//		doFilterBtn = new Button("Поехали!");
//		doUseFilters = new CheckBox("Фильтровать иследования");
//		this.results_viewer = display;
//		//this.service = service;
//		//this.setText("Фильтрация");
//		this.setWidget(doUseFilters);
//		TreeItem filt1 = new TreeItem();
//		FilterRealDiapasonView filtSelectionSize = new FilterRealDiapasonView("Объем выборки") {
//			@Override
//			public FilterDiapasonDTO getFilterDTO() {
//				FilterDiapasonDTO dto = new FilterDiapasonDTO("SocioResearch", "selection_size", getFromValue(), getToValue());
//				return dto;
//			}
//		};
//		allFiltersRegister.add(filtSelectionSize);
//		filt1.setWidget(filtSelectionSize.asWidget());
//		this.addItem(filt1);
//		TreeItem filt2 = new TreeItem();
//		FilterDataDiapasonView filtFieldStart = new FilterDataDiapasonView("Старт поля") {
//			@Override
//			public FilterDiapasonDTO getFilterDTO() {
//				FilterDateDiapasonDTO dto = new FilterDateDiapasonDTO("SocioResearch", "start_date", getFromValue(), getToValue());
//				return dto;
//			}
//		};
//		allFiltersRegister.add(filtFieldStart);
//		filt2.setWidget(filtFieldStart.asWidget());
//		this.addItem(filt2);
//		//allFiltersRegister.add(filtFieldStart);
//		TreeItem filt3 = new TreeItem();
//		FilterDataDiapasonView filtFieldEnd = new FilterDataDiapasonView("Конец поля") {
//			@Override
//			public FilterDiapasonDTO getFilterDTO() {
//				FilterDateDiapasonDTO dto = new FilterDateDiapasonDTO("SocioResearch", "end_date", getFromValue(), getToValue());
//				return dto;
//			}
//		};
//		allFiltersRegister.add(filtFieldEnd);
//		filt3.setWidget(filtFieldEnd.asWidget());
//		this.addItem(filt3);
//		//allFiltersRegister.add(filtFieldEnd);
//		TreeItem filt_gen_selection = new TreeItem();
//		FilterMultiMatchView filtGS = new FilterMultiMatchView("Генеральная совокупность") {
//			@Override
//			public FilterBaseDTO getFilterDTO() {
//				ArrayList<FilterBaseDTO> filters = new ArrayList<FilterBaseDTO>();
//				ArrayList<String> variants = getVariants();
//				int i = 0;
//				for(String variant:variants)
//				{
//					CheckBox cb = (CheckBox)root.getChild(i).getWidget();
//					if(cb.getValue()== true)
//					{
//						FilterMatchDTO dto = new FilterMatchDTO("SocioResearch","gen_geathering", "==", variant);
//						filters.add(dto);
//					}
//					i++;
//				}
//				FilterMultiDTO mult_dto = new FilterMultiDTO(filters);
//				return mult_dto;
//			}
//			
//			@Override
//			public void loadVariants(final FilterMultiMatchView.PostProcess postprocess) {
//				new RPCCall<ArrayList<SSE_DTO>>() {
//					@Override
//					public void onFailure(Throwable caught) {
//						Window.alert("Error loading match filter variants :"+caught.getMessage());
//					}
//					@Override
//					public void onSuccess(ArrayList<SSE_DTO> result) {
//						ArrayList<String> varis = new ArrayList<String>();
//						for(SSE_DTO dto:result)
//						{
//							varis.add(dto.getContents());
//						}
//						variants = varis;
//						postprocess.process();
//					}
//					@Override
//					protected void callService(
//							AsyncCallback<ArrayList<SSE_DTO>> cb) {
//						service.getSSEs("SocioResearch","gengeath", cb);
//					}
//				}.retry(2);
//			}
//		};
//				
//		//allFiltersRegister.add(filtFieldEnd);
//		filt_gen_selection.setWidget(filtGS.asWidget());
//		allFiltersRegister.add(filtGS);
//		this.addItem(filt_gen_selection);
//
//		
//		TreeItem method_item = new TreeItem();
//		FilterMultiMatchView filtMethod = new FilterMultiMatchView("Метод сбора информации") {
//			@Override
//			public FilterBaseDTO getFilterDTO() {
//				ArrayList<FilterBaseDTO> filters = new ArrayList<FilterBaseDTO>();
//				ArrayList<String> variants = getVariants();
//				int i = 0;
//				for(String variant:variants)
//				{
//					CheckBox cb = (CheckBox)root.getChild(i).getWidget();
//					if(cb.getValue()== true)
//					{
//						FilterMatchDTO dto = new FilterMatchDTO("SocioResearch","method", "==", variant);
//						filters.add(dto);
//					}
//					i++;
//				}
//				FilterMultiDTO mult_dto = new FilterMultiDTO(filters);
//				return mult_dto;
//			}
//			
//			@Override
//			public void loadVariants(final FilterMultiMatchView.PostProcess postprocess) {
//				new RPCCall<ArrayList<SSE_DTO>>() {
//					@Override
//					public void onFailure(Throwable caught) {
//						Window.alert("Error loading match filter variants :"+caught.getMessage());
//					}
//					@Override
//					public void onSuccess(ArrayList<SSE_DTO> result) {
//						ArrayList<String> varis = new ArrayList<String>();
//						for(SSE_DTO dto:result)
//						{
//							varis.add(dto.getContents());
//						}
//						variants = varis;
//						postprocess.process();
//					}
//					@Override
//					protected void callService(
//							AsyncCallback<ArrayList<SSE_DTO>> cb) {
//						service.getSSEs("SocioResearch","method", cb);
//					}
//				}.retry(2);
//			}
//		};
//				
//		//allFiltersRegister.add(filtFieldEnd);
//		method_item.setWidget(filtMethod.asWidget());
//		allFiltersRegister.add(filtMethod);
//		this.addItem(method_item);
//
//		TreeItem researchers_item = new TreeItem();
//		FilterMultiMatchView filtResearchers = new FilterMultiMatchView("Исследователи") {
//			@Override
//			public FilterBaseDTO getFilterDTO() {
//				ArrayList<FilterBaseDTO> filters = new ArrayList<FilterBaseDTO>();
//				ArrayList<String> variants = getVariants();
//				int i = 0;
//				for(String variant:variants)
//				{
//					CheckBox cb = (CheckBox)root.getChild(i).getWidget();
//					if(cb.getValue()== true)
//					{
//						FilterMatchDTO dto = new FilterMatchDTO("SocioResearch","researchers", "==", variant);
//						filters.add(dto);
//					}
//					i++;
//				}
//				FilterMultiDTO mult_dto = new FilterMultiDTO(filters);
//				return mult_dto;
//			}
//			
//			@Override
//			public void loadVariants(final FilterMultiMatchView.PostProcess postprocess) {
//				new RPCCall<ArrayList<SSE_DTO>>() {
//					@Override
//					public void onFailure(Throwable caught) {
//						Window.alert("Error loading match filter variants :"+caught.getMessage());
//					}
//					@Override
//					public void onSuccess(ArrayList<SSE_DTO> result) {
//						ArrayList<String> varis = new ArrayList<String>();
//						for(SSE_DTO dto:result)
//						{
//							varis.add(dto.getContents());
//						}
//						variants = varis;
//						postprocess.process();
//					}
//					@Override
//					protected void callService(
//							AsyncCallback<ArrayList<SSE_DTO>> cb) {
//						service.getSSEs("SocioResearch","researcher", cb);
//					}
//				}.retry(2);
//			}
//		};
//				
//		//allFiltersRegister.add(filtFieldEnd);
//		researchers_item.setWidget(filtResearchers.asWidget());
//		allFiltersRegister.add(filtResearchers);
//		this.addItem(researchers_item);
//		
//		TreeItem org_item1 = new TreeItem();
//		FilterMultiMatchView filtOrgs1 = new FilterMultiMatchView("Организация-исполнитель") {
//			@Override
//			public FilterBaseDTO getFilterDTO() {
//				ArrayList<FilterBaseDTO> filters = new ArrayList<FilterBaseDTO>();
//				ArrayList<String> variants = getVariants();
//				int i = 0;
//				for(String variant:variants)
//				{
//					CheckBox cb = (CheckBox)root.getChild(i).getWidget();
//					if(cb.getValue()== true)
//					{
//						FilterMatchDTO dto = new FilterMatchDTO("SocioResearch","org_impl_name", "==", variant);
//						filters.add(dto);
//					}
//					i++;
//				}
//				FilterMultiDTO mult_dto = new FilterMultiDTO(filters);
//				return mult_dto;
//			}
//			
//			@Override
//			public void loadVariants(final FilterMultiMatchView.PostProcess postprocess) {
//				new RPCCall<ArrayList<SSE_DTO>>() {
//					@Override
//					public void onFailure(Throwable caught) {
//						Window.alert("Error loading match filter variants :"+caught.getMessage());
//					}
//					@Override
//					public void onSuccess(ArrayList<SSE_DTO> result) {
//						ArrayList<String> varis = new ArrayList<String>();
//						for(SSE_DTO dto:result)
//						{
//							varis.add(dto.getContents());
//						}
//						variants = varis;
//						postprocess.process();
//					}
//					@Override
//					protected void callService(
//							AsyncCallback<ArrayList<SSE_DTO>> cb) {
//						service.getSSEs("SocioResearch","org_impl", cb);
//					}
//				}.retry(2);
//			}
//		};
//				
//		//allFiltersRegister.add(filtFieldEnd);
//		org_item1.setWidget(filtOrgs1.asWidget());
//		allFiltersRegister.add(filtOrgs1);
//		this.addItem(org_item1);
//		//this.addItem(doUseFilters);
//		this.addItem(doFilterBtn);
//		bindReactions();
//	}
//	private void bindReactions()
//	{
//		doFilterBtn.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				doFiltersProcess();
//			}
//		});
//	}
//	
//	
//	private void doFiltersProcess()
//	{
//		final ArrayList<FilterBaseDTO> filters = new ArrayList<FilterBaseDTO>();
//		for(IFilterProvider provider:allFiltersRegister)
//		{
//			filters.add(provider.getFilterDTO());
//		}
//		
////		new RPCCall<ArrayList<SocioResearchDTO>>() {
////
////			@Override
////			public void onFailure(Throwable caught) {
////				Window.alert("error on gettinf filtering results"+caught.getMessage());
////			}
////
////			@Override
////			public void onSuccess(ArrayList<SocioResearchDTO> result) {
////				results_viewer.clear();
////				for(SocioResearchDTO dto:result)
////				{
////					results_viewer.add(new Label(dto.getName()));
////				}
////			}
////
////			@Override
////			protected void callService(
////					AsyncCallback<ArrayList<SocioResearchDTO>> cb) {
////				service.getResearchSummaries(filters,cb);
////			}
////		}.retry(2);
//		
//		new RPCCall<String>() {
//
//			@Override
//			public void onFailure(Throwable caught) {
//				results_viewer.clear();
//				results_viewer.add(new Label("Error on performing search!    "+caught.getMessage()));
//			}
//
//			@Override
//			public void onSuccess(String result) {
//				results_viewer.clear();
//				results_viewer.add(new Label(result));
//			}
//
//			@Override
//			protected void callService(AsyncCallback<String> cb) {
//				service.doIndexSearch("json-str", cb);
//			}
//		}.retry(2);
//		
//	}
}
