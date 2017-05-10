#!/bin/bash
################################################################################################################################
#
#This file aids in git pushing to a different branch so as to ensure the build folder is always pushed.
#
################################################################################################################################

cd ../../../../javaGreen
git rm --cached -r .
cd $FOLDER_INPUT

#Add in a file to obtain current machine's IPADDR
ifconfig en0 | grep inet | grep -v inet6 | awk '{print $2}' > ipadd

#Checking out to new branch and push
git checkout -b epoch$(date +%s)

#Delete all except current branch
git branch -D `git branch | grep -E epoch.*`

git add build/
git add ipadd
git commit -m 'no comments'
git push

#Retrieving ref names
LOCAL_BRANCH=`git for-each-ref --format='%(refname)' | egrep 'epoch' | awk -F/ 'NR==1{print $NF}'`

git branch -r | egrep ^..origin/epoch.*$ | egrep -v ${LOCAL_BRANCH} | while read line; do
  if [ -n '${line}' ]; then
    branch=`echo ${line} | awk -F/ '{print $NF}'`
    git push origin :${branch}
  else
    echo 'Remote branch not found'
  fi
done
