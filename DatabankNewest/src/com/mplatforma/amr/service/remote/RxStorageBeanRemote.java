package com.mplatforma.amr.service.remote;

import com.mresearch.databank.shared.RxStoredDTO;
import javax.ejb.Remote;


@Remote
public interface RxStorageBeanRemote {
   long storeFile(byte[] cont,String name,String desc);
   byte[] getFileContents(long id);
   RxStoredDTO getFileInfo(long id);
   boolean deleteFile(long file_id);
}
