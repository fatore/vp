/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizationbasics.coordination;

import visualizationbasics.model.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Fernando Vieira Paulovich
 */
public abstract class AbstractCoordinator {

    public AbstractCoordinator() {
        models = new ArrayList<AbstractModel>();
    }

    public abstract void coordinate(List<AbstractInstance> selectedInstances, Object parameter);

    public synchronized void addModel(AbstractModel model) {
        if (model == null) {
            throw new NullPointerException();
        }

        if (!models.contains(model)) {
            models.add(model);
        }
    }

    public synchronized void deleteModel(AbstractModel model) {
        models.remove(model);
    }
    protected List<AbstractModel> models;
}
