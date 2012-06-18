package net.nettape.object;

import java.util.LinkedList;

import net.nettape.server.service.HandOfGod;


public class ThreadList {
	  
	  /* Here is the instance of the Singleton */
	  private static ThreadList instance_;

	  /* Prevent direct access to the constructor*/
	  private ThreadList() {
	    super();
	  }

	  public LinkedList<HandOfGod> List;

	  public synchronized static ThreadList getInstance() {

	    /* in a non-thread-safe version of a Singleton   */
	    /* the following line could be executed, and the */ 
	    /* thread could be immediately swapped out */
	    if (instance_ == null) {

	           instance_ = new ThreadList();
	           instance_.List = new LinkedList<HandOfGod>();

	    }
	    return instance_;
	  }
	}
