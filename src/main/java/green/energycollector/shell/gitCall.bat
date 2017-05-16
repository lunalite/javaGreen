ECHO OFF
SET FOLDER_INPUT=D:\Documents\IdeaProjects\algorithms2
SET MAIN_INPUT=D:\Documents\IdeaProjects\algorithms2\algorithms\Main.java
SET NetworkIP=129.31.180.171

cd %FOLDER_INPUT%
git rm --cached -r .

::Add in a file to obtain current machine's IPADDR
::FOR /F "tokens=2,3" %%A IN ('ping %computername% -n 1 -4') DO IF "from"== "%%A" set "IP=%%~B"
::echo %IP:~0,-1%

echo %NetworkIP% > ipadd

::Checking out to new branch and push
for /f "tokens=1 delims=.," %a in ('powershell Get-Date -UFormat %s') do set date=%a
git checkout -b epoch%date%

::Delete all except current branch
git branch -D `git branch | grep -E epoch.*`

git add build/
git add ipadd
git commit -m 'no comments'
git push