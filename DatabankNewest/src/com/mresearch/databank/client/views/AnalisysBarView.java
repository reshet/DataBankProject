package com.mresearch.databank.client.views;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.mresearch.databank.client.DatabankApp;
import com.mresearch.databank.client.event.RecalculateDistributionsEvent;
import com.mresearch.databank.client.event.ShowVarDetailsEvent;
import com.mresearch.databank.client.presenters.UserResearchPerspectivePresenter;
import com.mresearch.databank.shared.IPickableElement;
import com.mresearch.databank.shared.UserAccountDTO;

public class AnalisysBarView extends Composite implements UserResearchPerspectivePresenter.AnalisysDisplay{

	private static AnalisysBarViewUiBinder uiBinder = GWT
			.create(AnalisysBarViewUiBinder.class);

	interface AnalisysBarViewUiBinder extends UiBinder<Widget, AnalisysBarView> {
	}
	@UiField CheckBox weights_use,filters_use;
	@UiField Button filters_details_btn,filters_add_btn,filters_delete_btn;
	private SimpleEventBus eventBus;
	private UserResearchPerspectivePresenter.Display display;
	private Long current_research_id = null;
	
	public AnalisysBarView(SimpleEventBus bus,UserResearchPerspectivePresenter.Display display) {
		initWidget(uiBinder.createAndBindUi(this));
		this.eventBus = bus;
		this.display = display;
		bind();
	}
	
	
	private void bind()
	{
		this.getWeightsUse().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UserAccountDTO dto = DatabankApp.get().getCurrentUser();
				dto.setWeights_use(getWeightsUseState());
				DatabankApp.get().updateUserAccountState();
				current_research_id = DatabankApp.get().getCurrentUser().getCurrent_research();
				TreeItem it = display.getSelectedItem();
				if (it instanceof VarDescItem)
				{
					VarDescItem rv = (VarDescItem)it;
					eventBus.fireEvent(new ShowVarDetailsEvent(rv.getVar_id()));
					//weights will be recalculated on server; may be not syncronized!!!
				}
				
				eventBus.fireEvent(new RecalculateDistributionsEvent(getWeightsUseState(), getWeightsUseState(),
						DatabankApp.get().getCurrentUser().getFiltersToProcess(current_research_id)));
				//rise event!
				//dto.setFilters_use(display.getFiltersUseState());
			}
		});
		getFiltersUse().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UserAccountDTO dto = DatabankApp.get().getCurrentUser();
				dto.setFilters_use(getFiltersUseState());
				DatabankApp.get().updateUserAccountState();
				current_research_id = DatabankApp.get().getCurrentUser().getCurrent_research();
				eventBus.fireEvent(new RecalculateDistributionsEvent(getWeightsUseState(), getWeightsUseState(),
						DatabankApp.get().getCurrentUser().getFiltersToProcess(current_research_id)));
			
				//rise event!
							//dto.setFilters_use(display.getFiltersUseState());
			}
		});
		getFiltersDetailesBtn().addClickHandler(new ClickHandler() {
			final PopupPanel panel = new PopupPanel(true);
			
			@Override
			public void onClick(ClickEvent event) {
				panel.clear();
				panel.setPopupPosition(event.getClientX(), event.getClientY());
				ArrayList<IPickableElement> elems = new ArrayList<IPickableElement>();
				
//				final ArrayList<String> filters = DatabankApp.get().getCurrentUser().getFilters(current_research_id);
//				for(final String filter:filters)
//				{
//					elems.add(new IPickableElement() {
//						@Override
//						public String getTextRepresent() {
//							return filter;
//						}
//						
//						@Override
//						public String getID() {
//							return String.valueOf(filters.indexOf(filter));
//						}
//					});
//				}
//				//ArrayList<String> used_elems = new ArrayList<String>();
				current_research_id = DatabankApp.get().getCurrentUser().getCurrent_research();
				final ArrayList<String> filters = DatabankApp.get().getCurrentUser().getFilters();
				final ArrayList<Long> filter_categs = DatabankApp.get().getCurrentUser().getFilters_categories();
				int i = 0;
				for(final String filter:filters)
				{
					if (filter_categs.get(i)!=null && filter_categs.get(i).equals(current_research_id))
					{
						final int inter = i;
						elems.add(new IPickableElement() {
							@Override
							public String getTextRepresent() {
								return filter;
							}
							
							@Override
							public long getID() {
								return inter;
							}
						});
					}
					i++;
				}
				
				panel.add(new PickElementsTableView(elems, DatabankApp.get().getCurrentUser().getFiltersToProcessIndecies(current_research_id), new IPickBinder() {
					@Override
					public void processPickChoice(ArrayList<Long> selected_keys) {
						ArrayList<Integer> usage = new ArrayList<Integer>();
						ArrayList<String> filters = DatabankApp.get().getCurrentUser().getFilters(current_research_id);
						for(String touse:filters)
						{
							usage.add(new Integer(0));
						}
						for(Long sel_index:selected_keys)
						{
							Integer index = sel_index.intValue();
							usage.set(index, new Integer(1));
						}
						DatabankApp.get().getCurrentUser().setFilters_usage(usage,current_research_id);
						DatabankApp.get().updateUserAccountState();
						eventBus.fireEvent(new RecalculateDistributionsEvent(getWeightsUseState(),getWeightsUseState(),
								DatabankApp.get().getCurrentUser().getFiltersToProcess(current_research_id)));
						panel.hide();
					}
					
					@Override
					public String getCommandName() {
						return "Применить!";
					}

					@Override
					public String getTitle() {
						return "Доступные фильтры:";
					}
				}));
				panel.show();
			}
		});
		getFiltersAddBtn().addClickHandler(new ClickHandler() {
			final PopupPanel panel = new PopupPanel(true);
			@Override
			public void onClick(ClickEvent event) {
				HorizontalPanel hor = new HorizontalPanel();
				panel.clear();
				panel.setPopupPosition(event.getClientX(), event.getClientY());
				final TextBox t = new TextBox();
				t.setSize("500px", "40px");
				Button doFilter = new Button("Добавить фильтр!");
				doFilter.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						panel.hide();
						current_research_id = DatabankApp.get().getCurrentUser().getCurrent_research();
						final ArrayList<String> filters = DatabankApp.get().getCurrentUser().getFilters();
						final ArrayList<Long> filter_categs = DatabankApp.get().getCurrentUser().getFilters_categories();
						final ArrayList<Integer> filter_usage = DatabankApp.get().getCurrentUser().getFilters_usage();
						filters.add(t.getText());
						filter_categs.add(current_research_id);
						filter_usage.add(0);
						DatabankApp.get().updateUserAccountState();
						Window.alert("Filter created:"+t.getText());
					}
				});
				hor.add(t);
				hor.add(doFilter);
				panel.add(hor);
				panel.show();
			}
		});
		getFiltersDeleteBtn().addClickHandler(new ClickHandler() {
			final PopupPanel panel = new PopupPanel(true);
			@Override
			public void onClick(ClickEvent event) {
				panel.clear();
				panel.setPopupPosition(event.getClientX(), event.getClientY());
				ArrayList<IPickableElement> elems = new ArrayList<IPickableElement>();
				current_research_id = DatabankApp.get().getCurrentUser().getCurrent_research();
				final ArrayList<String> filters = DatabankApp.get().getCurrentUser().getFilters();
				final ArrayList<Long> filter_categs = DatabankApp.get().getCurrentUser().getFilters_categories();
				int i = 0;
				for(final String filter:filters)
				{
					if (filter_categs.get(i)==current_research_id)
					{
						final int inter = i;
						elems.add(new IPickableElement() {
							@Override
							public String getTextRepresent() {
								return filter;
							}
							
							@Override
							public long getID() {
								return inter;
							}
						});
					}
					i++;
				}
				//DatabankApp.get().getCurrentUser().getFiltersToProcess(current_research_id)
				panel.add(new PickElementsTableView(elems,new ArrayList<Long>() , new IPickBinder() {
					@Override
					public void processPickChoice(ArrayList<Long> selected_keys) {
						ArrayList<String> filters = DatabankApp.get().getCurrentUser().getFilters();
						ArrayList<Long> filters_categs = DatabankApp.get().getCurrentUser().getFilters_categories();
						ArrayList<Integer> filters_usage = DatabankApp.get().getCurrentUser().getFilters_usage();
						
						for(Long index:selected_keys)
						{
							if(filters_categs.get(index.intValue()).equals(current_research_id))
							{
								filters.remove(index);
								filters_usage.remove(index);
								filters_categs.remove(index);
							}
							//if(filters_usage.contains(index))filters_usage.remove(filters_usage.indexOf(index));
						}
						DatabankApp.get().updateUserAccountState();
						eventBus.fireEvent(new RecalculateDistributionsEvent(getWeightsUseState(), getWeightsUseState(),
								DatabankApp.get().getCurrentUser().getFiltersToProcess(current_research_id)));
						panel.hide();
					}
					
					@Override
					public String getCommandName() {
						return "Удалить!";
					}

					@Override
					public String getTitle() {
						return "Доступные фильтры:";
					}
				}));
				panel.show();			
			}
		});

	}
	
	@UiHandler(value="weights_use")
	public void onWeightsUseClick(ClickEvent c)
	{
		if(weights_use.getValue()) weights_use.setText("Взвешивание включено");
			else weights_use.setText("Взвешивание отключено");
	}
	
	@Override
	public HasClickHandlers getWeightsUse() {
		return weights_use;
	}
	@Override
	public HasClickHandlers getFiltersUse() {
		return filters_use;
	}
	@Override
	public HasClickHandlers getFiltersDetailesBtn() {
		return filters_details_btn;
	}
	@Override
	public Integer getWeightsUseState() {
		return weights_use.getValue()?1:0;
	}
	@Override
	public Integer getFiltersUseState() {
		return filters_use.getValue()?1:0;
	}
	@Override
	public HasClickHandlers getFiltersAddBtn() {
		// TODO Auto-generated method stub
		return filters_add_btn;
	}

	@Override
	public HasClickHandlers getFiltersDeleteBtn() {
		// TODO Auto-generated method stub
		return filters_delete_btn;
	}

}
