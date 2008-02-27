package org.eclipse.imp.ant;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.TaskContainer;

public class ForTask extends Task implements TaskContainer {
	private List fTasks= new ArrayList();
	private List fValues= new ArrayList();
	private String fParamName;

	public void setParam(String name) {
		fParamName= name;
	}

	public void setValues(String valueStr) {
		String[] values= valueStr.split(",");
		for(int i=0; i < values.length; i++) {
			fValues.add(values[i]);
		}
	}

	public void addTask(Task task) {
		fTasks.add(task);
	}

	public void execute() throws BuildException {
		for(Iterator valueIter= fValues.iterator(); valueIter.hasNext(); ) {
			String value= (String) valueIter.next();
			getProject().setProperty(fParamName, value);

			for (Iterator taskIter= fTasks.iterator(); taskIter.hasNext(); ) {
				Task task= (Task) taskIter.next();
				task.perform();
			}
		}
	}
}
