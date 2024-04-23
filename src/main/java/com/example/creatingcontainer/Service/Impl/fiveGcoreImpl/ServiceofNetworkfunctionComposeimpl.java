package com.example.creatingcontainer.Service.Impl.fiveGcoreImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.creatingcontainer.Service.TenentClientInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.example.creatingcontainer.Model.DockerContainerResponse;
import com.example.creatingcontainer.Model.PorductUpdateInfo;
import com.example.creatingcontainer.Model.fivgcore.CoreDockerContainerNames;
import com.example.creatingcontainer.Repository.PorductUpdateInfoRepository;
import com.example.creatingcontainer.Repository.FivegCoreRepo.DockerNetworkConfigurationrepo;
import com.example.creatingcontainer.Service.InternalDataService;
import com.example.creatingcontainer.Service.Impl.VersionAndSdnUpdater;
import com.example.creatingcontainer.Service.fiveGcore.ServiceofNetworkfunctionCompose;


@Service
public class ServiceofNetworkfunctionComposeimpl extends Thread implements ServiceofNetworkfunctionCompose {
	
	@Autowired
	DockerNetworkConfigurationrepo configuration;
	
	@Autowired
	PorductUpdateInfoRepository porductUpdateInfoRepository;
	
	@Autowired
	InternalDataService internalDataService;

	@Autowired
	TenentClientInterface tenentClientInterface;
	
    private static final Logger logger = LoggerFactory.getLogger(ServiceofNetworkfunctionComposeimpl.class);

	
	 private String dockerengineIp;
		@Value("${docker.engine.ip}")
		public void setDockerengineIp(String dockerengineIp) {
			this.dockerengineIp = dockerengineIp;
		}
		
		private String dockerengineport;
		@Value("${docker.engine.port}")
		public void setDockerengineport(String dockerengineport) {
			this.dockerengineport = dockerengineport;
		}
		
		private String vmPathNameConfiguration;
		@Value("${vm.pathName.configuration}")
		public void setVmpathNameConfiguration(String vmPathNameConfiguration) {
			this.vmPathNameConfiguration = vmPathNameConfiguration;
		}
	
//	@Value("${docker.versionof.fivegcore}")
//	 public String versionFivegcore;
	
	 
	 public void functionversion(String versionNumber){
		configuration.updatetheVersioninDatabase(versionNumber,internalDataService.getDeploymentId());
		}
	
	private final Object lock = new Object();
	
//	 private String versionFivegcore;
//		@Value("${version.of.fivegcore}")
//		public void setversionFivegcore(String versionFivegcore) {
//			this.versionFivegcore = versionFivegcore;
//		}
	
//	public void fun2() {
//		 System.out.println(versionFivegcore + "11111111111111111");
//	}

	@Override
	public void niralosNrf() {
		
		ServiceofNetworkfunctionComposeimpl ti = new ServiceofNetworkfunctionComposeimpl();
		try {
			
			
//			String containerName = "niralos-nrf";
			
			
			synchronized (ti) {
				String endpoint = "/v1.44/images/create?fromImage=" + this.GlobalImage();
				this.pullImageWebclient(endpoint);
			}
			logger.info("Waiting for condition...complete");

			synchronized (lock) {
				try {
					lock.wait(); // Wait for the notification from the other thread (ti)
					// Creating the container
					String createEndpoint = "/containers/create?name=" + containerData().getNrf();
					Map<String, Object> jsonData = new HashMap<>();
					// Image
					jsonData.put("Image", this.GlobalImage());


					// Cmd
					List<String> cmdList = Arrays.asList("bin/bash", "-c",
							"./install/bin/niralos-nrfd -c /niral_config/yaml/nrf.yaml -l /niral_config/log/nrf.log");
					jsonData.put("Cmd", cmdList);

					// HostConfig
					Map<String, Object> hostConfig = new HashMap<>();
					List<String> binds = new ArrayList<>();
					binds.add("/home/"+vmPathNameConfiguration+"/niral_config:/niral_config");
					hostConfig.put("Binds", binds);

					hostConfig.put("NetworkMode", "niralos-mesh");
					// Add HostConfig to the main request
					jsonData.put("HostConfig", hostConfig);

					// RestartPolicy
					Map<String, String> restartPolicy = new HashMap<>();
					restartPolicy.put("Name", "always");
					hostConfig.put("RestartPolicy", restartPolicy);
					jsonData.put("HostConfig", hostConfig);

					// Network Configuration

					// Create a networks HashMap and add niralos-mesh property (similar to your
					// provided NetworkSettings)
					Map<String, Object> niralosMeshNetwork = new HashMap<>();
					niralosMeshNetwork.put("IPAMConfig", null);
					niralosMeshNetwork.put("Links", null);
					// Add other properties as needed

					Map<String, Object> networks = new HashMap<>();
					networks.put("niralos-mesh", niralosMeshNetwork);

					// Create a HostConfig HashMap and add Networks property
					hostConfig.put("Networks", networks);

					// Add networks to the container creation config
					jsonData.put("NetworkingConfig", Map.of("EndpointsConfig", networks));

					DockerContainerResponse dockerContainerResponse = this.Webclient().post().uri(createEndpoint)
							.bodyValue(jsonData).retrieve().bodyToMono(DockerContainerResponse.class).block();

					logger.info("Image container complete");
				
					String containerId = dockerContainerResponse.getId();
					if (containerId != null) {
						String startEndpoint = "/v1.44/containers/" + dockerContainerResponse.getId() + "/restart";
//				 String startUrl = dockerApiUrl + startEndpoint;
						this.restartContainer(startEndpoint);
					}
					tenentClientInterface.getClientDatas();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		} catch (Exception e) {
		}

	}

	@Override
	public void niralosScp() {
		ServiceofNetworkfunctionComposeimpl ti = new ServiceofNetworkfunctionComposeimpl();
		try {
//			String containerName = "niralos-scp";

			
			synchronized (ti) {
				String endpoint = "/v1.44/images/create?fromImage=" + this.GlobalImage();
				this.pullImageWebclient(endpoint);
			}
			logger.info("Waiting for condition...complete");

			synchronized (lock) {
				try {
					lock.wait(); // Wait for the notification from the other thread (ti)
					// Creating the container
					String createEndpoint = "/containers/create?name=" + containerData().getScp();
					Map<String, Object> jsonData = new HashMap<>();
					// Image
					jsonData.put("Image", this.GlobalImage());

//      //COMMAND
//      List<String> command = new ArrayList<>();

					// Cmd
					List<String> cmdList = Arrays.asList("bin/bash", "-c",
							"sleep 10;./install/bin/niralos-scpd -c /niral_config/yaml/scp.yaml -l /niral_config/log/scp.log");
					jsonData.put("Cmd", cmdList);

					// Create a List to represent the exposed ports
					List<String> exposedPorts = new ArrayList<>();

//      // Add an exposed port entry
//      exposedPorts.add("9090");
//
//      // Add the exposed ports list to the containerCreateConfig map
//      jsonData.put("ExposedPorts", exposedPorts);

					// HostConfig
					Map<String, Object> hostConfig = new HashMap<>();
					List<String> binds = new ArrayList<>();
					binds.add("/home/"+vmPathNameConfiguration+"/niral_config:/niral_config");
					hostConfig.put("Binds", binds);

					hostConfig.put("NetworkMode", "niralos-mesh");
					// Add HostConfig to the main request
					jsonData.put("HostConfig", hostConfig);

					// RestartPolicy
					Map<String, String> restartPolicy = new HashMap<>();
					restartPolicy.put("Name", "always");
					hostConfig.put("RestartPolicy", restartPolicy);
					jsonData.put("HostConfig", hostConfig);

					// Network Configuration

					// Create a networks HashMap and add niralos-mesh property (similar to your
					// provided NetworkSettings)
					Map<String, Object> niralosMeshNetwork = new HashMap<>();
					niralosMeshNetwork.put("IPAMConfig", null);
					niralosMeshNetwork.put("Links", null);
					// Add other properties as needed

					Map<String, Object> networks = new HashMap<>();
					networks.put("niralos-mesh", niralosMeshNetwork);

					// Create a HostConfig HashMap and add Networks property
					hostConfig.put("Networks", networks);

					// Add networks to the container creation config
					jsonData.put("NetworkingConfig", Map.of("EndpointsConfig", networks));

					DockerContainerResponse dockerContainerResponse = this.Webclient().post().uri(createEndpoint)
							.bodyValue(jsonData).retrieve().bodyToMono(DockerContainerResponse.class).block();

					logger.info("Image container complete");
					String containerId = dockerContainerResponse.getId();
					if (containerId != null) {
						String startEndpoint = "/v1.44/containers/" + dockerContainerResponse.getId() + "/restart";
//				 String startUrl = dockerApiUrl + startEndpoint;
						this.restartContainer(startEndpoint);
					}
					tenentClientInterface.getClientDatas();
				} catch (Exception e) {
				}
			}

		} catch (Exception e) {
		}

	}

	@Override
	public void niralosUdr() {
		ServiceofNetworkfunctionComposeimpl ti = new ServiceofNetworkfunctionComposeimpl();
		try {
//			String containerName = "niralos-udr";

		
			synchronized (ti) {
				String endpoint = "/v1.44/images/create?fromImage=" + this.GlobalImage();
				this.pullImageWebclient(endpoint);
			}
			logger.info("Waiting for condition...complete");

			synchronized (lock) {
				try {
					lock.wait(); // Wait for the notification from the other thread (ti)
					// Creating the container
					String createEndpoint = "/containers/create?name=" + containerData().getUdr();
					Map<String, Object> jsonData = new HashMap<>();
					// Image
					jsonData.put("Image", this.GlobalImage());


					// Cmd
					List<String> cmdList = Arrays.asList("bin/bash", "-c",
							"sleep 20; ./install/bin/niralos-udrd -c /niral_config/yaml/udr.yaml -l /niral_config/log/udr.log");
					jsonData.put("Cmd", cmdList);

					// HostConfig
					Map<String, Object> hostConfig = new HashMap<>();
					List<String> binds = new ArrayList<>();
					binds.add("/home/"+vmPathNameConfiguration+"/niral_config:/niral_config");
					hostConfig.put("Binds", binds);

					hostConfig.put("NetworkMode", "niralos-mesh");
					// Add HostConfig to the main request
					jsonData.put("HostConfig", hostConfig);

					// RestartPolicy
					Map<String, String> restartPolicy = new HashMap<>();
					restartPolicy.put("Name", "always");
					hostConfig.put("RestartPolicy", restartPolicy);
					jsonData.put("HostConfig", hostConfig);

					// Network Configuration

					// Create a networks HashMap and add niralos-mesh property (similar to your
					// provided NetworkSettings)
					Map<String, Object> niralosMeshNetwork = new HashMap<>();
					niralosMeshNetwork.put("IPAMConfig", null);
					niralosMeshNetwork.put("Links", null);
					// Add other properties as needed

					Map<String, Object> networks = new HashMap<>();
					networks.put("niralos-mesh", niralosMeshNetwork);

					// Create a HostConfig HashMap and add Networks property
					hostConfig.put("Networks", networks);

					// Add networks to the container creation config
					jsonData.put("NetworkingConfig", Map.of("EndpointsConfig", networks));

					DockerContainerResponse dockerContainerResponse = this.Webclient().post().uri(createEndpoint)
							.bodyValue(jsonData).retrieve().bodyToMono(DockerContainerResponse.class).block();

					logger.info("Image container complete");
					String containerId = dockerContainerResponse.getId();
					if (containerId != null) {
						String startEndpoint = "/v1.44/containers/" + dockerContainerResponse.getId() + "/restart";
//				 String startUrl = dockerApiUrl + startEndpoint;
						this.restartContainer(startEndpoint);
					}
					tenentClientInterface.getClientDatas();
				} catch (Exception e) {
				}
			}

		} catch (Exception e) {
		}
	}

	@Override
	public void niralosUdm() {
		ServiceofNetworkfunctionComposeimpl ti = new ServiceofNetworkfunctionComposeimpl();
		try {
//			String containerName = "niralos-udm";

		
			synchronized (ti) {
				String endpoint = "/v1.44/images/create?fromImage=" + this.GlobalImage();
				this.pullImageWebclient(endpoint);
			}
			logger.info("Waiting for condition...complete");

			synchronized (lock) {
				try {
					lock.wait(); // Wait for the notification from the other thread (ti)

					// Creating the container
					String createEndpoint = "/containers/create?name=" + containerData().getUdm();
					Map<String, Object> jsonData = new HashMap<>();
					// Image
					jsonData.put("Image", this.GlobalImage());

					// Cmd
					List<String> cmdList = Arrays.asList("bin/bash", "-c",
							"sleep 20; ./install/bin/niralos-udmd -c /niral_config/yaml/udm.yaml -l /niral_config/log/udm.log");
					jsonData.put("Cmd", cmdList);

					// HostConfig
					Map<String, Object> hostConfig = new HashMap<>();
					List<String> binds = new ArrayList<>();
					binds.add("/home/"+vmPathNameConfiguration+"/niral_config:/niral_config");
					hostConfig.put("Binds", binds);

					hostConfig.put("NetworkMode", "niralos-mesh");
					// Add HostConfig to the main request
					jsonData.put("HostConfig", hostConfig);

					// RestartPolicy
					Map<String, String> restartPolicy = new HashMap<>();
					restartPolicy.put("Name", "always");
					hostConfig.put("RestartPolicy", restartPolicy);
					jsonData.put("HostConfig", hostConfig);

					// Network Configuration

					// Create a networks HashMap and add niralos-mesh property (similar to your
					// provided NetworkSettings)
					Map<String, Object> niralosMeshNetwork = new HashMap<>();
					niralosMeshNetwork.put("IPAMConfig", null);
					niralosMeshNetwork.put("Links", null);
					// Add other properties as needed

					Map<String, Object> networks = new HashMap<>();
					networks.put("niralos-mesh", niralosMeshNetwork);

					// Create a HostConfig HashMap and add Networks property
					hostConfig.put("Networks", networks);

					// Add networks to the container creation config
					jsonData.put("NetworkingConfig", Map.of("EndpointsConfig", networks));

					DockerContainerResponse dockerContainerResponse = this.Webclient().post().uri(createEndpoint)
							.bodyValue(jsonData).retrieve().bodyToMono(DockerContainerResponse.class).block();

					logger.info("Image container complete");
					String containerId = dockerContainerResponse.getId();
					if (containerId != null) {
						String startEndpoint = "/v1.44/containers/" + dockerContainerResponse.getId() + "/restart";
//				 String startUrl = dockerApiUrl + startEndpoint;
						this.restartContainer(startEndpoint);
					}

				} catch (Exception e) {
				}
			}

		} catch (Exception e) {
		}

	}

	@Override
	public void niralosAusf() {

		ServiceofNetworkfunctionComposeimpl ti = new ServiceofNetworkfunctionComposeimpl();
		try {
//			String containerName = "niralos-ausf";

	
			synchronized (ti) {
				String endpoint = "/v1.44/images/create?fromImage=" + this.GlobalImage();
				this.pullImageWebclient(endpoint);
			}
			logger.info("Waiting for condition...complete");

			synchronized (lock) {
				try {
					lock.wait(); // Wait for the notification from the other thread (ti)

					// Creating the container
					String createEndpoint = "/containers/create?name=" + containerData().getAusf();
					Map<String, Object> jsonData = new HashMap<>();
					// Image
					jsonData.put("Image", this.GlobalImage());


					// Create a List to represent the port mappings
					List<Map<String, Object>> ports = new ArrayList<>();

					// Create a Map for the port mapping entry
					Map<String, Object> portMapping = new HashMap<>();
					portMapping.put("PrivatePort", 9090);
					portMapping.put("PublicPort", 9090);
					portMapping.put("Type", "tcp");

					// Add the port mapping entry to the ports list
					ports.add(portMapping);

					// Add the ports list to the containerCreateConfig map
					jsonData.put("Ports", ports);

					// Cmd
					List<String> cmdList = Arrays.asList("bin/bash", "-c",
							"sleep 20; ./install/bin/niralos-ausfd -c /niral_config/yaml/ausf.yaml -l /niral_config/log/ausf.log");
					jsonData.put("Cmd", cmdList);

					// HostConfig
					Map<String, Object> hostConfig = new HashMap<>();
					List<String> binds = new ArrayList<>();
					binds.add("/home/"+vmPathNameConfiguration+"/niral_config:/niral_config");
					hostConfig.put("Binds", binds);

					hostConfig.put("NetworkMode", "niralos-mesh");

					// Add HostConfig to the main request
					jsonData.put("HostConfig", hostConfig);

					// RestartPolicy
					Map<String, String> restartPolicy = new HashMap<>();
					restartPolicy.put("Name", "always");
					hostConfig.put("RestartPolicy", restartPolicy);
					jsonData.put("HostConfig", hostConfig);

					// Network Configuration

					// Create a networks HashMap and add niralos-mesh property (similar to your
					// provided NetworkSettings)
					Map<String, Object> niralosMeshNetwork = new HashMap<>();
					niralosMeshNetwork.put("IPAMConfig", null);
					niralosMeshNetwork.put("Links", null);
					// Add other properties as needed

					Map<String, Object> networks = new HashMap<>();
					networks.put("niralos-mesh", niralosMeshNetwork);

					// Create a HostConfig HashMap and add Networks property
					hostConfig.put("Networks", networks);

					// Add networks to the container creation config
					jsonData.put("NetworkingConfig", Map.of("EndpointsConfig", networks));

					DockerContainerResponse dockerContainerResponse = this.Webclient().post().uri(createEndpoint)
							.bodyValue(jsonData).retrieve().bodyToMono(DockerContainerResponse.class).block();

					logger.info("Image container complete");
					String containerId = dockerContainerResponse.getId();
					if (containerId != null) {
						String startEndpoint = "/v1.44/containers/" + dockerContainerResponse.getId() + "/restart";
//				 String startUrl = dockerApiUrl + startEndpoint;
						this.restartContainer(startEndpoint);
					}
					tenentClientInterface.getClientDatas();
				} catch (Exception e) {
				}
			}

		} catch (Exception e) {
		}
	}

	@Override
	public void niralosPcf() {
		ServiceofNetworkfunctionComposeimpl ti = new ServiceofNetworkfunctionComposeimpl();
		try {
//			String containerName = "niralos-pcf";

			
			synchronized (ti) {
				String endpoint = "/v1.44/images/create?fromImage=" + this.GlobalImage();
				this.pullImageWebclient(endpoint);
			}
			logger.info("Waiting for condition...complete");

			synchronized (lock) {
				try {
					lock.wait(); // Wait for the notification from the other thread (ti)

					// Creating the container
					String createEndpoint = "/containers/create?name=" + containerData().getPcf();
					Map<String, Object> jsonData = new HashMap<>();
					// Image
					jsonData.put("Image", this.GlobalImage());


					// Cmd
					List<String> cmdList = Arrays.asList("bin/bash", "-c",
							"sleep 20; ./install/bin/niralos-pcfd -c /niral_config/yaml/pcf.yaml -l /niral_config/log/pcf.log");
					jsonData.put("Cmd", cmdList);

					// HostConfig
					Map<String, Object> hostConfig = new HashMap<>();
					List<String> binds = new ArrayList<>();
					binds.add("/home/"+vmPathNameConfiguration+"/niral_config:/niral_config");
					hostConfig.put("Binds", binds);

					hostConfig.put("NetworkMode", "niralos-mesh");
					// Add HostConfig to the main request
					jsonData.put("HostConfig", hostConfig);

					// RestartPolicy
					Map<String, String> restartPolicy = new HashMap<>();
					restartPolicy.put("Name", "always");
					hostConfig.put("RestartPolicy", restartPolicy);
					jsonData.put("HostConfig", hostConfig);

					// Network Configuration

					// Create a networks HashMap and add niralos-mesh property (similar to your
					// provided NetworkSettings)
					Map<String, Object> niralosMeshNetwork = new HashMap<>();
					niralosMeshNetwork.put("IPAMConfig", null);
					niralosMeshNetwork.put("Links", null);
					// Add other properties as needed

					Map<String, Object> networks = new HashMap<>();
					networks.put("niralos-mesh", niralosMeshNetwork);

					// Create a HostConfig HashMap and add Networks property
					hostConfig.put("Networks", networks);

					// Add networks to the container creation config
					jsonData.put("NetworkingConfig", Map.of("EndpointsConfig", networks));

					DockerContainerResponse dockerContainerResponse = this.Webclient().post().uri(createEndpoint)
							.bodyValue(jsonData).retrieve().bodyToMono(DockerContainerResponse.class).block();

					logger.info("Image container complete");
					String containerId = dockerContainerResponse.getId();
					if (containerId != null) {
						String startEndpoint = "/v1.44/containers/" + dockerContainerResponse.getId() + "/restart";
//				 String startUrl = dockerApiUrl + startEndpoint;
						this.restartContainer(startEndpoint);
					}
					tenentClientInterface.getClientDatas();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void niralosBsf() {
		ServiceofNetworkfunctionComposeimpl ti = new ServiceofNetworkfunctionComposeimpl();
		try {
//			String containerName = "niralos-bsf";

			
			synchronized (ti) {
				String endpoint = "/v1.44/images/create?fromImage=" + this.GlobalImage();
				this.pullImageWebclient(endpoint);
			}
			logger.info("Waiting for condition...complete");

			synchronized (lock) {
				try {
					lock.wait(); // Wait for the notification from the other thread (ti)

					// Creating the container
					String createEndpoint = "/containers/create?name=" + containerData().getBsf();
					Map<String, Object> jsonData = new HashMap<>();
					// Image
					jsonData.put("Image", this.GlobalImage());


					// Cmd
					List<String> cmdList = Arrays.asList("bin/bash", "-c",
							"sleep 20; ./install/bin/niralos-bsfd -c /niral_config/yaml/bsf.yaml -l /niral_config/log/bsf.log");
					jsonData.put("Cmd", cmdList);

					// HostConfig
					Map<String, Object> hostConfig = new HashMap<>();
					List<String> binds = new ArrayList<>();
					binds.add("/home/"+vmPathNameConfiguration+"/niral_config:/niral_config");
					hostConfig.put("Binds", binds);

					hostConfig.put("NetworkMode", "niralos-mesh");
					// Add HostConfig to the main request
					jsonData.put("HostConfig", hostConfig);

					// RestartPolicy
					Map<String, String> restartPolicy = new HashMap<>();
					restartPolicy.put("Name", "always");
					hostConfig.put("RestartPolicy", restartPolicy);
					jsonData.put("HostConfig", hostConfig);

					// Network Configuration

					// Create a networks HashMap and add niralos-mesh property (similar to your
					// provided NetworkSettings)
					Map<String, Object> niralosMeshNetwork = new HashMap<>();
					niralosMeshNetwork.put("IPAMConfig", null);
					niralosMeshNetwork.put("Links", null);
					// Add other properties as needed

					Map<String, Object> networks = new HashMap<>();
					networks.put("niralos-mesh", niralosMeshNetwork);

					// Create a HostConfig HashMap and add Networks property
					hostConfig.put("Networks", networks);

					// Add networks to the container creation config
					jsonData.put("NetworkingConfig", Map.of("EndpointsConfig", networks));

					DockerContainerResponse dockerContainerResponse = this.Webclient().post().uri(createEndpoint)
							.bodyValue(jsonData).retrieve().bodyToMono(DockerContainerResponse.class).block();

					logger.info("Image container complete");
					String containerId = dockerContainerResponse.getId();
					if (containerId != null) {
						String startEndpoint = "/v1.44/containers/" + dockerContainerResponse.getId() + "/restart";
//				 String startUrl = dockerApiUrl + startEndpoint;
						this.restartContainer(startEndpoint);
					}

				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void niralosNssf() {
		ServiceofNetworkfunctionComposeimpl ti = new ServiceofNetworkfunctionComposeimpl();
		try {
//			String containerName = "niralos-nssf";

		
			synchronized (ti) {
				String endpoint = "/v1.44/images/create?fromImage=" + this.GlobalImage();
				this.pullImageWebclient(endpoint);
			}
			logger.info("Waiting for condition...complete");

			synchronized (lock) {
				try {
					lock.wait(); // Wait for the notification from the other thread (ti)

					// Creating the container
					String createEndpoint = "/containers/create?name=" + containerData().getNssf();
					Map<String, Object> jsonData = new HashMap<>();
					// Image
					jsonData.put("Image", this.GlobalImage());

					// Cmd
					List<String> cmdList = Arrays.asList("bin/bash", "-c",
							"sleep 20; ./install/bin/niralos-nssfd -c /niral_config/yaml/nssf.yaml -l /niral_config/log/nssf.log");
					jsonData.put("Cmd", cmdList);

					// HostConfig
					Map<String, Object> hostConfig = new HashMap<>();
					List<String> binds = new ArrayList<>();
					binds.add("/home/"+vmPathNameConfiguration+"/niral_config:/niral_config");
					hostConfig.put("Binds", binds);

					hostConfig.put("NetworkMode", "niralos-mesh");
					// Add HostConfig to the main request
					jsonData.put("HostConfig", hostConfig);

					// RestartPolicy
					Map<String, String> restartPolicy = new HashMap<>();
					restartPolicy.put("Name", "always");
					hostConfig.put("RestartPolicy", restartPolicy);
					jsonData.put("HostConfig", hostConfig);

					// Network Configuration

					// Create a networks HashMap and add niralos-mesh property (similar to your
					// provided NetworkSettings)
					Map<String, Object> niralosMeshNetwork = new HashMap<>();
					niralosMeshNetwork.put("IPAMConfig", null);
					niralosMeshNetwork.put("Links", null);
					// Add other properties as needed

					Map<String, Object> networks = new HashMap<>();
					networks.put("niralos-mesh", niralosMeshNetwork);

					// Create a HostConfig HashMap and add Networks property
					hostConfig.put("Networks", networks);

					// Add networks to the container creation config
					jsonData.put("NetworkingConfig", Map.of("EndpointsConfig", networks));

					DockerContainerResponse dockerContainerResponse = this.Webclient().post().uri(createEndpoint)
							.bodyValue(jsonData).retrieve().bodyToMono(DockerContainerResponse.class).block();

					logger.info("Image container complete");
					String containerId = dockerContainerResponse.getId();
					if (containerId != null) {
						String startEndpoint = "/v1.44/containers/" + dockerContainerResponse.getId() + "/restart";
//				 String startUrl = dockerApiUrl + startEndpoint;
						this.restartContainer(startEndpoint);
					}
					tenentClientInterface.getClientDatas();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@Override
	public void niralosAmf() {
//		String containerName = "niralos-amf";
		ServiceofNetworkfunctionComposeimpl ti = new ServiceofNetworkfunctionComposeimpl();
		try {
			

			
			synchronized (ti) {
				String endpoint = "/v1.44/images/create?fromImage=" + this.GlobalImage();
				this.pullImageWebclient(endpoint);
			}
			logger.info("Waiting for condition...complete");

			synchronized (lock) {
				try {
					lock.wait(); // Wait for the notification from the other thread (ti)

					// Creating the container
					String createEndpoint = "/containers/create?name=" + containerData().getAmf();
					Map<String, Object> jsonData = new HashMap<>();
					// Image
					jsonData.put("Image", this.GlobalImage());


					// Cmd
					List<String> cmdList = Arrays.asList("bin/bash", "-c",
							"sleep 20; ./install/bin/niralos-amfd -c /niral_config/yaml/amf.yaml -l /niral_config/log/amf.log");
					jsonData.put("Cmd", cmdList);

					// HostConfig
					Map<String, Object> hostConfig = new HashMap<>();
					List<String> binds = new ArrayList<>();
					binds.add("/home/"+vmPathNameConfiguration+"/niral_config:/niral_config");
					hostConfig.put("Binds", binds);

					hostConfig.put("NetworkMode", "niralos-mesh");
					// Set the privileged flag in the containerCreateConfig map
					hostConfig.put("Privileged", true);

					// Add HostConfig to the main request
					jsonData.put("HostConfig", hostConfig);

					// RestartPolicy
					Map<String, String> restartPolicy = new HashMap<>();
					restartPolicy.put("Name", "always");
					hostConfig.put("RestartPolicy", restartPolicy);
					jsonData.put("HostConfig", hostConfig);

					// Create a networks HashMap and add niralos-mesh property (similar to your
					// provided NetworkSettings)
					Map<String, Object> niralosMeshNetwork = new HashMap<>();
					niralosMeshNetwork.put("IPAMConfig", null);
					niralosMeshNetwork.put("Links", null);

					// Add other properties as needed

					Map<String, Object> networks = new HashMap<>();
					networks.put("niralos-mesh", niralosMeshNetwork);

//       Create a HostConfig HashMap and add Networks property
					hostConfig.put("Networks", networks);

					// Add networks to the container creation config
					jsonData.put("NetworkingConfig", Map.of("EndpointsConfig", networks));

					DockerContainerResponse dockerContainerResponse = this.Webclient().post().uri(createEndpoint)
							.bodyValue(jsonData).retrieve().bodyToMono(DockerContainerResponse.class).block();

					logger.info("Image container complete");
					
					
					
					String containerId = dockerContainerResponse.getId();
					if (containerId != null) {
						String startEndpoint = "/v1.44/containers/" + dockerContainerResponse.getId() + "/restart";
//				 String startUrl = dockerApiUrl + startEndpoint;
						this.restartContainer(startEndpoint);
					}
					
				

				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		
		try {
			Thread.sleep(10000);
			
			//Adding the configuration of AMF
			String ip1 =configuration.upfN2();
			this.addTheMacvlanNetwork(containerData().getAmf(),ip1);
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	}

	@Override
	public void niralosSmf() {
		ServiceofNetworkfunctionComposeimpl ti = new ServiceofNetworkfunctionComposeimpl();
		try {
//			String containerName = "niralos-smf";

			
			synchronized (ti) {
				String endpoint = "/v1.44/images/create?fromImage=" + this.GlobalImage();
				this.pullImageWebclient(endpoint);
			}

			logger.info("Waiting for condition...complete");

			synchronized (lock) {
				try {
					lock.wait(); // Wait for the notification from the other thread (ti)

					// Creating the container
					String createEndpoint = "/containers/create?name=" + containerData().getSmf();
					Map<String, Object> jsonData = new HashMap<>();
					// Image
					jsonData.put("Image", this.GlobalImage());


					// Cmd
					List<String> cmdList = Arrays.asList("bin/bash", "-c",
							"sleep 20; ./install/bin/niralos-smfd -c /niral_config/yaml/smf.yaml -l /niral_config/log/smf.log");
					jsonData.put("Cmd", cmdList);

					// HostConfig
					Map<String, Object> hostConfig = new HashMap<>();
					List<String> binds = new ArrayList<>();
					binds.add("/home/"+vmPathNameConfiguration+"/niral_config:/niral_config");
					hostConfig.put("Binds", binds);

					hostConfig.put("NetworkMode", "niralos-mesh");
					// Add HostConfig to the main request
					jsonData.put("HostConfig", hostConfig);

					// RestartPolicy
					Map<String, String> restartPolicy = new HashMap<>();
					restartPolicy.put("Name", "always");
					hostConfig.put("RestartPolicy", restartPolicy);
					jsonData.put("HostConfig", hostConfig);

					// Network Configuration

					// Create a networks HashMap and add niralos-mesh property (similar to your
					// provided NetworkSettings)
					Map<String, Object> niralosMeshNetwork = new HashMap<>();
					niralosMeshNetwork.put("IPAMConfig", null);
					niralosMeshNetwork.put("Links", null);
					// Add other properties as needed

					Map<String, Object> networks = new HashMap<>();
					networks.put("niralos-mesh", niralosMeshNetwork);

					// Create a HostConfig HashMap and add Networks property
					hostConfig.put("Networks", networks);

					// Add networks to the container creation config
					jsonData.put("NetworkingConfig", Map.of("EndpointsConfig", networks));

					DockerContainerResponse dockerContainerResponse = this.Webclient().post().uri(createEndpoint)
							.bodyValue(jsonData).retrieve().bodyToMono(DockerContainerResponse.class).block();

					logger.info("Image container complete");
					String containerId = dockerContainerResponse.getId();
					if (containerId != null) {
						String startEndpoint = "/v1.44/containers/" + dockerContainerResponse.getId() + "/restart";
//				 String startUrl = dockerApiUrl + startEndpoint;
						this.restartContainer(startEndpoint);
					}
					tenentClientInterface.getClientDatas();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}
//	public void updateCommandInUPFDockerconfiguration(String networking) {
//		
//	}
	
	
	@Override
	public void niralosUpf(String networking) {
		
		ServiceofNetworkfunctionComposeimpl ti = new ServiceofNetworkfunctionComposeimpl();
		try {
//			String containerName = "niralos-upf";

			
			synchronized (ti) {
				String endpoint = "/v1.44/images/create?fromImage=" + this.GlobalImage();
				this.pullImageWebclient(endpoint);
			}
			logger.info("Waiting for condition...complete");

			synchronized (lock) {
				try {
					lock.wait(); // Wait for the notification from the other thread (ti)

					// Creating the container
					String createEndpoint = "/containers/create?name=" + containerData().getUpf();
					Map<String, Object> jsonData = new HashMap<>();
					// Image
					jsonData.put("Image", this.GlobalImage());


					
//					NiralAgentApplicationNApplication agentApplicationNApplication = new NiralAgentApplicationNApplication();
//					dockerNetworkConfigurationrepo
					
					switch (networking) {
					case "0": {
						// Cmd
						List<String> cmdList = Arrays.asList("bin/bash", "-c",
								"sleep 3; ip tuntap add name niralos-gw mode tun ; ip addr add 10.101.0.1/16 dev niralos-gw ; sysctl -w net.ipv6.conf.all.disable_ipv6=1 ; ip link set niralos-gw up ; sh -c \"echo 1 > /proc/sys/net/ipv4/ip_forward\" ; sleep 20 ; ./install/bin/niralos-upfd -c /niral_config/yaml/upf.yaml -l /niral_config/log/upf.log "

						);
						jsonData.put("Cmd", cmdList);
						break;
					}
					case "1": {
						String gateway =configuration.upfdefaultGateway();
						// Cmd
						List<String> cmdList = Arrays.asList("bin/bash", "-c",
								"sleep 3; ip tuntap add name niralos-gw mode tun ; ip addr add 10.101.0.1/16 dev niralos-gw ; sysctl -w net.ipv6.conf.all.disable_ipv6=1 ; ip link set niralos-gw up ; sh -c \"echo 1 > /proc/sys/net/ipv4/ip_forward\" ; iptables -t nat -A POSTROUTING -o eth0 -s 10.101.0.0/16 -p all -j MASQUERADE ; route add default dev eth0 gw"+gateway+"; sleep 20 ; ./install/bin/niralos-upfd -c /niral_config/yaml/upf.yaml -l /niral_config/log/upf.log "

						);
						jsonData.put("Cmd", cmdList);
						break;
					}
					default:
						throw new IllegalArgumentException("Unexpected value: " + networking);
					}
					
					

					// HostConfig
					Map<String, Object> hostConfig = new HashMap<>();
					List<String> binds = new ArrayList<>();
					binds.add("/home/"+vmPathNameConfiguration+"/niral_config:/niral_config");
					hostConfig.put("Binds", binds);

					hostConfig.put("NetworkMode", "niralos-mesh");
					// Add HostConfig to the main request
					jsonData.put("HostConfig", hostConfig);

					// RestartPolicy
					Map<String, String> restartPolicy = new HashMap<>();
					restartPolicy.put("Name", "always");
					hostConfig.put("Privileged", true);
					hostConfig.put("RestartPolicy", restartPolicy);
					jsonData.put("HostConfig", hostConfig);

					// Network Configuration

					// Create a networks HashMap and add niralos-mesh property (similar to your
					// provided NetworkSettings)
					Map<String, Object> niralosMeshNetwork = new HashMap<>();
					niralosMeshNetwork.put("IPAMConfig", null);
					niralosMeshNetwork.put("Links", null);
					// Add other properties as needed

					Map<String, Object> networks = new HashMap<>();
					networks.put("niralos-mesh", niralosMeshNetwork);

					// Create a HostConfig HashMap and add Networks property
					hostConfig.put("Networks", networks);

					// Add networks to the container creation config
					jsonData.put("NetworkingConfig", Map.of("EndpointsConfig", networks));

					DockerContainerResponse dockerContainerResponse = this.Webclient().post().uri(createEndpoint)
							.bodyValue(jsonData).retrieve().bodyToMono(DockerContainerResponse.class).block();

					logger.info("Image container complete");
					
					
					
					String containerId = dockerContainerResponse.getId();
					if (containerId != null) {
						String startEndpoint = "/v1.44/containers/" + dockerContainerResponse.getId() + "/restart";

						this.restartContainer(startEndpoint);
					}
					tenentClientInterface.getClientDatas();
				} catch (Exception e) {
				}
			}

		} catch (Exception e) {
		}
		
		try {
			Thread.sleep(10000);
			//Adding the configuration of upf
	String ip2 =configuration.upfN3();
	this.addTheMacvlanNetwork(containerData().getUpf(),ip2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}

	@Override
	public void niralosDeletionofContainer() {
		
		List<HashMap<String, Object>> startResponseBody = this.Webclient().get().uri("/v1.44/containers/json?all")
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<HashMap<String, Object>>>() {
				}).block();

		if (startResponseBody != null) {
			tenentClientInterface.getClientDatas();
			for (HashMap<String, Object> containerInfo : startResponseBody) {
				// Access data in the HashMap as needed
				logger.info("ContainerId : " + containerInfo.get("Id") + "Image: " +containerInfo.get("Image") + "Names : " + containerInfo.get("Names"));
				
				List<String> containersName =this.returnContainers();
				for (String hashMap : containersName) {
				// Add additional processing as needed
				String ContainerNamesSplitpricess = containerInfo.get("Names").toString();
				String Data [] = ContainerNamesSplitpricess.split("/");
				String Data1 [] = Data[1].split("]");
				
				if (hashMap.equals(Data1[0])) {
					this.Webclient().post().uri("/v1.44/containers/" + containerInfo.get("Id") + "/stop").retrieve()
							.toBodilessEntity().block();

					logger.info("Container stopped successfully: " + containerInfo.get("Id"));
					tenentClientInterface.getClientDatas();
					this.Webclient().delete().uri("/v1.44/containers/" + containerInfo.get("Id")).retrieve().toBodilessEntity()
							.block();

					logger.info("Container removed successfully: " + containerInfo.get("Id"));
				}

			}
		}}
		tenentClientInterface.getClientDatas();
	}


//	@Override
	public void addTheMacvlanNetwork(String containerName, String ip) {
//		WebClient webClient = WebClient.builder().baseUrl("http://192.168.0.24:2375").build();
		List<HashMap<String, Object>> containers = this.Webclient().get().uri("/v1.44/containers/json").retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<HashMap<String, Object>>>() {
				}) // Use List as a generic type
				.block(); // Block to wait for the response
		
		
		// Handle the list of containers (you may want to print or process the data)
		if (containers != null) {
			for (Map<String, Object> container : containers) {
				// Add additional processing as needed
				String Names = container.get("Names").toString();
				String Data [] = Names.split("/");
				String Data1 [] = Data[1].split("]");
//				String  list[] = ["niralos-amf","niralos-upf"]
				if(Data1[0].equals(containerName)){
					logger.info("ContainerId : " + container.get("Id") + "Container: " +Data1[0] + "Image : " + container.get("Image"));
		List<HashMap<String, Object>> startResponseBody = this.Webclient().get().uri("/v1.44/networks").retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<HashMap<String, Object>>>() {
				}).block();
		this.addNetworkConfiguration(startResponseBody,container.get("Id"),this.Webclient(),ip);
		
		
		
				}
			}
		}
		else
		{
			logger.info("contsiner id is null");
		}
	}
	
	public void restartContainer(String startEndpoint) {
		this.Webclient().post().uri(startEndpoint).retrieve().toBodilessEntity()
		.subscribe(startResponseBody -> {
			logger.info("Container start response: " + startResponseBody);
		}, throwable -> {
			if (throwable instanceof WebClientResponseException) {
				WebClientResponseException responseException = (WebClientResponseException) throwable;
				logger.error(
						"Error response body: " + responseException.getResponseBodyAsString());
			}
			logger.error("Error: " + throwable.getMessage());
		});
	}
	
	
	
	public void pullImageWebclient(String endpoint) {
		this.Webclient().post().uri(endpoint).retrieve().bodyToMono(String.class)
		.doOnSubscribe(
				subscription -> logger.info("Image pull for '" + this.GlobalImage() + "' in progress..."))
		.doOnTerminate(() -> {
			logger.info("Image pull complete.");
			synchronized (lock) {
				lock.notify(); // Notify the waiting thread (ti2)
			}
		}).subscribe(responseBody -> {
			// Process the response body if needed
		}, throwable -> {
			logger.error("Error: " + throwable.getMessage());
		});

	}
	public String GlobalImage() {
		
	
		String productName = "niralos-5g-core";
		PorductUpdateInfo porductUpdateInfo = porductUpdateInfoRepository.findByProductName(productName);
		String version=porductUpdateInfo.getProductVersion();	
		String imageRepo="niralnetworks/niralos-5g-core";
		String imageName = imageRepo+":"+version;

		try{
			tenentClientInterface.getClientDatas();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return imageName;
	}


	

	public WebClient Webclient() {
//		docker engine Ip and Port Configuration
		WebClient webClient = WebClient.builder().baseUrl("http://"+dockerengineIp+":"+dockerengineport).build();
		return webClient;
	}

	public List<HashMap<String, Object>> addNetworkConfiguration(List<HashMap<String, Object>> startResponseBody,Object containerId,WebClient webClient,String ip) {
		if (startResponseBody != null) {
			for (HashMap<String, Object> networkInfo : startResponseBody) {
				// Add additional processing as needed
				String Name = networkInfo.get("Name").toString();
				if (Name.equals("niralos-ngap-gtpu")) {
					Map<String, Object> requestBody = new HashMap<>();
					requestBody.put("Container", containerId);// needs to change here
					// EndpointConfig
					Map<String, Object> endpointConfig = new HashMap<>();
					Map<String, Object> ipamConfig = new HashMap<>();
					ipamConfig.put("IPv4Address", ip);
//	                     ipamConfig.put("IPv6Address", "2001:db8::5689");
					endpointConfig.put("IPAMConfig", ipamConfig);
					requestBody.put("EndpointConfig", endpointConfig);
					
					
					webClient.post().uri("/v1.43/networks/" + networkInfo.get("Id") + "/connect")
							.body(BodyInserters.fromValue(requestBody)).retrieve().bodyToMono(String.class)
							.subscribe(response -> {
								// Handle the response (e.g., print or process the response)
								logger.info(response);
							});

				}

			}
		}
		return null;
		
	}
	
	public CoreDockerContainerNames containerData() {
		CoreDockerContainerNames containerNames = new CoreDockerContainerNames();
		containerNames.setAmf("niralos-amf-1");
		containerNames.setSmf("niralos-smf-1");
		containerNames.setNrf("niralos-nrf-1");
		containerNames.setNssf("niralos-nssf-1");
		containerNames.setUpf("niralos-upf-1");
		containerNames.setUdr("niralos-udr-1");
		containerNames.setBsf("niralos-bsf-1");
		containerNames.setScp("niralos-scp-1");
		containerNames.setPcf("niralos-pcf-1");
		containerNames.setAusf("niralos-ausf-1");
		containerNames.setUdm("niralos-udm-1");
		
		
		return containerNames;
	}
	
     public List<String> returnContainers()
     {
    	 List<String> containers = new ArrayList<>();
         containers.add("niralos-amf-1");
         containers.add("niralos-smf-1");
         containers.add("niralos-nrf-1");
         containers.add("niralos-nssf-1");
         containers.add("niralos-upf-1");
         containers.add("niralos-udr-1");
         containers.add("niralos-bsf-1");
         containers.add("niralos-scp-1");
         containers.add("niralos-pcf-1");
         containers.add("niralos-ausf-1");
         containers.add("niralos-udm-1");
         return containers;
     }
	@Override
	public void pullImageofMongo() {

		ServiceofNetworkfunctionComposeimpl ti = new ServiceofNetworkfunctionComposeimpl();
	    try {
		String imageName = "mongo:4.4.2";
	    String containerName = "niralos-mongo";

	    
	
	  synchronized (ti) {
		  String endpoint = "/v1.44/images/create?fromImage=" + imageName;
          this.Webclient().post().uri(endpoint)
                  .retrieve()
                  .bodyToMono(String.class)
                  .doOnSubscribe(subscription -> logger.info("Image pull for '" + imageName + "' in progress..."))
                  .doOnTerminate(() -> {
                	  logger.info("Image pull complete.");
                      synchronized (lock) {
                          lock.notify(); // Notify the waiting thread (ti2)
                      }
                  })
                  .subscribe(responseBody -> {
                      // Process the response body if needed
                  }, throwable -> {
                	  logger.error("Error: " + throwable.getMessage());
                  });
      }
	  logger.info("Waiting for condition...complete");
	  
	  synchronized (lock) {
          try {
              lock.wait(); // Wait for the notification from the other thread (ti)
	    //Creating the container
      		String createEndpoint = "/containers/create?name=" + containerName;
//	    String createUrl = dockerApiUrl + createEndpoint; 


      Map<String, Object> requestBodyMap = new HashMap<>();
      requestBodyMap.put("Image", imageName);
//      requestBodyMap.put("Env", new String[]{"MONGO_INITDB_ROOT_USERNAME=admin", "MONGO_INITDB_ROOT_PASSWORD=adminpass"});
      requestBodyMap.put("ExposedPorts", Map.of("27017/tcp", Map.of()));
      Map<String, Object> hostConfig = new HashMap<>();
      hostConfig.put("PortBindings", Map.of("27017/tcp", List.of(Map.of("HostPort", "27017"))));
      
      List<String> binds = new ArrayList<>();
		hostConfig.put("Binds", binds);
		binds.add("/home/"+vmPathNameConfiguration+"/mongo/data:/data/db");
		
		hostConfig.put("NetworkMode", "niralos-mesh");
		// Add HostConfig to the main request
		requestBodyMap.put("HostConfig", hostConfig);

		// RestartPolicy
		Map<String, String> restartPolicy = new HashMap<>();
		restartPolicy.put("Name", "always");
		hostConfig.put("RestartPolicy", restartPolicy);
		requestBodyMap.put("HostConfig", hostConfig);

		// Network Configuration

		// Create a networks HashMap and add niralos-mesh property (similar to your
		// provided NetworkSettings)
		Map<String, Object> niralosMeshNetwork = new HashMap<>();
		niralosMeshNetwork.put("IPAMConfig", null);
		niralosMeshNetwork.put("Links", null);
		// Add other properties as needed

		Map<String, Object> networks = new HashMap<>();
		networks.put("niralos-mesh", niralosMeshNetwork);

		// Create a HostConfig HashMap and add Networks property
		hostConfig.put("Networks", networks);

		// Add networks to the container creation config
		requestBodyMap.put("NetworkingConfig", Map.of("EndpointsConfig", networks));
		requestBodyMap.put("Name", "mongo-container");
      
      
      
      
      requestBodyMap.put("HostConfig", hostConfig);
	  
      DockerContainerResponse dockerContainerResponse=  this.Webclient().post()
      .uri(createEndpoint)
      .bodyValue(requestBodyMap)
      .retrieve()
      .bodyToMono(DockerContainerResponse.class)
      .doOnSuccess(response -> System.out.println("Container created: " + response))
      .block(); // blocking for simplicity, consider using subscribe() in a real application
		
	  
      logger.info("Image container complete");

		String containerId =  dockerContainerResponse.getId();
		if (containerId != null) {
		 String startEndpoint = "/v1.44/containers/"+dockerContainerResponse.getId()+"/restart";
//		 String startUrl = dockerApiUrl + startEndpoint;

		 this.Webclient()
		 .post().uri(startEndpoint)
		 .retrieve()
		 .toBodilessEntity()
		 .subscribe(startResponseBody -> {
			 logger.info("Container start response: " + startResponseBody);
		 }, throwable -> {
		     if (throwable instanceof WebClientResponseException) {
		         WebClientResponseException responseException = (WebClientResponseException) throwable;
		         logger.error("Error response body: " + responseException.getResponseBodyAsString());
		     }
		     logger.error("Error: " + throwable.getMessage());
		 });
		}
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
          logger.info("wait is finish now it's time to rock");
    }
    
}catch (Exception e) {
	logger.error("error occured "+e);
}
		
	}

	@Override
	public void niralosDeletionofUpfContainer() {
		
		List<HashMap<String, Object>> startResponseBody = this.Webclient().get().uri("/v1.44/containers/json?all")
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<HashMap<String, Object>>>() {
				}).block();
	
		if (startResponseBody != null) {
			for (HashMap<String, Object> containerInfo : startResponseBody) {
				// Access data in the HashMap as needed
				logger.info("ContainerId : " + containerInfo.get("Id") + "Image: " +containerInfo.get("Image") + "Names : " + containerInfo.get("Names"));
				

				// Add additional processing as needed
				String ContainerNamesSplitpricess = containerInfo.get("Names").toString();
				String Data [] = ContainerNamesSplitpricess.split("/");
				String Data1 [] = Data[1].split("]");
				
				if ("niralos-upf-1".equals(Data1[0])) {
					this.Webclient().post().uri("/v1.44/containers/" + containerInfo.get("Id") + "/stop").retrieve()
							.toBodilessEntity().block();

					logger.info("Container stopped successfully: " + containerInfo.get("Id"));

					this.Webclient().delete().uri("/v1.44/containers/" + containerInfo.get("Id")).retrieve().toBodilessEntity()
							.block();

					logger.info("Container removed successfully: " + containerInfo.get("Id"));
				}

			}
		}
		
	}

	@Override
	public void niralosDeletionofAmfContainer() {
		List<HashMap<String, Object>> startResponseBody = this.Webclient().get().uri("/v1.43/containers/json?all")
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<HashMap<String, Object>>>() {
				}).block();
	
		if (startResponseBody != null) {
			for (HashMap<String, Object> containerInfo : startResponseBody) {
				// Access data in the HashMap as needed
				logger.info("ContainerId : " + containerInfo.get("Id") + "Image: " +containerInfo.get("Image") + "Names : " + containerInfo.get("Names"));
				

				// Add additional processing as needed
				String ContainerNamesSplitpricess = containerInfo.get("Names").toString();
				String Data [] = ContainerNamesSplitpricess.split("/");
				String Data1 [] = Data[1].split("]");
				
				if ("niralos-amf-1".equals(Data1[0])) {
					this.Webclient().post().uri("/v1.43/containers/" + containerInfo.get("Id") + "/stop").retrieve()
							.toBodilessEntity().block();

					logger.info("Container stopped successfully: " + containerInfo.get("Id"));

					this.Webclient().delete().uri("/v1.43/containers/" + containerInfo.get("Id")).retrieve().toBodilessEntity()
							.block();

					logger.info("Container removed successfully: " + containerInfo.get("Id"));
				}

			}
		}
		
	}

	@Override
	public void niralosDeletionofSmfContainer() {
		List<HashMap<String, Object>> startResponseBody = this.Webclient().get().uri("/v1.43/containers/json?all")
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<HashMap<String, Object>>>() {
				}).block();
	
		if (startResponseBody != null) {
			for (HashMap<String, Object> containerInfo : startResponseBody) {
				// Access data in the HashMap as needed
				logger.info("ContainerId : " + containerInfo.get("Id") + "Image: " +containerInfo.get("Image") + "Names : " + containerInfo.get("Names"));
				

				// Add additional processing as needed
				String ContainerNamesSplitpricess = containerInfo.get("Names").toString();
				String Data [] = ContainerNamesSplitpricess.split("/");
				String Data1 [] = Data[1].split("]");
				
				if ("niralos-smf-1".equals(Data1[0])) {
					this.Webclient().post().uri("/v1.43/containers/" + containerInfo.get("Id") + "/stop").retrieve()
							.toBodilessEntity().block();

					logger.info("Container stopped successfully: " + containerInfo.get("Id"));

					this.Webclient().delete().uri("/v1.43/containers/" + containerInfo.get("Id")).retrieve().toBodilessEntity()
							.block();

					logger.info("Container removed successfully: " + containerInfo.get("Id"));
				}

			}
		}
		
	}

	@Override
	public void niralosDeletionofNssfContainer() {
		List<HashMap<String, Object>> startResponseBody = this.Webclient().get().uri("/v1.43/containers/json?all")
				.accept(MediaType.APPLICATION_JSON).retrieve()
				.bodyToMono(new ParameterizedTypeReference<List<HashMap<String, Object>>>() {
				}).block();
	
		if (startResponseBody != null) {
			for (HashMap<String, Object> containerInfo : startResponseBody) {
				// Access data in the HashMap as needed
				logger.info("ContainerId : " + containerInfo.get("Id") + "Image: " +containerInfo.get("Image") + "Names : " + containerInfo.get("Names"));
				

				// Add additional processing as needed
				String ContainerNamesSplitpricess = containerInfo.get("Names").toString();
				String Data [] = ContainerNamesSplitpricess.split("/");
				String Data1 [] = Data[1].split("]");
				
				if ("niralos-nssf-1".equals(Data1[0])) {
					this.Webclient().post().uri("/v1.43/containers/" + containerInfo.get("Id") + "/stop").retrieve()
							.toBodilessEntity().block();

					logger.info("Container stopped successfully: " + containerInfo.get("Id"));

					this.Webclient().delete().uri("/v1.43/containers/" + containerInfo.get("Id")).retrieve().toBodilessEntity()
							.block();

					logger.info("Container removed successfully: " + containerInfo.get("Id"));
				}

			}
		}
		
	}
		
	
	



}


