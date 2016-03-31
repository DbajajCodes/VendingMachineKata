# VendingMachineKata
Welcome to my implementation for the Vending Machine Kata, you can read about the Kata itself at

https://github.com/guyroyse/vending-machine-kata

This Kata was quite interesting in that it made me think quite a bit and get to pratice the few TDD techniques that I had learnt during my stint at AAA Life, having worked a bit with Pillar.

I tried to break down each functional requirement into a sub-module and Test drive to accomplish the given requirement.

Once all the modules were working, I needed to figure a way to hook them all together and have a legible UI in no time. Pardom my use of Java's swing API but the underlying logic behind all the calculations is purely test driven.

# runing the program
1. download the project into your workspace
2. import into your IDE as a local maven project
3. run the maven build with the target - clean install
4. go to src-->main-->java-->com.pillar.impl.VendingMachine.java OR com.pillar.impl.VendingMachineImpl.java
5. Run As - Java Application
6. BOOM !

Note - I was not entirely convinced with my NON TDD use of the UI layer via swing, hence I wrote a parallel version of the logic, purely Test Driven. It looks very basic and interacts with the user and does not check for the quality of user input, but it does not employ any code thats not test driven/covered.


# Update - 03/2016
After an honest yet awesome feedback, I figured there was a lot needed to be done before the code could be called complete - I removed the GUI layer,
converted the entire project into a simple, command prompt driven flow, added more coverage to each file/line of code I wrote.

Also, I made sure that the tests all pass out of the box :-)


