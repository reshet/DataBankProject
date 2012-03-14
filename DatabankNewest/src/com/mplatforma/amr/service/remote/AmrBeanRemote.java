package com.mplatforma.amr.service.remote;

import javax.ejb.Remote;

@Remote
public interface AmrBeanRemote {

    long getBookCount();
    
}
