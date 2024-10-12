def testApplication(){
  //All eniromental variables present in jenkinsFile are available in groovy as well
  echo "Testing ...."
  echo "Testing Branch $BRANCH_NAME"
}
return this