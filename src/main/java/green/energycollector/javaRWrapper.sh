#! /bin/bash
end=$((SECONDS+20))

while [ $SECONDS -lt $end ]; do
    java -classpath $1 $2;
    echo $SECONDS;
    echo 'pls';
    :
done
