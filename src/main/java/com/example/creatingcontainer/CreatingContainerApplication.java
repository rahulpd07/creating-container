package com.example.creatingcontainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.creatingcontainer.Repository.InternalDataRepository;
import com.example.creatingcontainer.Service.InitializationService;
import com.example.creatingcontainer.Service.PorductUpdateInfoInterface;
import com.example.creatingcontainer.Service.fiveGcore.DockerNetworkConfiguration;
 
 
 
@SpringBootApplication
public class CreatingContainerApplication implements CommandLineRunner{

	 @Autowired
	 DockerNetworkConfiguration dockerNetworkConfiguration;
	 @Autowired
	 PorductUpdateInfoInterface porductUpdateInfoInterface;
	 @Autowired
	 InternalDataRepository internalDataRepository;
	 @Autowired
	 InitializationService initializationService;
	
	private String dockern2Ip;
	@Value("${docker.n2ip}")
	public void setdockern2Ip(String dockern2Ip) {
		this.dockern2Ip = dockern2Ip;
	}
	 private String dockern3Ip;
		@Value("${docker.n3ip}")
		public void setdockern3Ip(String dockern3Ip) {
			this.dockern3Ip = dockern3Ip;
		}
		 private String gatewayIp;
			@Value("${docker.gatewayip}")
			public void setgatewayIp(String gatewayIp) {
				this.gatewayIp = gatewayIp;
			}
			@Value("${docker.versionof.fivegcore}")
			 public String dockerversionFivegcore;
			public void setDockerVersionFivegcore(String dockerversionFivegcore) {
				this.dockerversionFivegcore = dockerversionFivegcore;
			}
			
		    @Value("${docker.engine.port}")
		    private String dockerEngineport;
			public void setDockerEngineport(String dockerEngineport) {
				this.dockerEngineport = dockerEngineport;
			}
			@Value("${docker.engine.ip}")
		    private String dockerEngineIp;
			public void setDockerEngineIp(String dockerEngineIp) {
				this.dockerEngineIp = dockerEngineIp;
			}
			
			@Value("${global.controller.ip}")
			public String globalControllerIp;
			public void setGlobalControllerIp(String globalControllerIp) {
				this.globalControllerIp = globalControllerIp;
			}
			
			@Value("${global.controller.port}")
			public String globalControllerPort;
			public void setGlobalControllerPort(String globalControllerPort) {
				this.globalControllerPort = globalControllerPort;
			}

	public static void main(String[] args) {
		SpringApplication.run(CreatingContainerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println("11111111111111");
		initializationService.initialize();  
		System.out.println("111111111111111100000000000000");
		porductUpdateInfoInterface.savePorductUpdateInfo();
		System.out.println("ippppppppppppppppppppppppp");
		String deploymentId = internalDataRepository.getClientId();
		System.out.println("hello");
		
		internalDataRepository.updatedockerIpandPort(dockerEngineIp,dockerEngineport,deploymentId,globalControllerIp,globalControllerPort);	
		dockerNetworkConfiguration.confiurationofDockerIp(dockern2Ip,dockern3Ip,gatewayIp,dockerversionFivegcore);

	}
}
