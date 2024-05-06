## Overview
This repository is used to demonstrate a CI/CD pipeline using GitHub Actions. The pipeline includes the following stages:
- Checkout the test project from Git repository
- Install Firefox version 124.0.2
- Build project
- Run tests on Firefox
- Generate report
- Publish report to github
- Upload report

The pipeline will be triggered once there is a new commit in the project repository, all stages will be executed and the test report will be generated and published.
## How to run the workflow
#### Prerequisites
- Sign up an GitHub Account
- Install git on your local machine
- Using pulic repository https://github.com/camthinguyen/Automation-Test-Project
  
#### Step 1 - Pull code Automation-Test-Project from git repository
- Create folder "Automation-Test-Project" on your local machine
- Open gitbash
- Navigate to your directory "Automation-Test-Project"
- Use git init command creates a new Git repository

  `git init`

- Pull code from git repo "Automation-Test-Project"

  `git pull git@github.com:camthinguyen/Automation-Test-Project.git`

#### Step 2: Change code and push it to github
- You can change existing code or add new file. In this case, I will add a new file temp.txt
- Commit and push code to github

  `git add .`

  `git commit -m "Add new file"`

  `git remote add origin git@github.com:camthinguyen/Automation-Test-Project.git`

  `git push origin master`

#### Step 3: View the report after pipeline run successfully
After commit code successful, the workflow will be triggered and run
![2024-05-06_22h08_59](https://github.com/camthinguyen/Automation-Test-Project/assets/17824398/72906621-dca2-446b-b941-194b755de70f)

Click link to view the action [https://github.com/camthinguyen/Automation-Test-Project/actions](https://github.com/camthinguyen/Automation-Test-Project/actions/runs/8971042402/job/24635979466)
The test result will be puplished to show the test metrics

![2024-05-06_22h10_34](https://github.com/camthinguyen/Automation-Test-Project/assets/17824398/7edaaa5e-3219-4172-9a2a-bcce8ffe5907)

Or you can download report.zip to view fully html report
![2024-05-06_22h14_34](https://github.com/camthinguyen/Automation-Test-Project/assets/17824398/7a2fff03-7c67-4758-9258-e41a623d7c47)

Open Automation-Test-Project-Report.html
![2024-05-06_22h23_08](https://github.com/camthinguyen/Automation-Test-Project/assets/17824398/19ae98f6-6518-4e0f-ad2f-057b82d8f32c)

![2024-05-06_22h21_07](https://github.com/camthinguyen/Automation-Test-Project/assets/17824398/80faca35-711e-4d7d-bb72-fa270e8b0d2d)


## Appendix (for reference purpose)
### How-to set up CI CD pipeline for Automation-Test-Project with Github Actions

#### Step 1 - Create a new public repository
- Sign in to github https://github.com/
- Click on button "+" and select "New repository"
- Input name for repository Automation-Test-Project
- Choose option "public" 
- Click button "Create repository" at the end of the page to create new repository
  
#### Step 2 - Link the Automation-Test-Project project to repository
- Open gitbash, navigate to project folder Automation-Test-Project

- Initialize the local directory as a Git repository.
  
  `git init`

- Add the files in your new local repository. This stages them for the first commit
  
  `git add .`
  
  or:
  
  `git add --all`

- Commit the files that you've staged in your local repository.
  
  `git commit -m "First commit"`

- Remote the local repository to the remote one
  
  `git remote add origin <remote repository URL>`

- Push the changes in your local repository to GitHub.
  
  `git push origin master`

#### Step 3 - Create pipeline action 
- Select to open Automation-Test-Project repository
- Click button "Actions"
- On the "Java with Maven" workflow, click "Configure"
- Github shows the workflow sample for Maven project, you can customize it to match your workflow.
- Click Commit changes. The maven.yml workflow file is added to the .github/workflows directory of Automation-Test-Project repository. I have customize the maven.yml as below;

```sh
## This workflow will build a Java project with Maven, and cache/restore any dependencies to improve the workflow execution time
# For more information see: https://docs.github.com/en/actions/automating-builds-and-tests/building-and-testing-java-with-maven

# This workflow uses actions that are not certified by GitHub.
# They are provided by a third-party and are governed by
# separate terms of service, privacy policy, and support
# documentation.

name: Automation-Test-Project CICD

on:
  push:
    branches: [ "master" ]
  pull_request:
    branches: [ "master" ]

jobs:
  buid-test:
    runs-on: ubuntu-latest

    permissions:
      checks: write
      pull-requests: write
    
    steps:
      - name: Checkout
        uses: actions/checkout@v4
      - name: Set up Firefox
        uses: browser-actions/setup-firefox@v1
        with:
          firefox-version:  '124.0.2'          
                                  
      - name: Build project 
        run: mvn compile
        
      - name: Run test
        run: mvn test -Dbrowser=firefox -Dheadless=true
          
      - name: Generate html report
        if: success() || failure()
        run: mvn site
        
      - name: Publish Test Results
        if: success() || failure()
        uses: EnricoMi/publish-unit-test-result-action@v2        
        with:
         files: |
          target/surefire-reports/TEST-saucedemo.com.testcases.LoginTest.xml
          target/surefire-reports/TEST-saucedemo.com.testcases.AddProductsToCartTest.xml
          
      - uses: actions/upload-artifact@v4
        if: success() || failure()
        with:
          name: report
          path: target/site

```
> Notes:
 Github action file use extension .yml or .yaml
 >
 `name: Automation-Test-Project CICD`
 Name of workflow, you can modify your workflow's name
 
`on` Specifies the trigger for this workflow. In this case, the trigger runs the workflow if there is a push on "master" branch or "pull-request" merged to master branch
 
`jobs` List of jobs used in workflow

`buid-test:` Name of the job
 
`runs-on: ubuntu-latest` Configures the job to run on the latest version of an Ubuntu Linux runner. Github supply us some runners on ubuntu, window or macOs (ubuntu-latest, ubuntu-22.04, windows-latest, macos-latest...). We need to define run-ons on for every job by using github runners or self-hosted runners
 ```sh
permissions:
checks: write
pull-requests: write
```
Set permissions for "buid-test" job, it's used in puplish report step on the workflow
```sh
steps
 - name: Checkout
   uses: actions/checkout@v4
```  
Step name is "Checkout", in this step we use "actions/checkout@v4 supplied by GitHub. This is an action that checks out your repository onto the runner

```sh
- name: Set up Firefox
    uses: browser-actions/setup-firefox@v1
    with:
      firefox-version:  '124.0.2'     
```
Set up firefox version 124.0.2

```sh
 - name: Build project 
   run: javac src/main/java/com/example/logic/*.java
```
Step Build project, using "run" command to build the java project

```sh
- name: Run test
  run: mvn test -Dbrowser=firefox -Dheadless=true
```
Step for executing the testcases using maven command, using headless option

```sh
 - name: Generate report
   if: success() || failure()
   run: mvn site
```
 `if: success() || failure()` Set a condition for running this step that is not dependent on the result of the previous step
 `run: mvn site` Generate the surfire report from the test result 
 
 ```sh
 - name: Publish Test Results
   if: success() || failure()
   uses: EnricoMi/publish-unit-test-result-action@v2        
    with:
    files: |
      target/surefire-reports/TEST-saucedemo.com.testcases.LoginTest.xml
      target/surefire-reports/TEST-saucedemo.com.testcases.AddProductsToCartTest.xml
  ```
`uses: EnricoMi/publish-unit-test-result-action@v2` This GitHub Action analyses test result files and publishes the results on GitHub

```sh 
target/surefire-reports/TEST-saucedemo.com.testcases.LoginTest.xml
          target/surefire-reports/TEST-saucedemo.com.testcases.AddProductsToCartTest.xml
```
`The path of xml files are used to publish report on github. It supports JSON, TRX and XML file formats, and runs on Linux, macOS and Windows

```sh
- uses: actions/upload-artifact@v4
        if: success() || failure()
        with:
          name: report
          path: target/site
```
`Upload html report on github, download "report.zip" to view fully report.


