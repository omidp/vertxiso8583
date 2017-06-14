## vertx8583

This project is using JPOS and Vertx to parse iso 85383 message over tcp network

##  Step to use it

* mvn clean install
* import into IDE
* run main class
* use telnet or netcat to send a message
* the result should be as follow

```

----ISO MESSAGE-----
  MTI : 0200
    Field-3 : 201234
    Field-4 : 10000
    Field-7 : 110722180
    Field-11 : 123456
    Field-41 : 999991
    Field-44 : A5DFGR
    Field-105 : ABCDEFGHIJ 1234567890
--------------------
0200B220000000900000000000000080000020123400000001000001107221801234560699999106A5DFGR021ABCDEFGHIJ 1234567890
Server is now listening!
I received some bytes: 112
----ISO MESSAGE-----
  MTI : 0200
    Field-3 : 201234
    Field-4 : 000000010000
    Field-7 : 0110722180
    Field-11 : 123456
    Field-41 : 999991
    Field-44 : A5DFGR
    Field-105 : ABCDEFGHIJ 1234567890
--------------------
terminal code : 999991

```
