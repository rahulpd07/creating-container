package com.example.creatingcontainer.Service.Impl;

import com.example.creatingcontainer.Dto.*;
import com.example.creatingcontainer.Model.DataEntity;
import com.example.creatingcontainer.Repository.DataEntityRepository;
import com.example.creatingcontainer.Repository.PorductUpdateInfoRepository;
import com.example.creatingcontainer.Service.DataEntityInterface;
import com.example.creatingcontainer.Service.InternalDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@EnableScheduling
public class DataEntityInterfaceImpl implements DataEntityInterface {
    @Autowired
    DataEntityRepository dataEntityRepository;

    @Autowired
    InternalDataService internalDataService;
    @Autowired
    PorductUpdateInfoRepository porductUpdateInfoRepository;
    @Autowired
    TenentClientInterfaceImpl tenentClientInterfaceImpl;

    @Scheduled(fixedRate = 1000)
    @Override
    public void saveAllDatasToDb() {
        String deploymentId = internalDataService.getDeploymentId();
        List<DataEntity> dataFromDb = dataEntityRepository.findAll();
        ProductRootDto productRootDto1 = tenentClientInterfaceImpl.getClientDatas();
        List<DataEntity> dataEntity = dataEntityRepository.findAll();
            SiteDetails sitedetails = productRootDto1.getSiteDetails();
            Address address = sitedetails.getAddress();
            PersonOfContact personOfContact = productRootDto1.getSiteDetails().getPersonOfContact();

                if (dataEntity.isEmpty()) {

                    DataEntity model = new DataEntity();
                    model.setCity(address.getCity());
                    model.setCountry(address.getCountry());
                    model.setPinCode(address.getPinCode());
                    model.setStreetName(address.getStreetName());
                    model.setState(address.getState());
                    model.setSiteName(sitedetails.getSiteName());
                    model.setContact(personOfContact.getContact());
                    model.setEmail(personOfContact.getEmail());
                    model.setFullName(personOfContact.getFullName());
                    model.setProvisionStatus(productRootDto1.isProvisionStatus());
                    model.setDeploymentId(deploymentId);
                    dataEntityRepository.save(model);
                } else {
                    dataEntityRepository.updateDataEntityTable(productRootDto1.isProvisionStatus(),sitedetails.getSiteName(),address.getStreetName(),address.getCity(),address.getPinCode(),address.getState(),address.getCountry(),personOfContact.getFullName(),personOfContact.getContact(),personOfContact.getEmail(),deploymentId);
                }
            }

    public List<DataEntity> getSiteInfoFromDb()
    {
      return  dataEntityRepository.findAll();
    }
public DataEntityDto setDataEntityToDto()
{
    List<DataEntity>  allDataFromEntity = dataEntityRepository.findAll();
    DataEntityDto dataEntityDto = new DataEntityDto();
    for (DataEntity dataEntity1 : allDataFromEntity)
    {
        dataEntityDto.setProvisionStatus(dataEntity1.isProvisionStatus());
        dataEntityDto.setSiteName(dataEntity1.getSiteName());
        dataEntityDto.setStreetName(dataEntity1.getStreetName());
        dataEntityDto.setCity(dataEntity1.getCity());
        dataEntityDto.setPinCode(dataEntity1.getPinCode());
        dataEntityDto.setState(dataEntity1.getState());
        dataEntityDto.setCountry(dataEntity1.getCountry());
        dataEntityDto.setFullName(dataEntity1.getFullName());
        dataEntityDto.setContact(dataEntity1.getContact());
        dataEntityDto.setEmail(dataEntity1.getEmail());
        dataEntityDto.setContact(dataEntity1.getContact());
        dataEntityDto.setDeploymentId(dataEntity1.getDeploymentId());
    }
    return dataEntityDto;
}
}

