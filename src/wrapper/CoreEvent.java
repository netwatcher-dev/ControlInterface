/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wrapper;

/**
 *
 * @author djo
 */
public interface CoreEvent 
{
    void protocoleListUpdated();
    void errorHasOccured(Exception ex);
    void autoRefresh(boolean enable);
    void coreStateRefresh();
}
