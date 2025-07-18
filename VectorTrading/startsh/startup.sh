ps -ef|grep -v "grep"|grep -v "bash"|grep -v ".sh"|grep -e XPRICE
if [ $? -ne 0 ];then
	echo "======================= startup CMDSFX =========================="
	nohup java -Dlogback.configurationFile=./config/logback.xml -Djava.ext.dirs=baselib:mylib:"$JAVA_HOME/jre/lib/ext" -Xms512m -Xmx1024m -Dapp=XPRICE com.erayt.rate.feed.base.Main >./logs/myout.out 2>&1 &
	echo "Start Success"
else
	echo "======================= startup CMDSFX =========================="
	echo "app is running..."
fi