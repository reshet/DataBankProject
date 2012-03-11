package com.mplatforma.amr.service.remote;

import javax.ejb.Remote;

import com.mresearch.databank.shared.UserAccountDTO;

@Remote
public interface UserAccountBeanRemote {

    UserAccountDTO getUserAccount(String email, String password);
    UserAccountDTO getDefaultUser();   
    UserAccountDTO updateAccountResearchState(UserAccountDTO dto);
    void initDefaults();
    
}
