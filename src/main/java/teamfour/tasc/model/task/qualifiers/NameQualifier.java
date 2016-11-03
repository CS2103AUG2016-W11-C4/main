//@@author A0127014W

package teamfour.tasc.model.task.qualifiers;

import java.util.Set;

import teamfour.tasc.commons.util.StringUtil;
import teamfour.tasc.model.task.ReadOnlyTask;

/**
 * This qualifier allows tasks with names or tags
 * which matches the specified keywords to pass.
 */
public class NameQualifier implements Qualifier {
    private Set<String> nameKeyWords;

    public NameQualifier(Set<String> nameKeyWords) {
        this.nameKeyWords = nameKeyWords;
    }
    
    @Override
    public boolean run(ReadOnlyTask task) {
        boolean tagFound = false;
        for (String keyword : nameKeyWords) {
            tagFound = task.getTags().getInternalList().stream()
                    .filter(tag -> StringUtil.containsIgnoreCasePartial(tag.toString(), keyword)).findAny()
                    .isPresent() || tagFound;
        }
        return nameKeyWords.stream()
                .filter(keyword -> StringUtil.containsIgnoreCasePartial(task.getName().getName(), keyword))
                .findAny().isPresent() || tagFound;
    }
    
    @Override
    public String toString() {
        return "name=" + String.join(", ", nameKeyWords);
    }
}