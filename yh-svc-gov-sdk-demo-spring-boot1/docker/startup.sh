
GCLOG_DIR=$LOG_DIR
while [ ! -d $GCLOG_DIR ]
do
 echo "sleep wait ${GCLOG_DIR}"
 sleep 1
done

PROJECT_NAME="$1"

DT=`date +"%Y%m%d_%H%M%S"`
GC_OPTS="$GC_OPTS -Xloggc:${GCLOG_DIR}/gc_${HOSTNAME}_${DT}.gc"
GC_OPTS="$GC_OPTS -XX:HeapDumpPath=${GCLOG_DIR}/heapdump_${HOSTNAME}_${DT}.hprof"

START_OPTS="-javaagent:/opt/lune/skywalking-agent/skywalking-agent.jar -javaagent:/opt/lune/fiss-agent.jar"

exec java $MEM_OPTS $GC_OPTS $START_OPTS -server -jar ${PROJECT_NAME}.jar

