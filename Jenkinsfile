pipeline {
	agent none
	tools {
    	maven 'Maven'
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
            	git url: 'https://github.com/ergaurav21/Proximity.git'
            
                 sh "docker-compose up -d"
            	sh "mvn test"  
			
       	}  
	
    	}
		stage("Post clean up") {   
			agent { label 'Slave-1' }
        	steps {  	 
            
            	
                 sh "docker-compose down"
            	
			
        	}  
	
    	     }
		
		stage("docker") {   
			agent { label 'Slave-1' }
        	steps {  	 
            
            	 sh "docker login -u ergaurav21 -p india@123"
                 sh "docker build -t ergaurav21/proximity:proxity ."
		 sh "docker push ergaurav21/proximity:proxity"
            	
			
        	}  
	
    	     }
		
 
		
	}

}
