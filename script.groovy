def buildApplication(){
  echo "building the application..."
}
def testApplication(){
  echo "Running tests..."
}
def deployApplication(){
  //All eniromental variables present in jenkinsFile are available in groovy as well
  echo 'Deploying...'
  echo "deploying version ${params.VERSION}"
}
return this