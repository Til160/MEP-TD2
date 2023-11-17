node {
    stage("Creation environnement"){
        def docker = tool 'docker'
        env.PATH = "${docker}/bin:${env.PATH}"
        sh 'export PATH=/usr/local/bin:$PATH'
    }   
    stage("Clonage du repo"){
        sh 'rm -rf MEP-TD2'
        sh 'git clone https://github.com/Til160/MEP-TD2'
        withCredentials([usernamePassword(credentialsId: 'docker-hub', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
            sh 'docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD'
        }
    }
    stage("Build") {
        sh 'docker build -t til160/mep_td2 ./MEP-TD2'
    }
    stage("Push"){
        sh 'docker push til160/mep_td2:latest'
    }
}
