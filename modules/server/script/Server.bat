@REM The MIT License (MIT)
@REM
@REM Copyright (c) 2019 ��֮�Ƽ�������
@REM
@REM Permission is hereby granted, free of charge, to any person obtaining a copy of
@REM this software and associated documentation files (the "Software"), to deal in
@REM the Software without restriction, including without limitation the rights to
@REM use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
@REM the Software, and to permit persons to whom the Software is furnished to do so,
@REM subject to the following conditions:
@REM
@REM The above copyright notice and this permission notice shall be included in all
@REM copies or substantial portions of the Software.
@REM
@REM THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
@REM IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
@REM FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
@REM COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
@REM IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
@REM CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
@REM

@echo off
setlocal enabledelayedexpansion

set Tag=KeepBx-System-JpomApplication
set MainClass=org.springframework.boot.loader.JarLauncher
set basePath=%~dp0
set Lib=%basePath%lib\
@REM �����޸�----------------------------------��
set LogName=server.log
@REM �����������Զ��޸Ĵ�����
set RUNJAR=
@REM �����޸�----------------------------------��
@REM �Ƿ�������̨��־�ļ�����
set LogBack=true
set JVM=-server
set ARGS= --jpom.applicationTag=%Tag% --jpom.log=%basePath%log --server.port=2122

@REM ��ȡjar
call:listDir

if "%1"=="" (
    color 0a
    TITLE Jpom����ϵͳBAT����̨
    echo. ***** Jpom����ϵͳBAT����̨ *****
    ::*************************************************************************************************************
    echo.
        echo.  [1] ���� start
        echo.  [2] �ر� stop
        echo.  [3] �鿴����״̬ status
        echo.  [4] ���� restart
        echo.  [5] ���� use
        echo.  [0] �� �� 0
    echo.
    @REM ����
    echo.������ѡ������:
    set /p ID=
    IF "!ID!"=="1" call:start
    IF "!ID!"=="2" call:stop
    IF "!ID!"=="3" call:status
    IF "!ID!"=="4" call:restart
    IF "!ID!"=="5" call:use
    IF "!ID!"=="0" EXIT
)else (
     if "%1"=="restart" (
        call:restart
     )else (
        call:use
     )
)
if "%2" NEQ "upgrade" (
    PAUSE
)else (
 @REM ����ֱ�ӽ���
)
EXIT 0

@REM ����
:start
    if "%JAVA_HOME%"=="" (
        echo �����á�JAVA_HOME����������
        PAUSE
        EXIT 2
    )

	echo ������.....�رմ��ڲ�Ӱ������
	javaw %JVM% -Djava.class.path="%JAVA_HOME%/lib/tools.jar;%RUNJAR%" -Dapplication=%Tag% -Dbasedir=%basePath%  %MainClass% %ARGS% >> %basePath%%LogName%
	timeout 3
goto:eof


@REM ��ȡjar
:listDir
	if "%RUNJAR%"=="" (
		for /f "delims=" %%I in ('dir /B %Lib%') do (
			if exist %Lib%%%I if not exist %Lib%%%I\nul (
			    if "%%~xI" ==".jar" (
                    if "%RUNJAR%"=="" (
				        set RUNJAR=%Lib%%%I
                    )
                )
			)
		)
	)else (
		set RUNJAR=%Lib%%RUNJAR%
	)
	echo ���У�%RUNJAR%
goto:eof

@REM �ر�Jpom
:stop
	java -Djava.class.path="%JAVA_HOME%/lib/tools.jar;%RUNJAR%" %MainClass% %ARGS% --event=stop
goto:eof

@REM �鿴Jpom����״̬
:status
	java -Djava.class.path="%JAVA_HOME%/lib/tools.jar;%RUNJAR%" %MainClass% %ARGS% --event=status
goto:eof

@REM ����Jpom
:restart
	echo ֹͣ��....
	call:stop
	timeout 3
	echo ������....
	call:start
goto:eof

@REM ��ʾ�÷�
:use
	echo please use (start��stop��restart��status)
goto:eof


