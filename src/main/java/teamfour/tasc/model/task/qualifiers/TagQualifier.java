//@@author A0148096W

package teamfour.tasc.model.task.qualifiers;

import java.util.Set;

import teamfour.tasc.commons.util.StringUtil;
import teamfour.tasc.model.task.ReadOnlyTask;

/**
 * This qualifier allows tasks with any tag
 * which matches the specified tags to pass.
 */
public class TagQualifier implements Qualifier {
    private Set<String> tagNames;

    public TagQualifier(Set<String> tagNames) {
        this.tagNames = tagNames;
    }

    @Override
    public boolean run(ReadOnlyTask task) {
        String taskTagsString = task.tagsString();
        taskTagsString = taskTagsString.replace("[", "");
        taskTagsString = taskTagsString.replace("]", "");
        String source = taskTagsString.replace(",", "");
        return tagNames.stream()
                .filter(tagName -> StringUtil.containsIgnoreCase(source, tagName))
                .findAny()
                .isPresent();
    }

    @Override
    public String toString() {
        return "tags=" + String.join(", ", tagNames);
    }
}