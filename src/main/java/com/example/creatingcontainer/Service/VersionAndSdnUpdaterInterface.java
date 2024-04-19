package com.example.creatingcontainer.Service;

import java.util.ArrayList;

import com.example.creatingcontainer.Dto.TanentClientRootDto;
 

public interface VersionAndSdnUpdaterInterface {

	public TanentClientRootDto pullAndUpdateSdnController();
	public TanentClientRootDto pullAndUpdate5GCore();

 }
