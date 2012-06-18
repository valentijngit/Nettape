@echo off
if not exist target mkdir target
if not exist target\classes mkdir target\classes


echo compile classes
javac -nowarn -d target\classes -sourcepath jvm -cp "C:\Documents and Settings\Valentijn\Mijn documenten\jni4net-0.5.0.0-bin\lib\jni4net.j-0.5.0.0.jar;" jvm\nettapeshadow\ShadowCopy.java 
IF %ERRORLEVEL% NEQ 0 goto end


echo NettapeShadow.j4n.jar 
jar cvf NettapeShadow.j4n.jar  -C target\classes nettapeshadow\ShadowCopy.class  > nul 
IF %ERRORLEVEL% NEQ 0 goto end


echo NettapeShadow.j4n.dll 
C:\WINDOWS\Microsoft.NET\Framework\v2.0.50727\csc /nologo /warn:0 /t:library /out:NettapeShadow.j4n.dll /recurse:clr\*.cs  /reference:C:\WINDOWS\Microsoft.NET\Framework\v2.0.50727\mscorlib.dll /reference:"C:\Documents and Settings\Valentijn\Mijn documenten\jni4net-0.5.0.0-bin\samples\myCSharpDemoCalc\work\NettapeShadow.dll" /reference:"C:\Documents and Settings\Valentijn\Mijn documenten\jni4net-0.5.0.0-bin\lib\jni4net.n-0.5.0.0.dll"
IF %ERRORLEVEL% NEQ 0 goto end


:end
