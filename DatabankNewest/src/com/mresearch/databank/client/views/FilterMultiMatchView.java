package com.mresearch.databank.client.views;


import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public abstract class FilterMultiMatchView extends Composite implements IFilterProvider{

	private static FilterRealDiapasonViewUiBinder uiBinder = GWT
			.create(FilterRealDiapasonViewUiBinder.class);

	interface FilterRealDiapasonViewUiBinder extends
			UiBinder<Widget, FilterMultiMatchView> {
	}
	@UiField VerticalPanel tree_panel;
	private TreeItem root_item;
	protected TreeItem root = new TreeItem();
	protected ArrayList<String> variants;
	public ArrayList<String> getVariants() {
		return variants;
	}
	public abstract class PostProcess
	{
		public void doProcess()
		{
			for(String varnt:variants)
			{
				TreeItem item = new TreeItem();
				item.setWidget(new CheckBox(varnt));
				root.addItem(item);
			}
		}
		public abstract void process();
	}
	public FilterMultiMatchView(String field_name) {
		initWidget(uiBinder.createAndBindUi(this));
		Tree tree = new Tree();
		tree.addItem(root);
		root.setWidget(new CheckBox(field_name));
		loadVariants(new PostProcess(){
			@Override
			public void process() {
				doProcess();
			}});	
		tree_panel.add(tree);
	}
	public TreeItem getRootItem()
	{
		return root_item;
	}
	public abstract void loadVariants(PostProcess proc);
}
