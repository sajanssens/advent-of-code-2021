pipeline {
    agent any

//     post {
//         always {
//             deleteDir() // clean up our workspace
//         }
//     }
 
    stages {
        stage("Build") {
            steps {
                script {
                    bat '''
                        set;
                        mvn clean package
                    '''
                }
            }
        }
    }
}
