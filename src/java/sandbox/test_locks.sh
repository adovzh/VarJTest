TEST_LOCKS_CLASS=dan.vjtest.sandbox.concurrency.lock.LockTest
CPATH=../../../out/production/VarJTest
JAVA_CMD=/usr/lib/icedtea7/bin/java
set -x
for i in {1..8}; do $JAVA_CMD -XX:-UseBiasedLocking -cp $CPATH $TEST_LOCKS_CLASS JVM $i; done
for i in {1..8}; do $JAVA_CMD -XX:+UseBiasedLocking -cp $CPATH $TEST_LOCKS_CLASS JVM $i; done
for i in {1..8}; do $JAVA_CMD -cp $CPATH $TEST_LOCKS_CLASS JUC $i; done