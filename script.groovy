def buildApplication(){
  echo "building the application..."
}
def testApplication(){
  echo "Running tests..."
}
def deployApplication(DEPLOYMENT_SERVER){
  //All eniromental variables present in jenkinsFile are available in groovy as well
  echo 'Deploying...'
  echo "deploying version ${params.VERSION}"
  echo "deploying to enviroment ${DEPLOYMENT_SERVER}"
}
return this