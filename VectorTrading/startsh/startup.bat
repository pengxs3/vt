@echo off
tasklist /v |findstr "XPRICE"|find /i "cmd.exe" && exit
title XPRICE



set JAVA_OPTIONS=-Xms512m -Xmx1024m


rem run domypayEngine
rem :run
rem ping -n 1 127.0.0.1>nul
java -Djava.ext.dirs=baselib;mylib;"%JAVA_HOME%\jre\lib\ext" %JAVA_OPTIONS% com.erayt.rate.feed.base.Main
rem goto run
:end 