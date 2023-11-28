package model.interfaces;

/**
 * @author UBIS
 * This interface must be implemented by any observer that wants
 * to be notified of ant change to the state of the observable.
 */
public interface MyObserver 
{
	/**
	 * @param observable: the observable that notifies the observer.
	 */
	public void update(MyObservable observable);
}
