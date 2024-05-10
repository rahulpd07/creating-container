package com.example.creatingcontainer.Service.Impl;

import com.example.creatingcontainer.Dto.DockerIPandDockerPort;
import com.example.creatingcontainer.Model.Dto;
import com.example.creatingcontainer.Repository.InternalDataRepository;
import com.example.creatingcontainer.Repository.PorductUpdateInfoRepository;
import com.example.creatingcontainer.Service.CurrentRunningVersionInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
public class CurrentRunningVersion implements CurrentRunningVersionInterface {
    @Autowired
    InternalDataRepository internalDataRepository;

    @Autowired
    PorductUpdateInfoRepository porductUpdateInfoRepository;

    public void updateCoreSdnRunning(String version, String productName) {
        porductUpdateInfoRepository.updateTheInitialVersionOfSdnToDb(version, productName);
    }

    public DockerIPandDockerPort setdockerIpAndPort() {
        DockerIPandDockerPort model = new DockerIPandDockerPort();
        List<Object[]> result = internalDataRepository.returnIpandPort();
        for (Object[] row : result) {
            model.setDocker_ip((String) row[0]);
            model.setDocker_port((String) row[1]);
        }
        return model;
    }


    public List<Dto> getRunningContainer() {


        DockerIPandDockerPort dockerIPandDockerPort = this.setdockerIpAndPort();
        WebClient webClient = WebClient.create();
        List<Dto> allRunningContainers =
                webClient.get()
                        .uri("http://" + dockerIPandDockerPort.docker_ip + ":" + dockerIPandDockerPort.docker_port + "/v1.44/containers/json?limit=100")
                        .retrieve()
                        .bodyToFlux(Dto.class)
                        .collectList()
                        .block();
        Set<String> uniqueImage = new HashSet<>();
        for (Dto dto : allRunningContainers) {
            String imageName = dto.getImage();
            uniqueImage.add(imageName);

        }
        ArrayList<String> uniqueValue = new ArrayList<>();
        for(String onlyUnique : uniqueImage)
        {
            uniqueValue.add(onlyUnique);
        }
        Map<String, String> keyValueMap = new HashMap<>();

        // Iterating over each string in the input array
        for (String version : uniqueValue) {
            // Splitting each string by ":"
            String[] parts = version.split(":");

            // If the split result contains exactly two parts
            if (parts.length == 2) {
                // Adding the key-value pair to the HashMap
                keyValueMap.put(parts[0], parts[1]);
            }
        }
        System.out.println(keyValueMap);
        for (Map.Entry<String, String> entry : keyValueMap.entrySet()) {
            if(keyValueMap.containsKey("niralnetworks/niralos-5g-core"))
            {
                String version = keyValueMap.get("niralnetworks/niralos-5g-core");
                updateCoreSdnRunning(version,"niralos-5g-core");
            }  if (keyValueMap.containsKey("niralnetworks/niralos-local-sdn")) {
                String version = keyValueMap.get("niralnetworks/niralos-local-sdn");
                updateCoreSdnRunning(version,"niralos-local-sdn");
            }  if (!keyValueMap.containsKey("niralnetworks/niralos-5g-core") || !keyValueMap.containsKey("niralnetworks/niralos-local-sdn")) {

                 if (!keyValueMap.containsKey("niralnetworks/niralos-5g-core"))
                {

                    updateCoreSdnRunning( null,"niralos-5g-core");
                }
                if (!keyValueMap.containsKey("niralnetworks/niralos-local-sdn"))
                {

                    updateCoreSdnRunning( null,"niralos-local-sdn");
                }
            }
        }


        return allRunningContainers;

    }
}