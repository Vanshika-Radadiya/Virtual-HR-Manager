package com.vanshika.springboot.service;

import java.util.*;

import com.vanshika.springboot.model.Question;

public class QueData {
	
	public static Map<String, Integer> categories = Map.of("oops",1,"os",2,"dbms",3);
	public static Map<Integer, String> revCategories = Map.of(1,"oops",2,"os",3,"dbms");

	
	public static List<Map<String, Question>> oops = List.of(
		    // -------------------------------------------------------------
		    // LIST INDEX 0: MAP OF 3 EASY QUESTIONS
		    // -------------------------------------------------------------
		    Map.of(
		        "Q1", new Question("What is OOps", Map.of(
		            "Encapsulation", 1, "Inheritance", 1, "Polymorphism", 1
		        )),
		        "Q2", new Question("What is a Class and an Object?", Map.of(
		            "Blueprint", 1, "Instance", 1, "State", 1
		        )),
		        "Q3", new Question("What is the purpose of a Constructor?", Map.of(
		            "Initialize", 1, "Object Creation", 1, "Same Name", 1
		        ))
		    ),

		    // -------------------------------------------------------------
		    // LIST INDEX 1: MAP OF 3 MEDIUM QUESTIONS
		    // -------------------------------------------------------------
		    Map.of(
		        "Q4", new Question("Explain Polymorphism and its types.", Map.of(
		            "Overloading", 1, "Overriding", 1, "Runtime", 1
		        )),
		        "Q5", new Question("What is the difference between an Abstract Class and an Interface?", Map.of(
		            "Multiple Inheritance", 1, "Abstract Methods", 1, "Constructor", 1
		        )),
		        "Q6", new Question("How do you achieve Encapsulation and data hiding?", Map.of(
		            "Private Variables", 1, "Getters", 1, "Setters", 1
		        ))
		    ),

		    // -------------------------------------------------------------
		    // LIST INDEX 2: MAP OF 3 HARD QUESTIONS
		    // -------------------------------------------------------------
		    Map.of(
		        "Q7", new Question("Explain Diamond Problem in inheritance and how to resolve it.", Map.of(
		            "Multiple Inheritance", 1, "Ambiguity", 1, "Interfaces", 1
		        )),
		        "Q8", new Question("What is the difference between Composition and Inheritance?", Map.of(
		            "Has-A Relationship", 1, "Is-A Relationship", 1, "Loose Coupling", 1
		        )),
		        "Q9", new Question("What is runtime polymorphism and how does Virtual Method Invocation work?", Map.of(
		            "Dynamic Binding", 1, "Virtual Table", 1, "Overriding", 1
		        ))
		    )
		);
	
	
	public static List<Map<String, Question>> dbms = List.of(
		    // -------------------------------------------------------------
		    // LIST INDEX 0: MAP OF 3 EASY QUESTIONS
		    // -------------------------------------------------------------
		    Map.of(
		        "Q1", new Question("What is a DBMS and what are its main advantages?", Map.of(
		            "Data Redundancy", 1, "Data Centralization", 1, "Security", 1
		        )),
		        "Q2", new Question("Explain the primary key, foreign key, and unique key constraints.", Map.of(
		            "Unique Identifier", 1, "Referential Integrity", 1, "No Nulls", 1
		        )),
		        "Q3", new Question("What is the difference between DELETE, TRUNCATE, and DROP commands?", Map.of(
		            "DML", 1, "DDL", 1, "Rollback", 1
		        ))
		    ),

		    // -------------------------------------------------------------
		    // LIST INDEX 1: MAP OF 3 MEDIUM QUESTIONS
		    // -------------------------------------------------------------
		    Map.of(
		        "Q4", new Question("Explain ACID properties in a database transaction.", Map.of(
		            "Atomicity", 1, "Consistency", 1, "Isolation", 1
		        )),
		        "Q5", new Question("What is Database Normalization and why do we need 1NF, 2NF, and 3NF?", Map.of(
		            "Anomalies", 1, "Redundancy", 1, "Dependency", 1
		        )),
		        "Q6", new Question("What are Joins in SQL and explain Inner, Left, and Right Joins.", Map.of(
		            "Combine Rows", 1, "Matching Values", 1, "Null Values", 1
		        ))
		    ),

		    // -------------------------------------------------------------
		    // LIST INDEX 2: MAP OF 3 HARD QUESTIONS
		    // -------------------------------------------------------------
		    Map.of(
		        "Q7", new Question("What is the difference between Conflict Serializability and View Serializability?", Map.of(
		            "Schedule Execution", 1, "Precedence Graph", 1, "Blind Writes", 1
		        )),
		        "Q8", new Question("How do Two-Phase Locking (2PL) and Strict 2PL prevent concurrency anomalies?", Map.of(
		            "Growing Phase", 1, "Shrinking Phase", 1, "Cascading Rollback", 1
		        )),
		        "Q9", new Question("Explain Indexing and how B-Trees/B+ Trees optimize search queries.", Map.of(
		            "Binary Search", 1, "Disk I/O", 1, "Leaf Nodes", 1
		        ))
		    )
		);
		
	
	public static List<Map<String, Question>> os = List.of(
		    // -------------------------------------------------------------
		    // LIST INDEX 0: MAP OF 3 EASY QUESTIONS
		    // -------------------------------------------------------------
		    Map.of(
		        "Q1", new Question("What is an Operating System and its main purpose?", Map.of(
		            "Interface", 1, "Hardware", 1, "Resource Management", 1
		        )),
		        "Q2", new Question("What is the difference between a Process and a Thread?", Map.of(
		            "Program in Execution", 1, "Lightweight Process", 1, "Shared Memory", 1
		        )),
		        "Q3", new Question("What is Virtual Memory?", Map.of(
		            "RAM Extension", 1, "Hard Disk Space", 1, "Illusion", 1
		        ))
		    ),

		    // -------------------------------------------------------------
		    // LIST INDEX 1: MAP OF 3 MEDIUM QUESTIONS
		    // -------------------------------------------------------------
		    Map.of(
		        "Q4", new Question("What is Deadlock and what are its four necessary conditions?", Map.of(
		            "Mutual Exclusion", 1, "Hold and Wait", 1, "Circular Wait", 1
		        )),
		        "Q5", new Question("Explain the difference between Paging and Segmentation.", Map.of(
		            "Fixed Size", 1, "Variable Size", 1, "Fragmentation", 1
		        )),
		        "Q6", new Question("What is Context Switching and why is it expensive?", Map.of(
		            "Save State", 1, "Load State", 1, "Overhead", 1
		        ))
		    ),

		    // -------------------------------------------------------------
		    // LIST INDEX 2: MAP OF 3 HARD QUESTIONS
		    // -------------------------------------------------------------
		    Map.of(
		        "Q7", new Question("Explain the Thrashing phenomenon in memory management and how to prevent it.", Map.of(
		            "Page Faults", 1, "Swapping", 1, "Working Set Model", 1
		        )),
		        "Q8", new Question("How does the Banker's Algorithm help in Deadlock avoidance?", Map.of(
		            "Safe State", 1, "Resource Allocation", 1, "Avoidance", 1
		        )),
		        "Q9", new Question("What is a Semaphore and how does it solve the Producer-Consumer problem?", Map.of(
		            "Critical Section", 1, "Mutex", 1, "Synchronization", 1
		        ))
		    )
		);

	
	

}
;