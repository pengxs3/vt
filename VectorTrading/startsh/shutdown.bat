@echo off
title 关闭CMDS价源产品

set JAVA_OPTIONS=-Xms512m -Xmx1024m


rem run domypayEngine 
rem :run
rem ping -n 1 127.0.0.1>nul
java -Djava.ext.dirs=baselib;mylib %JAVA_OPTIONS% com.erayt.rate.feed.base.Main stop
rem goto run
:end