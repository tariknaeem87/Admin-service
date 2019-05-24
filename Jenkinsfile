#!groovy​
properties([[$class: 'jenkins.model.BuildDiscarderProperty', strategy: [$class: 'LogRotator',
                                                                        numToKeepStr: '10',
                                                                        artifactNumToKeepStr: '10']]])
node {
    step([$class: 'WsCleanup'])
    
    stage('Check Out') {
        checkout scm
    }
        
        def mvnHome = tool 'MAVEN3'
        build_version = env.BUILD_NUMBER
      
        stage('Build & Test') {
            sh " C:\softwares\apache-maven-3.6.1\bin\mvn clean verify -DBUILD_NUMBER=$build_version"
            
            junit(testResults: "admin-service/target/surefire-reports/*.xml")
            
            publishHTML([allowMissing: false, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'admin-service-it/target/jacoco-report', reportFiles: 'index.html', reportName: 'Jacoco Report'])
            publishHTML([allowMissing: false, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'admin-service-it/target/cucumber-html-report', reportFiles: 'index.html', reportName: 'Cucumber Report'])
            publishHTML([allowMissing: false, alwaysLinkToLastBuild: true, keepAll: true, reportDir: 'admin-service-it/target/site/serenity/', reportFiles: 'index.html', reportName: 'Serenity Report'])
        }
        
        
        def sonarqubeScannerHome = tool name: 'sonarqubescanner', type: 'hudson.plugins.sonar.SonarRunnerInstallation'
        stage('Code Analysis') {
            //sh "${sonarqubeScannerHome}/bin/sonar-scanner -e -Dsonar.host.url=http://sonar.ms-accelerator.com -Dsonar.projectName=admin-service -Dsonar.projectKey=com.dell.tsp.admin.tsp-admin-service:tsp-admin-service -Dsonar.sources=. -Dsonar.java.binaries=."
            sh "${mvnHome}/bin/mvn sonar:sonar"
        }

        stage('Deploy to Nexus') {
            sh "${mvnHome}/bin/mvn deploy -DBUILD_NUMBER=$build_version -Dmaven.test.skip=true"
         }
            
        stage('Deploy to Test') {
       
              
       		if(isUnix()) {
	    		sh "cf api https://api.system.emcdigital.lab --skip-ssl-validation"
	    		withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId:'pcf-demo-credentials-id', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
  					sh 'cf auth $USERNAME $PASSWORD'
            	}
           		sh "cf target -o appmod -s development"
        		sh "cf push -f manifests/manifest-test.yml"
        		sh "cf logout"
				} else{
				bat "cf api https://api.system.emcdigital.lab --skip-ssl-validation"
	    		withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId:'pcf-demo-credentials-id', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
  				bat 'cf auth %USERNAME% %PASSWORD%'
		    	}
				bat "cf target -o appmod -s development"
        			bat "cf push -f manifests/manifest-test.yml"
        			bat "cf logout"
				}
		
            }
            
        stage('Int Tests') {
              //sh 'docker rmi $(docker images --filter "dangling=true" -q --no-trunc) '
              sh "docker ps"
           }
           
        stage('Deploy to QA') {
       
              
       		if(isUnix()) {
	    		sh "cf api https://api.system.emcdigital.lab --skip-ssl-validation"
	    		withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId:'pcf-demo-credentials-id', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
  					sh 'cf auth $USERNAME $PASSWORD'
            	}
           		sh "cf target -o appmod -s qa"
        		sh "cf push -f manifests/manifest-qa.yml"
        		sh "cf logout"
				} else{
				bat "cf api https://api.system.emcdigital.lab --skip-ssl-validation"
	    		withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId:'pcf-demo-credentials-id', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
  				bat 'cf auth %USERNAME% %PASSWORD%'
		    	}
				bat "cf target -o appmod -s qa"
        			bat "cf push -f manifests/manifest-qa.yml"
        			bat "cf logout"
				}
		
            }
}
