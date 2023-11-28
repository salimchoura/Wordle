package model.interfaces;

import java.util.ArrayList;

/**
 * 
 * @author UBIS
 * This class is an observable class to notify observers of any state change in the
 * the observable. The observable class must extend this class MyObserverable.
 *
 */
public class MyObservable 
{
	private ArrayList<MyObserver> observers = new ArrayList<>();
	
	/**
	 * @param anObserver: the observer to add
	 */
	public void addObserver(MyObserver anObserver) 
	{
		observers.add(anObserver);
	}
	
	/**
	 * @param theObservable: the observable that notifies the observers
	 */
	public void notifyObservers(MyObservable theObservable) 
	{
		for (MyObserver obs : observers) 
		{
			obs.update(theObservable);
		}
	}
}