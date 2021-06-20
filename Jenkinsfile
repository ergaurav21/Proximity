pipeline {
	agent none
	tools {
    	maven 'Maven'
	}
	environment {
              registry = "ergaurav21/proximity"
              registryCredential = 'docker'		
	         }
	stages {
    	stage("Checkout") {   
        	agent { label 'Slave-1' }
        	steps {               	 
            	git url: 'https://github.com/ergaurav21/Proximity.git'          	 
           	 
        	}    
    	}
    	stage('Build') {
        	agent { label 'Slave-1' }
        	steps {
          
        	sh "mvn clean compile"  	 
        	}
    	}
		
		
   	 
    	stage("Unit test") {          	 
        	agent { label 'Slave-1' }
        	steps {  	 
            	
            
                 sh "docker compose up -d"
            	sh "mvn test"  
			
       	}  
	
    	}
		stage("Post clean up") {   
			agent { label 'Slave-1' }
        	steps {  	 
            
            	
                 sh "docker compose down"
            	
			
        	}  
	
    	     }
		
	 stage ('Building a Docker image'){
		 agent { label 'Slave-1' }
               steps {
                 script {
                  docker.build registry + ":$BUILD_NUMBER"
                 }
             }
          }
		
   stage('Deploy Image') {
	   agent { label 'Slave-1' }
      steps{
        script {
          docker.withRegistry( 'https://registry-1.docker.io/v2/', registryCredential ) {
            sh 'docker push $registry:$BUILD_NUMBER'
          }
        }
      }
    }
  
  stage ('Deploy to Docker') {
	  agent { label 'Slave-1' }
          agent {
               label 'Slave-1' 
          }
      steps {
	      sh "docker run -p 8080:8080 -d $registry:$BUILD_NUMBER"
        sh "docker ps -a"
       }           
             
     }
		
	}

}
