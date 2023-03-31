@echo off
if "%1" == "h" goto begin
mshta vbscript:createobject("wscript.shell").run("""%~0"" h",0)(window.close)&&exit
:begin

@echo off
title frpc.bat
set _启动命令=.\frpc -c .\frpc.ini

::下面参数不用设置
:LOOP
echo [%date% %time%]运行"%_启动命令%"
%_启动命令%
goto LOOP