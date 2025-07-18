echo "======================= shutdown CMDSFX =========================="
ps -ef | grep java | grep XPRICE | grep -v grep | awk '{print $2}' | xargs kill -9
echo "Shutdown Success"