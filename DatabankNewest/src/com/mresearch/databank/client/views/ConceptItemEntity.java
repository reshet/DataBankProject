package com.mresearch.databank.client.views;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.TreeItem;
import com.mresearch.databank.client.helper.RPCCall;
import com.mresearch.databank.client.service.AdminSocioResearchService;
import com.mresearch.databank.client.service.AdminSocioResearchService.Util;
import com.mresearch.databank.client.service.AdminSocioResearchServiceAsync;
import com.mresearch.databank.shared.ICatalogizationConcept;
import com.mresearch.databank.shared.MetaUnitDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedEntityDTO;
import com.mresearch.databank.shared.MetaUnitMultivaluedStructureDTO;
import java.util.ArrayList;

public class ConceptItemEntity extends TreeItem
  implements ICatalogizationConcept
{
  private Long entity_id;

  public ConceptItemEntity(String name, Long id)
  {
    super(name);
    this.entity_id = id;
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
      }

      protected void callService(AsyncCallback<MetaUnitMultivaluedEntityDTO> cb)
      {
        AdminSocioResearchService.Util.getInstance().getMetaUnitMultivaluedEntityDTO(ConceptItemEntity.this.entity_id.longValue(), cb);
      }
    }
    .retry(2);
  }

  protected ConceptItemEntity composeConceptNode(MetaUnitMultivaluedEntityDTO dto) {
    ConceptItemEntity it = new ConceptItemEntity(dto.getDesc(), Long.valueOf(dto.getId()));
    for (int i = 0; i < dto.getItem_ids().size(); i++)
    {
      it.addItem(new ConceptItemItem((String)dto.getItem_names().get(i), (Long)dto.getItem_ids().get(i)));
    }
    return it;
  }
  public Long getEntity_id() {
    return this.entity_id;
  }
  public void setEntity_id(Long entity_id) {
    this.entity_id = entity_id;
  }

  protected ConceptItemStructure composeConceptsTree(MetaUnitMultivaluedDTO dto) {
    boolean hasConceptInside = false;
    ConceptItemStructure it = new ConceptItemStructure(dto.getDesc(), Long.valueOf(dto.getId()));
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
}