# Java_Graph_Maker

A Java program that allows a user to create and run operations on a (weighted, directed) graph using a variety of data structures.

To run, open file location in a command interface and input "java -jar GraphMaker.jar <implementation>"
 where <implementation> is either "adjList",  "asocIndMat", "frndIndMat", or "adjMat".
 
 "adjList" is a multigraph implementation using an adjacency list.
 "asocIndMat" is a multigraph implementation using an incidence matrix.
 "frndIndMat" is a graph implementation using an incidence matrix.
 "adjMat" is a graph implementation using an adjacency matrix.
 
Be warned that as this project was developed to evaluate and compare the complexity of various data structures, the incidence matrix implementations perform poorly when building graphs of high density.

This project was collated from work done on several assignments completed for  course COSC 2123/1285 (Algorithms & Analysis) at RMIT University.

Created by Carl Dietz, 2017
Assistance and advice provided by Dr. Jeffery Chan of RMIT university and fellow student Joshua Ooi.
