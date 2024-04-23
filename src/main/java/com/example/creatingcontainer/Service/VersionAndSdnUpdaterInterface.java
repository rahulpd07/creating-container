package com.example.creatingcontainer.Service;

import java.util.ArrayList;

import com.example.creatingcontainer.Dto.TanentClientRootDto;
 

public interface VersionAndSdnUpdaterInterface {

	public void pullAndUpdateSdnController();
	public void pullAndUpdate5GCore();

 }
