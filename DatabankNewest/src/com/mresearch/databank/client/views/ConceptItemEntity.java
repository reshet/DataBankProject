	package com.mresearch.databank.client.views;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TreeItem;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchService.Util;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.client.service.UserSocioResearchService;
import com.mresearch.databank.shared.ICatalogizationConcept;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedStructureDTO;
import java.util.ArrayList;

public class ConceptItemEntity extends TreeItem implements WrappedCustomLabel,ICatalogizationConcept
{
  private Long entity_id;
  private String entity_system_name;
  private String catalog_path;
  private Label l;
  public ConceptItemEntity(String name, Long id,String sys_name,String base_path)
  {
    //super(name);
    this.l = new Label(name);
    l.setWordWrap(true);
    l.setWidth("200px");
	this.setWidget(l);
	l.setStylePrimaryName("gwt-TreeItem");
	this.setTitle(name);
	this.entity_id = id;
    this.entity_system_name = sys_name;
    this.catalog_path = !base_path.equals("")?base_path+"/"+name:name;
  }

  public String getEntity_system_name() {
	return entity_system_name;
}

public void refreshContents() {
    new RPCCall<MetaUnitMultivaluedEntityDTO>()
    {
      public void onFailure(Throwable caught)
      {
      }

      public void onSuccess(MetaUnitMultivaluedEntityDTO result)
      {
        ConceptItemEntity.this.removeItems();
        for (MetaUnitDTO dto : result.getSub_meta_units())
        {
          if ((dto instanceof MetaUnitMultivaluedEntityDTO))
          {
            TreeItem it = ConceptItemEntity.this.composeConceptNode((MetaUnitMultivaluedEntityDTO)dto);
            ConceptItemEntity.this.addItem(it);
          } else {
            if (!(dto instanceof MetaUnitMultivaluedStructureDTO))
              continue;
            TreeItem it = ConceptItemEntity.this.composeConceptsTree((MetaUnitMultivaluedStructureDTO)dto);
            if (it == null) continue; ConceptItemEntity.this.addItem(it);
          }
        }
        for (int i = 0; i < result.getItem_ids().size(); i++)
        {
          ConceptItemEntity.this.addItem(new ConceptItemItem((String)result.getItem_names().get(i), (Long)result.getItem_ids().get(i),getEntity_system_name(),getCatalog_path()));
        }
      }

      protected void callService(AsyncCallback<MetaUnitMultivaluedEntityDTO> cb)
      {
    	//AdminSocioResearchService.Util.getInstance().getE
        UserSocioResearchService.Util.getInstance().getMetaUnitMultivaluedEntityDTO(ConceptItemEntity.this.entity_id.longValue(), cb);
      }
    }
    .retry(2);
  }

  
  protected ConceptItemEntity composeConceptNode(MetaUnitMultivaluedEntityDTO dto) {
    ConceptItemEntity it = new ConceptItemEntity(dto.getDesc(), Long.valueOf(dto.getId()),getEntity_system_name()+"_"+dto.getUnique_name(),getCatalog_path());
    for (int i = 0; i < dto.getItem_ids().size(); i++)
    {
      it.addItem(new ConceptItemItem((String)dto.getItem_names().get(i), (Long)dto.getItem_ids().get(i),getEntity_system_name()+"_"+dto.getUnique_name(),it.getCatalog_path()));
    }
    return it;
  }
  public String getCatalog_path() {
	return catalog_path;
}

public Long getEntity_id() {
    return this.entity_id;
  }
  public void setEntity_id(Long entity_id) {
    this.entity_id = entity_id;
  }

  protected ConceptItemStructure composeConceptsTree(MetaUnitMultivaluedDTO dto) {
    boolean hasConceptInside = false;
    ConceptItemStructure it = new ConceptItemStructure(dto.getDesc(), Long.valueOf(dto.getId()),getEntity_system_name()+"_"+dto.getUnique_name(),getCatalog_path());
    for (MetaUnitDTO dt : dto.getSub_meta_units())
    {
      if ((dt instanceof MetaUnitMultivaluedStructureDTO))
      {
        ConceptItemStructure itt = composeConceptsTree((MetaUnitMultivaluedStructureDTO)dt);
        if (itt == null)
          continue;
        it.addItem(itt);
        hasConceptInside = true;
      } else {
        if (!(dt instanceof MetaUnitMultivaluedEntityDTO))
          continue;
        ConceptItemEntity itt = composeConceptNode((MetaUnitMultivaluedEntityDTO)dt);
        it.addItem(itt);
        hasConceptInside = true;
      }
    }
    if (hasConceptInside) return it;
    return null;
  }

@Override
public Label getLabel() {
	return l;
}
}