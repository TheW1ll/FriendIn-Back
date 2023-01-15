cd hsqldb
java -cp hsqldb.jar org.hsqldb.Server &
hsql_server_id=$!
cd ..
./gradlew -Dorg.gradle.java.home=/home/junkyards/.jdks/corretto-17.0.5 bootRun &
back_id=$!

function finish {
  echo "on arrête tout"
  kill $back_id
  kill $hsql_server_id
}

trap finish 1 2 3

read t
finish