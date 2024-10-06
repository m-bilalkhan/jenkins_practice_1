def buildApplication(){
  withCredentials([usernamePassword(credentialsId: 'docker-hub-repo-creds', passwordVariable: 'PASS', usernameVariable: 'USER')]) {
    sh 'docker build -t mbilalkhan/practice-repo:1.0.0 .'
    sh 'echo $PASS | docker login -u $USER --password-stdin'
    sh 'docker push mbilalkhan/practice-repo:1.0.0'
  }
}
def deployApplication(){
  //All eniromental variables present in jenkinsFile are available in groovy as well
  echo 'Deploying...'
}
return this