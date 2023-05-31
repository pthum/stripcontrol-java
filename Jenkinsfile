library 'jshared' _

properties([
    parameters([booleanParam('deployParam')])
  ])
def serviceName = "stripcontrol-quarkuslights"
def deploy = params.deployParam
def buildUtil = new de.backenddev.jshared.BuildUtil(this)
def tag

node {

  def mvnt = tool name: 'Maven39', type: 'maven'
  def jvt = tool name: 'openjdk11', type: 'jdk'
  stage("prepare") {
      tag = buildUtil.cleanCheckout() 
  }

  stage("test and package") {
    withEnv(["PATH+MAVEN=${mvnt}/bin","PATH+JAVA=${jvt}/bin","JAVA_HOME=${jvt}","M2_HOME=${mvnt}"]) {
        sh "mvn clean package -Dmaven.test.failure.ignore=true"
    }
    buildUtil.publishTests('target/surefire-reports/*.xml')
    recordCoverage(tools: [[pattern: '**/target/site/jacoco/jacoco.xml']])
  }

  stage("build") {
    dir(serviceName) {
      def buildArgs=[:]
      def platforms=["linux/arm64", "linux/arm/v7"]
      buildUtil.buildAndPushImage(serviceName, tag, "src/main/docker/Dockerfile.jvm", ".",buildArgs, platforms)
    }
  }

  stage("deploy") {
    if(buildUtil.hasBranchDeployConfiguration() && params.deployParam){
      buildUtil.deployImageRemotely(serviceName, tag)
    }
  }
}