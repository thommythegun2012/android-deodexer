# Android Deodexer #
## Author ##
> Luis Peregrina

## About this program ##
> This program is a Java implementation of the Windows scripts developed by:
> abcdjdj (http://forum.xda-developers.com/member.php?u==4663391)
> Abhinav2 (http://forum.xda-developers.com/member.php?u==4766488)

> I have adapted some steps in the scripts, because they could have been avoided (like copying an apk, renaming it to zip, un zipping; my change was to just unzip an apk in a different folder).

## Requirements ##
> -Java Runtime Environment (JRE) version 7 (1.7.x)
> -Permission to create directories and files in the directory that contains the application.


## How to use ##
> Just double click the file AndroidDeodexer.jar. If it doesnt execute, open a console and in the directory type "java -jar AndroidDeodexer.jar", this should work, if it doesnt it probable that you dont have the required runtime environment.

## Original thread in XDA ##
> http://forum.xda-developers.com/showthread.php?t==2213235

## Known problems ##
> I personally had a problem while executing adb under Lubuntu, it took like 3 minutes to start, and when it did it failed. Well, it turns out the "lo" (loopback) interface was either down or firewalled. By executing "sudo iptables -I INPUT -i lo -j ACCEPT" and "sudo iptables -I OUTPUT -o lo -j ACCEPT" the adb binary was finally able to run as intended. Check https://code.google.com/p/android/issues/detail?id==3025.