package org.eclipse.imp.ant;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

/**
 * Trivial task that sets a property from a value, subject to substitutions from a regexp matching
 * operation on a string input.<br>
 * The following task attributes are defined (all are required):<br>
 * <ul>
 *   <li><b>name</b> the name of the property which will be bound
 *   <li><b>regexp</b> the regular expression, in the standard java.util.regexp.Pattern syntax
 *   <li><b>input</b> the string to be matched against 'regexp'
 *   <li><b>value</b> the value to be assigned to the property 'name', after match substitutions.
 *   'value' may contain references to match groups, as in "\1 \2".
 * </ul>
 * @author rfuhrer@watson.ibm.com
 */
public class PropertyRegexpTask extends Task {
    private String fInput;
    private String fName;
    private String fRegexp;
    private String fValue;

    public PropertyRegexpTask() {
        setTaskName("propertyRegexp");
        setDescription("Sets the property named 'name' to 'value', which is subject to substitutions from matching 'input' against the regular expression 'regexp'.");
    }

    public void setInput(String input) {
        fInput= input;
    }

    public void setName(String name) {
        fName= name;
    }

    public void setRegexp(String regexp) {
        fRegexp= regexp;
    }

    public void setValue(String value) {
        fValue= value;
    }

    @Override
    public void execute() throws BuildException {
        Pattern pat= Pattern.compile(fRegexp);
        Matcher matcher= pat.matcher(fInput);
        String value= fValue;
        if (matcher.matches()) {
            for(int i=0; i <= matcher.groupCount(); i++) {
                value= value.replace("\\" + i, matcher.group(i));
            }
        }
        getProject().setProperty(fName, value);
    }
}
