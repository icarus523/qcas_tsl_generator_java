CLS
@echo off
title qcasTSLgenerator
set _version=1.8
set _subversion_history=MS Excel XLS mode enabled, Deleting Games using Excel game list, Support for Bally Gaming

:menuLOOP
echo.
echo.= qcasTSLgenerator v%_version% ===========================
echo.= %_subversion_history%
echo.
echo. MENU: 
echo. 
for /f "tokens=1,2,* delims=_ " %%A in ('"findstr /b /c:":menu_" "%~f0""') do echo.  %%B  %%C
set choice=
echo.&set /p choice=Make a choice or hit ENTER to quit: ||GOTO:EOF
echo.&call:menu_%choice%
GOTO:menuLOOP

:menu_1   Generate TSL file in XLS Mode
echo qcasTSLgenerator v%_version%
echo. 
echo Answer the following prompt, take note of spaces in file names. 
echo by surrounding full paths with double quotes "c:\like\this". 
echo. 
set /p inputfile=Enter MS Excel XLS file name: 
set /p tslfile=Enter current TSL file: 
set /p NumberofGames=Enter number of new games to be added: 
echo Running qcasTSLgenerator... Press Return to Continue...
java -jar bin\qcasTSLgeneratorv1.8.jar %inputfile% %tslfile% %NumberofGames%
goto :_EOF

:menu_2  Generate TSL file in XML/CSV Mode
color 1f
echo qcasTSLgenerator v%_version%
echo. 
echo Answer the following prompt, take note of spaces in file names. 
echo by surrounding full paths with double quotes "c:\like\this". 
echo. 
set /p inputfile=Enter XML/CSV file name: 
set /p tslfile=Enter current TSL file: 
echo Running qcasTSLgenerator... Press Return to Continue...
java -jar bin\qcasTSLgeneratorv1.8.jar %inputfile% %tslfile%
goto :_EOF

:menu_3 Concatenate individual manufacturer TSL files to Single TSL file
color 1f
echo qasTSLgenerator v%_version%
echo. 
echo Concatenates to a single TSL file. 
echo.
echo Note: Press Ctrl-C now to save individual Manufacturer TSL files. 
echo Otherwise continue. 
echo. 
set /p filename=Enter Filename to merge individual TSL files to: 
copy ARI.tsl+IGT.tsl+ARU.tsl+STA.tsl+BAL.tsl+KON.tsl+AGT.tsl+VGT.tsl %filename%
echo CLEANING UP.
del ARI.tsl
del IGT.tsl
del ARU.tsl
del STA.tsl
del BAL.tsl
del KON.tsl
del AGT.tsl
del VGT.tsl
echo DONE. TSL FILE %filename%, COMPLETED. HAVE A NICE DAY.
set _exitStatus=0
goto :_EOF

:menu_4 Delete games from TSL file using an Excel (XLS) games list
color 1f
echo qasTSLgenerator v%_version%
echo. 
echo WARNING: This process deletes games from the TSL file.
echo.
set /p filename=Enter TSL file name:
set /p excelfilename=Enter MS Excel XLS file name: 
set /p NumberofGamesDeleted=Enter number of games in %excelfilename%: 
echo.
java -jar bin\TSLgameremoverv1.0.jar %filename% %excelfilename% %NumberofGamesDeleted%
goto :_EOF

:menu_T   Tips 
echo. - Make sure to remove headers in the XLS file. 
echo. - When typing in Filenames, etc, Press "tab" - autocomplete works!
echo. - Each "." - dot, that is printed is one game that the program reads in!
GOTO:_EOF

:menu_C   Clear Screen
cls
GOTO:_EOF


:_EOF
REM echo The exit status is %_exitStatus%.
echo.
cmd /c exit %_exitStatus%