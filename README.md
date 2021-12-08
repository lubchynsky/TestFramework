# TestFramework
##Execution:
    - From the IDE: just run main class -> framework.Main
    - From CMD:
        - Build project 'mvn clean package'
        - Inside target folder, execute 'java -jar AkamaiTestFramework.jar'
    - Test results from executions could be found under results folder.

##Additional parameters:
    - Without passing any parameter, default suite will be executed on CHROME browser
    - Browser type [firefox, chrome, edge] could be specified by passing -Dbrowser={} parameter
        -Dbrowser=firefox
        -Dbrowser=chrome
        -Dbrowser=edge
    - Test suite could be specified by passing -Dtests={} parameter. Multiple tests could be passed and separated by ';'
        -Dtests=NoJobsFoundNotificationTest
        -Dtests=UnloggedCustomerSearchJobTest
        -Dtests=NoJobsFoundNotificationTest;UnloggedCustomerSearchJobTest
        
##Execution commands examples:
    1) java -jar AkamaiTestFramework.jar --> executes default suite and default browser
    2) java -jar -Dbrowser=firefox AkamaiTestFramework.jar --> executes default suite on FIREFOX
    3) java -jar -Dbrowser=edge -Dtests=NoJobsFoundNotificationTest AkamaiTestFramework.jar --> executes NoJobsFoundNotificationTest on EDGE
        