##DA-ex2##
***Warning***
 -already missed the deadline
 -prior
*Assignment-Description:*
    Implement Suzuki’s and Kasami’s algorithm for mutual exclusion in a distributed system with Java/RMI.
    Part 1:
 - Write the remote interface and the global framework of the Component class implementing the components of the distributed algorithm.
 - Create the framework for the Main class that will create the Component objects and their threads on a single host.
 - It must be possible to specify the number of these components. Include into Main and Component the functionality of registering and looking up components.
 Part 2
 - Include into Component the functionality for broadcasting and receiving messages for requesting
 the critical section.
 - Include random delays in the critical sections and between finishing one execution of the 
 critical section and requesting access again.
 Part 3
 - Include into Component the functionality for sending and receiving the token. It can be assumed that a single designated process initially contains the token.
 - Make sure that the output of the algorithm makes it possible to check its correct operation.
#Material
 - https://www.youtube.com/watch?v=aWne_qIR2XI
 - BrightSpace/Course Information/Course Description.pdf pg. 66
 - BrightSpace/Lectures/Chapter4/01-IN4150-mutual-exclusion-2019-2020.ppt
