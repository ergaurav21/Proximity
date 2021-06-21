pipeline {
	agent { docker {
		image 'maven:latest'
	}
	}
	tools {
    	maven 'Maven'
	}
	environment {
              registry = "ergaurav21/proximity"
              registryCredential = 'docker'		
	         }
	stages {
    	stage("Checkout") {   
        	
        	steps {               	 
            	git url: 'https://github.com/ergaurav21/Proximity.git'          	 
           	 
        	}    
    	}
    	stage('Build') {
		
        	steps {
          
        	sh "mvn clean compile"  	 
        	}
    	}
		
		
   	 
    	stage("Unit test") {          	 
        	
        	steps {  	 
            	
            
                 sh "docker compose up -d"
            	sh "mvn test"  
			
       	}  
	
    	}
		stage("Post clean up") {   
			
        	steps {  	 
            
            	
                 sh "docker compose down"
            	
			
        	}  
	
    	     }
		
	 stage ('Building a Docker image'){
               steps {
                 script {
                  docker.build registry + ":$BUILD_NUMBER"
                 }
             }
          }
		
   stage('Deploy Image') {
      steps{
        script {
          docker.withRegistry( '', registryCredential ) {
            sh 'docker push $registry:$BUILD_NUMBER'
          }
        }
      }
    }
  
  stage ('Deploy to Docker') {
          
      steps {
	      sh "docker run -p 8080:8080 -d $registry:$BUILD_NUMBER"
        sh "docker ps -a"
       }           
             
     }
		
	}

}
