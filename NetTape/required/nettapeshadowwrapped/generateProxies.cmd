@echo off
copy ..\..\lib\*.* work
..\..\bin\proxygen.exe work\NettapeShadow.dll -wd work
cd work
call build.cmd
cd ..

