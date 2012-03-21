package com.mresearch.databank.client.presenters;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

import com.mresearch.databank.client.event.OrgListChangedEvent;
import com.mresearch.databank.client.event.OrgListChangedEventHandler;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchServiceAsync;
import com.mresearch.databank.client.views.IPickBinder;
import com.mresearch.databank.client.views.PickElementsTableView;
import com.mresearch.databank.client.views.VariableDetailedView.JSON_Construct;
import com.mresearch.databank.client.views.DBfillers.MetaUnitCollector;
import com.mresearch.databank.client.views.DBfillers.MetaUnitEntityItemRegistrator;
import com.mresearch.databank.client.views.DBfillers.MetaUnitFiller;
import com.mresearch.databank.shared.JSON_Representation;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedStructureDTO;
import com.mresearch.databank.shared.OrgDTO;
import com.mresearch.databank.shared.SSE_DTO;
import com.mresearch.databank.shared.SocioResearchDTO;
import com.mresearch.databank.shared.VarDTO_Light;
import com.mresearch.databank.shared.IPickableElement;
import com.sun.jersey.json.impl.JSONHelper;

public class AdminResearchDetailedPresenter implements Presenter{
	public interface Display
	{
		VerticalPanel getViewPanel();
		VerticalPanel getEditPanel();
		VerticalPanel getGroupEditPanel();
		VerticalPanel getFilesPanel();
		Widget asWidget();
	}
	public interface EditDisplay
	 {
		 long getResearchID();
		 ArrayList<String> getConcepts();
		 ArrayList<String> getPublications();
		 ArrayList<String> getPublications_dois();
		 ArrayList<String> getPublications_urls();
		 ArrayList<String> getResearchers();
		 String getMethod();
		 Long getOrgImplID();
		 Long getOrgOrderedID();
		 String getSelectionApprchCompl();
		 String getSelectionApprchRand();
		 
		 int getSelectionSize();
		 String getGenerealG();
		 Date getStartDate();
		 Date getEndDate();
		 String getName();	
		 long getWeightVarID();
		 HasClickHandlers getCondirmBtn();
		 HasClickHandlers getDeleteBtn();
		 HasClickHandlers getAddOrgImplPopup();
		 
		 HasClickHandlers getAddOrgOrderPopup();
		 
		 HasClickHandlers getCancelAddOrgBtn();
		 HasClickHandlers getAddOrgImplBtn();
		 HasClickHandlers getAddOrgOrderBtn();
		 void setOrgImpl(ArrayList<String> names,ArrayList<Long> ids);
		 String getOrgImplName(long org_impl_id);
		 String getOrgOrderName(long org_order_id);
		 String getWeightVarName(long weight_var_id);
		 void setOrgOrder(ArrayList<String> names,ArrayList<Long> ids);	 
		 void setVarsWeight(ArrayList<String> names,ArrayList<Long> ids);
		 void setGenGeaths(ArrayList<SSE_DTO> sses);
		 void setMethods(ArrayList<SSE_DTO> sses);
		 void setResearches(ArrayList<SSE_DTO> sses);
		 void setConcepts(ArrayList<SSE_DTO> sses);
		 Widget asWidget();
		 OrgDTO getAddOrgDTO();
		 Widget getPopupAddOrg();
		 void setOrgPopupPosition(int x, int y);
		 void setOrgPopupVisibility(boolean b);
		 MetaUnitFiller getDBfiller();
		 MetaUnitCollector getDBcollector();
		 MetaUnitEntityItemRegistrator getDBregistrator();
		 
	 }
	
	public interface GroupEditDisplay
	 {
		 //String getResearchID();
		 ArrayList<String> getConcepts();
		 ArrayList<String> getPublications();
		 ArrayList<String> getPublications_dois();
		 ArrayList<String> getResearchers();
		 String getMethod();
		 String getOrgImplID();
		 String getOrgOrderedID();
		 String getSelectionApprchCompl();
		 String getSelectionApprchRand();
		 
		 //int getSelectionSize();
		 String getGenerealG();
		 Date getStartDate();
		 Date getEndDate();
		 //String getName();	
		 //String getWeightVarID();
		 HasClickHandlers getCondirmBtn();
		 HasClickHandlers getDeleteBtn();
		 HasClickHandlers getAddOrgImplPopup();
		 HasClickHandlers getAddOrgOrderPopup();
		 
		 HasClickHandlers getAddOrgImplBtn();
		 HasClickHandlers getAddOrgOrderBtn();
		 HasClickHandlers getEthalonSelector();
		 void updateViewedDTO(SocioResearchDTO dto);
		 void setOrgImpl(ArrayList<String> names,ArrayList<String> ids);
		 String getOrgImplName(String org_impl_id);
		 String getOrgOrderName(String org_order_id);
		 long getEthalonSelectedID();
		 VerticalPanel getPickResearchesToPropagatePanel();
		// String getWeightVarName(String weight_var_id);
		 void setOrgOrder(ArrayList<String> names,ArrayList<String> ids);	 
		 //void setVarsWeight(ArrayList<String> names,ArrayList<String> ids);
		 void setResearchesAvaible(ArrayList<String> names,ArrayList<Long> ids);
		 Widget asWidget();
		 OrgDTO getAddOrgDTO();
		 Widget getPopupAddOrg();
		 void setOrgPopupPosition(int x, int y);
		 void setOrgPopupVisibility(boolean b);
	 }
	public interface FilesEditDisplay
	 {
		 Widget asWidget();
	 }
	
	 private final Display display;
	 private EditDisplay edit_display;
	 private GroupEditDisplay gr_edit_displ;
	 private FilesEditDisplay files_ed_displ;
	 private final AdminSocioResearchServiceAsync rpcAdminService;
	 private final UserSocioResearchServiceAsync rpcUserService;
	 private final SimpleEventBus eventBus;
	public AdminResearchDetailedPresenter(UserSocioResearchServiceAsync rpcUserSerice,AdminSocioResearchServiceAsync rpcAdminService, SimpleEventBus eventBus,
		      Display view,EditDisplay edit_dspl, GroupEditDisplay gr_edit_displ,FilesEditDisplay files_ed_displ)
	{
		 this.rpcAdminService = rpcAdminService;
		 this.rpcUserService = rpcUserSerice;
		    this.eventBus = eventBus;
		    this.display = view;
		    this.edit_display = edit_dspl;
		    this.gr_edit_displ = gr_edit_displ;
		    this.files_ed_displ = files_ed_displ;
		    this.display.getEditPanel().add(this.edit_display.asWidget());
		    this.display.getGroupEditPanel().add(this.gr_edit_displ.asWidget());
		    this.display.getFilesPanel().add(this.files_ed_displ.asWidget());
		    bind();
	}
	 
	@Override
	public void go(HasWidgets container,ArrayList<String> p_names,ArrayList<String> p_values) {
		 container.clear();
		 container.add(display.asWidget());
		 fetchOrgLists();
		 fetchWeightVarCandidates(edit_display.getResearchID());
		 fetchResearchListData();
		 fetchGenGeathCloud();
		 fetchMethodCloud();
		 fetchResearchersCloud();
		 fetchConceptsCloud();
		 //fetchDatabankStructure();
	}
	public void bind()
	{
		gr_edit_displ.getEthalonSelector().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				long res_id = gr_edit_displ.getEthalonSelectedID();
				fetchResearchDetailes(res_id);
			}
		});
		edit_display.getAddOrgImplBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				addOrgImpl(edit_display.getAddOrgDTO());
				edit_display.setOrgPopupVisibility(false);
			}
		});
		edit_display.getCancelAddOrgBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				edit_display.setOrgPopupVisibility(false);
			}
		});
		
		edit_display.getAddOrgImplPopup().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
//				PopupPanel p = new PopupPanel();
//				p.setSize("400px", "400px");
//				p.setVisible(true);
//				p.setPopupPosition(event.getClientX(), event.getClientY());
//				p.add(new Label("Some text !!!!!!!!!!!"));
//				p.show();
				edit_display.setOrgPopupPosition(event.getClientX(), event.getClientY());
				edit_display.setOrgPopupVisibility(true);
			}
		});
		edit_display.getCondirmBtn().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				updateResearch(composeDTOtoUpdate());
			}
		});
		eventBus.addHandler(OrgListChangedEvent.TYPE, new OrgListChangedEventHandler() {
			@Override
			public void onOrgListChanged(OrgListChangedEvent event) {
				fetchOrgLists();
			}
		});
	}
	private SocioResearchDTO composeDTOtoUpdate()
	{
		SocioResearchDTO dto = new SocioResearchDTO();
		dto.setId(edit_display.getResearchID());
		dto.setName(edit_display.getName());
		dto.setConcepts(edit_display.getConcepts());
		dto.setStart_date(edit_display.getStartDate());
		dto.setEnd_date(edit_display.getEndDate());
		dto.setGen_geathering(edit_display.getGenerealG());
		dto.setSelection_size(edit_display.getSelectionSize());
		//dto.setSelection_appr(edit_display.getSelectionApprch());
		dto.setSel_randomity(edit_display.getSelectionApprchRand());
		dto.setSel_complexity(edit_display.getSelectionApprchCompl());
		dto.setOrg_impl_id(edit_display.getOrgImplID());
		dto.setOrg_order_id(edit_display.getOrgOrderedID());
		dto.setPublications(edit_display.getPublications());
		dto.setPublications_dois(edit_display.getPublications_dois());
		dto.setPublications_urls(edit_display.getPublications_urls());
		dto.setResearchers(edit_display.getResearchers());
		dto.setMethod(edit_display.getMethod());
		dto.setVar_weight_id(edit_display.getWeightVarID());
		dto.setOrg_impl_name(edit_display.getOrgImplName(edit_display.getOrgImplID()));
		dto.setOrg_order_name(edit_display.getOrgOrderName(edit_display.getOrgOrderedID()));
		dto.setVar_weight_name(edit_display.getWeightVarName(edit_display.getWeightVarID()));
		
		JSON_Representation json = edit_display.getDBfiller().getJSON();
		edit_display.getDBregistrator().populateItemsLinksTo(dto.getId(), "socioresearch");
		dto.setJson_desctiptor(json.getObj().toString());
		dto.setFilling(edit_display.getDBcollector().returnCollectedMap());
		return dto;
		
	}
	
	
	
	
	private void fetchDatabankStructure()
	{
		new RPCCall<MetaUnitMultivaluedEntityDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching databank structure "+caught.getMessage());
			}

			@Override
			protected void callService(AsyncCallback<MetaUnitMultivaluedEntityDTO> cb) {
				rpcAdminService.getDatabankStructure("socioresearch", cb);
			}

			@Override
			public void onSuccess(MetaUnitMultivaluedEntityDTO result) {
				Window.alert("SUCCESS fetching databank structure!");
				
			}
		}.retry(2);
	}

	
	
	
	
	
	private void fetchGenGeathCloud()
	{
		new RPCCall<ArrayList<SSE_DTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching sses dtos "+caught.getMessage());
				edit_display.setGenGeaths(new ArrayList<SSE_DTO>());
			}

			@Override
			public void onSuccess(ArrayList<SSE_DTO> result) {
				edit_display.setGenGeaths(result);
			}

			@Override
			protected void callService(AsyncCallback<ArrayList<SSE_DTO>> cb) {
				rpcUserService.getSSEs("SocioResearch","gengeath", cb);
			}
			
		}.retry(2);
	}
	private void fetchMethodCloud()
	{
		new RPCCall<ArrayList<SSE_DTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching sses dtos "+caught.getMessage());
				edit_display.setMethods(new ArrayList<SSE_DTO>());
			}

			@Override
			public void onSuccess(ArrayList<SSE_DTO> result) {
				edit_display.setMethods(result);
			}

			@Override
			protected void callService(AsyncCallback<ArrayList<SSE_DTO>> cb) {
				rpcUserService.getSSEs("SocioResearch","method", cb);
			}
			
		}.retry(2);
	}
	private void fetchResearchersCloud()
	{
		new RPCCall<ArrayList<SSE_DTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching sses dtos "+caught.getMessage());
				edit_display.setMethods(new ArrayList<SSE_DTO>());
			}

			@Override
			public void onSuccess(ArrayList<SSE_DTO> result) {
				edit_display.setResearches(result);
			}

			@Override
			protected void callService(AsyncCallback<ArrayList<SSE_DTO>> cb) {
				rpcUserService.getSSEs("SocioResearch","researcher", cb);
			}
			
		}.retry(2);
	}
	private void fetchConceptsCloud()
	{
		new RPCCall<ArrayList<SSE_DTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching sses dtos "+caught.getMessage());
				edit_display.setMethods(new ArrayList<SSE_DTO>());
			}

			@Override
			public void onSuccess(ArrayList<SSE_DTO> result) {
				edit_display.setConcepts(result);
			}

			@Override
			protected void callService(AsyncCallback<ArrayList<SSE_DTO>> cb) {
				rpcUserService.getSSEs("SocioResearch","concept", cb);
			}
			
		}.retry(2);
	}
	private void fetchResearchDetailes(final long id_research)
	{
		new RPCCall<SocioResearchDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on getting research detailes :"+caught.getMessage());
			}

			@Override
			public void onSuccess(SocioResearchDTO result) {
				gr_edit_displ.updateViewedDTO(result);
				
			}

			@Override
			protected void callService(AsyncCallback<SocioResearchDTO> cb) {
				rpcUserService.getResearch(id_research, cb);
			}
		}.retry(3);
	}
	void updateResearch(final SocioResearchDTO dto)
	{
		new RPCCall<SocioResearchDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error while updating research!");
			}

			@Override
			public void onSuccess(SocioResearchDTO result) {
				Window.alert("Research updated!");
			}

			@Override
			protected void callService(AsyncCallback<SocioResearchDTO> cb) {
				rpcAdminService.updateResearch(dto, cb);
			}
		}.retry(3);
	}
	void updateResearchGrouped(final SocioResearchDTO dto)
	{
		new RPCCall<SocioResearchDTO>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error while updating research!");
			}

			@Override
			public void onSuccess(SocioResearchDTO result) {
				Window.alert("Research updated!");
			}

			@Override
			protected void callService(AsyncCallback<SocioResearchDTO> cb) {
				rpcAdminService.updateResearchGrouped(dto, cb);
			}
		}.retry(3);
	}
	void addOrgImpl(final OrgDTO dto)
	{
		new RPCCall<Long>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error on Org add!");
			}

			@Override
			public void onSuccess(Long result) {
				//Window.alert("Org added!");
				eventBus.fireEvent(new OrgListChangedEvent());
			}

			@Override
			protected void callService(AsyncCallback<Long> cb) {
				rpcAdminService.addOrgImpl(dto, cb);
			}
		}.retry(3);
	}
	void fetchOrgLists()
	{
		new RPCCall<ArrayList<OrgDTO>>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(ArrayList<OrgDTO> result) {
				ArrayList<Long> ids = new ArrayList<Long>();
				ArrayList<String> names = new ArrayList<String>();
				for(OrgDTO dto:result)
				{
					ids.add(dto.getId());
					names.add(dto.getName());
				}
				edit_display.setOrgImpl(names,ids);
				edit_display.setOrgOrder(names,ids);
			}

			@Override
			protected void callService(AsyncCallback<ArrayList<OrgDTO>> cb) {
				rpcUserService.getOrgList(cb);
			}
		}.retry(3);
	}
	private void fetchResearchListData()
	{
		//final ArrayList<NewsDTO> newsData = new ArrayList<NewsDTO>();
		
		new RPCCall<ArrayList<SocioResearchDTO>>() {
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error fetching " +
						" news: "
			            + caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<SocioResearchDTO> result) {
				ArrayList<String> names = new ArrayList<String>();
				ArrayList<Long> ids = new ArrayList<Long>();
				
				for(SocioResearchDTO dto:result)
				{
					names.add(dto.getName());
					ids.add(dto.getID());
				}
				gr_edit_displ.setResearchesAvaible(names, ids);
				ArrayList<IPickableElement> elems = new ArrayList<IPickableElement>();
				for(SocioResearchDTO dto:result)
				{
					elems.add(dto);
				}
				gr_edit_displ.getPickResearchesToPropagatePanel().add(new PickElementsTableView(elems,new ArrayList<Long>(), new IPickBinder() {
					
					@Override
					public void processPickChoice(ArrayList<Long> selected_keys) {
						SocioResearchDTO dto = composeDTOtoUpdate();
						for(Long key:selected_keys)
						{
							dto.setId(key);
							updateResearchGrouped(dto);
						}
					}
					
					@Override
					public String getCommandName() {
						return "Распространить метаданные!";
					}

					@Override
					public String getTitle() {
						return "Доступные исследования:";
					}
				}));
				
			}

			@Override
			protected void callService(
					AsyncCallback<ArrayList<SocioResearchDTO>> cb) {
				rpcUserService.getResearchSummaries(cb);
			}
		}.retry(3);
	}
	
	
	void fetchWeightVarCandidates(final long research_ID)
	{
		new RPCCall<ArrayList<VarDTO_Light>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("error while fetching weight var candidates!");
			}

			@Override
			public void onSuccess(ArrayList<VarDTO_Light> result) {
				ArrayList<Long> ids = new ArrayList<Long>();
				ArrayList<String> names = new ArrayList<String>();
				for(VarDTO_Light dto:result)
				{
					ids.add(dto.getId());
					names.add(dto.getCode());
				}
				edit_display.setVarsWeight(names, ids);
			}

			@Override
			protected void callService(AsyncCallback<ArrayList<VarDTO_Light>> cb) {
				rpcUserService.getResearchVarsSummaries(research_ID, cb);
			}
		}.retry(2);
	}
}
